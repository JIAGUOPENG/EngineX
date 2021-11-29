package com.risk.riskmanage.knowledge.model;

public class RuleExcel {
	
	/**
	 * 规则名称
	 * */
	private String name;
	
	/**
	 * 规则代码
	 * */
	private String code;
	
	/**
	 * 规则描述
	 * */
	private String description;
	
	/**
	 * 优先级
	 * */
	private Integer priority;
	
	/**
	 * 规则字段内容
	 * */
	private String  fieldContent;
	
	/**
	 * 规则内容
	 * */
	private String content;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getFieldContent() {
		return fieldContent;
	}
	public void setFieldContent(String fieldContent) {
		this.fieldContent = fieldContent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
