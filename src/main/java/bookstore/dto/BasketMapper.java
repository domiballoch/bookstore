package bookstore.dto;

import bookstore.domain.Basket;
import org.springframework.stereotype.Component;

@Component
public class BasketMapper {

    public BasketDTO toDto(final Basket basket) {

        final Basket mappedBasket = new Basket();
        mappedBasket.setBooks(basket.getBooks());
//        for(Book mappedBook : mappedBasket.getBooks()) {
//            for(Book basketBook : basket.getBooks()) {
//                mappedBook.setIsbn(basketBook.getIsbn());
//                mappedBook.setTitle(basketBook.getTitle());
//                mappedBook.setAuthor(basketBook.getAuthor());
//                mappedBook.setCategory(basketBook.getCategory());
//                mappedBook.setPrice(basketBook.getPrice());
//                mappedBook.setStock(basketBook.getStock());
//            }
//        }

        return new BasketDTO(mappedBasket);
    }
}
