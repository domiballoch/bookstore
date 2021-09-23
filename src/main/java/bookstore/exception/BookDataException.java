package bookstore.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class BookDataException extends DataAccessException {

    public BookDataException(String errorMessage) {
        super(errorMessage);
    }
}
