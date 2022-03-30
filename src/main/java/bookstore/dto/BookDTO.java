package bookstore.dto;

import bookstore.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BookDTO {

    private Book book;
}
