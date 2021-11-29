package com.risk.riskmanage.engine.model.response.param;

import lombok.Data;

import java.util.List;

@Data
public class RuleInfoOutputResponse {

    /**
     * 规则id
     */
    private Long id;

    /**
     * 名称
     * */
    private String name;

    /**
     * 代码
     * */
    private String code;

    /**
     *  节点策略输出字段集合
     */
    private List<NodeStrategyOutputResponse> ruleOutputList;
}
