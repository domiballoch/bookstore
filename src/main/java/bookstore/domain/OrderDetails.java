//package bookstore.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.io.Serializable;
//@Builder(toBuilder = true)
//@Data
//@Entity
//@Table(name = "orders")
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(of = {"id"})
//@ToString(of = {"orderId", "totalItems", "totalPrice", "orderDate"})
//public class OrderDetails implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_details_id")
//    private Long orderDetailsId;
//
//
//}
