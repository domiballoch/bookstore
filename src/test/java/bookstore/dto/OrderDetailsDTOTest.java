package bookstore.dto;

import org.junit.jupiter.api.Test;

import static bookstore.utils.TestDataUtils.CREATE_ONE_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderDetailsDTOTest {

    @Test
    public void buildOrderDetailsDtoFromEntity() {
        OrderDetailsMapper orderDetailsMapper = new OrderDetailsMapper();
        final OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.toDto(CREATE_ONE_ORDER);

        assertThat(orderDetailsDTO.getOrderDetails()).isEqualTo(CREATE_ONE_ORDER);
    }
}

