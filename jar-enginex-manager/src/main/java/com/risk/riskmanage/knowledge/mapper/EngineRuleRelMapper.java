package com.risk.riskmanage.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.risk.riskmanage.knowledge.model.KnowledgeTreeRel;
import com.risk.riskmanage.common.mapper.BaseMapper;

/**
 * ClassName:KnowledgeTreeRelMapper</br>
 * Description:引擎与引用规则关系 mapper
 * @see 
 * */
public interface EngineRuleRelMapper  extends BaseMapper<KnowledgeTreeRel>{
	
	/**
	 * insertRel：(批量插入关系)
	 *
	 * @param  param 关系集合
	 * @return 
	 * */
	public int insertRel(Map<String,Object> param);
	
	/**
	 * deleteRel:(批量删除关系)
	 *
	 * @param param 关系集合
	 * @return
	 * */
	public int deleteRel(Map<String,Object> param);
	
	/**
	 * getRuleIdsByEngineId:(根据引擎id,获取引用规则id集合)
	 *
	 * @param param 关系集合
	 * @return
	 * */
	public List<Long> getRuleIdsByEngineId(Long engineId);

}