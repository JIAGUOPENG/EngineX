package com.risk.riskmanage.engine.model.response.param;

import lombok.Data;

import java.util.List;

/**
 * 节点类型
 */
@Data
public class NodeTypeResponse {

    /**
     * 节点类型
     */
    private Integer nodeType;

    /**
     * 节点类型名称
     */
    private String nodeTypeName;

    /**
     * 节点信息集合
     */
    private List<NodeInfoResponse> nodeInfoList;
}
