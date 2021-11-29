package com.risk.riskmanage.datamanage.model;

import java.io.Serializable;

import com.risk.riskmanage.common.model.BasePage;

public class FormulaField extends BasePage implements Serializable {

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
	 * 公式用到的字段编号（表主键）
	 * */
	private Long formulaFieldId;
	
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
	public Long getFormulaFieldId() {
		return formulaFieldId;
	}
	public void setFormulaFieldId(Long formulaFieldId) {
		this.formulaFieldId = formulaFieldId;
	}
	
}
