package com.risk.riskmanage.rule.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.risk.riskmanage.rule.model.RuleFieldInfo;

import com.risk.riskmanage.rule.model.vo.RuleVo;

import java.util.List;

public interface RuleFieldInfoService extends IService<RuleFieldInfo> {
    List<RuleFieldInfo> queryByRuleId(Long ruleId);

    boolean insertRuleField(List<RuleFieldInfo> list,Long ruleId);

    boolean updateRuleField(List<RuleFieldInfo> list,Long ruleId);

    boolean deleteRuleField(Long ruleId);

    public String assemblyRuleContent(RuleVo rule, List<RuleFieldInfo> fieldInfoList);
}
