package HouseIt.dal.impl;

import HouseIt.dal.IUserDao;
import HouseIt.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> implements IUserDao {

}
