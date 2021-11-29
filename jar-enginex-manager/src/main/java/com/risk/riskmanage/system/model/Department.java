package com.risk.riskmanage.system.model;

import java.io.Serializable;

import com.risk.riskmanage.common.model.BasePage;

/**
 * ClassName:DepartmentVo <br/>
 * Description: 后台部门管理实体类. <br/>
 */
public class Department extends BasePage implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 部门id
	 */
	private Long id;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 部门编号
	 */
	private String deptCode;
	/**
	 * 部门排序
	 */
	private Integer deptOrder;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Integer getDeptOrder() {
		return deptOrder;
	}

	public void setDeptOrder(Integer deptOrder) {
		this.deptOrder = deptOrder;
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
}
