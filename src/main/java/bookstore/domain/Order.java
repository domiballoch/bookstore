package bookstore.domain;

import java.io.Serializable;

//Order entity so record purchases by users
public class Order implements Serializable {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long orderId;

    //@JsonFormat(pattern = "dd/MM/yyyy...")
    //private LocalDateTime orderDate;

    //@ManyToOne(cascade = { CascadeType.ALL })
    //@JoinColumn(name="userId")
    //private List<User> user;

    //@OneToMany(mappedBy = "isbn", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    //private List<Book> books;
}
