package bookstore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"orderDetailsId"})
@ToString(of = {"orderDetailsId", "bookList", "users", "orderDate"})
public class OrderDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private Long orderDetailsId;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Long fk_userId;

//    @JsonIgnore
//    @Column(insertable = false, updatable = false)
//    private Long fk_isbn;

    @Column(name = "order_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name="fk_userId")
    private Users users;

    //@OneToMany(mappedBy = "isbn", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    //private List<Book> bookList;

    @OneToMany
    @JoinTable(name = "book_order", joinColumns = @JoinColumn(name = "fk_isbn"),
            inverseJoinColumns = @JoinColumn(name = "fk_orderDetailsId"))
    private List<Book> bookList = new ArrayList<>();

}
