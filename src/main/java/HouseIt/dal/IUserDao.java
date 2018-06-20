package HouseIt.dal;

import HouseIt.model.User;

public interface IUserDao extends IBaseDao<User> {

    User findByUsername(String username);

}
