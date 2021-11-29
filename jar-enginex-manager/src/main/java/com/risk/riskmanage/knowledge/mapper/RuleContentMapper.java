package com.risk.riskmanage.knowledge.mapper;

import java.util.List;

import com.risk.riskmanage.knowledge.model.RuleContent;
import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.engine.model.NodeKnowledge;

/**
 * ClassName:RuleContentInfoMapper </br>
 * Description:规则内容mapper
 * @see 
 * */
public interface RuleContentMapper extends BaseMapper<RuleContent>{
	
	/** 
	 * getRuleContentList : (根据规则id,，获取规则内容集合)
	 *
	 * @param  ruleId 规则id
	 * @return 规则下的所有内容
	 * */
	public  List<RuleContent> getRuleContentList(Long ruleId); 
	
	/**
	 * insertRuleContent: (批量新增规则内容记录)
	 *
	 * @param  ruleContentList 规则内容信息
	 * @return 
	 * */
	public int insertRuleContent(List<RuleContent> ruleContentList);
	
	/**
	 * updateRuleContent : (批量修改规则内容记录)
	 *
	 * @param  rlist 规则内容信息集合
	 * @return 
	 * */
	public boolean updateRuleContent(List<RuleContent> rlist);
	
	/**
	 * deleteRuleContent : (批量删除规则内容记录)
	 *
	 * @param  rlist 规则内容信息集合
	 * @return 
	 * */
	public boolean deleteRuleContent(List<RuleContent> rlist);
	
	/**
	 * 	根据引擎节点选择的规则查询字段
	 * @param nodeKnowledge  引擎规则关系
	 * @return 查询到在规则字段 
	 * @see
	 */
	public List<RuleContent> selectNodeByRuleList(NodeKnowledge nodeKnowledge);
}
