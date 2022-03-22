package bookstore.domain;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public Users savedUser(final Users param) {

        final Users mappedUser = null;
        mappedUser.setFirstName(param.getFirstName());
        mappedUser.setLastName(param.getLastName());
        mappedUser.setAddressLine1(param.getAddressLine1());
        mappedUser.setAddressLine2(param.getAddressLine2());
        mappedUser.setPostCode(param.getPostCode());


        return savedUser(mappedUser);
    }
}
