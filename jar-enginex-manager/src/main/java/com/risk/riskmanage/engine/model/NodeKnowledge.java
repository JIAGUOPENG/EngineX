package com.risk.riskmanage.engine.model;

import java.io.Serializable;

/**
 * 节点与知识库映射关系模型
 *
 *
 */
public class NodeKnowledge implements Serializable {
    private static final long serialVersionUID = -55965399064577379L;
    /**
	 * 主键编号
	 */
    private Long relId;

    /**
     * 节点编号
     */
    private Long nodeId;

    /**
     * 知识库信息编号
     */
    private Long knowledgeId;
    
    /**
     * 知识库类型1规则2评分卡
     */
    private Integer knowledgeType;

    public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

	public Integer getKnowledgeType() {
		return knowledgeType;
	}

	public void setKnowledgeType(Integer knowledgeType) {
		this.knowledgeType = knowledgeType;
	}
}