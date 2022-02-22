package admin.web;
 
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import admin.domain.Navigation;
import admin.domain.User;
import admin.domain.Admin;

import admin.web.IUserService;
import admin.web.INavigationService;
import admin.web.INavigationService;

@Controller
@RequestMapping("/")
public class Index {
	/* 将默认首页访问转发到WEB-INF/index.jsp */
    @RequestMapping(value = { "/index", "/index.html" }, method = RequestMethod.GET)
    public String toIndex(HttpServletRequest request) {
		System.out.printf("Enter index of admin.web with Context %s\n",request.getContextPath());
		System.out.printf("Enter index of  admin.web with parameter %s\n",request.getServletPath());

        return "index";
    }
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public String toIndex(){
		System.out.printf("Enter admin.web index.do\n");
		return "index";
	}
	
	/**
	 * 获取管理后台左侧菜单部分
	 * @param request
	 * @param response
	 * @return
	 */
	@Autowired
	INavigationService navigationServiceImpl;
	
	@RequestMapping(value="/getMenu.do")
	public String getMenu(HttpServletRequest request,HttpServletResponse response){
		System.out.printf("parameter of getMenu.go is %s\n",request.getParameter("menuName"));
//		Staff loginStaff=(Staff)request.getSession().getAttribute("loginStaff");  //获取当前登录的员工信息
//		if(null!=loginStaff){
//			if(loginStaff.getRoleType().equals(RoleType.ADMIN_ROLE)){
				List<Navigation> adminMenu=navigationServiceImpl.getNavigationTreeAll();
				request.setAttribute("menu", adminMenu);
				System.out.printf("result List size is  %d",adminMenu.size());

//			}
//			else{
//				List<Navigation> roleMenu=navigationServiceImpl.getNavigationTreeByRole(Integer.parseInt(loginStaff.getRoleId()));
//				request.setAttribute("menu", roleMenu);
//			}
//			
//		}
		request.setAttribute("menuName", request.getParameter("menuName"));
		return "forward:/common/admin/navigation.jsp";
	}
	@RequestMapping(value="/login.do",method= RequestMethod.GET)
	public String toLogin(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("url", request.getParameter("url"));
		return "login";
	}
	/* 将默认首页访问转发到WEB-INF/index.jsp */
    @RequestMapping(value = {"/","","/login","login.html" }, method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request) {
		System.out.printf("Enter login of admin.web with Context %s\n",request.getContextPath());
		System.out.printf("Enter login of admin.web with parameter %s\n",request.getServletPath());

        return "login";
    }
	@RequestMapping(value="/logout.do",method= RequestMethod.GET)
	public String toLogout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("loginAdmin", null);
		return "redirect:login.do";
	}
	
	@Autowired
	IAdminService adminServiceImpl;
	
	
	@RequestMapping(value="/login.do",method= RequestMethod.POST)
	public String doLogin(HttpServletRequest request,HttpServletResponse response
			,@RequestParam("username")String username,@RequestParam("password")String password){
		Admin loginAdmin=adminServiceImpl.getAdminLogin(username, password);
		
		if(null!=loginAdmin){
			request.getSession().setAttribute("loginAdmin", loginAdmin);
			if(null!=request.getParameter("url")&&!request.getParameter("url").equals("")){
				return "redirect:"+request.getParameter("url");
			}
			else{
				return "redirect:/index.do";
			}
		}
		else{
			request.setAttribute("errMsg", "账号或密码错误");
			return "login";
		}
	}
    
}

