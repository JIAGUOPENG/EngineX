package com.risk.riskmanage.knowledge.service.impl;


import com.risk.riskmanage.common.basefactory.BaseService;


import com.risk.riskmanage.knowledge.mapper.RuleMapper;
import com.risk.riskmanage.knowledge.model.*;

import com.risk.riskmanage.knowledge.service.KnowledgeTreeService;

import com.risk.riskmanage.knowledge.service.RuleService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;


/**
 * ClassName:RuleServiceImpl <br/>
 * Description: 规则接口实现类. <br/>
 */
@Service
public class RuleServiceImpl extends BaseService implements RuleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RuleMapper ruleMapper;

    @Override
    public List<Rule> getRuleList(Map<String, Object> paramMap) {
        return ruleMapper.getRuleList(paramMap);
    }

}
