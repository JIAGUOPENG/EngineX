package com.risk.riskmanage.datamanage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.risk.riskmanage.common.model.BasePage;
import com.risk.riskmanage.datamanage.vo.FieldSubCondVo;

public class FieldCond extends BasePage implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 条件编号
	 * */
	private Long id;
	
	/**
	 * 字段编号
	 * */
	private Long fieldId;
	
	/**
	 * 字段条件值
	 * */
	private String conditionValue;
	
	/**
	 * 字段条件区域设置json格式
	 * */
	private String content;
	
	/**
	 * 条件字段编号
	 * */
	private Long condFieldId;
	
	/**
	 * 条件字段的运算符
	 * */
	private String condFieldOperator;
	
	/**
	 * 条件字段的条件设置值
	 * */
	private String condFieldValue;
	
	/**
	 * 条件字段间的逻辑符
	 * */
	private String condFieldLogical;
	
	/**
	 * 创建时间
	 * */
	private Date created;
	
	private List<FieldSubCondVo> fieldSubCond;

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

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getCondFieldId() {
		return condFieldId;
	}

	public void setCondFieldId(Long condFieldId) {
		this.condFieldId = condFieldId;
	}

	public String getCondFieldOperator() {
		return condFieldOperator;
	}

	public void setCondFieldOperator(String condFieldOperator) {
		this.condFieldOperator = condFieldOperator;
	}

	public String getCondFieldValue() {
		return condFieldValue;
	}

	public void setCondFieldValue(String condFieldValue) {
		this.condFieldValue = condFieldValue;
	}

	public String getCondFieldLogical() {
		return condFieldLogical;
	}

	public void setCondFieldLogical(String condFieldLogical) {
		this.condFieldLogical = condFieldLogical;
	}

	public List<FieldSubCondVo> getFieldSubCond() {
		return fieldSubCond;
	}

	public void setFieldSubCond(List<FieldSubCondVo> fieldSubCond) {
		this.fieldSubCond = fieldSubCond;
	}
	
}
