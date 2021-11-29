package com.risk.riskmanage.system.controller.v2;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.basefactory.CcpBaseController;
import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.common.model.BaseParam;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.engine.model.Engine;
import com.risk.riskmanage.logger.ArchivesLog;
import com.risk.riskmanage.system.model.SysMenu;
import com.risk.riskmanage.system.model.SysUser;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.system.model.response.SysMenuVo;
import com.risk.riskmanage.util.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller("sysMenuControllerV2")
@RequestMapping("v2/sysMenu")
@ResponseBody
public class SysMenuController extends CcpBaseController {

	/**
	 * @api {POST} /v2/sysMenu/getMenuList 6.31. 获取资源列表
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} pageNo 页数
	 * @apiParam {Integer} pageSize 每页的条数
	 * @apiSuccess {JSON} pager 分页信息
	 * @apiSuccess {JSONArray} listMenu 资源列表
	 * @apiSuccess (listMenu) {Long} id 资源编号
	 * @apiSuccess (listMenu) {Long} userId 分配者
	 * @apiSuccess (listMenu) {String} name 资源名称
	 * @apiSuccess (listMenu) {String} versionCode 资源代号
	 * @apiSuccess (listMenu) {String} url 资源路径
	 * @apiSuccess (listMenu) {Long} parentId 父节点
	 * @apiSuccess (listMenu) {String} des 资源描述
	 * @apiSuccess (listMenu) {Long} birth 创建时间
	 * @apiSuccess (listMenu) {String} icon 图标
	 * @apiSuccess (listMenu) {Integer} sort 顺序（值越小优先级越高）
	 * @apiSuccess (listMenu) {Integer} status 状态：0停用，1启用, -1删除
	 * @apiParamExample {json} 请求示例：
	 * {"pageNo":1,"pageSize":2}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":{"pager":{"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"total":17,"pages":9,"list":null,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"lastPage":8,"firstPage":1},"listMenu":[{"id":1,"userId":0,"name":"引擎管理","versionCode":"0001","url":"sysMenu/getChildMenu","parentId":0,"des":"引擎管理","birth":1498721562000,"icon":null,"sort":0,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"hidden":false},{"id":2,"userId":0,"name":"规则管理","versionCode":"0002","url":"sysMenu/getChildMenu","parentId":0,"des":"规则管理","birth":1498807962000,"icon":"bb","sort":5,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"hidden":false}]}}
	 */
	@RequestMapping(value = "getMenuList", method = RequestMethod.POST)
	public ResponseEntityDto getMenuList(@RequestBody BaseParam baseParam) {
		PageHelper.startPage(baseParam.getPageNo(),baseParam.getPageSize());
		// 获取所有菜单
		List<SysMenu> listMenu = s.sysMenuService.getAllSysMenu();
		PageInfo<SysMenu> pageInfo = new PageInfo<SysMenu>(listMenu);
		pageInfo.setList(null);
		HashMap<String, Object> modelMap = new HashMap<>();
		modelMap.put("listMenu", listMenu);
		modelMap.put("pager", pageInfo);
		return ResponseEntityBuilder.buildNormalResponse(modelMap);
	}

