package com.risk.riskmanage.engine.model;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EngineNode implements Serializable{
	
	private static final long serialVersionUID = -1867357850853531748L;

	/**
	 * 节点编号
	 */
    private Long nodeId;
    
    /**
     * 父节点编号
     */
    private String parentId;

    /**
     * 版本编号
     */
    private Long versionId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点code
     */
    private String nodeCode;

    /**
     * 节点顺序
     */
    private Integer nodeOrder;

    /**
     * 节点类型
     */
    private Integer nodeType;

    /**
     * 节点X轴
     */
    private double nodeX;

    /**
     * 节点Y轴
     */
    private double nodeY;
    
    /**
     * 节点脚本
     */
    private String nodeScript;
    
    /**
     * 节点json
     */
    private String nodeJson;
    
    /**
     * 节点类型,图标等信息
     */
    private String params;
    
    /**
     * 下一节点
     */
    private String nextNodes;
    
    /**
     * 评分卡id
     * 
     */
    private Long cardId;
    
    /**
     * 规则集合
     */
    private List<Long> ruleList;
    
    private Integer lastNextnode;
    /**
     * 快照信息
     */
    private String snapshot;

}