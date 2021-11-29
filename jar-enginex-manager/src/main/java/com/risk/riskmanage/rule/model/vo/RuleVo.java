package com.risk.riskmanage.rule.model.vo;




import com.risk.riskmanage.rule.model.RuleFieldInfo;
import com.risk.riskmanage.rule.model.RuleInfo;
import com.risk.riskmanage.tactics.model.TacticsOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class RuleVo extends RuleInfo {

    private RuleConditionVo ruleConditionVo;//规则对应的结点树

    private List<RuleFieldInfo> ruleFieldList;//简单规则条件列表

    private List<TacticsOutput> tacticsOutputList;//输出字段

    private List<RuleVersionVo> ruleVersionList;//规则版本列表
}
