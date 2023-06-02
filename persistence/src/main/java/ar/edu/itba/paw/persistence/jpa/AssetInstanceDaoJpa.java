package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PageImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.Sort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AssetInstanceDaoJpa implements AssetInstanceDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public AssetInstanceImpl addAssetInstance(AssetInstanceImpl ai) {
        em.persist(ai);
        return ai;
    }

    @Override
    public Optional<AssetInstanceImpl> getAssetInstance(int assetId) {
        String queryString = "FROM AssetInstanceImpl as ai WHERE ai.id = :id";
        TypedQuery<AssetInstanceImpl> query = em.createQuery(queryString, AssetInstanceImpl.class);
        query.setParameter("id", (long) assetId);
        List<AssetInstanceImpl> list = query.getResultList();
        return list.stream().findFirst();
    }

    @Override
    public void changeStatus(AssetInstanceImpl assetInstance, AssetState as) {
        assetInstance.setAssetState(as);
        em.persist(assetInstance);
    }

    @Override
    public void changeStatusByLendingId(AssetInstanceImpl ai, AssetState as) {
        ai.setAssetState(as);
        em.persist(ai);
    }

    @Override
    public Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery) {

        // Base query for getting the assets IDs for a given page
        StringBuilder queryNativeString = new StringBuilder("SELECT ai.id FROM AssetInstance ai JOIN Book b on ai.assetid = b.uid WHERE ai.status = :state");
        // Base query for getting the total amount of assetsInstances
        StringBuilder queryCountString = new StringBuilder("SELECT COUNT(ai.id) FROM AssetInstance ai JOIN Book b on ai.assetid = b.uid WHERE ai.status = :state");

        // Query filters based on the search filters
        StringBuilder queryFilters = new StringBuilder();
        StringBuilder queryFiltersORM = new StringBuilder();

        // Order by (Postgres and ORM)
        String orderByPostgres = " ORDER BY " +
                getPostgresFromSort(searchQuery.getSort()) +
                " " +
                getPostgresFromSortDirection(searchQuery.getSortDirection()) + " ";
        String orderByORM = " ORDER BY " +
                getOrmFromSort(searchQuery.getSort()) +
                " " +
                getOrmFromSortDirection(searchQuery.getSortDirection()) + " ";

        // Pagination
        final int offset = (pageNum - 1) * itemsPerPage;
        final int limit = itemsPerPage;
        String pagination = " LIMIT :limit OFFSET :offset ";

        // If there's a search parameter
        if(!searchQuery.getSearch().equals("")) {
            queryFilters.append(" AND ( b.title ILIKE CONCAT('%', :search, '%') OR b.author ILIKE CONCAT('%', :search, '%') ) ");
            queryFiltersORM.append(" AND ( UPPER(ai.book.title) LIKE CONCAT('%', :search, '%') OR UPPER(ai.book.author) LIKE CONCAT('%', :search, '%') ) ");
        }

        // If the search is filtered bt languages
        if(!searchQuery.getLanguages().isEmpty()){
            queryFilters.append(" AND b.lang IN (:languages) ");
            queryFiltersORM.append(" AND ai.book.language IN (:languages) ");
        }

        // If the search is filtered by physicalConditions
        if(!searchQuery.getPhysicalConditions().isEmpty()){
            queryFilters.append(" AND ai.physicalCondition IN (:physicalConditions) ");
            queryFiltersORM.append(" AND ai.physicalCondition IN (:physicalConditions) ");
        }

        // Append the filters
        queryCountString.append(queryFilters);
        queryNativeString.append(queryFilters);

        // Order by and pagination for the native queries
        queryNativeString.append(orderByPostgres);
        queryNativeString.append(pagination);

        // Get IDs for given page
        final Query queryNative = em.createNativeQuery(queryNativeString.toString());

        // Count pages
        final Query queryCount = em.createNativeQuery(queryCountString.toString());

        final String search = searchQuery.getSearch().toUpperCase().replace("%", "\\%");
        if(!searchQuery.getSearch().equals("")) {
            queryNative.setParameter("search", search);
            queryCount.setParameter("search", search);
        }

        // If the search is filtered bt languages
        if(!searchQuery.getLanguages().isEmpty()){
            queryNative.setParameter("languages", searchQuery.getLanguages());
            queryCount.setParameter("languages", searchQuery.getLanguages());
        }

        // If the search is filtered by physicalConditions
        if(!searchQuery.getPhysicalConditions().isEmpty()){
            queryNative.setParameter("physicalConditions", searchQuery.getPhysicalConditions());
            queryCount.setParameter("physicalConditions", searchQuery.getPhysicalConditions());
        }

        queryNative.setParameter("state", "PUBLIC");
        queryCount.setParameter("state", "PUBLIC");

        queryNative.setParameter("limit", limit);
        queryNative.setParameter("offset", offset);

        // Get the total assetInstances and calculate the number of pages
        final int totalPages = (int) Math.ceil((double) ((Number) queryCount.getSingleResult()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());

        // In case of empty result -> Return a Page with empty lists
        if(list.isEmpty())
            return Optional.of(new PageImpl(new ArrayList<>(), 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        // Get the AssetInstances that match those IDs for given page
        final TypedQuery<AssetInstanceImpl> query = em.createQuery("FROM AssetInstanceImpl AS ai WHERE id IN (:ids) " + orderByORM, AssetInstanceImpl.class);
        query.setParameter("ids", list);
        List<AssetInstanceImpl> assetInstances = query.getResultList();

        return Optional.of(new PageImpl(assetInstances, pageNum, totalPages, new ArrayList<>(), getLanguages(searchQuery, queryFiltersORM.toString()), getPhysicalConditions(searchQuery, queryFiltersORM.toString())));
    }

    private List<String> getLanguages(SearchQuery searchQuery, String queryFilters){
        String queryString = "SELECT DISTINCT ai.book.language FROM AssetInstanceImpl AS ai WHERE ai.assetState = :state " + queryFilters;

        TypedQuery<String> query = em.createQuery(queryString, String.class);

        return getResults(query, searchQuery, queryString);
    }

    private List<String> getPhysicalConditions(SearchQuery searchQuery, String queryFilters){
        String queryString = "SELECT DISTINCT ai.physicalCondition FROM AssetInstanceImpl AS ai WHERE ai.assetState = :state " + queryFilters;

        TypedQuery<PhysicalCondition> query = em.createQuery(queryString, PhysicalCondition.class);

        return getResults(query, searchQuery, queryString).stream().map(Enum::name).collect(Collectors.toList());
    }

    private <T> List<T> getResults(TypedQuery<T> query, SearchQuery searchQuery, String queryString){
        query.setParameter("state", AssetState.PUBLIC);

        final String search = searchQuery.getSearch().toUpperCase().replace("%", "\\%");
        if(!searchQuery.getSearch().equals(""))
            query.setParameter("search", search);

        if(!searchQuery.getLanguages().isEmpty())
            query.setParameter("languages", searchQuery.getLanguages());

        if(!searchQuery.getPhysicalConditions().isEmpty())
            query.setParameter("physicalConditions", getPhysicalConditionsList(searchQuery.getPhysicalConditions()));

        return query.getResultList();
    }

    private List<PhysicalCondition> getPhysicalConditionsList(List<String> list){
        return list.stream().map(PhysicalCondition::fromString).collect(Collectors.toList());
    }

    private String getPostgresFromSort(Sort sort) {

        switch (sort) {
            case TITLE_NAME:
                return "b.title";
            case AUTHOR_NAME:
                return "b.author";
            case RECENT:
                return "ai.id";
        }
        return "ai.id";
    }

    private String getOrmFromSort(Sort sort) {

        switch (sort) {
            case TITLE_NAME:
                return "ai.book.title";
            case AUTHOR_NAME:
                return "ai.book.author";
            case RECENT:
                return "ai.id";
        }
        return "ai.id";
    }

    private String getPostgresFromSortDirection(SortDirection sortDirection) {
        switch (sortDirection) {
            case ASCENDING:
                return "ASC";
            case DESCENDING:
                return "DESC";
        }
        return "ASC";
    }

    private String getOrmFromSortDirection(SortDirection sortDirection) {
        return getPostgresFromSortDirection(sortDirection);
    }

}