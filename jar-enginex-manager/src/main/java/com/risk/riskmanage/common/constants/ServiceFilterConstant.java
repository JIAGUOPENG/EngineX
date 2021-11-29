package com.risk.riskmanage.common.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * 服务过滤配置 此处配置的uri都是不需要通过session管理，免token传参。
 */
public class ServiceFilterConstant {
	private static Set<String> uriSet = new HashSet<String>();
	static {
		uriSet.add("/Riskmanage/v2/login/login");// 密码登录
		uriSet.add("/Riskmanage/v2/datamanage/field/downTemplate");// 指标模板下载
		uriSet.add("/Riskmanage/v2/datamanage/listmanage/downTemplate");// 名单库模板下载
	}

	public static boolean isSessionFilter(String uri) {
		if (uriSet.contains(uri)) {
			return true;
		}
		return false;
	}
}
