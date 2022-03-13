package bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PurchaseDTO {

    private Basket basket;
    private Users user;
    private Orders order;
}
