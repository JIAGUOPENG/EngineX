package com.risk.riskmanage.system.controller.v2;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.basefactory.BaseController;
import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.common.model.BaseParam;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.logger.ArchivesLog;
import com.risk.riskmanage.system.model.SysUser;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.SessionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @apiDefine sysManager 6.系统管理
 */
@Controller("sysUserControllerV2")
@RequestMapping("v2/sysUser")
@ResponseBody
public class SysUserController extends BaseController{

	/**
	 * @api {POST} /v2/sysUser/getUserList 6.11. 获取用户列表
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} pageNo 页数
	 * @apiParam {Integer} pageSize 每页的条数
	 * @apiSuccess {JSON} pager 分页信息
	 * @apiSuccess {JSONArray} listUser 用户列表
	 * @apiSuccess (listUser) {Long} id 用户Id
	 * @apiSuccess (listUser) {Long} organId 组织编号
	 * @apiSuccess (listUser) {String} employeeId 员工编号
	 * @apiSuccess (listUser) {String} account 账号
	 * @apiSuccess (listUser) {String} nickName 姓名
	 * @apiSuccess (listUser) {String} cellphone 手机号
	 * @apiSuccess (listUser) {String} email 邮箱
	 * @apiSuccess (listUser) {Integer} status 状态：0停用，1启用, -1删除
	 * @apiSuccess (listUser) {String} author 创建人
	 * @apiSuccess (listUser) {Long} birth 创建时间
	 * @apiSuccess (listUser) {JSON} sysRole 角色信息
	 * @apiSuccess (sysRole) {Long} id 角色编号
	 * @apiSuccess (sysRole) {String} roleName 角色名称
	 * @apiSuccess (listUser) {JSON} sysOrgan 公司信息
	 * @apiSuccess (sysOrgan) {Long} id 组织编号
	 * @apiSuccess (sysOrgan) {String} name 组织名称
	 * @apiParamExample {json} 请求示例：
	 * {"pageNo":1,"pageSize":2}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":{"pager":{"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"total":12,"pages":6,"list":null,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6,"lastPage":6,"firstPage":1},"listUser":[{"id":149,"organId":46,"employeeId":"011","account":"rong360","password":"4m774i0~4m2&5n1c4i55296#2@1j010i","nickName":"rong","email":"11@qq.com","cellphone":"15222222222","qq":null,"latestTime":null,"latestIp":null,"status":1,"birth":1613801940000,"author":"超级管理员","sysRole":{"id":70,"organId":0,"roleName":"管理员","roleCode":null,"roleDesc":null,"author":null,"birth":null,"status":0},"sysOrgan":{"id":46,"name":"管理员","versionCode":null,"email":null,"telephone":null,"status":0,"author":null,"birth":null,"token":null}},{"id":148,"organId":46,"employeeId":"010","account":"yljr","password":"4m774i0~4m2&5n1c4i55296#2@1j010i","nickName":"yljr","email":"11@qq.com","cellphone":"15222222222","qq":null,"latestTime":null,"latestIp":null,"status":1,"birth":1613720093000,"author":"超级管理员","sysRole":{"id":70,"organId":0,"roleName":"管理员","roleCode":null,"roleDesc":null,"author":null,"birth":null,"status":0},"sysOrgan":{"id":46,"name":"管理员","versionCode":null,"email":null,"telephone":null,"status":0,"author":null,"birth":null,"token":null}}]}}
	 */
	@RequestMapping(value = "getUserList", method = RequestMethod.POST)
	public ResponseEntityDto<Object> getUserList(@RequestBody BaseParam baseParam){
		SysUser sysUser = new SysUser();
		//获取登录人所在公司
    	User user = SessionManager.getLoginAccount();
    	long organId = user.getOrganId();
    	sysUser.setOrganId(organId);
		PageHelper.startPage(baseParam.getPageNo(), baseParam.getPageSize());
		List<SysUser> listUser = s.sysUserService.getAllUsers(sysUser);
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(listUser);
		pageInfo.setList(null);
		HashMap<String, Object> modelMap = new HashMap<>();
		modelMap.put("listUser", listUser);
		modelMap.put("pager", pageInfo);
		return ResponseEntityBuilder.buildNormalResponse(modelMap);
	}
    
