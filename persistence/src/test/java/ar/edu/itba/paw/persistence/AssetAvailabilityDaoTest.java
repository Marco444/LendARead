package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.itba.edu.paw.persistenceinterfaces.AssetAvailabilityDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class AssetAvailabilityDaoTest {

    @Autowired
    private DataSource ds;
    @Autowired
    private AssetAvailabilityDao assetAvailabilityDao;

    @PersistenceContext
    private EntityManager em;
    private final static UserImpl USER = new UserImpl(0,"EMAIL", "NAME", "TELEPHONE", "PASSWORD_NOT_ENCODED", Behaviour.BORROWER);
    private final static BookImpl BOOK = new BookImpl(0, "ISBN", "AUTHOR", "TITLE", "LANGUAGE");
    private final static LocationImpl LOCATION = new LocationImpl(0, "LOCATION","ZIPCODE", "LOCALITY", "PROVINCE", "COUNTRY",USER);
    private final static AssetInstanceImpl ASSET_INSTANCE = new AssetInstanceImpl(0,BOOK, PhysicalCondition.ASNEW, USER, LOCATION, null, AssetState.PUBLIC, 7, "DESCRIPTION");
    private final static LocalDate borrowDate = LocalDate.now();
    private final static LocalDate devolutionDate = LocalDate.now().plusDays(7);
    private JdbcTemplate jdbcTemplate;

    @Rollback
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }


    @Test
    public void testBorrowAssetInstance() {

        LendingState lendingState = LendingState.ACTIVE;

        LendingImpl result = assetAvailabilityDao.borrowAssetInstance(ASSET_INSTANCE, USER, borrowDate, devolutionDate, lendingState);

        Assert.assertNotNull(result);

        em.flush();
        em.clear();

        LendingImpl retrievedLending = em.find(LendingImpl.class, result.getId());
        Assert.assertNotNull(retrievedLending);

        Assert.assertEquals(ASSET_INSTANCE.getId(), retrievedLending.getAssetInstance().getId());
        Assert.assertEquals(USER, retrievedLending.getUserReference());
        Assert.assertEquals(borrowDate, retrievedLending.getLendDate());
        Assert.assertEquals(devolutionDate, retrievedLending.getDevolutionDate());
        Assert.assertEquals(lendingState, retrievedLending.getActive());
    }
    @Test
    public void testGetActiveLendings() {


        LendingImpl activeLending1 = new LendingImpl(ASSET_INSTANCE, USER, borrowDate, devolutionDate, LendingState.ACTIVE);
        LendingImpl activeLending2 = new LendingImpl(ASSET_INSTANCE, USER, borrowDate, devolutionDate, LendingState.ACTIVE);
        LendingImpl finishedLending = new LendingImpl(ASSET_INSTANCE, USER, borrowDate, devolutionDate, LendingState.FINISHED);

        em.persist(activeLending1);
        em.persist(activeLending2);
        em.persist(finishedLending);

        List<LendingImpl> activeLendings = assetAvailabilityDao.getActiveLendings(ASSET_INSTANCE);

        Assert.assertEquals(3, activeLendings.size());
        Assert.assertTrue(activeLendings.contains(activeLending1));
        Assert.assertTrue(activeLendings.contains(activeLending2));
        Assert.assertFalse(activeLendings.contains(finishedLending));
    }
    @Test
    public void testChangeLendingStatus() {
        LendingImpl lending = new LendingImpl(ASSET_INSTANCE, USER, borrowDate, devolutionDate, LendingState.ACTIVE);

        em.persist(lending);

        LendingState newLendingState = LendingState.FINISHED;
        assetAvailabilityDao.changeLendingStatus(lending, newLendingState);

        em.flush();
        em.clear();

        LendingImpl retrievedLending = em.find(LendingImpl.class, lending.getId());
        Assert.assertNotNull(retrievedLending);
        Assert.assertEquals(newLendingState, retrievedLending.getActive());
    }

}
