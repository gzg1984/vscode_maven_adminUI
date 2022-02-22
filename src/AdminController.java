package com.genesisdo.chinalxr.admin.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.genesisdo.base.Md5Encrypt;
import com.genesisdo.chinalxr.admin.pojo.AdminPO;
import com.genesisdo.chinalxr.admin.pojo.AdminVO;
import com.genesisdo.chinalxr.admin.service.IAdminService;
import com.genesisdo.chinalxr.backup.pojo.BackupInfo;
import com.genesisdo.chinalxr.navigation.pojo.Navigation;
import com.genesisdo.chinalxr.navigation.service.INavigationService;
import com.genesisdo.chinalxr.user.pojo.UserPO;
import com.genesisdo.chinalxr.user.pojo.UserVO;
import com.genesisdo.chinalxr.user.service.IUserService;
import com.genesisdo.framework.base.CommonAjaxResult;
import com.genesisdo.framework.utils.ResponseUtils;
import com.genesisdo.web.IMap;

@Controller
@RequestMapping(value="/admin/")
public class AdminController {
	/**
	 * 备份文件根目录
	 */
	@Value("${backup.file_path}")
	String backupBasePath;
	/**
	 * mysql bin目录
	 */
	@Value("${backup.sql_dumb_path}")
	String sqlDumpPath;
	/**
	 * 数据库host
	 */
	@Value("${backup.host}")
	String dbHost;
	
	@Value("${backup.username}")
	String dbUsername;
	
	@Value("${backup.password}")
	String dbPassword;
	
	@Value("${backup.database}")
	String database;
	
	@Value("${prefix}")
	String fileBase;
	
	/**
	 * 默认重置密码
	 */
	@Value("${admin.default_password}")
	String defaultPassword; 
	@Autowired
	INavigationService navigationServiceImpl;
	
	@Autowired
	IAdminService adminServiceImpl;
	
	@Autowired
	IUserService userServiceImpl;
	
	private static final String zipFileName="files.zip";
	
	private static final String sqlFileName="lxr_db.sql";
	

	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public String toIndex(){
		return "admin/index";
	}
	
