package bookstore.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"orderId", "totalItems", "totalPrice", "orderDate"})
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "total_items")
    private int totalItems;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="userId")
    private Users users;

    @OneToMany(mappedBy = "isbn", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private List<Book> books;
}
