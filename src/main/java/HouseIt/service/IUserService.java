package HouseIt.service;

import HouseIt.entities.User;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.exception.UserExistsException;
import HouseIt.security.ResetPasswordHelper;

public interface IUserService {

    User getUser();

    User createUser(User user) throws UserExistsException;

    void updateUserPassword(ResetPasswordHelper helper) throws MyEntityNotFoundException;

    void deleteUser(User user) throws MyEntityNotFoundException;

}
