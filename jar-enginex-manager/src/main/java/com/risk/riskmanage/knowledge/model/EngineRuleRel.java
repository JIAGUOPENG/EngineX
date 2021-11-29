package com.risk.riskmanage.knowledge.model;

import java.io.Serializable;
/**
 * ClassName:KnowledgeTreeRel <br/>
 * Description: 引擎与引用规则关系实体类 <br/>
 */
public class EngineRuleRel  implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * */
	private Long id;
	
	/**
	 * 引擎id
	 * */
	private Long engineId;
	
	/**
	 * 树形目录id
	 * */
	private Long ruleId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEngineId() {
		return engineId;
	}

	public void setEngineId(Long engineId) {
		this.engineId = engineId;
	}
}
