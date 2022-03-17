package bookstore.domain;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"orderId", "orderDetailsList"})
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(insertable = false, updatable = false)
    private Long userId;

    @Column(insertable = false, updatable = false)
    private Long orderDetailsId;

    @Column(name = "order_details")
    private List<OrderDetails> orderDetailsList;

    @JsonIgnore
    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name="userId")
    private Optional<Users> users;

    @JsonIgnore
    @OneToOne(mappedBy = "orderDetailsId")//, fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private OrderDetails orderDetails;

}
