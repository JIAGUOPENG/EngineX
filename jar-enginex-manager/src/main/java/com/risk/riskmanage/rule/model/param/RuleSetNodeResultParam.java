package com.risk.riskmanage.rule.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleSetNodeResultParam {
    private Long id;//规则的id
    private Integer difficulty;//标识：1基础规则，2复杂规则，3脚本规则
    private Long versionId;//版本id
    private String resultEn;
    private String scoreEn;
    private String code;
    private String name;
}