	/**
	 * @api {POST} /v2/sysMenu/save 6.32. 创建资源
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {String} name 资源名称
	 * @apiParam {String} versionCode 资源代号
	 * @apiParam {String} url 资源路径
	 * @apiParam {Long} parentId 父节点
	 * @apiParam {String} des 资源描述
	 * @apiParam {String} icon 图标
	 * @apiParam {Integer} sort 顺序（值越小优先级越高）
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"name":"测试资源","versionCode":"0066","url":"testMenu","parentId":0,"des":"测试资源描述","icon":"el-icon-eleme","sort":2}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":1}
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ArchivesLog(operationType = OpTypeConst.SAVE_SYS_MENU)
	public ResponseEntityDto save(@RequestBody SysMenu sysMenu) {
		List<SysMenu> list = s.sysMenuService.validateMenuOnly(sysMenu);
		if(list!=null&&list.size()>0){
			throw new ApiException(ErrorCodeEnum.CREATE_MENU_NAME_REPEAT.getCode(), ErrorCodeEnum.CREATE_MENU_NAME_REPEAT.getMessage());
		}
		int num = s.sysMenuService.createSysMenu(sysMenu);
		return ResponseEntityBuilder.buildNormalResponse(num);
	}

	/**
	 * @api {POST} /v2/sysMenu/getMenuInfo/{id} 6.33. 获取资源详情
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Long} id 资源编号（url参数）
	 * @apiSuccess {Long} id 资源编号
	 * @apiSuccess {Long} userId 分配者
	 * @apiSuccess {String} name 资源名称
	 * @apiSuccess {String} versionCode 资源代号
	 * @apiSuccess {String} url 资源路径
	 * @apiSuccess {Long} parentId 父节点
	 * @apiSuccess {String} des 资源描述
	 * @apiSuccess {Long} birth 创建时间
	 * @apiSuccess {String} icon 图标
	 * @apiSuccess {Integer} sort 顺序（值越小优先级越高）
	 * @apiSuccess {Integer} status 状态：0停用，1启用, -1删除
	 * @apiParamExample {json} 请求示例：
	 * {}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":{"id":32,"userId":135,"name":"测试资源","versionCode":"0066","url":"testMenu","parentId":0,"des":"测试资源描述","birth":1616760174000,"icon":"el-icon-eleme","sort":2,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"hidden":false}}
	 */
	@RequestMapping(value = "/getMenuInfo/{id}", method = RequestMethod.POST)
	public ResponseEntityDto getMenuInfo(@PathVariable long id) {
		SysMenu sysMenu = s.sysMenuService.findById(id);
		return ResponseEntityBuilder.buildNormalResponse(sysMenu);
	}

	/**
	 * @api {POST} /v2/sysMenu/update 6.34. 修改资源
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Long} id 资源编号
	 * @apiParam {String} name 资源名称
	 * @apiParam {String} versionCode 资源代号
	 * @apiParam {String} url 资源路径
	 * @apiParam {Long} parentId 父节点
	 * @apiParam {String} des 资源描述
	 * @apiParam {String} icon 图标
	 * @apiParam {Integer} sort 顺序（值越小优先级越高）
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"id":32,"name":"测试资源1","versionCode":"0067","url":"testMenu","parentId":0,"des":"测试资源描述","icon":"el-icon-eleme","sort":5}
	 * @apiSuccessExample {json} Success-Response:
	 * {"status":"1","error":"00000000","msg":null,"data":1}
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ArchivesLog(operationType = OpTypeConst.UPDATE_SYS_MENU)
	public ResponseEntityDto update(@RequestBody SysMenu sysMenu) {
		List<SysMenu> list = s.sysMenuService.validateMenuOnly(sysMenu);
		if(list!=null&&list.size()>0){
			throw new ApiException(ErrorCodeEnum.CREATE_MENU_NAME_REPEAT.getCode(), ErrorCodeEnum.CREATE_MENU_NAME_REPEAT.getMessage());
		}
		int num = s.sysMenuService.updateSysMenu(sysMenu);
		return ResponseEntityBuilder.buildNormalResponse(num);
	}

	/**
	 * @api {POST} /v2/sysMenu/updateStatus 6.35. 资源删除
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} status 状态：-1删除
	 * @apiParam {String} ids 资源编号，逗号分隔
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"status":-1,"ids":"26"}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":1}
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ArchivesLog(operationType = OpTypeConst.UPDATE_SYS_MENU_STATUS)
	public ResponseEntityDto updateStatus(@RequestBody Map<String, Object> param) {
		int status = (Integer) param.get("status");
		String ids = (String)param.get("ids");
		int num = 0;
		List<Integer> list = new ArrayList<Integer>();

		if (ids != "") {
			String[] strs = ids.split(",");
			for (int i = 0; i < strs.length; i++) {
				list.add(Integer.parseInt(strs[i]));
			}
		}

		if (list != null && list.size() > 0) {
			num = s.sysMenuService.updateStatus(status, list);
		}
		return ResponseEntityBuilder.buildNormalResponse(num);
	}

	/**
	 * @api {POST} /v2/sysMenu/getTreeMenu 6.36. 新增/修改资源获取父节点树
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} parentId 父节点Id
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"parentId":0}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":[{"id":22,"userId":0,"name":"模型管理","versionCode":"0007","url":"sysMenu/getChildMenu","parentId":0,"des":"模型管理","birth":1498980762000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false},{"id":23,"userId":0,"name":"数据源管理","versionCode":"0008","url":"sysMenu/getChildMenu","parentId":0,"des":"数据源管理","birth":1498984362000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false},{"id":24,"userId":0,"name":"黑白名单库管理","versionCode":"0009","url":"sysMenu/getChildMenu","parentId":0,"des":"黑白名单库管理","birth":1498987962000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false},{"id":25,"userId":0,"name":"评分卡管理","versionCode":"0010","url":"sysMenu/getChildMenu","parentId":0,"des":"评分卡管理","birth":1498897962000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false}]}
	 */
	@RequestMapping(value = "getTreeMenu", method = RequestMethod.POST)
	public ResponseEntityDto getTreeMenu(@RequestBody Map<String, Object> param){
		long parentId = Long.valueOf(param.get("parentId").toString());
		List<SysMenu> listMenu = s.sysMenuService.getAllValidMenu();
		if(listMenu!=null&&listMenu.size()>0){
			for(int i=0;i<listMenu.size();i++){
				if(listMenu.get(i).getId()==parentId){
					listMenu.get(i).setChecked(true);
				}
			}
		}
		return ResponseEntityBuilder.buildNormalResponse(listMenu);
	}
	
