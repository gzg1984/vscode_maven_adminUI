package admin.web;
 
import admin.domain.User;
import java.util.ArrayList;
import java.util.List;

public interface IUserService {
	public User getUserById(int userId);
	public List<User> getAllUser();

}
