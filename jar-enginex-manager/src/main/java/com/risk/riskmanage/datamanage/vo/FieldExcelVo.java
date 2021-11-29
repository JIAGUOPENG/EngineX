package com.risk.riskmanage.datamanage.vo;

import java.util.Date;

public class FieldExcelVo {

	/**
	 * 主键
	 * */
	private Integer id;

	/**
	 * 字段英文名
	 * */
	private String fieldEn;

	/**
	 * 字段中文名
	 * */
	private String fieldCn;

	/**
	 * 字段类型名称
	 * */
	private String fieldType;

	/**
	 * 字段存值类型
	 * */
	private String valueType;

	/**
	 * 字段约束范围
	 * */
	private String valueScope;

	/**
	 * 是否衍生字段
	 * */
	private String isDerivative;

	/**
	 * 是否输出字段
	 * */
	private String isOutput;

	/**
	 * 衍生字段公式
	 * */
	private String formula;

	/**
	 * 创建人
	 * */
	private String author;

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

	public String getFieldEn() {
		return fieldEn;
	}

	public void setFieldEn(String fieldEn) {
		this.fieldEn = fieldEn;
	}

	public String getFieldCn() {
		return fieldCn;
	}

	public void setFieldCn(String fieldCn) {
		this.fieldCn = fieldCn;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValueScope() {
		return valueScope;
	}

	public void setValueScope(String valueScope) {
		this.valueScope = valueScope;
	}

	public String getIsDerivative() {
		return isDerivative;
	}

	public void setIsDerivative(String isDerivative) {
		this.isDerivative = isDerivative;
	}

	public String getIsOutput() {
		return isOutput;
	}

	public void setIsOutput(String isOutput) {
		this.isOutput = isOutput;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
