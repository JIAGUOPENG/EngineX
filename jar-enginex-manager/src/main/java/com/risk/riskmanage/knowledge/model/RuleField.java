package com.risk.riskmanage.knowledge.model;

import java.io.Serializable;

import com.risk.riskmanage.util.StringUtil;

/**
 * ClassName:RuleFieldVo <br/>
 * Description: 规则字段实体类. <br/>
 * @see
 */
public class RuleField implements Serializable{
	

	 private static final long serialVersionUID = 1L;

    /**
     * 主键
     * */
    private Long id;
    
    /**
     * 逻辑运算符
     * */
    private String logical;
    
    /**
     * 字段内容
     * */
    private String field;
    
    /**
     * 运算符
     * */
    private String  operator;
    
    /**
     * 字段值
     * */
    private String fieldValue;
   
    /**
     * 关联的规则的id
     * */
    private Long ruleId;
   
    /**
     * 关联的字段的id
     * */
    private String fieldId;
    
    /**
     * 关联的字段的英文名称
     * */
    private String fieldEn;
    
    /**
     * 关联的字段的值类型
     * */
    private Integer valueType;
    
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
	
	public String getLogical() {
		return logical;
	}

	public void setLogical(String logical) {
		this.logical = logical;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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

	public Long getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getFieldId() {
		return fieldId;
	}
	
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
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
