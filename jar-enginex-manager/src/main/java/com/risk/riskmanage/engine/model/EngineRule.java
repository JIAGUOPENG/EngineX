package com.risk.riskmanage.engine.model;

import java.util.Map;

public class EngineRule {

	private String refused;
	
	private String code ;
	
	private String policyName;
	
	private String desc;
	
	private String Strtus;
	
	
	private Map<String, String >fields;

	
	public String getStrtus() {
		return Strtus;
	}

	public void setStrtus(String strtus) {
		Strtus = strtus;
	}

	public String getRefused() {
		return refused;
	}

	public void setRefused(String refused) {
		this.refused = refused;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
	
	
	
	
	
}
