package bookstore.dto;

import bookstore.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserDTO {

    private Users user;
}
