package com.risk.riskmanage.util;

import com.risk.riskmanage.system.model.User;
import lombok.Data;

/**
 * session 包装类
 */
@Data
public class AccountSessionWrap {
	public AccountSessionWrap(String ip, String requestUri) {
		init(null, ip, requestUri);
	}

	public AccountSessionWrap(User user, String ip, String requestUri) {
		init(user, ip, requestUri);
	}

	private void init(User user, String ip, String requestUri) {
		setUser(user);
		setIp(ip);
		setRequestUri(requestUri);
	}

	/** session */
	private User user;
	private String ip;
	private String requestUri;
	/** 请求唯一标识 */
	private String traceId;

}