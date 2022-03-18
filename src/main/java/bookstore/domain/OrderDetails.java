package bookstore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"orderDetailsId"})
@ToString(of = {"orderDetailsId", "bookList", "totalPrice", "orderDate", "user"})
public class OrderDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private Long orderDetailsId;

    @Column(insertable = false, updatable = false)
    private Long fk_userId;

    @Column(insertable = false, updatable = false)
    private Long fk_isbn;

    @Transient
    private List<Book> bookList;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "order_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @Column(name = "user")
    private Users user;

//    @JsonIgnore
//    @OneToOne(targetEntity = Orders.class)
//    @JoinColumn(name="orderId")
//    private Orders order;

    @JsonIgnore
    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name="userId")
    private Optional<Users> users;

    @JsonIgnore
    @OneToMany(mappedBy = "isbn", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private Optional<Book> book;
}
