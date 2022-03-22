package bookstore.service;

import bookstore.dao.UserRepository;
import bookstore.domain.Users;
import bookstore.exception.BookstoreDataException;
import bookstore.exception.BookstoreNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.DATABASE_NOT_AVAILABLE;
import static bookstore.utils.BookStoreConstants.USER_NOT_FOUND;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Finds all users
     *
     * @return
     */
    @Override
    public List<Users> findAllUsers() {
        log.info("Finding all users");
        return userRepository.findAll();
    }

    /**
     * Finds user by id
     *
     * @param userId
     * @return
     */
    @Override
    public Optional<Users> findUserById(final long userId){
        log.info("Finding user by id: {}", userId);
        return Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new BookstoreDataException(DATABASE_NOT_AVAILABLE)));
    }

    /**
     * Adds new user
     *
     * @param user
     * @return
     */
    @Override
    public Users addNewUser(final Users user) {
        log.info("Adding new user");
        final Users newUser = Users.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .addressLine1(user.getAddressLine1())
                .addressLine2(user.getAddressLine2())
                .postCode(user.getPostCode())
                .build();
        userRepository.save(newUser);

        log.info("New user added: {}", newUser.toString());
        return newUser;
    }

    /**
     * Deletes user by id
     *
     * @param userId
     */
    @Override
    public void deleteUser(long userId) {
        log.info("Deleting user by id: {}", userId);
        try {
            userRepository.deleteById(userId);
        } catch(BookstoreNotFoundException e) {
            log.info(USER_NOT_FOUND);
        }
        log.info("Deleted user by id: {}", userId);
    }

    /**
     * Updates user by id
     *
     * @param user
     * @param userId
     * @return
     */
    @Override
    public Users updateUser(final Users user, final long userId) {
        Optional<Users> foundUser = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new BookstoreNotFoundException(USER_NOT_FOUND)));
        log.info("Updating user: {}", foundUser.toString());
        userRepository.delete(foundUser.get());
        user.setUserId(userId);
        userRepository.save(user);
        log.info("Saving user: {}", user.toString());
        return user;
    }
}
