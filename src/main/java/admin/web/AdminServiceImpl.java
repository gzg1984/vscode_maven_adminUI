package admin.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.IDao.AdminMapper;
import admin.domain.Admin;
import admin.web.IAdminService;
import java.security.MessageDigest;

@Service("adminServiceImpl")
public class AdminServiceImpl implements IAdminService {
	
	@Autowired
	AdminMapper adminMapper;

	//@Override
	//public void addAdmin(AdminPO admin) {
	//	this.adminMapper.addAdmin(admin);
	//}

	//@Override
	//public void updateAdmin(AdminPO admin) {
	//	this.adminMapper.updateAdmin(admin);
	//}

	
	public Admin getAdminLogin(String username, String password) {
		//IMap params=new IMap();
		//params.put("username", username);
		//params.put("password", Md5Encrypt.md5(password));
		try {

		byte s[] = MessageDigest.getInstance("MD5").digest(password.getBytes());
		String md5password = "";
		for (int i = 0; i < s.length; i++) {
			md5password += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
		}

		List<Admin> result=this.adminMapper.getAdminVOList(username,md5password);
		if(result.isEmpty()){
			return null;
		}else{
			assert result.size()==1;
			return result.get(0);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<Admin> getAllAdmin() {
		return this.adminMapper.getAllAdmin();
	}

	/*
	@Override
	public boolean checkAdminUsernameUnique(String username) {
		IMap params=new IMap();
		params.put("username", username);
		List<AdminVO> result=this.adminMapper.getAdminVOList(params);
		if(result.isEmpty()){
			return true;
		}else{
			assert result.size()==1;
			return false;
		}
	}

	@Override
	public void deleteAdmin(String adminId) {
		this.adminMapper.deleteAdmin(adminId);
	}
*/
}
