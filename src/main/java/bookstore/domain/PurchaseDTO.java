package bookstore.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PurchaseDTO {

    private List<Book> orderList;
    private Users user;
}
