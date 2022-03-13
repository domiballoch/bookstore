package bookstore.service;

import bookstore.domain.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<Users> findUserById(final long userId);

    List<Users> findAllUsers();

    Users addNewUser(final Users user);

    void deleteUser(final long userId);

    Users updateUser(final Users user, final long userId);
}
