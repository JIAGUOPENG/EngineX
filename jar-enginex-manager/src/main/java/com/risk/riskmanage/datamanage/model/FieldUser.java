package com.risk.riskmanage.datamanage.model;

import java.io.Serializable;
import java.util.Date;

import com.risk.riskmanage.common.model.BasePage;

public class FieldUser extends BasePage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * */
	private Long id;
	
	/**
	 * 字段编号（表主键）
	 * */
	private Long fieldId;
	
	/**
	 * 该字段归属的组织编号
	 * */
	private Long organId;
	
	/**
	 * 该字段归属的引擎id（表主键）
	 * */
	private Long engineId;
	
	/**
	 * 创建或修改该字段的用户编号
	 * */
	private Long userId;
	
	/**
	 * 启用停用删除标志
	 * */
	private int status;
	
	/**
	 * 创建时间
	 * */
	private Date created;
	
	/**
	 * 更新时间
	 * */
	private Date updated;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	public Long getEngineId() {
		return engineId;
	}
	public void setEngineId(Long engineId) {
		this.engineId = engineId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
