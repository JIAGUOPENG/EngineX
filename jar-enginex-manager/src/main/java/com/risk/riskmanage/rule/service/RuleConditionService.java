package com.risk.riskmanage.rule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.risk.riskmanage.rule.model.RuleConditionInfo;
import com.risk.riskmanage.rule.model.vo.RuleConditionVo;

import java.util.List;

/**
 * (RuleConditionInfo)表服务接口
 */
public interface RuleConditionService extends IService<RuleConditionInfo> {


    /**
     * 根据ruleId查询装配好返回
     * @param versionId 规则版本id
     * @return 对象列表
     */
    RuleConditionVo queryByVersionId(Long versionId);

    /**
     * 新增数据
     * @param ruleConditionVo 实例对象
     * @return 实例对象
     */
    RuleConditionVo insertRuleCondition(RuleConditionVo ruleConditionVo,Long ruleId);

    RuleConditionVo updateRuleCondition(Long ruleId,RuleConditionVo ruleConditionVo);

    boolean deleteRuleCondition(Long ruleId,Long versionId);


    RuleConditionVo assemble(List<RuleConditionInfo> list);

    List<RuleConditionInfo> disassemble(RuleConditionVo vo,Long ruleId,boolean needTempId);

}
