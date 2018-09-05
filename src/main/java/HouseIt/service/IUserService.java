package HouseIt.service;

import HouseIt.entities.User;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.exception.PasswordsDontMatchException;
import HouseIt.exception.UserExistsException;
import HouseIt.security.ResetPasswordHelper;

public interface IUserService {

    User getUser();

    User createUser(User user) throws UserExistsException;

    boolean updateUserPassword(ResetPasswordHelper helper) throws PasswordsDontMatchException;

    void deleteUser(User user) throws MyEntityNotFoundException;

}
