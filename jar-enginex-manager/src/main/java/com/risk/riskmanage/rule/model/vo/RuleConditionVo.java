package com.risk.riskmanage.rule.model.vo;

import com.risk.riskmanage.rule.model.RuleConditionInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;



import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class RuleConditionVo extends RuleConditionInfo {

    private List<RuleConditionVo> children;//规则子节点
}