	/**
	 * @api {POST} /v2/sysMenu/findTreeList 6.37.1. 权限分配，获取资源树
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} roleId 角色编号
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"roleId":76}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":[{"id":22,"userId":0,"name":"模型管理","versionCode":"0007","url":"sysMenu/getChildMenu","parentId":0,"des":"模型管理","birth":1498980762000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false},{"id":23,"userId":0,"name":"数据源管理","versionCode":"0008","url":"sysMenu/getChildMenu","parentId":0,"des":"数据源管理","birth":1498984362000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false},{"id":24,"userId":0,"name":"黑白名单库管理","versionCode":"0009","url":"sysMenu/getChildMenu","parentId":0,"des":"黑白名单库管理","birth":1498987962000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false},{"id":25,"userId":0,"name":"评分卡管理","versionCode":"0010","url":"sysMenu/getChildMenu","parentId":0,"des":"评分卡管理","birth":1498897962000,"icon":null,"status":1,"roleId":0,"checked":false,"chkDisabled":false,"isHidden":false}]}
	 */
	@RequestMapping(value = "findTreeList", method = RequestMethod.POST)
	public ResponseEntityDto findTreeList(@RequestBody Map<String, Object> param){
		long roleId = Long.valueOf(param.get("roleId").toString());
		long organRoleId = 0;
		List<SysMenu> listAll = new ArrayList<SysMenu>();
		 //获取登录用户id
		User user = SessionManager.getLoginAccount();
		long userId = user.getUserId();
		long orgId = user.getOrganId();
		//获取登录人角色id
		SysUser sysUser = s.sysUserService.findRoleByUserId(userId);
		if(sysUser!=null){
			organRoleId = sysUser.getSysRole().getId();
		}
		//角色资源
		List<SysMenu> listRoleMenu = s.sysMenuService.findTreeList(roleId);
		if(orgId==1){
			//全部启用资源
			listAll = s.sysMenuService.getAllValidMenu(); 
			if(listAll!=null&&listAll.size()>0){
				for(int i=0;i<listAll.size();i++){
					//初始化禁用节点
					if(listAll.get(i).getId()==14 || listAll.get(i).getId()==13){
						listAll.get(i).setChkDisabled(true);
						listAll.get(i).setHidden(true);
					}
					//判断默认选中
					long id_i = listAll.get(i).getId();
					if (listRoleMenu != null && listRoleMenu.size() > 0) {
						for (int j = 0; j < listRoleMenu.size(); j++) {
							long id_j = listRoleMenu.get(j).getId();
							if (id_j == id_i) {
								listAll.get(i).setChecked(true);
							}
						}
					}
				}
			}
			
		}else{
			//公司资源
			listAll = s.sysMenuService.findTreeList(organRoleId);
			if(listAll!=null&&listAll.size()>0){
				for(int i=0;i<listAll.size();i++){
					//初始化禁用节点
					if(listAll.get(i).getId()==4){
						listAll.get(i).setChkDisabled(true);
						listAll.get(i).setHidden(true);
					}
					//判断默认选中
					long id_i = listAll.get(i).getId();
					if (listRoleMenu != null && listRoleMenu.size() > 0) {
						for (int j = 0; j < listRoleMenu.size(); j++) {
							long id_j = listRoleMenu.get(j).getId();
							if (id_j == id_i) {
								listAll.get(i).setChecked(true);
							}
						}
					}
				}
			}
		}
		return ResponseEntityBuilder.buildNormalResponse(listAll);
	}
	/**
	 * @api {POST} /v2/sysMenu/insertRoleMenu 6.38.1. 权限分配，保存、修改资源树
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} roleId 角色编号
	 * @apiParam {String} ids 资源编号，逗号分隔
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"roleId":76,"ids":"1,18,2,15,3,16,17,4,11,12,19"}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":11}
	 */
	@RequestMapping(value = "insertRoleMenu", method = RequestMethod.POST)
	@ArchivesLog(operationType = OpTypeConst.SAVE_OR_UPDATE_MENU_ROLE)
	public ResponseEntityDto insertRoleMenu(@RequestBody Map<String, Object> param) {
		long roleId = Long.valueOf(param.get("roleId").toString());
		String ids = (String)param.get("ids");
		int num = 0;
		List<Integer> list = new ArrayList<Integer>();

		if (ids != "") {
			String[] strs = ids.split(",");
			for (int i = 0; i < strs.length; i++) {
				list.add(Integer.parseInt(strs[i]));
			}
			if (list != null && list.size() > 0) {
				num = s.sysMenuService.insertRoleMenu(roleId, list);
			}
		}else{
			num = s.sysMenuService.deleteRoleMenu(roleId);
		}
		return ResponseEntityBuilder.buildNormalResponse(num);
	}
	
