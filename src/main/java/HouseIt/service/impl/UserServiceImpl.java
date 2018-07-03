package HouseIt.service.impl;

import HouseIt.dal.IUserDao;
import HouseIt.model.User;
import HouseIt.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Overrides userdetailsservice and instead returns my custom wrapper for the domain user
 **/

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService {

    private IUserDao userDao;

    @Autowired
    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Unable to find user by username %s.", username));
        }

        return new AuthenticatedUser(user.getUsername(), user.getPassword(), user); // Returns custom wrapper of my user domain
    }

}
