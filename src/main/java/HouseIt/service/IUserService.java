package HouseIt.service;

import HouseIt.exception.HouseItServiceException;
import HouseIt.model.User;

import java.util.List;

public interface IUserService {

    List<User> getUsers() throws HouseItServiceException;

    User findUser(User user) throws HouseItServiceException;

    boolean createUser(User user) throws HouseItServiceException;

    boolean updateUser(User user) throws HouseItServiceException;

    boolean deleteUser(User user) throws HouseItServiceException;

}
