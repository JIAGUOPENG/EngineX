package com.risk.riskmanage.rule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.risk.riskmanage.rule.mapper.RuleLoopGroupActionMapper;
import com.risk.riskmanage.rule.model.RuleLoopGroupAction;

import java.util.List;

/**
 * (RuleLoopGroupAction)表服务接口
 */
public interface RuleLoopGroupActionService extends IService<RuleLoopGroupAction> {

    List<RuleLoopGroupAction> getRuleLoopList(Long forId,Long conditionId);

    boolean addLoopGroupList(Long forId,Long conditionId, List<RuleLoopGroupAction> loopGroupActions);
    boolean deleteLoopGroupByForId(Long forId);
}
