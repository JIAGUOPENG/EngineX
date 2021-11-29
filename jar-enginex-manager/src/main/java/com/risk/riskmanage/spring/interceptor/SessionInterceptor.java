package com.risk.riskmanage.spring.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.risk.riskmanage.common.constants.Constants;
import com.risk.riskmanage.common.constants.ServiceFilterConstant;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.redis.RedisManager;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.AccountSessionWrap;
import com.risk.riskmanage.util.CommonUtil;
import com.risk.riskmanage.util.RequestUtil;
import com.risk.riskmanage.util.SessionManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 *  会话拦截器
 */
@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RedisManager redisManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String ip = RequestUtil.getClientIP(request);
		AccountSessionWrap acsw = new AccountSessionWrap(ip, uri);
		String reqUuid = CommonUtil.getUUID();
		String requestMethod = request.getMethod();
		StringBuffer requestURL = request.getRequestURL();
		acsw.setTraceId(reqUuid);
		String token = request.getHeader(Constants.SYSTEM_KEY_TOKEN);
		logger.debug("===>> 【会话拦截】-BEGIN: {} - {} {}, ### traceId:{},{}, token:{} ### ===>>", ip, requestMethod, requestURL, reqUuid, uri, token);
		SessionManager.setSession(acsw);
		if (ServiceFilterConstant.isSessionFilter(uri)) {
			return true;
		}
		if (StringUtils.isBlank(token)) {
			output(response, ErrorCodeEnum.ERROR_TOKEN_EXPIRE.getCode(),ErrorCodeEnum.ERROR_TOKEN_EXPIRE.getMessage());
			return false;
		}

		try {
			String value = redisManager.get(token);
			if(StringUtils.isBlank(value)){
				output(response, ErrorCodeEnum.ERROR_TOKEN_EXPIRE.getCode(),ErrorCodeEnum.ERROR_TOKEN_EXPIRE.getMessage());
				return false;
			}

			// token更新频率，设置离过期时间还剩n秒以内才更新一次token
			Long time = redisManager.ttl(token);
			if(time.intValue() <= Constants.LOGIN_TOKEN_REFRESH_TIME.intValue()){
				redisManager.set(token, value, Constants.LOGIN_TOKEN_TIME.intValue());
			}

			User user = JSONObject.parseObject(value, User.class);
			acsw.setUser(user);
		} catch (ApiException e1) {
			output(response, e1.errCode, e1.getMessage());
			return false;
		} catch (Exception e) {
			logger.error("【会话拦截】调用Token验证服务异常,uri:{}，token:{},IP:{}",uri,token,ip,e);
			output(response, ErrorCodeEnum.SERVER_ERROR.getCode(), ErrorCodeEnum.SERVER_ERROR.getMessage());
			return false;
		}
		
		return true;
	}

	private void output(HttpServletResponse response, String errCode, String errMsg) {
		ResponseEntityDto ret = ResponseEntityBuilder.buildErrorResponse(errCode, errMsg);
		try {
			logger.info("【会话拦截】未通过,{\"errCode\":" + errCode + ",\"errMsg:\":" + errMsg + "}");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			byte[] jsonBytes = JSON.toJSONBytes(ret);
			OutputStream output = response.getOutputStream();
			output.write(jsonBytes);
			output.flush();
		} catch (IOException e) {
			logger.error("【会话拦截】输出响应报文异常！,{},{}",errCode,errMsg, e);
		}
	}
}