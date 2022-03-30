package bookstore.dto;

import bookstore.domain.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDto(final Book book) {

        final Book mappedBook = new Book();
        mappedBook.setIsbn(book.getIsbn());
        mappedBook.setTitle(book.getTitle());
        mappedBook.setAuthor(book.getAuthor());
        mappedBook.setCategory(book.getCategory());
        mappedBook.setPrice(book.getPrice());
        mappedBook.setStock(book.getStock());

        return new BookDTO(mappedBook);
    }
}
