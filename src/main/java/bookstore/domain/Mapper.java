package bookstore.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class Mapper {

    public PurchaseDTO toDto(final Basket basket, final Users user) {

        final List<Book> orderList = new ArrayList<>();
        for(Book mappedBook : orderList) {
            for(Book basketBook : basket) {
                mappedBook.setTitle(basketBook.getTitle());
                mappedBook.setAuthor(basketBook.getAuthor());
                mappedBook.setCategory(basketBook.getCategory());
                mappedBook.setPrice(basketBook.getPrice());
            }
        }

        final Users mappedUser = null;
        mappedUser.setFirstName(user.getFirstName());
        mappedUser.setLastName(user.getLastName());
        mappedUser.setAddressLine1(user.getAddressLine1());
        mappedUser.setAddressLine2(user.getAddressLine2());
        mappedUser.setPostCode(user.getPostCode());

        return new PurchaseDTO(orderList, mappedUser);
    }
}
