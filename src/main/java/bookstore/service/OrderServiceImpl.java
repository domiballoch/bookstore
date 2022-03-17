package bookstore.service;

import bookstore.dao.OrderRepository;
import bookstore.domain.Basket;
import bookstore.domain.OrderDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Basket basket;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasketService basketService;

    /**
     * Saves order details to DB
     *
     * @param orderDetails
     * @return
     */
    @Override
    public void submitOrder(final OrderDetails orderDetails) {
        log.info("Saving order details: {} {}", orderDetails.toString());
        OrderDetails newOrderDetails = OrderDetails.builder()
                .books(orderDetails.getBooks())
                .totalPrice(orderDetails.getTotalPrice())
                .orderDate(orderDetails.getOrderDate())
                .user(orderDetails.getUser()).build();

        orderRepository.save(newOrderDetails);

        log.info("Order complete: {} {}", newOrderDetails.toString());
        basketService.clearBasketAfterOrder();
    }
    //TODO:Stock & Order flow
    //1.Stock of object should reduce once added to basket
    //2.Once Order is saved, List of isbns's saved under Orders, Users
    //3.Stock property of Book is updated with patch in DB (Stock should have separate table)
}
