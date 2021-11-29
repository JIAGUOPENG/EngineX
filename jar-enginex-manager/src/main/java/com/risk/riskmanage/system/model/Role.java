package com.risk.riskmanage.system.model;

import java.io.Serializable;

import com.risk.riskmanage.common.model.BasePage;

/**
 * ClassName:RoleVo <br/>
 * Description: 角色实体类. <br/>
 */
public class Role extends BasePage implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色编码
	 */
	private String roleCode;
	/**
	 * 角色描述
	 */
	private String descripttion;
	/**
	 * 状态
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDescripttion() {
		return descripttion;
	}

	public void setDescripttion(String descripttion) {
		this.descripttion = descripttion;
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
		return "Role [id=" + id + ", name=" + name + ", roleCode=" + roleCode
				+ ", descripttion=" + descripttion + ", status=" + status
				+ ", creator=" + creator + ", createTime=" + createTime
				+ ", updater=" + updater + ", updateTime=" + updateTime + "]";
	}
	
	
}
