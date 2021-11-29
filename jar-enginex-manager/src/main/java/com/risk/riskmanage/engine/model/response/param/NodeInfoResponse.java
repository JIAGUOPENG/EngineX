package com.risk.riskmanage.engine.model.response.param;

import lombok.Data;
import java.util.List;

/**
 * 节点信息
 */
@Data
public class NodeInfoResponse {

    /**
     * 节点编号
     */
    private Long nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     *  节点策略输出字段集合
     */
    private List<NodeStrategyOutputResponse> strategyOutputList;

    /**
     * 规则集输出信息
     */
    private RuleOutputResponse ruleOutput;

    /**
     * 名单库输出信息
     */
    private ListDbOutputResponse listDbOutput;
}
