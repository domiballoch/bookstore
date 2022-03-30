package bookstore.dto;

import org.junit.jupiter.api.Test;

import static bookstore.utils.TestDataUtils.CREATE_ONE_BOOK;
import static org.assertj.core.api.Assertions.assertThat;

public class BookDTOTest {

    @Test
    public void buildBookDtoFromEntity() {
        BookMapper bookMapper = new BookMapper();
        final BookDTO bookDTO = bookMapper.toDto(CREATE_ONE_BOOK);

        assertThat(bookDTO.getBook()).isEqualTo(CREATE_ONE_BOOK);
    }
}