	/**
	 * 获取管理后台左侧菜单部分
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getMenu.do")
	public String getMenu(HttpServletRequest request,HttpServletResponse response){
		System.out.println(request.getParameter("menuName"));
//		Staff loginStaff=(Staff)request.getSession().getAttribute("loginStaff");  //获取当前登录的员工信息
//		if(null!=loginStaff){
//			if(loginStaff.getRoleType().equals(RoleType.ADMIN_ROLE)){
				List<Navigation> adminMenu=navigationServiceImpl.getNavigationTreeAll();
				request.setAttribute("menu", adminMenu);
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
		return "admin/login";
	}
	@RequestMapping(value="/logout.do",method= RequestMethod.GET)
	public String toLogout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().setAttribute("loginAdmin", null);
		return "redirect:/admin/login.do";
	}
	@RequestMapping(value="/login.do",method= RequestMethod.POST)
	public String doLogin(HttpServletRequest request,HttpServletResponse response
			,@RequestParam("username")String username,@RequestParam("password")String password){
		AdminVO loginAdmin=adminServiceImpl.getAdminLogin(username, password);
		
		if(null!=loginAdmin){
			request.getSession().setAttribute("loginAdmin", loginAdmin);
			if(null!=request.getParameter("url")&&!request.getParameter("url").equals("")){
				return "redirect:"+request.getParameter("url");
			}
			else{
				return "redirect:/admin/index.do";
			}
		}
		else{
			request.setAttribute("errMsg", "账号或密码错误");
			return "admin/login";
		}
	}
	@RequestMapping(value="/user/list.do")
	public String toUserList(HttpServletRequest request,HttpServletResponse response){
		return "admin/user/list";
	}
	@RequestMapping(value="/user/manager.do")
	public String toManagerList(HttpServletRequest request,HttpServletResponse response){
		return "admin/user/manager";
	}
	@RequestMapping(value="/user/updatePassword.do",method=RequestMethod.GET)
	public String toUpdatePassword(HttpServletRequest request,HttpServletResponse response){
		return "admin/user/manager_password";
	}
	@RequestMapping(value="/user/updatePassword.do",method=RequestMethod.POST)
	public String doUpdatePassword(@RequestParam String passwordOriginal,@RequestParam String password,HttpServletRequest request,HttpServletResponse response){
		AdminVO admin=(AdminVO) request.getSession().getAttribute("loginAdmin");
		if(null!=admin){
			if(admin.getPassword().equals(Md5Encrypt.md5(passwordOriginal))){
				admin.setPassword(Md5Encrypt.md5(password));
				this.adminServiceImpl.updateAdmin(admin);
				request.setAttribute("errMsg", "修改成功");
				request.setAttribute("success", true);
			}else{
				request.setAttribute("errMsg", "原始密码错误");
				request.setAttribute("success", false);
			}
			return "admin/user/manager_password"; 
		}
		return "redirect:/admin/login.do";
	}
	@RequestMapping(value="/user/addManager.do",method=RequestMethod.GET)
	public String toManagerAdd(HttpServletRequest request,HttpServletResponse response){
		return "admin/user/manager_add";
	}
	
	@RequestMapping(value="/user/addManager.do",method=RequestMethod.POST)
	public String doAddManager(@ModelAttribute AdminPO admin,HttpServletRequest request,HttpServletResponse response){
		admin.setPassword(Md5Encrypt.md5(admin.getPassword()));
		this.adminServiceImpl.addAdmin(admin);
		request.setAttribute("errMsg", "添加成功");
		return "admin/user/manager_add";
	}
	@RequestMapping(value="/user/checkAdminUsernameUnique.do",method=RequestMethod.POST)
	public void checkAdminUsernameUnique(HttpServletResponse response,@RequestParam(value="username")String username){
		try {
			response.getWriter().write(this.adminServiceImpl.checkAdminUsernameUnique(username) ? "true" : "false");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/user/toggleMute.do",method=RequestMethod.POST)
	public void toggleMute(HttpServletResponse response,@RequestParam String userId){
		IMap result=new IMap();
		try{
			UserVO user=this.userServiceImpl.getUserByUserId(userId);
			UserPO userPO=new UserPO();
			userPO.setUserId(userId);
			if("1".equals(user.getIsMute())){
				userPO.setIsMute("0");
			}else{
				userPO.setIsMute("1");
			}
			
			this.userServiceImpl.updateUser(userPO);
			result.put("success", true);
			result.put("msg", "操作成功");
		}catch(Exception ex){
			ex.printStackTrace();
			result.put("success", false);
			result.put("msg", "系统出错,请重试");
		}
		
		ResponseUtils.writeJSONString(response, result);
		
	}
	
	@RequestMapping(value="/user/toggleFroze.do",method=RequestMethod.POST)
	public void toggleFroze(HttpServletResponse response,@RequestParam String userId){
		IMap result=new IMap();
		try{
			UserVO user=this.userServiceImpl.getUserByUserId(userId);
			UserPO userPO=new UserPO();
			userPO.setUserId(userId);
			if("1".equals(user.getIsLock())){
				userPO.setIsLock("0");
			}else{
				userPO.setIsLock("1");
			}
			
			this.userServiceImpl.updateUser(userPO);
			result.put("success", true);
			result.put("msg", "操作成功");
		}catch(Exception ex){
			ex.printStackTrace();
			result.put("success", false);
			result.put("msg", "系统出错,请重试");
		}
		
		ResponseUtils.writeJSONString(response, result);
		
	}
	@RequestMapping(value="/user/resetAdminPassword.do",method=RequestMethod.POST)
	public void resetAdminPassword(HttpServletResponse response,HttpServletRequest request,@RequestParam String adminId){
		IMap result=new IMap();
		try {
			AdminVO admin=(AdminVO) request.getSession().getAttribute("loginAdmin");
			if("1".equals(admin.getIsSuper())){ //有超级管理员权限
				AdminPO resetAdmin=new AdminPO();
				resetAdmin.setPassword(Md5Encrypt.md5(defaultPassword));
				resetAdmin.setAdminId(adminId);
				this.adminServiceImpl.updateAdmin(resetAdmin);
				result.put("success", true);
				result.put("msg", "已经重置为"+defaultPassword);
			}else{
				result.put("success", false);
				result.put("msg", "权限不足");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}finally{
			ResponseUtils.writeJSONString(response, result);
		}
	}
	@RequestMapping(value = "/user/deleteAdmin.do", method = RequestMethod.POST)
	public void deleteAdmin(@RequestParam(value = "ids") String projectId, HttpServletResponse response) {
		try {
			String[] ids = projectId.split(",");
			for (String id : ids) {
				this.adminServiceImpl.deleteAdmin(id);
			}
			ResponseUtils.writeJSONString(response, new CommonAjaxResult(true, "删除成功!"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResponseUtils.writeJSONString(response, new CommonAjaxResult(false, "删除失败!"));
		}
	}
	@RequestMapping(value="/site/manage.do")
	public String toBackup(HttpServletRequest request){
		File backupBaseDir=new File(backupBasePath);
		List<BackupInfo> backupDirs=new ArrayList<BackupInfo>();
		if(!backupBaseDir.exists()){
			backupBaseDir.mkdirs();
		}else{
			File[] fileList=backupBaseDir.listFiles();
			for(File file:fileList){
				BackupInfo info=new BackupInfo();
				info.setBackupDate(DateFormatUtils.format(new Date(file.lastModified()), "yyyy-MM-dd HH:mm:ss"));
				info.setBackupName(file.getName());
				backupDirs.add(info);
			}
			request.setAttribute("dirs", backupDirs);
		}
		return "admin/site/backup";
	}
	
	@RequestMapping(value = "/site/addBackup.do")
	public void addBackup(HttpServletRequest request,
			HttpServletResponse response) {
		IMap result=new IMap();
		String enablePath=this.getEnablePathName(1); //通过递归方法获取要创建备份的文件夹路径名称
		try {
			Runtime rt = Runtime.getRuntime(); // 返回与当前的Java应用程序的运行时对象
			// 调用 调用mysql的安装目录的命令
			String dumpCommand=sqlDumpPath+"/mysqldump -h" + dbHost + " -u" 
			+ dbUsername + " -p" + dbPassword + " --set-charset=UTF8 " + database;
			Process child = rt.exec(dumpCommand);
			// 设置导出编码为utf-8。这里必须是utf-8
			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
			InputStreamReader xx = new InputStreamReader(in, "utf-8");
			// 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			// 要用来做导入用的sql目标文件：
			File sqlFile=new File(enablePath+File.separator+sqlFileName);
			FileOutputStream fout = new FileOutputStream(sqlFile);
			OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
			writer.write(outStr);
			writer.flush();
			in.close();
			xx.close();
			br.close();
			writer.close();
			fout.close();
			System.out.println("数据库备份完成");
			
			/* 不备份代码文件,只备份SQL  liguanghui  2017-12-03
			 //File zipFile = new File(zipFileName);
	        System.out.println("压缩中...");
	        String zipFilePath=enablePath+File.separator+zipFileName;
	        //创建zip输出流
	        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFilePath));
	        //创建缓冲输出流
	        BufferedOutputStream bos = new BufferedOutputStream(out);
	        File sourceFile = new File(fileBase);
	        //调用函数
	        FileUploadUtil.compress(out,bos,sourceFile,"",false);
	        bos.close();
	        out.close();
	        System.out.println("压缩完成");
	        */
	        result.put("success", true);
	        result.put("msg","备份完成");
			
		} catch (Exception e) {
			e.printStackTrace();
			new File(backupBasePath).delete(); //备份失败时,删除新创建的备份文件夹
			 result.put("success", false);
			 result.put("msg","备份失败");
		}
		ResponseUtils.writeJSONString(response, result);
	}
	/**
	 * 整站还原
	 * @param request
	 * @param response
	 * @param backupName 备份名称
	 */
	@RequestMapping(value = "/site/restore.do")
	public void resotre(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("backupName")String backupName) {
		IMap result=new IMap();
		try {
			File backupDir=new File(backupBasePath+File.separator+backupName);
			if(!backupDir.exists()){
				 result.put("success", false);
				 throw new Exception("还原失败,没有找到备份文件"+backupDir.getAbsolutePath());
			}else{
				File sqlFile=new File(backupBasePath+File.separator+backupName+File.separator+this.sqlFileName);
				if(!sqlFile.exists()){
					result.put("success", false);
					throw new Exception("SQL文件缺失,无法还原");
				}
				/* 不还原代码文件,只还原SQL liguanghui 2017-12-03
				File zipFile=new File(backupBasePath+File.separator+backupName+File.separator+this.zipFileName); 
				if(!zipFile.exists()){
					result.put("success", false);
					throw new Exception("备份文件缺失,无法还原");
				}
				*/
				Runtime rt = Runtime.getRuntime(); // 返回与当前的Java应用程序的运行时对象
				// 调用 调用mysql的安装目录的命令
				String sqlLoginCmd=sqlDumpPath+"/mysql -h" + dbHost + " -u" 
				+ dbUsername + " -p" + dbPassword  +" " +database;
				Process child = rt.exec(sqlLoginCmd);
				
				 OutputStream os = child.getOutputStream();  
			     OutputStreamWriter writer = new OutputStreamWriter(os);  
			        //命令1和命令2要放在一起执行 
			     writer.write("source "+sqlFile.getAbsolutePath());  
			     writer.flush();  
			     writer.close();  
			     os.close();  
			   
				// 设置导出编码为utf-8。这里必须是utf-8
				// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
				InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
				InputStreamReader xx = new InputStreamReader(in, "utf-8");
//				// 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
				String inStr;
				StringBuffer sb = new StringBuffer("");
				String outStr;
//				// 组合控制台输出信息字符串
				BufferedReader br = new BufferedReader(xx);
				while ((inStr = br.readLine()) != null) {
					sb.append(inStr + "\r\n");
				}
				outStr = sb.toString();
//				// 要用来做导入用的sql目标文件：
				in.close();
				xx.close();
				br.close();
				result.put("msg", outStr);
				if(child.exitValue()!=0){
					 System.out.println("数据库还原失败");
					 result.put("success", false);
					 throw new Exception("无法还原数据库");
				}
				//不还原zip
//				FileUploadUtil.unzip(zipFile.getAbsolutePath(), fileBase, false); 
				 result.put("success", true);
				 result.put("msg", "还原成功");
				
			}
			
		}catch(Exception ex){
			result.put("success", false);
			result.put("msg", ex.getMessage());
		}finally{
			ResponseUtils.writeJSONString(response, result);
		}
	}
	
	/**
	 * 递归生成可用的备份文件夹名称
	 * 文件夹以当前日期yyyyMMdd日期格式加上3位递增序列组成,例:20171120001
	 * @param index
	 * @return
	 */
	private String getEnablePathName(int index){
		File baseDirs=new File(backupBasePath);
		if(!baseDirs.exists()){
			baseDirs.mkdirs();
		}
		String dirName=backupBasePath+File.separator+DateFormatUtils.format(new Date(), "yyyyMMdd")+String.format("%03d", index);
		File dirs=new File(dirName);
		if(dirs.exists()){
			return getEnablePathName(++index);
		}else{
			dirs.mkdirs(); //创建当前路径
			return dirName;
		}
	}
	
}
