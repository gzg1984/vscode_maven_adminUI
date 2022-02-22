package admin.web;
 
import java.util.List;

import javax.annotation.Resource;
 
import org.springframework.stereotype.Service;
 
import admin.IDao.UserMapper;
import admin.domain.User;
import admin.web.IUserService;
 
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserMapper userDao;

	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}
 
	public List<User> getAllUser(){
		return this.userDao.getAllUser();
	}

}

