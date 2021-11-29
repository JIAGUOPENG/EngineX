package com.risk.riskmanage.rule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.risk.riskmanage.rule.model.RuleLoopGroupAction;
import com.risk.riskmanage.rule.mapper.RuleLoopGroupActionMapper;
import com.risk.riskmanage.rule.service.RuleLoopGroupActionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (RuleLoopGroupAction)表服务实现类
 */
@Service("ruleLoopGroupActionService")
public class RuleLoopGroupActionServiceImpl extends ServiceImpl<RuleLoopGroupActionMapper,RuleLoopGroupAction> implements RuleLoopGroupActionService {
    @Resource
    private RuleLoopGroupActionMapper ruleLoopGroupActionMapper;

    @Override
    public List<RuleLoopGroupAction> getRuleLoopList(Long forId, Long conditionId) {
        RuleLoopGroupAction ruleLoopGroupAction = new RuleLoopGroupAction();
        ruleLoopGroupAction.setConditionForId(forId);
        ruleLoopGroupAction.setConditionGroupId(conditionId);
        List<RuleLoopGroupAction> loopList = ruleLoopGroupActionMapper.selectList(new QueryWrapper<>(ruleLoopGroupAction));
        if (loopList==null){
            loopList = new ArrayList<>();
        }
        return loopList;
    }

    @Override
    public boolean addLoopGroupList(Long forId,Long conditionId, List<RuleLoopGroupAction> loopGroupActions) {
        for (RuleLoopGroupAction loopGroupAction : loopGroupActions) {
            loopGroupAction.setConditionForId(forId);
            loopGroupAction.setConditionGroupId(conditionId);
        }
        boolean add = this.saveBatch(loopGroupActions);
        return add;
    }

    @Override
    public boolean deleteLoopGroupByForId(Long forId) {
        if (forId==null){
            return false;
        }
        RuleLoopGroupAction ruleLoopGroupAction = new RuleLoopGroupAction();
        ruleLoopGroupAction.setConditionForId(forId);
        ruleLoopGroupActionMapper.delete(new QueryWrapper<>(ruleLoopGroupAction));
        return true;
    }
}
