package com.risk.riskmanage.datamanage.model;

import java.io.Serializable;
import java.util.Date;

import com.risk.riskmanage.common.model.BasePage;

public class FieldTypeUser extends BasePage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 * */
	private Integer id;
	
	/**
	 * 字段类型编号（表主键）
	 * */
	private Integer fieldTypeId;
	
	/**
	 * 该字段类型归属的组织编号
	 * */
	private Long organId;
	
	/**
	 * 该字段类型归属的引擎id（表主键）
	 * */
	private Integer engineId;
	
	/**
	 * 创建或修改该字段的用户编号
	 * */
	private Long userId;
	
	/**
	 * 创建时间
	 * */
	private Date created;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFieldTypeId() {
		return fieldTypeId;
	}
	public void setFieldTypeId(Integer fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	public Integer getEngineId() {
		return engineId;
	}
	public void setEngineId(Integer engineId) {
		this.engineId = engineId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

}
