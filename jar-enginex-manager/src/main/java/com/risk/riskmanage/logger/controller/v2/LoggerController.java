package com.risk.riskmanage.logger.controller.v2;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.basefactory.BaseController;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.logger.model.Logger;
import com.risk.riskmanage.logger.model.request.LoggerParam;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("loggerControllerV2")
@RequestMapping("v2/sysLog")
@ResponseBody
public class LoggerController extends BaseController {

	/**
	 * @api {POST} /v2/sysLog/getLogList 6.51. 获取日志列表
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} pageNo 页数
	 * @apiParam {Integer} pageSize 每页的条数
	 * @apiParam {String} searchKey 搜索关键字
	 * @apiParam {String} startDate 搜索开始时间
	 * @apiParam {String} endDate 搜索结束时间
	 * @apiSuccess {JSON} pager 分页信息
	 * @apiSuccess {JSONArray} logList 日志列表
	 * @apiSuccess (logList) {Long} id 日志Id
	 * @apiSuccess (logList) {String} opType 操作类型
	 * @apiSuccess (logList) {String} organName 公司名称
	 * @apiSuccess (logList) {String} opName 操作名称
	 * @apiSuccess (logList) {Long} opUserId 操作人员id
	 * @apiSuccess (logList) {String} opUserName 操作人员名称
	 * @apiSuccess (logList) {Long} organId 组织id
	 * @apiSuccess (logList) {String} method 方法名
	 * @apiSuccess (logList) {String} requestPath 请求地址
	 * @apiSuccess (logList) {String} requestParam 请求参数
	 * @apiSuccess (logList) {String} responseParam 响应参数
	 * @apiSuccess (logList) {String} ip 请求ip
	 * @apiSuccess (logList) {Long} startTime 开始时间
	 * @apiSuccess (logList) {Long} endTime 结束时间
	 * @apiParamExample {json} 请求示例：
	 * {"pageNo":1,"pageSize":2,"searchKey":"修改","startDate":"2021-3-11","endDate":"2021-3-21"}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":{"pager":{"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"total":283,"pages":142,"list":null,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"firstPage":1,"lastPage":8},"logList":[{"id":5345,"opType":"updateSysMenu","organName":"rik","opName":"修改系统菜单","opUserId":1,"opUserName":"超级管理员","organId":1,"method":"update","requestPath":"http://localhost:8080/Riskmanage/v2/sysMenu/update","requestParam":"}","responseParam":"ResponseEntityDto(super=com.risk.riskmanage.common.model.ResponseEntityDto@8bc7d62e, status=1, error=00000000, msg=null, data=1)","ip":"0:0:0:0:0:0:0:1","startTime":1616251925000,"endTime":1616251938000},{"id":5342,"opType":"updateOrgan","organName":"rik","opName":" 修改组织信息","opUserId":1,"opUserName":"超级管理员","organId":1,"method":"update","requestPath":"http://localhost:8080/Riskmanage/v2/sysOrganization/update","requestParam":"}","responseParam":"ResponseEntityDto(super=com.risk.riskmanage.common.model.ResponseEntityDto@8bc7d62e, status=1, error=00000000, msg=null, data=1)","ip":"0:0:0:0:0:0:0:1","startTime":1616234345000,"endTime":1616234352000}]}}
	 */
	@RequestMapping(value = "getLogList", method = RequestMethod.POST)
	public ResponseEntityDto getLogList(@RequestBody LoggerParam loggerParam){
		Map<String,Object> param = JSONObject.parseObject(JSONObject.toJSONString(loggerParam), Map.class);
    	User user = SessionManager.getLoginAccount();
		if(!user.getNickName().equals("超级管理员")){
			param.put("organId", user.getOrganId());
		}
		PageHelper.startPage(loggerParam.getPageNo(), loggerParam.getPageSize());
		List<Logger> logList = s.loggerService.getLogList(param);
		PageInfo<Logger> pageInfo = new PageInfo<Logger>(logList);
		pageInfo.setList(null);
		HashMap<String, Object> modelMap = new HashMap<>();
		modelMap.put("pager", pageInfo);
		modelMap.put("logList", logList);
		return ResponseEntityBuilder.buildNormalResponse(modelMap);
	}

	/**
	 * @api {POST} /v2/sysLog/getLogInfo/{id} 6.52. 获取日志详情
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Long} id 日志Id（url参数）
	 * @apiSuccess {Long} id 日志Id
	 * @apiSuccess {String} opType 操作类型
	 * @apiSuccess {String} organName 公司名称
	 * @apiSuccess {String} opName 操作名称
	 * @apiSuccess {Long} opUserId 操作人员id
	 * @apiSuccess {String} opUserName 操作人员名称
	 * @apiSuccess {Long} organId 组织id
	 * @apiSuccess {String} method 方法名
	 * @apiSuccess {String} requestPath 请求地址
	 * @apiSuccess {String} requestParam 请求参数
	 * @apiSuccess {String} responseParam 响应参数
	 * @apiSuccess {String} ip 请求ip
	 * @apiSuccess {Long} startTime 开始时间
	 * @apiSuccess {Long} endTime 结束时间
	 * @apiParamExample {json} 请求示例：
	 * {}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":{"id":5342,"opType":"updateOrgan","organName":"rik","opName":" 修改组织信息","opUserId":1,"opUserName":"超级管理员","organId":1,"method":"update","requestPath":"http://localhost:8080/Riskmanage/v2/sysOrganization/update","requestParam":"}","responseParam":"ResponseEntityDto(super=com.risk.riskmanage.common.model.ResponseEntityDto@8bc7d62e, status=1, error=00000000, msg=null, data=1)","ip":"0:0:0:0:0:0:0:1","startTime":1616234345000,"endTime":1616234352000}}
	 */
	@RequestMapping(value = "getLogInfo/{id}", method = RequestMethod.POST)
	public ResponseEntityDto getLogInfo(@PathVariable Long id){
		Logger logger = s.loggerService.findById(id);
		return ResponseEntityBuilder.buildNormalResponse(logger);
	}

}
