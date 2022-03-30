package bookstore.dto;

import org.junit.jupiter.api.Test;

import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDTOTest {

    @Test
    public void buildUserDtoFromEntity() {
        UserMapper userMapper = new UserMapper();
        final UserDTO userDTO = userMapper.toDto(CREATE_ONE_USER);

        assertThat(userDTO.getUser()).isEqualTo(CREATE_ONE_USER);
    }
}

