/* 
 * Copyright © 2016 GenesisDo 
 * 延边创为软件开发有限公司
 * http://www.genesisdo.com
 * All rights reserved. 
 */ 
package admin.IDao;

import java.util.List;

import admin.domain.Navigation;
//import com.genesisdo.web.IMap;

/**
 * navigation DAO
 * @author maoxuwen
 *
 */
public interface NavigationMapper {
    int deleteByPrimaryKey(Integer navigationId);

    int insert(Navigation record);

    int insertSelective(Navigation record);

    Navigation selectByPrimaryKey(Integer navigationId);

    int updateByPrimaryKeySelective(Navigation record);

    int updateByPrimaryKey(Navigation record);
	/**
	 * 根据查询条件,查询navigation表记录
	 * 
	 * @param params
	 * @return
	 */
	//List<Navigation> getNavigationList(IMap params);
	List<Navigation> getNavigationList();

	/**
	 * 根据role_id 查询对应角色的菜单
	 * 
	 * @param params
	 * @return
	 */
	//List<Navigation> getNavigationListByRole(IMap params);
	
}
