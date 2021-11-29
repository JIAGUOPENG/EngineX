package com.risk.riskmanage.rule.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.risk.riskmanage.rule.model.RuleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Rule表数据库访问层
 */
@Mapper
public interface RuleInfoMapper extends BaseMapper<RuleInfo> {

    RuleInfo queryById(Integer id);

    List<RuleInfo> queryRuleList(RuleInfo ruleInfo);

    int updateStatus(@Param(value = "ids") Long[] ids, @Param(value = "status") Integer status);

    int updateParent(@Param(value = "ids") Long[] ids,@Param(value = "parentId")  Long parentId);
}

