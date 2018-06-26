package HouseIt.service.impl;

import HouseIt.dal.IUserDao;
import HouseIt.model.User;
import HouseIt.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Overrides userdetailsservice and instead returns my custom wrapper for the domain user
 **/

@Service("userService")
public class UserServiceImpl implements UserDetailsService {

    private IUserDao userDao;

    @Autowired
    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user != null) {
            return new AuthenticatedUser(user); // Returns custom wrapper of my user domain
        } else {
            throw new UsernameNotFoundException(String.format("Unable to find user by username %s.", username));
        }

    }

}
