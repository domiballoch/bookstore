package bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

    //    @NotNull(message = "Category cannot be null")
    //    @Column(name = "quantity")
    //    private int quantity;

    //    public Map<Category, List<Book>> getBooksByCategory() {
    //    return booksByCategory;
    //    }
    
//    @Column(name="created_at")
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(pattern="yyyy-MM-dd")
//    private Date createdAt = new Date();

}
