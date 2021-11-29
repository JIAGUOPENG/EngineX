package com.risk.riskmanage.common.constants;

public enum OpTypeEnum {
	
	/**
	 * 登入
	 * */
	LOGIN ("login",OpTypeConst.LOGIN),
	
	/**
	 * 登出
	 * */
	LOGOUT("logout",OpTypeConst.LOGOUT),

	/**
	 * 保存菜单
	 * */
	SAVE_MENU("saveMenu",OpTypeConst.SAVE_MENU),
	
	/**
	 * 修改菜单
	 * */
	UPDATE_MENU("updateMenu",OpTypeConst.UPDATE_MENU),

	/**
	 * 删除菜单
	 * */
	DELETE_MENU("deleteMenu",OpTypeConst.DELETE_MENU),
	
	
	/**
	 * 保存相应角色的菜单权限
	 * */
	SAVE_MENU_ROLE("saveMenuRole",OpTypeConst.SAVE_MENU_ROLE),


	/**
	 * 保存系统菜单
	 * */
	SAVE_SYS_MENU("saveSysMenu",OpTypeConst.SAVE_SYS_MENU),
	
	/**
	 * 修改系统菜单
	 * */
	UPDATE_SYS_MENU("updateSysMenu",OpTypeConst.UPDATE_SYS_MENU),

	/**
	 * 删除系统菜单
	 * */
	DELETE_SYS_MENU("deleteSysMenu",OpTypeConst.DELETE_SYS_MENU),

	/**
	 * 批量删除系统菜单
	 * */
	BATCH_DELETE_SYS_MENU("batchDeleteSysMenu",OpTypeConst.BATCH_DELETE_SYS_MENU),
	
	/**
	 * 保存、修改资源树
	 * */
	SAVE_OR_UPDATE_MENU_ROLE("saveOrUpdateMenuRole",OpTypeConst.SAVE_OR_UPDATE_MENU_ROLE),
	
	/**
	 * 保存、修改引擎树
	 * */
	SAVE_ROLE_ENGINE("saveRoleEngine",OpTypeConst.SAVE_ROLE_ENGINE),
	
	/**
	 * 保存组织信息
	 * */
	SAVE_ORGAN("saveOrgan",OpTypeConst.SAVE_ORGAN),
	
	/**
	 * 修改组织信息
	 * */
	UPDATE_ORGAN("updateOrgan",OpTypeConst.UPDATE_ORGAN),
	
	/**
	 *删除组织信息
	 * */
	DELTET_ORGAN("deleteOrgan",OpTypeConst.DELTET_ORGAN),
	
	/**
	 *批量删除组织信息
	 * */
	BATCH_DELTET_ORGAN("batchDeleteOrgan",OpTypeConst.BATCH_DELTET_ORGAN),
	
	/**
	 *修改组织状态信息
	 * */
	UPDATE_ORGAN_STATUS("updateOrganStatus",OpTypeConst.UPDATE_ORGAN_STATUS),
	
	/**
	 *保存本组织角色
	 * */
	SAVE_SYS_ROLE("saveSysRole",OpTypeConst.SAVE_SYS_ROLE),
	
	/**
	 *修改本组织角色
	 * */
	UPDATE_SYS_ROLE("updateSysRole",OpTypeConst.UPDATE_SYS_ROLE),
	
	/**
	 *删除本组织角色
	 * */
	DELETE_SYS_ROLE("deleteSysRole",OpTypeConst.DELETE_SYS_ROLE),
	
	/**
	 *批量删除本组织角色
	 * */
	BATCH_DELETE_SYS_ROLE("batchDeleteSysRole",OpTypeConst.BATCH_DELETE_SYS_ROLE),
	
	/**
	 *保存公司管理员角色
	 * */
	SAVE_ORANG_MANAGER_ROLE("saveOrganManagerRole",OpTypeConst.SAVE_ORANG_MANAGER_ROLE),

	/**
	 *修改本组织角色状态
	 * */
	UPDATE_SYS_ROLE_STATUS("updateSysRoleStatus",OpTypeConst.UPDATE_SYS_ROLE_STATUS),
	
	/**
	 *保存用户信息
	 * */
	SAVE_SYS_USER("saveSysUser",OpTypeConst.SAVE_SYS_USER),
	
	/**
	 *修改用户信息
	 * */
	UPDATE_SYS_USER("updateSysUser",OpTypeConst.UPDATE_SYS_USER),
	
	/**
	 *删除用户信息
	 * */
	DELETE_SYS_USER("deleteSysUser",OpTypeConst.DELETE_SYS_USER),
	
	/**
	 *批量删除用户信息
	 * */
	BATCH_DELETE_SYS_USER("batchDeleteSysUser",OpTypeConst.BATCH_DELETE_SYS_USER),

	/**
	 *修改用户状态
	 * */
	UPDATE_SYS_USER_STATUS("updateSysUserStatus",OpTypeConst.UPDATE_SYS_USER_STATUS),
	
	/**
	 *重置用户密码
	 * */
	RESET_PASSWORD("resetPassword",OpTypeConst.RESET_PASSWORD),

	/**
	 *修改用户密码
	 * */
	UPDTE_PASSWORD("updatePassword",OpTypeConst.UPDTE_PASSWORD),

	/**
	 * 保存字段映射或数据管理目录
	 * */
	SAVE_FILED_TREE("saveFieldTree",OpTypeConst.SAVE_FILED_TREE),

