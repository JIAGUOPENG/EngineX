package com.risk.riskmanage.knowledge.model;

import java.io.Serializable;

import com.risk.riskmanage.util.StringUtil;

/**
 * ClassName:RuleContentVo <br/>
 * Description: 规则内容实体类. <br/>
 * @see
 */
public class RuleContent implements Serializable{
	
	
	  private static final long serialVersionUID = 1L;
	  
	/**
	 * 主键
	 * */
	private Long id;
	  
	/**
	 * 字段名
	 * */
	private String field;
	
	/**
	 * 字段值
	 * */
	private String fieldValue; 
	
	/**
	 * 字段id
	 * */
	private String fieldId; 
	  
   /**
    * 规则Id
    * */
	private Long ruleId;
	/**
	 * 类型：1 常量、2 变量
	 */
	private Integer variableType;
	
    
    /**
     * 关联的字段的英文名称
     * */
    private String fieldEn;
	
	  /**
     * 关联的字段的值类型
     * */
    private Integer valueType;

	public Integer getVariableType() {
		return variableType;
	}

	public void setVariableType(Integer variableType) {
		this.variableType = variableType;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	/**
     * 关联的字段的取值范围
     * */
    private String valueScope;
    
    /**
     * 关联的字段的值拆解后的数组
     * */
    private String[] values;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
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
		if(!StringUtil.isBlank(valueScope)){
			if(valueType == 3){
				values = valueScope.split(",");
			}else{
				values =	new String[]{valueScope}; 
			}
		}else{
			values = null;
		}
		return values;
	}

	public String getFieldEn() {
		return fieldEn;
	}

	public void setFieldEn(String fieldEn) {
		this.fieldEn = fieldEn;
	}
}