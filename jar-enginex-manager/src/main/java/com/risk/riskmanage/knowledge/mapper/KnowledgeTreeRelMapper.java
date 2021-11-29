package com.risk.riskmanage.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.risk.riskmanage.knowledge.model.KnowledgeTreeRel;
import com.risk.riskmanage.common.mapper.BaseMapper;

/**
 * ClassName:KnowledgeTreeRelMapper</br>
 * Description:规则管理树形目录与引擎关系 mapper
 * @see
 *  
 * */
public interface KnowledgeTreeRelMapper  extends BaseMapper<KnowledgeTreeRel>{
	
	/**
	 * insertRel：(批量插入关系)
	 *
	 * @param  list 关系集合
	 * @return 
	 * */
	public int insertRel(Map<String,Object> param);
	
	/**
	 * deleteRel:(批量删除关系)
	 *
	 * @param engineId 引擎id
	 * @return
	 * */
	public int deleteRel(Long engineId);
	
	/**
	 * findTreeIdsByEngineId:(根据引擎id，获取引擎使用的组织目录节点的id的集合)
	 *
	 * @param engineId 引擎id
	 * @return
	 * */
	public List<Long> findTreeIdsByEngineId(Long engineId);
}
