package bookstore.dto;

import bookstore.domain.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderDetailsDTO {

    private OrderDetails orderDetails;
}
