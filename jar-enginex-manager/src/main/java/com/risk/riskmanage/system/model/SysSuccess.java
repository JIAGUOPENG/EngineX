package com.risk.riskmanage.system.model;

public class SysSuccess {
	
	private boolean success;
	private String msg;
	
	public SysSuccess() {
	}
	
	public SysSuccess(boolean value, String msg) {
		super();
		this.success = value;
		this.msg = msg;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "SysSuccess [success=" + success + ", msg=" + msg + "]";
	}
	
	
	
}
