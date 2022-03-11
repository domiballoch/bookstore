package bookstore.utils;

import bookstore.domain.Book;
import bookstore.domain.Category;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TestDataUtils {

    public static final String TITLE = "title1";
    public static final String AUTHOR ="author1";
    public static final List<Book> BOOKLIST = new ArrayList<>();

    public static final Book CREATE_ONE_BOOK = Book.builder()
            .isbn(5L)
            .category(Category.COOKING)
            .title("title5")
            .author("author5")
            .price(BigDecimal.valueOf(49.99))
            .stock(10)
            .build();

    public static final Book CREATE_ANOTHER_BOOK = Book.builder()
            .isbn(6L)
            .category(Category.HORROR)
            .title("title6")
            .author("author6")
            .price(BigDecimal.valueOf(24.49))
            .stock(10)
            .build();

    public static final Book CREATE_YET_ANOTHER_BOOK = Book.builder()
            .isbn(7L)
            .category(Category.ROMANCE)
            .title("title7")
            .author("author7")
            .price(BigDecimal.valueOf(14.19))
            .stock(10)
            .build();

    public static void filterBookListByTitleAndAuthor(String title, String author) {
        long inStock = BOOKLIST.stream()
                .filter(book ->
                        book.getTitle() == title && book.getAuthor() == author).count();
        //boolean inStock = (booklist > 0) ? true : false;
    }

    public static Book returnOneBook() {
        return Book.builder()
                .isbn(4L)
                .category(Category.SCIENCE_FICTION)
                .title("title4")
                .author("author4")
                .price(BigDecimal.valueOf(39.99))
                .stock(10)
                .build();
    }

    public static void returnBookList() {
        Book book1 = Book.builder()
                .isbn(1L)
                .category(Category.SCIENCE_FICTION)
                .title("title1")
                .author("author1")
                .price(BigDecimal.valueOf(9.99))
                .stock(10)
                .build();

        Book book2 = Book.builder()
                .isbn(2L)
                .category(Category.COOKING)
                .title("title2")
                .author("author2")
                .price(BigDecimal.valueOf(19.99))
                .stock(10)
                .build();

        Book book3 = Book.builder()
                .isbn(3L)
                .category(Category.KIDS)
                .title("title3")
                .author("author3")
                .price(BigDecimal.valueOf(29.99))
                .stock(10)
                .build();

        BOOKLIST.addAll(Arrays.asList(book1, book2, book3));
    }

    public List<Book> getSpecificBookFromBookList_IfMatchByName(final String param) {
        //initialise
        returnBookList();
        final String name = "^(" + param + ").*"; //regex

       return BOOKLIST.stream().filter(book -> book.getTitle()
                               .matches(name))
                               .collect(Collectors.toList());
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
