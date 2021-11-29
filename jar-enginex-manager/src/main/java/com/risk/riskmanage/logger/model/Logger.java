package com.risk.riskmanage.logger.model;

import java.util.Date;

/**
 * ClassName:Logger <br/>
 * Description: 日志实体类. <br/>
 */
public class Logger {
       
	  /**
	   * 主键
	   * */
	  private Long id; 
	  
	  /**
	   * 操作类型
	   * */
	  private String opType;
	  
	  
	  /**
	   * 公司名称
	   * */
	  private String organName;
	  
	  /**
	   * 操作名称
	   * */
	  private String opName;
	  
	  /**
	   * 操作人员id
	   * */
	  private Long opUserId;
	  
	  /**
	   * 操作人员id
	   * */
	  private String opUserName;
	  
	  /**
	   * 组织id
	   * */
	  private Long organId;
	  
	  /**
	   * 方法名
	   * */
	  private String method;
	  
	  /**
	   * 请求地址
	   * */
	  private String requestPath;
	  
	  /**
	   * 请求参数
	   * */
	  private String requestParam;
	  
	  /**
	   * 响应参数
	   * */
	  private String responseParam;
	  
	  /**
	   * 请求ip
	   * */
	  private String ip;
	  
	  /**
	   * 开始时间
	   * */
	  private Date startTime;
	  /**
	   * 结束时间
	   * */
	  private Date endTime;
	  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public Long getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(Long opUserId) {
		this.opUserId = opUserId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	public String getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	public String getResponseParam() {
		return responseParam;
	}
	public void setResponseParam(String responseParam) {
		this.responseParam = responseParam;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
	
}
