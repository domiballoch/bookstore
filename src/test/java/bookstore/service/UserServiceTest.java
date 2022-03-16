package bookstore.service;

import bookstore.dao.UserRepository;
import bookstore.domain.Users;
import bookstore.exception.BookstoreDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookStoreConstants.DATABASE_NOT_AVAILABLE;
import static bookstore.utils.TestDataUtils.CREATE_ANOTHER_USER;
import static bookstore.utils.TestDataUtils.CREATE_ONE_USER;
import static bookstore.utils.TestDataUtils.USERLIST;
import static bookstore.utils.TestDataUtils.returnOneUser;
import static bookstore.utils.TestDataUtils.returnUserData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void prepareData() {
        returnUserData();
    }

    @DisplayName("Should return all users")
    @Test
    public void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(USERLIST);
        final List<Users> result = userService.findAllUsers();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(USERLIST);
        verify(userRepository, times(1)).findAll();
    }

    @DisplayName("Should return empty list")
    @Test
    public void shouldReturnEmptyList() {
        final List<Users> emptyList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(emptyList);
        final List<Users> result = userService.findAllUsers();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(Collections.EMPTY_LIST);
        verify(userRepository, times(1)).findAll();
    }

    @DisplayName("Should return one user by id")
    @Test
    public void shouldReturnUserById(){
        final long userId = 4;
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(returnOneUser()));
        final Optional<Users> result = userService.findUserById(userId);

        assertThat(result).isEqualTo(Optional.of(returnOneUser()));
        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @DisplayName("Should throw bookstore not found exception when return one user by id")
    @Test
    public void shouldThrowBookstoreNotFoundException_WhenReturnOneUserById(){
        final long userId = 10;
        Exception exception = assertThrows(BookstoreDataException.class, () -> {
            userService.findUserById(userId);
        });
        final String expectedMessage = DATABASE_NOT_AVAILABLE;
        final String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("Should add new user")
    @Test
    public void shouldAddNewUser(){
        final Users newUser = CREATE_ONE_USER;
        when(userRepository.save(any(Users.class))).thenReturn(newUser);
        final Users savedUser = userService.addNewUser(newUser);

        savedUser.setUserId(1L); //genius
        assertThat(savedUser).isEqualTo(newUser);
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @DisplayName("Should delete one user")
    @Test
    public void shouldDeleteOneUser(){
        final long userId = 4;
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(any(Long.class));
    }

    @DisplayName("Should update one user")
    @Test
    public void shouldUpdateOneUser(){
        Users user = (CREATE_ONE_USER);
        final Users newUserDetails = (CREATE_ANOTHER_USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userService.updateUser(newUserDetails, 1)).thenReturn(newUserDetails);
        newUserDetails.setUserId(CREATE_ONE_USER.getUserId());

        userRepository.save(user);
        final Users updatedUser = userService.updateUser(newUserDetails, 1);
        assertThat(updatedUser).isEqualTo(newUserDetails);
        verify(userRepository, times(2)).save(any(Users.class));
    }
}
