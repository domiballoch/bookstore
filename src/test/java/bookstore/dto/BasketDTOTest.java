package bookstore.dto;

import org.junit.jupiter.api.Test;

import static bookstore.utils.TestDataUtils.BASKET;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketDTOTest {

    @Test
    public void buildOrderDetailsDtoFromEntity() {
        BasketMapper basketMapper = new BasketMapper();
        final BasketDTO basketDTO = basketMapper.toDto(BASKET);

        assertThat(basketDTO.getBasket().getBooks()).isEqualTo(BASKET.getBooks());
    }
}
