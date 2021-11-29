package com.risk.riskmanage.rule.model.vo;


import com.risk.riskmanage.rule.model.RuleVersion;
import com.risk.riskmanage.tactics.model.TacticsOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleVersionVo extends RuleVersion {
    private RuleConditionVo ruleConditionVo;//规则对应的结点树

    private List<TacticsOutput> tacticsOutputList;//输出字段

    private List<TacticsOutput> failOutputList;//失败输出字段
}
