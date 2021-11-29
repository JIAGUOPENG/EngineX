
package com.risk.riskmanage.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.risk.riskmanage.system.service.MenuSevice;
import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.Menu;
import com.risk.riskmanage.system.model.MenuJson;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends BaseService implements MenuSevice {

	@Override
	public List<Menu> getMenuList(Menu menu) {
		return menuMapper.selectByExample(menu);
	}

	@Override
	public int getMenuCount(Menu menu) {
		return menuMapper.countByExample(menu);
	}

	@Override
	public void insertMenu(Menu menu) {
       menuMapper.insertSelective(menu);
	}

	@Override
	public void updateMenu(Menu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public boolean isExistByMenuName(String menuName, Long menuId) {
		 if(menuName != null && !("".equals(menuName))){
			 Menu menu = new Menu();
			 menu.setId(menuId);
			 menu.setName(menuName);
			 int rowCount=menuMapper.isExist(menu);
			 if(rowCount>0){
				 return true;
			 }
		 }
		return false;
	}

	@Override
	public boolean isExistByMenuCode(String menuCode, Long menuId) {
		 if(menuCode != null && !("".equals(menuCode))){
			 Menu menu = new Menu();
			 menu.setId(menuId);
			 menu.setMenuCode(menuCode);
			 int rowCount=menuMapper.isExist(menu);
			 if(rowCount>0){
				 return true;
			 }
		 }
		return false;
	}

	@Override
	public void deleteMenu(Long[] deletIds) {
        menuMapper.deleteMenu(deletIds);
	}

	@Override
	public List<Menu> selectByRole(Menu menu) {
		return menuMapper.selectByRole(menu);
	}

	@Override
	public boolean addMenuRole(Long[] deletIds, String roleCode) {
		if(deletIds.length>0 &&  roleCode !=null ){
			Menu menu = new Menu();
			menu.setRoleCode(roleCode);
			menuMapper.deleteMenuRole(menu);
			for (int i = 0; i < deletIds.length; i++) {
				menu.setId(deletIds[i]);
				menuMapper.insertMenuRole(menu);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<MenuJson> findUserMenuByUser(String loginName) {
		if(loginName != null && !("".equals(loginName))){
			List<Menu> menuList = menuMapper.findUserMenuByUser(loginName);
			List<Menu> menuOneList = new ArrayList<Menu>();
			for (Menu menu : menuList) {
				if(menu.getPid() == 1){
					menuOneList.add(menu);
				}
			}
			
			List<MenuJson> menuJsonList = new ArrayList<MenuJson>();
			
			for(Menu menuOne : menuOneList){
				MenuJson menuJson = new MenuJson();
				menuJson.setMenuid(menuOne.getId().toString());
				menuJson.setIcon(menuOne.getIcon());
				menuJson.setMenuname(menuOne.getName());
				List<MenuJson> menuTwoList = new ArrayList<MenuJson>();
				for(Menu menu : menuList){
					if(menu.getPid() == menuOne.getId()){
						MenuJson menuJsonTemp = new MenuJson();
						menuJsonTemp.setMenuid(menu.getId().toString());
						menuJsonTemp.setIcon(menu.getIcon());
						menuJsonTemp.setMenuname(menu.getName());
						menuJsonTemp.setUrl(menu.getUrl());
						menuTwoList.add(menuJsonTemp);
					}
				}
				menuJson.setMenus(menuTwoList);
				menuJsonList.add(menuJson);
			}
			
			return menuJsonList;
			
		}
		return null;
	}

}

