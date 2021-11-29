package com.risk.riskmanage.common.exception;


/**
 * 自定义异常消息处理
 */
public class ApiException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1136843834946392402L;

	/**
	 * 异常编码
	 */
	public final String errCode;

	/**
	 * 异常消息
	 */
	public final String message;

	/**
	 * data
	 */
	public final Object data;

	public ApiException(Throwable e) {
		super(e);
		errCode = "";
		message = "";
		data = null;
	}

	public ApiException(String errCode, String message) {
		super(message);
		this.errCode = errCode;
		this.message = message;
		this.data = null;
	}

	public ApiException(String errCode, String message, Object data) {
		super(message);
		this.errCode = errCode;
		this.message = message;
		this.data = data;
	}

	public ApiException(String errCode, String message, Throwable e) {
		super(message, e);
		this.errCode = errCode;
		this.message = message;
		this.data = null;
	}

}
