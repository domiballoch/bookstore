package bookstore.utils;

import bookstore.domain.Basket;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.domain.OrderDetails;
import bookstore.domain.Users;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TestDataUtils {

    //----- Book data -----//

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

    public static final List<Book> BOOKLIST = new ArrayList<>();

    public static void populateBookList() {
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

        BOOKLIST.addAll(List.of(book1, book2, book3));
    }

    public static void filterBookListByTitleAndAuthor(String title, String author) {
        long inStock = BOOKLIST.stream()
                .filter(book ->
                        book.getTitle() == title && book.getAuthor() == author).count();
    }

    public List<Book> getSpecificBookFromBookList_IfMatchByName(final String param) {
        //initialise
        populateBookList();
        final String name = "^(" + param + ").*"; //regex

       return BOOKLIST.stream().filter(book -> book.getTitle()
                               .matches(name))
                               .collect(Collectors.toList());
    }

    //----- User data -----//

    public static final Users CREATE_ONE_USER = Users.builder()
            .userId(1L)
            .firstName("Bob")
            .lastName("Jones")
            .addressLine1("99 Orange Grove")
            .addressLine2("London")
            .postCode("SW4").build();

    public static final Users CREATE_ANOTHER_USER = Users.builder()
            .userId(2L)
            .firstName("Harry")
            .lastName("Peters")
            .addressLine1("77 Pine Forest")
            .addressLine2("London")
            .postCode("SW5").build();

    public static final Users CREATE_YET_ANOTHER_USER = Users.builder()
            .userId(3L)
            .firstName("Mia")
            .lastName("Cho")
            .addressLine1("50 Flora Grange")
            .addressLine2("London")
            .postCode("SW6").build();

    public static Users returnOneUser() {
        return Users.builder()
                .userId(4L)
                .firstName("Jo")
                .lastName("Williams")
                .addressLine1("77 Canopy Canyon")
                .addressLine2("London")
                .postCode("SW5").build();
    }

    public static final List<Users> USERLIST = new ArrayList<>();

    public static void populateUserData(){
        Users user1 = Users.builder()
                .userId(1L)
                .firstName("Francis")
                .lastName("Baker")
                .addressLine1("10 Park Lane")
                .addressLine2("London")
                .postCode("SW1").build();
        Users user2 = Users.builder()
                .userId(2L)
                .firstName("Emily")
                .lastName("Porter")
                .addressLine1("285 Mayflower Street")
                .addressLine2("London")
                .postCode("SW2").build();
        Users user3 = Users.builder()
                .userId(3L)
                .firstName("Mavis")
                .lastName("Threephorn")
                .addressLine1("100 Castle Close")
                .addressLine2("London")
                .postCode("SW3").build();

        USERLIST.addAll(List.of(user1, user2, user3));
    }

    //----- Order data -----//

    public static final OrderDetails CREATE_ONE_ORDER = OrderDetails.builder()
            .orderDetailsId(1L)
            .bookList(List.of(CREATE_ONE_BOOK))
            .users(CREATE_ONE_USER)
            .orderDate(LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS)).build();


    public static final OrderDetails CREATE_ANOTHER_ORDER = OrderDetails.builder()
            .orderDetailsId(2L)
            .bookList(List.of(CREATE_ANOTHER_BOOK, CREATE_YET_ANOTHER_BOOK))
            .users(CREATE_ANOTHER_USER)
            .orderDate(LocalDateTime.now().minusDays(2).truncatedTo(ChronoUnit.SECONDS)).build();


    public static final OrderDetails CREATE_YET_ANOTHER_ORDER = OrderDetails.builder()
            .orderDetailsId(3L)
            .bookList(List.of(returnOneBook()))
            .users(CREATE_YET_ANOTHER_USER)
            .orderDate(LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS)).build();


    public static OrderDetails returnOneOrder(final List<Book> books, final Users user) {
        final OrderDetails newOrder = OrderDetails.builder()
                .orderDetailsId(4L)
                .bookList(books)
                .users(user)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
        return newOrder;
    }

    public static final List<OrderDetails> ORDERLIST = new ArrayList<>();

    public static void populateOrderData(){
        OrderDetails order1 = OrderDetails.builder()
                .orderDetailsId(5L)
                .bookList(BOOKLIST)
                .users(CREATE_ONE_USER)
                .orderDate(LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS))
                .build();

        OrderDetails order2 = OrderDetails.builder()
                .orderDetailsId(6L)
                .bookList(BOOKLIST)
                .users(CREATE_ANOTHER_USER)
                .orderDate(LocalDateTime.now().minusDays(2).truncatedTo(ChronoUnit.SECONDS))
                .build();

        OrderDetails order3 = OrderDetails.builder()
                .orderDetailsId(7L)
                .bookList(BOOKLIST)
                .users(CREATE_YET_ANOTHER_USER)
                .orderDate(LocalDateTime.now().minusDays(3).truncatedTo(ChronoUnit.SECONDS))
                .build();

        ORDERLIST.addAll(List.of(order1, order2, order3));
    }

    public static final Basket BASKET = new Basket();

    public static void populateBasket(){
        BASKET.addBook(CREATE_ONE_BOOK);
        BASKET.addBook(CREATE_ANOTHER_BOOK);
        BASKET.addBook(CREATE_YET_ANOTHER_BOOK);
    }
}