	/**
	 * @api {POST} /v2/sysUser/save 6.12. 创建用户
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Long} organId 组织编号
	 * @apiParam {String} employeeId 员工编号
	 * @apiParam {String} account 账号
	 * @apiParam {String} nickName 姓名
	 * @apiParam {String} cellphone 手机号
	 * @apiParam {String} email 邮箱
	 * @apiParam {JSON} sysRole 角色信息
	 * @apiParam (sysRole) {Long} id 角色编号
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"organId":46,"employeeId":"012","account":"testuser","nickName":"张三","email":"11@qq.com","cellphone":"15222222222","sysRole":{"id":71}}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":1}
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.SAVE_SYS_USER)
    public ResponseEntityDto<Object> save(@RequestBody SysUser sysUser){
    	String nickName = sysUser.getNickName();
    	if(nickName.equals("超级管理员")){
			throw new ApiException(ErrorCodeEnum.CREATE_USER_NAME_ERROR.getCode(), ErrorCodeEnum.CREATE_USER_NAME_ERROR.getMessage());
    	}
    	String account = sysUser.getAccount();
    	sysUser.setAccount(account);
    	//验证唯一性
    	List<SysUser> list = s.sysUserService.validateUserOnly(sysUser);
    	if(list!=null&&list.size()>0){
			throw new ApiException(ErrorCodeEnum.CREATE_USER_NAME_REPEAT.getCode(), ErrorCodeEnum.CREATE_USER_NAME_REPEAT.getMessage());
    	}
    	long num = s.sysUserService.createSysUser(sysUser);
    	return ResponseEntityBuilder.buildNormalResponse(num);
    }

	/**
	 * @api {POST} /v2/sysUser/getUserInfo/{id} 6.13. 获取用户详情
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Long} id 用户Id（url参数）
	 * @apiSuccess {Long} id 用户Id
	 * @apiSuccess {Long} organId 组织编号
	 * @apiSuccess {String} employeeId 员工编号
	 * @apiSuccess {String} account 账号
	 * @apiSuccess {String} nickName 姓名
	 * @apiSuccess {String} cellphone 手机号
	 * @apiSuccess {String} email 邮箱
	 * @apiSuccess {Integer} status 状态：0停用，1启用, -1删除
	 * @apiSuccess {String} author 创建人
	 * @apiSuccess {Long} birth 创建时间
	 * @apiSuccess {JSON} sysRole 角色信息
	 * @apiSuccess (sysRole) {Long} id 角色编号
	 * @apiSuccess (sysRole) {String} roleName 角色名称
	 * @apiSuccess {JSON} sysOrgan 公司信息
	 * @apiSuccess (sysOrgan) {Long} id 组织编号
	 * @apiSuccess (sysOrgan) {String} name 组织名称
	 * @apiParamExample {json} 请求示例：
	 * {}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":{"id":149,"organId":46,"employeeId":"011","account":"rong360","password":"4m774i0~4m2&5n1c4i55296#2@1j010i","nickName":"rong","email":"11@qq.com","cellphone":"15222222222","qq":null,"latestTime":null,"latestIp":null,"status":1,"birth":1613801940000,"author":"超级管理员","sysRole":{"id":70,"organId":0,"roleName":"管理员","roleCode":null,"roleDesc":null,"author":null,"birth":null,"status":0},"sysOrgan":{"id":46,"name":null,"versionCode":null,"email":null,"telephone":null,"status":0,"author":null,"birth":null,"token":null}}}
	 */
	@RequestMapping(value = "/getUserInfo/{id}", method = RequestMethod.POST)
	public ResponseEntityDto<Object> getUserInfo(@PathVariable long id) {
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		//获取用户所在公司
		User user = SessionManager.getLoginAccount();
		long organId = user.getOrganId();
		sysUser.setOrganId(organId);
		SysUser result = s.sysUserService.findById(sysUser);
		return ResponseEntityBuilder.buildNormalResponse(result);
	}

	/**
	 * @api {POST} /v2/sysUser/update 6.14. 修改用户
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Long} id 用户Id
	 * @apiParam {Long} organId 组织编号
	 * @apiParam {String} employeeId 员工编号
	 * @apiParam {String} account 账号
	 * @apiParam {String} nickName 姓名
	 * @apiParam {String} cellphone 手机号
	 * @apiParam {String} email 邮箱
	 * @apiParam {JSON} sysRole 角色信息
	 * @apiParam (sysRole) {Long} id 角色编号
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"id":150,"organId":46,"employeeId":"012","account":"testuser2","nickName":"张三","email":"11@qq.com","cellphone":"15222222222","sysRole":{"id":72}}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":1}
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDATE_SYS_USER)
    public ResponseEntityDto<Object> update(@RequestBody SysUser sysUser) {
    	String account = sysUser.getAccount();
    	sysUser.setAccount(account);
    	List<SysUser> list = s.sysUserService.validateUserOnly(sysUser);
    	if(list!=null&&list.size()>0){
			throw new ApiException(ErrorCodeEnum.CREATE_USER_NAME_REPEAT.getCode(), ErrorCodeEnum.CREATE_USER_NAME_REPEAT.getMessage());
    	}
    	int num = s.sysUserService.updateSysUser(sysUser);
		return ResponseEntityBuilder.buildNormalResponse(num);
    }
    
	/**
	 * @api {POST} /v2/sysUser/updateStatus 6.15. 用户停用、启用、删除
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} status 状态：0停用，1启用, -1删除
	 * @apiParam {String} ids 用户Id，逗号分隔
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"status":0,"ids":"150,151"}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":2}
	 */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDATE_SYS_USER_STATUS)
    public ResponseEntityDto<Object> updateStatus(@RequestBody Map<String, Object> param){
		int status = (Integer) param.get("status");
		String ids = (String)param.get("ids");
    	int num = 0;
    	List<Integer> list = new ArrayList<Integer>();
    	
    	if(ids!=""){
    		String[] strs = ids.split(",");
    	    for(int i=0;i<strs.length;i++){
    	       list.add(Integer.parseInt(strs[i]));
    	    }
    	}
        
    	if(list!=null && list.size()>0){
    		 num = s.sysUserService.updateStates(status,list);
    	}
    	return ResponseEntityBuilder.buildNormalResponse(num);
    }
    
	/**
	 * @api {POST} /v2/sysUser/updatePassword 6.16. 修改密码
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Long} id 用户Id
	 * @apiParam {String} password 新密码
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"id":136,"password":"654321"}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":1}
	 */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDTE_PASSWORD)
    public ResponseEntityDto updatePassword(@RequestBody SysUser sysUser){
    	int num = s.sysUserService.updatePassword(sysUser);
		return ResponseEntityBuilder.buildNormalResponse(num);
    }

}