	/**
	 * @api {POST} /v2/sysMenu/insertRoleEngine 6.38.2. 权限分配，保存、修改引擎树
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiParam {Integer} roleId 角色编号
	 * @apiParam {String} ids 引擎Id，逗号分隔
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {"roleId":76,"ids":"1,1_217,11_217,111_217,112_217,1121_217,12_217,121_217,122_217,1221_217,123_217,1231_217,13_217,131_217,132_217,1321_217,133_217,1331_217,14_217,141_217,15_217,151_217"}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":22}
	 */
	@RequestMapping(value = "insertRoleEngine", method = RequestMethod.POST)
	@ArchivesLog(operationType = OpTypeConst.SAVE_ROLE_ENGINE)
	public ResponseEntityDto insertRoleEngine(@RequestBody Map<String, Object> param) {
		long roleId = Long.valueOf(param.get("roleId").toString());
		String ids = (String)param.get("ids");
		int num = 0;
		List<String> list = new ArrayList<String>();
		
		if (ids != "") {
			String[] strs = ids.split(",");
			for (int i = 0; i < strs.length; i++) {
				list.add(strs[i]);
			}
			if (list != null && list.size() > 0) {
				num = s.sysMenuService.insertRoleEngine(roleId, list);
			}
		}else{
			num = s.sysMenuService.deleteRoleEngine(roleId);
			//初始化公司管理员引擎权限：
			Engine engineVo = new Engine();
			User user = SessionManager.getLoginAccount();
			long organId = user.getOrganId();
			//查询角色所在公司
			long organ_id = s.sysRoleService.getOrganByRoleId(roleId);
			engineVo.setOrganId(organ_id);
			if(organId==1){
				String idstr = "1,";
				List<String> list_str = new ArrayList<String>();

			}
		}
		return ResponseEntityBuilder.buildNormalResponse(num);
	}

