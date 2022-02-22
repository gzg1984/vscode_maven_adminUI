package admin.web;
 
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import admin.domain.User;
import admin.domain.Admin;
import admin.domain.Navigation;

import admin.web.IUserService;
import admin.web.IAdminService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
	@Resource
	private IAdminService adminService;
	
	@RequestMapping("/index")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "index";
	}


	/*  /admin/user/list.do */
	@RequestMapping(value="/list.do")
	public String toUserList(HttpServletRequest request,HttpServletResponse response,Model model){
		System.out.printf("Enter list.do with Context %s\n",request.getContextPath());
		System.out.printf("Enter list.do with parameter %s\n",request.getServletPath());
		
		List<User> users = this.userService.getAllUser();
		model.addAttribute("users", users);
		return "user/list";
	}
	
	/*  /admin/user/manager.do */
	@RequestMapping(value="/manager.do")
	public String toManagerList(HttpServletRequest request,HttpServletResponse response,Model model){
		System.out.printf("Enter manager.do with Context %s\n",request.getContextPath());
		System.out.printf("Enter manager.do with parameter %s\n",request.getServletPath());
		

		
		List<Admin> users = this.adminService.getAllAdmin();
		

		for(int i=0;i<users.size();i++){
			Admin a=users.get(i);
			System.out.printf("admin name is %s\n",a.getUsername());
		}
		
		

		model.addAttribute("adminUsers", users);
		

		return "user/manager";

	}

	
}

