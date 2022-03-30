package bookstore.dto;

import bookstore.domain.OrderDetails;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsMapper {

    public OrderDetailsDTO toDto(final OrderDetails orderDetails) {

        final OrderDetails mappedOrderDetails = new OrderDetails();
        mappedOrderDetails.setOrderDetailsId(orderDetails.getOrderDetailsId());
        mappedOrderDetails.setBookList(orderDetails.getBookList());
        mappedOrderDetails.setUsers(orderDetails.getUsers());
        mappedOrderDetails.setOrderDate(orderDetails.getOrderDate());

        return new OrderDetailsDTO(mappedOrderDetails);
    }
}
