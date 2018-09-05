package HouseIt.controller.rest;

import HouseIt.dal.IUserDao;
import HouseIt.entities.User;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.exception.PasswordsDontMatchException;
import HouseIt.exception.UserExistsException;
import HouseIt.security.ResetPasswordHelper;
import HouseIt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get user (NB: used mainly for establishing controller tests...)
    @GetMapping(value = "/users/user", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUser() {
        User user = userService.getUser();
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // Create user
    @PostMapping(value = "/users/create-user", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User u) throws UserExistsException {
        User user = userService.createUser(u);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    // Update user password
    @PutMapping(value = "/users/update-password", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TENANT')")
    public ResponseEntity<String> updateUser(@RequestBody ResetPasswordHelper passwords) throws PasswordsDontMatchException {
        boolean update = userService.updateUserPassword(passwords);
        if (update) {
            return new ResponseEntity<>("Password updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Passwords don't match...", HttpStatus.BAD_REQUEST);
        }
    }

    // Delete user
    @DeleteMapping(value = "/users/delete-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@RequestBody User user) throws MyEntityNotFoundException {
        userService.deleteUser(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
