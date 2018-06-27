package HouseIt.controller;

import HouseIt.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserDetailsService userService;

    /*@PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody AuthenticatedUser user) throws AuthenticationException {

        return new ResponseEntity<String>(HttpStatus.OK);
    }*/

}