	/**
	 * @api {POST} /v2/sysMenu/getMenus 6.39. 获取菜单信息
	 * @apiGroup sysManager
	 * @apiVersion 2.0.0
	 * @apiSuccess {String} status 状态: 1成功, 0失败
	 * @apiParamExample {json} 请求示例：
	 * {}
	 * @apiSuccessExample {json} 成功返回数据示例：
	 * {"status":"1","error":"00000000","msg":null,"data":[{"title":"系统首页","index":"sysMenu/getChildMenu","icon":null,"subs":[]},{"title":"引擎列表","index":"sysMenu/getChildMenu","icon":"xx","subs":[]},{"title":"引擎管理","index":"sysMenu/getChildMenu","icon":null,"subs":[]},{"title":"指标管理","index":"sysMenu/getChildMenu","icon":null,"subs":[]},{"title":"规则管理","index":"sysMenu/getChildMenu","icon":"bb","subs":[]},{"title":"评分卡管理","index":"sysMenu/getChildMenu","icon":null,"subs":[]},{"title":"模型管理","index":"sysMenu/getChildMenu","icon":null,"subs":[]},{"title":"数据源管理","index":"sysMenu/getChildMenu","icon":null,"subs":[]},{"title":"黑白名单库管理","index":"sysMenu/getChildMenu","icon":null,"subs":[]},{"title":"系统管理","index":"sysMenu/getChildMenu","icon":null,"subs":[{"title":"用户管理","index":"sysUser/view","icon":null,"subs":[]},{"title":"角色管理","index":"sysRole/view","icon":"aa","subs":[]},{"title":"日志管理","index":"log/index","icon":null,"subs":[]}]}]}
	 */
	@RequestMapping(value = "getMenus", method = RequestMethod.POST)
	public ResponseEntityDto getMenus(){
		List<SysMenu> menuList = new ArrayList<>();
		User user = SessionManager.getLoginAccount();
		long orgId = user.getOrganId();
		long userId = user.getUserId();
		if(orgId==1){
			menuList = s.sysMenuService.getAllValidMenu();
		}else{
			long roleId = 0;
			SysUser sysUser = s.sysUserService.findRoleByUserId(userId);
			if(sysUser!=null){
				roleId = sysUser.getSysRole().getId();
			}
			menuList = s.sysMenuService.findTreeList(roleId);
		}

		long parentId = 0;
		List<SysMenuVo> result = recursionMenu(menuList, parentId);

		return ResponseEntityBuilder.buildNormalResponse(result);
	}

	/**
	 * 递归获取子菜单
	 * @param menuList
	 * @param parentId
	 * @return
	 */
	private List<SysMenuVo> recursionMenu(List<SysMenu> menuList, long parentId){
		List<SysMenuVo> sysMenuVoList = new ArrayList<>();
		for(SysMenu sysMenu : menuList) {
			if(sysMenu.getParentId() == parentId){
				SysMenuVo sysMenuVo = new SysMenuVo();
				sysMenuVo.setTitle(sysMenu.getName());
				sysMenuVo.setIndex(StringUtils.isBlank(sysMenu.getUrl()) ? UUID.randomUUID().toString() : sysMenu.getUrl());
				sysMenuVo.setIcon(sysMenu.getIcon());

				List<SysMenuVo> sysMenuVos = recursionMenu(menuList, sysMenu.getId());
				if(!sysMenuVos.isEmpty()){
					sysMenuVo.setSubs(sysMenuVos);
				}

				sysMenuVoList.add(sysMenuVo);
			}
		}
		return sysMenuVoList;
	}

}