package com.risk.riskmanage.knowledge.model;

import java.io.Serializable;
/**
 * ClassName:KnowledgeTreeRel <br/>
 * Description: 规则管理树形目录与引擎关系实体类 <br/>
 * @see
 */
public class KnowledgeTreeRel  implements Serializable{

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
	private Long treeId;

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

	public Long getTreeId() {
		return treeId;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}
}
