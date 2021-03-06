package HouseIt.service.impl;

import HouseIt.dal.IUserDao;
import HouseIt.entities.User;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.exception.PasswordsDontMatchException;
import HouseIt.exception.UserExistsException;
import HouseIt.security.AuthenticatedUser;
import HouseIt.security.ResetPasswordHelper;
import HouseIt.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Overrides userdetailsservice and instead returns my custom wrapper for the domain user
 **/

@Service("userService")
@Transactional
public class UserServiceImpl implements UserDetailsService, IUserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Unable to find user by username %s.", username));
        }

        return user;
    }

    public User createUser(User user) throws UserExistsException {
        User u = userDao.findByUsername(user.getUsername());
        if (u != null) {
            throw new UserExistsException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.createUser(user);
    }

    public boolean updateUserPassword(ResetPasswordHelper helper) throws PasswordsDontMatchException {
        boolean updateSuccess = false;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getPrincipal().toString();
            User u = userDao.findByUsername(username);

            if (u == null) {
                throw new UsernameNotFoundException(String.format("Unable to find user by username %s.", username));
            }

            String oldPassword = helper.getOldPassword();
            String newPassword = helper.getNewPassword();

            if (passwordEncoder.matches(oldPassword, u.getPassword())) {
                u.setPassword(passwordEncoder.encode(newPassword));
                userDao.updateEntity(u);
                updateSuccess = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return updateSuccess;
    }

    public void deleteUser(User user) throws MyEntityNotFoundException {
        User u = userDao.findByUsername(user.getUsername());
        if (u != null) {
            userDao.deleteEntity(User.class, u.getId());
        }
    }

}