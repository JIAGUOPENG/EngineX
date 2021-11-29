
package com.risk.riskmanage.system.controller.v2;

import com.alibaba.fastjson.JSONObject;
import com.risk.riskmanage.common.basefactory.BaseController;
import com.risk.riskmanage.common.constants.Constants;
import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.logger.ArchivesLog;
import com.risk.riskmanage.redis.RedisManager;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.model.request.LoginInfoParam;
import com.risk.riskmanage.util.AccountSessionWrap;
import com.risk.riskmanage.util.MD5;
import com.risk.riskmanage.util.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @apiDefine account 1. 账户
 */
@Controller("loginControllerV2")
@RequestMapping("/v2/login/*")
public class LoginController extends BaseController{

	@Autowired
	private RedisManager redisManager;

	/**
	 * @api {POST} /v2/login/login 1.01. 用户登录
	 * @apiGroup account
	 * @apiVersion 1.0.1
	 * @apiParam {String} account 账号
	 * @apiParam {String} password 密码
	 * @apiSuccess {String} token 会话token
	 * @apiParamExample {json} Request:
	 * {"account":"admin","password":"123456"}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":{"token":"21fd6379df134ea590a462e4de1f6b33"}}
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	@ArchivesLog(operationType = OpTypeConst.LOGIN)
    public ResponseEntityDto<Object> login(@RequestBody LoginInfoParam param) {
    	Map<String, Object> map = new HashMap<>();
		String account = param.getAccount();
		String password = param.getPassword();
        if(!("".equals(account)) && !("".equals(password))){
			User user = s.userService.selectLoginInfo(account.trim(), MD5.GetMD5Code(password));
			if(null != user && user.getStatus()==1){
				String token = UUID.randomUUID().toString().replaceAll("-", "");
				redisManager.set(token, JSONObject.toJSONString(user), Constants.LOGIN_TOKEN_TIME.intValue());
				map.put("token", token);

				AccountSessionWrap acsw = new AccountSessionWrap(null, null);
				acsw.setUser(user);
				SessionManager.setSession(acsw);
			}else{
				return ResponseEntityBuilder.buildErrorResponse(ErrorCodeEnum.LOGIN_ERROR);
			}
        }
		return ResponseEntityBuilder.buildNormalResponse(map);
    }

	/**
	 * @api {POST} /v2/login/logout 1.02. 用户登出
	 * @apiGroup account
	 * @apiVersion 1.0.1
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} Request:
	 * {}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":null}
	 */
	@ResponseBody
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	@ArchivesLog(operationType = OpTypeConst.LOGOUT)
	public ResponseEntityDto<Object> logout(HttpServletRequest request) {
		String token = request.getHeader(Constants.SYSTEM_KEY_TOKEN);
		if(StringUtils.isNotBlank(token)){
			redisManager.del(token);
		}
		return ResponseEntityBuilder.buildNormalResponse();
	}

}

