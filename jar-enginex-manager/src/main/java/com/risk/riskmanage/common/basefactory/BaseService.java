package com.risk.riskmanage.common.basefactory;

import com.risk.riskmanage.datamanage.mapper.*;
import com.risk.riskmanage.knowledge.mapper.*;
import com.risk.riskmanage.logger.mapper.LoggerMapper;
import com.risk.riskmanage.system.mapper.*;

import javax.annotation.Resource;



/**
 * @ClassName: BaseService <br/>
 * @Description: 基础service,统一注入mapper接口. <br/>
 */
public abstract  class BaseService {

	
	/**
	 * 后台角色管理mapper.
	 */
	@Resource
	public RoleMapper roleMapper;
	
	/**
	 * 后台部门管理mapper.
	 */
	@Resource
	public DepartmentMapper departmentMapper;
    
	/**
	 * 后台用户管理mapper.
	 */
	@Resource
	public UserMapper userMapper;
	

	/**
	 *菜单管理
	 */
	@Resource
	public MenuMapper menuMapper;

	/**
	 * 用户管理
	 */
	@Resource
	public SysUserMapper sysUserMapper;
	
	/**
	 * 角色管理
	 */
	@Resource
	public SysRoleMapper sysRoleMapper;
	
	/**
	 * 菜单管理
	 */
	@Resource
	public SysMenuMapper sysMenuMapper;
	
	/**
	 * 组织管理
	 */
	@Resource
	public SysOrganizationMapper sysOrganizationMapper;
	
	/**
	 * 知识库目录管理
	 */
	@Resource
	public KnowledgeTreeMapper knowledgeTreeMapper;
	
	/**
	 * 规则管理树形目录与引擎关系管理
	 */
	@Resource
	public KnowledgeTreeRelMapper knowledgeTreeRelMapper;
	
	/**
	 * 规则管理
	 */
	@Resource
	public RuleMapper ruleMapper;
	
	/**
	 * 规则字段管理
	 */
	@Resource
	public RuleFieldMapper ruleFieldMapper;
	
	/**
	 * 规则内容管理
	 */
	@Resource
	public RuleContentMapper ruleContentMapper;
	/**
	 * 字段类型管理
	 */
	@Resource
	public FieldTypeMapper fieldTypeMapper;
	
	/**
	 * 字段类型用户关系管理
	 */
	@Resource
	public FieldTypeUserMapper fieldTypeUserMapper;
	
	/**
	 * 字段管理
	 */
	@Resource
	public FieldMapper fieldMapper;
	
	/**
	 * 字段用户关系管理
	 */
	@Resource
	public FieldUserMapper fieldUserMapper;

	/**
	 * 用户字段条件区域设置
	 */
	@Resource
	public FieldCondMapper fieldCondMapper;

	/**
	 * 引擎与引用规则关系
	 */
	@Resource
	public  EngineRuleRelMapper engineRuleRelMapper;

	/**
	 * 日志mapper
	 */
	@Resource
	public LoggerMapper loggerMapper;

}
