package com.risk.riskmanage.datamanage.vo;

import java.io.Serializable;

public class FieldFormulaVo implements Serializable {

	private static final long serialVersionUID = 1L;

	// [{fvalue: "0",formula: "a",farr: [{fieldCN:"引擎字段1-1",fieldCond:[{"inputOne":"c","inputThree":"5"},{"inputOne":"b","inputThree":"12"}]},{fieldCN:"通用字段2贷前",fieldCond:[{"inputOne":"(30,40]","inputThree":"5"},{"inputOne":"[45,51)","inputThree":"12"}]}]}];
	
	/**
	 * 衍生字段公式设置对应的值
	 * */
	private String fvalue;
	
	/**
	 * 衍生字段公式
	 * */
	private String formula;
	
	/**
	 * 衍生字段公式里字段的条件区域设置
	 * */
	private Integer idx;
	
	/**
	 * 衍生字段公式里字段的条件区域设置
	 * */
	private String farr;
	
	/**
	 * 衍生字段公式里条件区域设置的某个字段中文名
	 * */
	private String fieldCN;
	
	/**
	 * 衍生字段公式里条件区域设置的某个字段的具体设置
	 * */
	private String fieldCond;

	public String getFvalue() {
		return fvalue;
	}

	public void setFvalue(String fvalue) {
		this.fvalue = fvalue;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getFarr() {
		return farr;
	}

	public void setFarr(String farr) {
		this.farr = farr;
	}

	public String getFieldCN() {
		return fieldCN;
	}

	public void setFieldCN(String fieldCN) {
		this.fieldCN = fieldCN;
	}

	public String getFieldCond() {
		return fieldCond;
	}

	public void setFieldCond(String fieldCond) {
		this.fieldCond = fieldCond;
	}
	
}
