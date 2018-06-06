package HouseIt.service.impl;

import HouseIt.dal.IUserDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.User;
import HouseIt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    private IUserDao userDao;

    @Autowired
    public UserServiceImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() throws HouseItServiceException {
        throw new NotImplementedException();
    }

    public User findUser(User user) throws HouseItServiceException {
        throw new NotImplementedException();
    }

    public boolean createUser(User user) throws HouseItServiceException {
        throw new NotImplementedException();
    }

    public boolean updateUser(User user) throws HouseItServiceException {
        throw new NotImplementedException();
    }

    public boolean deleteUser(User user) throws HouseItServiceException {
        throw new NotImplementedException();
    }

}
