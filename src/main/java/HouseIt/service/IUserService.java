package HouseIt.service;

import HouseIt.entities.User;
import HouseIt.exception.MyEntityNotFoundException;

public interface IUserService {

    User getUser();

    User createUser(User user);

    void updateUserPassword(String password) throws MyEntityNotFoundException;

    void deleteUser(User user) throws MyEntityNotFoundException;

}
