package HouseIt.dal;

import HouseIt.model.User;

public interface IUserDao extends IBaseDao<User> {

    void deleteUser(User user);

}
