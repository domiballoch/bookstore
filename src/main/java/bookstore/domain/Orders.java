package bookstore.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"orderId"})
@ToString(of = {"orderId", "orderDetailsList"})
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(insertable = false, updatable = false)
    private Long fk_orderDetailsId;

    @Transient
    private List<OrderDetails> orderDetailsList;

    @JsonIgnore
    @OneToOne(mappedBy = "orderDetailsId", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private OrderDetails orderDetails;

}
