package admin.web;

import java.util.List;

import admin.domain.Admin;;

public interface IAdminService {

	//void addAdmin(AdminPO admin);
	//void updateAdmin(AdminPO admin);
	Admin getAdminLogin(String username,String password);
	//void deleteAdmin(String adminId);
	/**
	 * 检查用户名是否重复,如果重复返回false,唯一返回true
	 * @param username
	 * @return
	 */
	//boolean checkAdminUsernameUnique(String username);
	public List<Admin>  getAllAdmin();

}
