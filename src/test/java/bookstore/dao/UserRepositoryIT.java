package bookstore.dao;

import bookstore.domain.Users;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Uses HSQLDB in-memory db
 */
@Sql(scripts = {"classpath:test_data/userRepositoryTest.sql"})
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserById() {
        final Optional<Users> user = userRepository.findById(2001L);

        assertThat(user.get().getUserId()).isEqualTo(2001);
        assertThat(user.get().getFirstName()).isEqualTo("John");
        assertThat(user.get().getLastName()).isEqualTo("Smith");
        assertThat(user.get().getAddressLine1()).isEqualTo("10 Something Road");
        assertThat(user.get().getAddressLine2()).isEqualTo("London");
        assertThat(user.get().getPostCode()).isEqualTo("SW1");
    }

    @Test
    public void findAllUsers() {
        final List<Users> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(5);
    }

    @Test
    public void saveOneUser() {
        final Users user = CREATE_ONE_USER;
        user.setUserId(2006L);
        userRepository.save(user);
        final List<Users> userList = userRepository.findAll();

        assertThat(userRepository.count()).isEqualTo(6);
        assertThat(userList.size()).isEqualTo(6);
        assertThat(userList.get(5).getUserId()).isEqualTo(2006L);
        assertThat(userList.get(5).getFirstName()).isEqualTo("Bob");
        assertThat(userList.get(5).getLastName()).isEqualTo("Jones");
        assertThat(userList.get(5).getAddressLine1()).isEqualTo("99 Orange Grove");
        assertThat(userList.get(5).getAddressLine2()).isEqualTo("London");
        assertThat(userList.get(5).getPostCode()).isEqualTo("SW4");
    }

    @Disabled //TODO:Fix cascade
    @Test
    public void deleteOneUser() {
        userRepository.deleteById(2005L);
        final List<Users> userList = userRepository.findAll();

        assertThat(userList.size()).isEqualTo(4);
        assertThat(userRepository.findById(2005L)).isNull();
    }

    @Test
    public void updateOneUser() {

    }

}