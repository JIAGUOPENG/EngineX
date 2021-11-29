package com.risk.riskmanage.engine.model;

import java.util.List;
import java.util.Map;

public class InputParam {
	private Map<String ,Object> inputParam;
	
	private List<Result>  result;

	public Map<String, Object> getInputParam() {
		return inputParam;
	}

	public void setInputParam(Map<String, Object> inputParam) {
		this.inputParam = inputParam;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}


	
	
	
	

}
