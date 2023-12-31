package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ISBNCheckerService;
import ar.edu.itba.paw.interfaces.LibraryAPIService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
public class ISBNCheckerServiceImpl implements ISBNCheckerService {

    private final LibraryAPIService libraryAPIService;
    private final AssetDao assetDao;

    @Autowired
    public ISBNCheckerServiceImpl(final LibraryAPIService libraryAPIService, final AssetDao assetDao) {
        this.libraryAPIService = libraryAPIService;
        this.assetDao = assetDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookImpl> getBookIfExistsByISBN(final String isbn) {
        Optional<BookImpl> bookOpt = this.assetDao.getBook(isbn);
        if (bookOpt.isPresent()) {
            return bookOpt;
        }
        try {
            BookImpl book = this.libraryAPIService.getBookByISBN(isbn);
            return Optional.of(book);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
