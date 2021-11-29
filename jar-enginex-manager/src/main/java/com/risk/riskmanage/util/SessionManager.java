package com.risk.riskmanage.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.risk.riskmanage.system.model.User;

/**
 * session管理类
 */
public class SessionManager {

	private static TransmittableThreadLocal<AccountSessionWrap> session = new TransmittableThreadLocal<AccountSessionWrap>() {

	};
	public static AccountSessionWrap getSession() {
		return session.get();
	}

	public static void setSession(AccountSessionWrap conn) {
		session.set(conn);
	}

	public static User getLoginAccount(){
		if(getSession() != null){
			return getSession().getUser();
		} else {
			return null;
		}
	}
}