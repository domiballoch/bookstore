package bookstore.utils;

import bookstore.domain.Book;
import bookstore.domain.Category;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class TestDataUtils {

    public static final String TITLE = "title1";
    public static final String AUTHOR ="author1";
    public static final List<Book> BOOKLIST = new ArrayList<>();
    //public static final Optional<List<Book>> RETURN_BOOKS_BY_TITLE_AND_AUTHOR;
    public static final Book CREATE_ONE_BOOK = Book.builder()
            .isbn(5)
            .category(Category.COOKING)
            .title("title5")
            .author("author5")
            .price(BigDecimal.valueOf(49.99))
            .build();

    //private Map<Category, List<Book>> booksByCategory = new HashMap<>();

    public static void filterBookListByTitleAndAuthor(String title, String author) {
        long inStock = BOOKLIST.stream()
                .filter(book ->
                        book.getTitle() == title && book.getAuthor() == author).count();
        //boolean inStock = (booklist > 0) ? true : false;
    }

    public static Book returnOneBook() {
        return Book.builder()
                .isbn(4)
                .category(Category.SCIENCE_FICTION)
                .title("title4")
                .author("author4")
                .price(BigDecimal.valueOf(39.99))
                .build();
    }

    public static void returnBookList() {
        Book book1 = Book.builder()
                .isbn(1)
                .category(Category.SCIENCE_FICTION)
                .title("title1")
                .author("author1")
                .price(BigDecimal.valueOf(9.99))
                .build();

        Book book2 = Book.builder()
                .isbn(2)
                .category(Category.COOKING)
                .title("title2")
                .author("author2")
                .price(BigDecimal.valueOf(19.99))
                .build();

        Book book3 = Book.builder()
                .isbn(3)
                .category(Category.KIDS)
                .title("title3")
                .author("author3")
                .price(BigDecimal.valueOf(29.99))
                .build();

        BOOKLIST.addAll(Arrays.asList(book1, book2, book3));
    }

//        book.getBookList().clear();
//        basket.getBasketList().clear();


//    public static void prepareUserData(){
//        User user = new User();
//        user.getUserList().clear();
//        new User(1,
//                "Francis",
//                "Baker",
//                "10",
//                "Park Lane",
//                "London",
//                "Essex",
//                "SW1",
//                "07432931421");
//        new User(2,
//                "Emily",
//                "Porter",
//                "285",
//                "Mayflower Street",
//                "London",
//                "Essex",
//                "SW2",
//                "07325857480");
//    }

//    public static void prepareAccountData(){
//        new Account(1,
//                "Francis@google.com",
//                "password1");
//        new Account(2,
//                "Emily@google.com",
//                "password2");
//    }

}
