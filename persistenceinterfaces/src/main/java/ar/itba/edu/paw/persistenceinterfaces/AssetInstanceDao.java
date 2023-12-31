package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.Optional;


public interface AssetInstanceDao {
     AssetInstanceImpl addAssetInstance(final AssetInstanceImpl ai);
     Optional<AssetInstanceImpl> getAssetInstance(final int assetId);

    void changeStatus(final AssetInstanceImpl ai,final AssetState as);
    void setReservability(final AssetInstanceImpl ai, final boolean value);

    void changeStatusByLendingId(AssetInstanceImpl ai, final AssetState as);

    void changePhysicalCondition(final AssetInstanceImpl ai, final PhysicalCondition physicalCondition);
    void changeLocation(final AssetInstanceImpl ai,final LocationImpl location);
    void changeMaxLendingDays(final AssetInstanceImpl ai, final int maxLendingDays);
    void changeImage(final AssetInstanceImpl ai,final ImageImpl image);
    Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery);
}
