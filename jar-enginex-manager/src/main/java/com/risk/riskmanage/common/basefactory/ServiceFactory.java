package com.risk.riskmanage.common.basefactory;

import com.risk.riskmanage.config.ConfigHolder;
import com.risk.riskmanage.datamanage.service.FieldService;
import com.risk.riskmanage.datamanage.service.FieldTypeService;
import com.risk.riskmanage.knowledge.service.*;
import com.risk.riskmanage.logger.service.LogService;
import com.risk.riskmanage.system.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: ServiceFactory <br/>
 * @Description: TODO ADD FUNCTION. <br/>
 */
@Service("s")
public class ServiceFactory {

	

	
	/**
	 * 后台用户管理Service.
	 */
	@Resource
	public UserService userService;
	

	
	/**
	 * 用户管理
	 */
	@Resource
	public SysUserService sysUserService;
	
	/**
	 * 角色管理
	 */
	@Resource
	public SysRoleService sysRoleService;
	
	/**
	 * 菜单管理
	 */
	@Resource
	public SysMenuService sysMenuService;
	
	
	/**
	 * 组织管理
	 */
	@Resource
	public SysOrganizationService sysOrganizationService;
	
	/**
	 * 知识库目录管理service
	 * */
	@Resource
	public KnowledgeTreeService knowledgeTreeService;
	

	
	/**
	 * 规则管理service
	 * */
	@Resource
	public RuleService ruleService;
	/**
	 * 字段类型管理
	 */
	@Resource
	public FieldTypeService fieldTypeService;
	
	/**
	 * 字段管理
	 */
	@Resource
	public FieldService fieldService;
	/**
	 * 日志service
	 * */
	@Resource
	public LogService loggerService;

	/**
	 * 配置中心
	 */
	@Resource
	public ConfigHolder configHolder;

}
