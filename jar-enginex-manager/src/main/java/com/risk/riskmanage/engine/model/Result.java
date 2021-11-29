package com.risk.riskmanage.engine.model;

import java.util.List;
import java.util.Map;

public class Result {
	private String resultType;//规则1代表加减法，2拒绝规则
	private Integer id;//规则编号
	private String code;//规则code
	private String name;
	private String value;
	private Map<String, Object> map;//评分
	private List<EngineRule> list;
	
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<EngineRule> getList() {
		return list;
	}
	public void setList(List<EngineRule> list) {
		this.list = list;
	}
	
	
}
