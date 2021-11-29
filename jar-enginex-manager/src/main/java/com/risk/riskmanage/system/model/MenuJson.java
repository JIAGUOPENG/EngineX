package com.risk.riskmanage.system.model;

import java.util.List;

/**
 * ClassName:MenuJson <br/>
 * Description: 转化成前端需要菜单实体类. <br/>
 */
public class MenuJson {

	private String menuid;
	private String icon;
	private String menuname;
	private String url;
	private List<MenuJson> menus;

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuJson> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuJson> menus) {
		this.menus = menus;
	}

}
