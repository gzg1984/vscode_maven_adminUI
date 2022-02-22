package admin.web;

import java.util.List;

import admin.domain.Navigation;;

/**
 * 菜单类的Service接口
 * @author david
 *
 */
public interface INavigationService {

	/**
	 * 用树形队列获取所有的菜单.
	 * @return
	 */
	List<Navigation> getNavigationTreeAll();
	
	/**
	 * 根据role_id 查询对应角色的菜单,用树形队列.
	 * @param roleId
	 * @return
	 */
	//List<Navigation> getNavigationTreeByRole(int roleId);
}
