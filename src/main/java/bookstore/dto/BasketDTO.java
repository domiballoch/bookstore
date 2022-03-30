package bookstore.dto;

import bookstore.domain.Basket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BasketDTO {

    private Basket basket;
}
