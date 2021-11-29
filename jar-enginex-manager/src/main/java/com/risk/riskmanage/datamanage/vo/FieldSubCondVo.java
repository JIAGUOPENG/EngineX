package com.risk.riskmanage.datamanage.vo;

import java.io.Serializable;

public class FieldSubCondVo implements Serializable{

	private static final long serialVersionUID = 1L;

	//[{"fieldId":"43","operator":"in","fieldValue":"b","logical":"and"}]
	
	/**
	 * 条件字段编号
	 * */
	private Integer fieldId;
	
	/**
	 * 条件字段的运算符
	 * */
	private String operator;
	
	/**
	 * 条件字段的条件设置值
	 * */
	private String fieldValue;
	
	/**
	 * 条件字段间的逻辑符
	 * */
	private String logical;
	
	/**
	 * 条件字段的值类型
	 * */
	private Integer valueType;
	
	/**
	 * 条件字段的取值范围
	 * */
	private String valueScope;
	
	/**
     * 条件字段的取值范围拆解后的数组
     * */
    private String[] values;
    
    /**
     * 条件字段的字段名
     */
	private String fieldCn;
	
	
	public Integer getFieldId() {
		return fieldId;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getLogical() {
		return logical;
	}
	public void setLogical(String logical) {
		this.logical = logical;
	}
	public Integer getValueType() {
		return valueType;
	}
	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	public String getValueScope() {
		return valueScope;
	}
	public void setValueScope(String valueScope) {
		this.valueScope = valueScope;
	}
	public String[] getValues() {
		if(valueType == 3){
			values = valueScope.split(",");
		}else{
			values = new String[]{valueScope}; 
		}
		return values;
	}
	public void setValues(String[] values) {
		this.values = values;
	}
	public String getFieldCn() {
		return fieldCn;
	}
	public void setFieldCn(String fieldCn) {
		this.fieldCn = fieldCn;
	}
	
	
	
}