	/**
	 * 修改字段映射或数据管理目录
	 * */
	UPDATE_FILED_TREE("updateFieldTree",OpTypeConst.UPDATE_FILED_TREE),
	
	/**
	 * 保存数据管理中的字段
	 * */
	SAVE_FILED("saveField",OpTypeConst.SAVE_FILED),
	
	/**
	 * 修改数据管理中的字段
	 * */
	UPDATE_FILED("updateField",OpTypeConst.UPDATE_FILED),
	
	/**
	 * 修改数据管理中的字段
	 * */
	UPDATE_FILED_STATUS("updateFieldStatus",OpTypeConst.UPDATE_FILED_STATUS),

	/**
	 * 保存字段映射中的字段
	 * */
	SAVE_MAPPING_FILED("saveMappingField",OpTypeConst.SAVE_MAPPING_FILED),
	
	/**
	 * 修改字段映射中的字段
	 * */
	UPDATE_MAPPING_FILED("updateMappingField",OpTypeConst.UPDATE_MAPPING_FILED),
	
	/**
	 * 修改字段映射中的字段状态
	 * */
	UPDATE_MAPPING_FILED_STATUS("updateMappingFieldStatus",OpTypeConst.UPDATE_MAPPING_FILED_STATUS),
	/**
	 * 保存知识库或规则管理目录
	 * */
	SAVE_KNOWLEDGE_TREE("saveKnowledgeTree",OpTypeConst.SAVE_KNOWLEDGE_TREE),
	
	/**
	 * 修改规则知识库或规则管理目录
	 * */
	UPDATE_KNOWLEDGE_TREE("updateKnowledgeTree",OpTypeConst.UPDATE_KNOWLEDGE_TREE),
	
	/**
	 * 保存规则
	 * */
	SAVE_RULE("saveRule",OpTypeConst.SAVE_RULE),
	
	/**
	 * 修改规则
	 * */
	UPDATE_RULE("upadteRule",OpTypeConst.UPDATE_RULE),
	
	/**
	 * 修改规则状态
	 * */
	UPDATE_RULE_STATUS("upadteRuleStatus",OpTypeConst.UPDATE_RULE_STATUS),
	/**
	 *修改规则状态
	 * */
	UPDATE_SCORECARD_STATUS("upadteScorecardStatus",OpTypeConst.UPDATE_SCORECARD_STATUS),
	
	/**
	 *节点重命名
	 * */
	RENAME_NODE("renameNode",OpTypeConst.RENAME_NODE),
	
	/**
	 *保存节点
	 * */
	SAVE_NODE("saveNode",OpTypeConst.SAVE_NODE),
	
	/**
	 *修改节点
	 * */
	UPDATE_NODE("updateNode",OpTypeConst.UPDATE_NODE),
	
	/**
	 *删除节点
	 * */
	DELETE_NODE("deleteNode",OpTypeConst.DELETE_NODE),
	
	/**
	 *批量删除节点
	 * */
	BATCH_DELETE_NODE("batchDeleteNode",OpTypeConst.BATCH_DELETE_NODE),
	
	/**
	 *删除节点之间的连线
	 * */
	DELETE_NODE_LINK("beleteNodeLink",OpTypeConst.DELETE_NODE_LINK),
	
	/**
	 *复制节点
	 * */
	COPY_NODE("copyNode",OpTypeConst.COPY_NODE),
	
	/**
	 *保存引擎
	 * */
	SAVE_ENGINE("saveEngine",OpTypeConst.SAVE_ENGINE),
	
	/**
	 *修改引擎
	 * */
	UPDATE_ENGINE("updateEngine",OpTypeConst.UPDATE_ENGINE),
	
	/**
	 *保存版本
	 * */
	SAVE_VERSION("saveVersion",OpTypeConst.SAVE_VERSION),
	
	/**
	 *修改版本
	 * */
	UPDATE_VERSION("updateVersion",OpTypeConst.UPDATE_VERSION),
	
	/**
	 *删除版本
	 * */
	DELETE_VERSION("deleteVersion",OpTypeConst.DELETE_VERSION),
	
	/**
	 * 引擎部署
	 * */
	ENGINDE_DEPLOY("engineDepoly",OpTypeConst.ENGINDE_DEPLOY),

	/**
	 * 引擎停止部署
	 * */
	ENGINDE_UNDEPLOY("engineUndepoly",OpTypeConst.ENGINDE_UNDEPLOY),
	
	/**
	 * 清空引擎节点
	 * */
	CLEAR_NODE("clearNode",OpTypeConst.CLEAR_NODE),
	
	/**
	 * 添加引擎引用规则关系
	 * */
	ADD_RULE_QUOTES_REL("addRuleQuotesRel",OpTypeConst.ADD_RULE_QUOTES_REL),
	
	/**
	 * 批量修改引擎引用规则状态
	 * */
	BATCH_UPDATE_STATUS_FOR_QUOTES_RULE("batchUpadteStatusForQuotesRule",OpTypeConst.BATCH_UPDATE_STATUS_FOR_QUOTES_RULE),
	
	/**
	 * 添加引擎引用字段关系
	 * */
	ADD_FIELD_QUOTES_REL("addFieldQuotesRel",OpTypeConst.ADD_FIELD_QUOTES_REL),

	/**
	 * 数据填写
	 * */
	FILL_DATA("fillData",OpTypeConst.FILL_DATA);
	
    private String value;
	
	private String type;
	 
	private OpTypeEnum(String value, String type)
	{
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
