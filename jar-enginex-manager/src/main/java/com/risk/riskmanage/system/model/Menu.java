package com.risk.riskmanage.system.model;

import java.io.Serializable;
import java.util.Arrays;

import com.risk.riskmanage.common.model.BasePage;

/**
 * ClassName:MenuVo <br/>
 * Description: 系统菜单管理界面. <br/>
 */
public class Menu extends BasePage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单主键id
	 */
	private Long id;
	/**
	 * 父id
	 */
	private Long pid;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单类型
	 */
	private Integer type;
	/**
	 * 菜单顺序
	 */
	private Integer muneOrder;
	/**
	 * easyui展开
	 */
	private String state;
	/**
	 * 菜单链接
	 */
	private String url;
	/**
	 * 菜单编号
	 */
	private String menuCode;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 菜单描述
	 */
	private String description;
	/**
	 * 菜单状态
	 */
	private Integer status;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 最后修改人
	 */
	private String updater;
	/**
	 * 最后修改时间
	 */
	private String updateTime;
	
	/**
	 * 父类
	 */
	private Long _parentId;
	
	/**
	 * 角色编码
	 */
	private String roleCode;

	/**
	 * 删除ids数组
	 */
	private Long []deletIds;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Long[] getDeletIds() {
		return deletIds;
	}

	public void setDeletIds(Long[] deletIds) {
		this.deletIds = deletIds;
	}
	
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Long get_parentId() {
		return _parentId;
	}

	public void set_parentId(Long _parentId) {
		this._parentId = _parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMuneOrder() {
		return muneOrder;
	}

	public void setMuneOrder(Integer muneOrder) {
		this.muneOrder = muneOrder;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", pid=" + pid + ", name=" + name + ", type="
				+ type + ", muneOrder=" + muneOrder + ", state=" + state
				+ ", url=" + url + ", menuCode=" + menuCode + ", icon=" + icon
				+ ", description=" + description + ", status=" + status
				+ ", creator=" + creator + ", createTime=" + createTime
				+ ", updater=" + updater + ", updateTime=" + updateTime
				+ ", _parentId=" + _parentId + ", roleCode=" + roleCode
				+ ", deletIds=" + Arrays.toString(deletIds) + "]";
	}
	
	
}
