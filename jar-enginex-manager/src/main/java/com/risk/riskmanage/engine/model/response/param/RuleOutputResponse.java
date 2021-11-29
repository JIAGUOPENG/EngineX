package com.risk.riskmanage.engine.model.response.param;

import lombok.Data;

import java.util.List;

@Data
public class RuleOutputResponse {

    /**
     * 规则的统计信息
     */
    private List<NodeStrategyOutputResponse> statisticsOutputList;

    /**
     * 规则信息
     */
    private List<RuleInfoOutputResponse> ruleInfoList;
}
