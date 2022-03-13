package bookstore.controller.rest;

import bookstore.domain.Users;
import bookstore.exception.BookstoreNotFoundException;
import bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static bookstore.utils.BookConstants.USER_NOT_FOUND;

@Slf4j
@RestController
@RequestMapping(value = "/rest", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/findAllUsers")
    public ResponseEntity<List<Users>> findAllUsers() {
        final List<Users> userList = userService.findAllUsers();
        if(userList.isEmpty()) {
            noResultsFound(userList, "All users");
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping(value = "/findUser/{userId}")
    public ResponseEntity<Optional<Users>> findUserById(@PathVariable final long userId) {
        final Optional<Users> user = Optional.ofNullable(userService.findUserById(userId)
                .orElseThrow(() -> new BookstoreNotFoundException(USER_NOT_FOUND)));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/addNewUser")
    public ResponseEntity<Users> addNewUser(@RequestBody final Users user) {
        final Users addedUser = userService.addNewUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.OK); //Http.Status.CREATED?
    }

    @DeleteMapping(value = "/deleteUser/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable final long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    //Using PUT for idempotency - resending the whole Entity

    @PutMapping(value = "/updateUser/{userId}")
    public ResponseEntity<Users> updateUser(@RequestBody final Users user, @PathVariable final long userId) {
        final Users updatedUser = userService.updateUser(user, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    private <T>  ResponseEntity<T> noResultsFound(final T t, final T r) {
        log.info("No results found: {}", r);
        return new ResponseEntity<T>(t, HttpStatus.NOT_FOUND);
    }
}
