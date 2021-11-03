package bookstore.service;

import bookstore.domain.Book;

public interface AdminService {

    Book addNewBookToBookstoreJson(final Book book);

    Book addNewBookToBookStoreWeb(final Book book);

    void deleteSingleBookFromBookstoreByIsbn(final long isbn);

	Book updateBookInBookstoreWeb(final Book book);

    Book updateBookInBookstoreJson(final Book book);

}
