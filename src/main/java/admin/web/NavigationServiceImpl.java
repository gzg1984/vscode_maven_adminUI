/* 
 * Copyright © 2016 GenesisDo 
 * 延边创为软件开发有限公司
 * http://www.genesisdo.com
 * All rights reserved. 
 */ 
package admin.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.IDao.*;
import admin.domain.*;
import admin.web.INavigationService;
//import com.genesisdo.web.IMap;

@Service("navigationServiceImpl")
public class NavigationServiceImpl implements INavigationService{

	//@Autowired
	//NavigationMapper navigationMapper;
	
	
	@Resource
	private NavigationMapper navigationMapper;
	
	
	//@Override
	public List<Navigation> getNavigationTreeAll() {
		//IMap param=new IMap();
		//List<Navigation> navList=navigationMapper.getNavigationList(param);
		List<Navigation> navList=navigationMapper.getNavigationList();
		List<Navigation> resultList =new ArrayList<Navigation>();
		for(int i=0;i<navList.size();){
			if(navList.get(i).getParentId()==0){
				resultList.add(navList.get(i));
				navList.remove(i);
			}else{
				i++;
			}
		}
		for(Navigation resultNav: resultList){
			Tree(resultNav, navList);
		}
		return resultList;
	}
	
	/*
	@Override
	public List<Navigation> getNavigationTreeByRole(int roleId) {
		IMap param=new IMap();
		param.put("roleId", roleId);
		List<Navigation> navList=navigationMapper.getNavigationListByRole(param);
		List<Navigation> resultList =new ArrayList<Navigation>();
		for(int i=0;i<navList.size();){
			if(navList.get(i).getParentId()==0){
				resultList.add(navList.get(i));
				navList.remove(i);
			}else{
				i++;
			}
		}
		for(Navigation resultNav: resultList){
			if(navList.size()>0){
				Tree(resultNav, navList);
			}else{
				break;
			}
		}
		return resultList;
	}*/
	
	
	/* Refer select * from navigation; */
	private void Tree(Navigation resultNav,List<Navigation> navList) {
		for(int i=0;i<navList.size();i++){
			Navigation navigation=navList.get(i);

			if(navigation.getParentId()==resultNav.getNavigationId()){
				if(resultNav.getChildren()==null){
					resultNav.setChildren(new ArrayList<Navigation>());
				}
				resultNav.getChildren().add(navigation);
				Tree(navigation, navList);
			}
		}
	}

}
