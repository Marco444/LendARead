package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAssetInstanceService.class);
    private final UserAssetsDao userAssetsDao;


    @Autowired
    public UserAssetInstanceServiceImpl(final UserAssetsDao userAssetsDao) {
        this.userAssetsDao = userAssetsDao;
    }

    @Transactional(readOnly = true)
    @Override
    public PageUserAssets getUserAssetsOfTable(final int pageNumber, final int itemsPerPage, final String email, final String tableSelected, final String filterAtribuite, final String filterValue, final String sortAtribuite, final String direction) {
        switch (tableSelected) {
            case "my_books":
                return userAssetsDao.getUsersAssets(pageNumber, itemsPerPage, email, filterAtribuite, filterValue, sortAtribuite, direction);
            case "borrowed_books":
                return userAssetsDao.getBorrowedAssets(pageNumber, itemsPerPage,  email, filterAtribuite, filterValue, sortAtribuite, direction);
            case "lended_books":
                return userAssetsDao.getLendedAssets(pageNumber, itemsPerPage, email, filterAtribuite, filterValue, sortAtribuite, direction);
        }

        return new PagingImpl(new ArrayList<>());
    }

    @Transactional(readOnly = true)
    @Override
    public LendingImpl getBorrowedAssetInstance(final int lendingId) throws AssetInstanceNotFoundException {
      Optional<LendingImpl> lending = userAssetsDao.getBorrowedAsset(lendingId);
      if (!lending.isPresent()) {
          LOGGER.error("Not found borrowed asset instance with the lending lendingId: {}", lendingId);
          throw new AssetInstanceNotFoundException("Not found BorrowedAssetInstance");
      }
      return lending.get();
    }

}
