package bookstore.dto;

import bookstore.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(final Users user) {

        final Users mappedUser = new Users();
        mappedUser.setUserId(user.getUserId());
        mappedUser.setFirstName(user.getFirstName());
        mappedUser.setLastName(user.getLastName());
        mappedUser.setAddressLine1(user.getAddressLine1());
        mappedUser.setAddressLine2(user.getAddressLine2());
        mappedUser.setPostCode(user.getPostCode());

        return new UserDTO(mappedUser);
    }
}
