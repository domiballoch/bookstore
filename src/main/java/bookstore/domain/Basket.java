package bookstore.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"books"})
@Component
public class Basket {

    private List<Book> books = new ArrayList<>();

    private int quantity; //TODO:Not implemented

    private BigDecimal totalPrice;

//    /**
//     * So not to allow books to be added via a getter method
//     * Which violate the encapsulation principle
//     *
//     * @return
//     */
//    public List<Book> getBooks(){
//        return Collections.unmodifiableList(books);
//    }

    /**
     * Separate method to add books
     *
     * @param book
     */
    public void addBook(final Book book){
        books.add(book);
    }
}
