package bookstore.domain;

import org.springframework.stereotype.Component;

@Component
class Mapper {

    public PurchaseDTO toDto(final Basket basket, final Users user, final Orders order) {

        final Basket mappedBasket = null;
        for(Book mappedBook : mappedBasket) {
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

        final Orders mappedOrder = null;
        mappedOrder.setTotalItems(order.getTotalItems());
        mappedOrder.setTotalPrice(order.getTotalPrice());
        mappedOrder.setOrderDate(order.getOrderDate());

        return new PurchaseDTO(mappedBasket, mappedUser, mappedOrder);
    }
}
