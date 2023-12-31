package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
final public class AssetExistanceServiceImpl implements AssetExistanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    private final AssetDao bookDao;

    private final UserDao userDao;

    private final AssetInstanceDao assetInstanceDao;

    private final LocationDao locationDao;

    private final ImagesDao photosDao;

    @Autowired
    public AssetExistanceServiceImpl(AssetDao bookDao, UserDao userDao, AssetInstanceDao assetInstanceDao, LocationDao locationDao, ImagesDao photosDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.assetInstanceDao = assetInstanceDao;
        this.locationDao = locationDao;
        this.photosDao = photosDao;
    }

    @Override
    @Transactional
    public AssetInstanceImpl addAssetInstance(AssetInstanceImpl assetInstance, byte[] photo) throws InternalErrorException {

        Optional<BookImpl> bookOptional = bookDao.getBook(assetInstance.getBook().getIsbn());
        BookImpl book;
        if (!bookOptional.isPresent()) {
            try {
                book = bookDao.addAsset(assetInstance.getBook());
            } catch (BookAlreadyExistException exception) {
                LOGGER.error("Failed to add  a new book");
                throw new InternalErrorException("Internal error");
            }
        } else {
            book = bookOptional.get();
        }
        //here probably the problem with the transactional and adding a location that references
        //the user.
        Optional<UserImpl> user = userDao.getUser(assetInstance.getOwner().getEmail());
        LocationImpl location = locationDao.addLocation(assetInstance.getLocation());
        ImageImpl image = photosDao.addPhoto(photo);
        assetInstance.setBook(book);
        assetInstance.setLocation(location);
        assetInstance.setImage(image);
        assetInstance.setUserReference(user.get());

        if (user.isPresent() ) {
            LOGGER.info("Add a new asset intance");
            return assetInstanceDao.addAssetInstance(assetInstance);
        } else {
            LOGGER.error("Failed to add  a new book instance");
            throw new InternalErrorException("Cannot addAssetInstance");
        }
    }
}
