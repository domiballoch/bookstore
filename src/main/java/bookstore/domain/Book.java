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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"isbn"})
@ToString(of = {"isbn", "category", "title", "author", "price", "stock"})
public class Book implements Serializable {

    //TODO: Lazy load for entities if relation has many dependencies

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isbn")
    private Long isbn;

//    @JsonIgnore
//    @Column(insertable = false, updatable = false)
//    private Long orderDetailsId;

    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @NotEmpty(message = "Title must not be empty")
    @Max(value = 100, message = "Title length must be less than one hundred chars")
    @Min(value = 1, message = "Title length must be greater than zero")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Author must not be empty")
    @Max(value = 50, message = "Author length must be less than fifty chars")
    @Min(value = 1, message = "Author length must be greater than zero")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Price cannot be null")
    @Digits(integer = 3, fraction = 2, message = "Price must have a max of 3 integers and 2 fractions")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @DecimalMax(value = "999.99", inclusive = true, message = "Price must be less than 1,000.00")
    @Column(name = "price")
    private BigDecimal price;

    @NotNull(message = "stock cannot be null")
    @Column(name = "stock")
    private int stock;

//    @JsonIgnore
//    @ManyToOne//(targetEntity = OrderDetails.class)
//    @JoinColumn(name="orderDetailsId") //add bi-directional
//    private OrderDetails orderDetails;

    @JsonIgnore
    @ManyToOne
    private OrderDetails orderDetails;
}
