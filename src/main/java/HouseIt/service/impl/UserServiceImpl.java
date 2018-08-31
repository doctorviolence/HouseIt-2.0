package HouseIt.service.impl;

import HouseIt.dal.IUserDao;
import HouseIt.entities.User;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.security.AuthenticatedUser;
import HouseIt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Overrides userdetailsservice and instead returns my custom wrapper for the domain user
 **/

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, IUserService {

    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public User findUser(long id) throws MyEntityNotFoundException {
        User u = userDao.findEntityById(User.class, id);
        if (u == null) {
            throw new MyEntityNotFoundException(String.format("User with ID %s not found.", id));
        }
        return u;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.createUser(user);
    }

    public void updateUser(User user) throws MyEntityNotFoundException {
        User u = findUser(user.getId());
        if (u != null) {
            userDao.updateEntity(user);
        }
    }

    public void deleteUser(long id) throws MyEntityNotFoundException {
        User u = findUser(id);
        if (u != null) {
            userDao.deleteEntity(User.class, id);
        }
    }

}