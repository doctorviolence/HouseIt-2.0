package HouseIt.dal;

import HouseIt.entities.User;

public interface IUserDao extends IBaseDao<User> {

    User findByUsername(String username);

    User createUser(User user);

}
