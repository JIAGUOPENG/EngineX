package com.risk.riskmanage.knowledge.service;

import java.util.*;

import com.risk.riskmanage.engine.model.request.KnowledgeTreeListParam;
import com.risk.riskmanage.knowledge.model.KnowledgeTree;

/**
 * ClassName:KnowledgeTreeService <br/>
 * Description: 知识库目录接口. <br/>
 * @see 	 
 */
public interface KnowledgeTreeService {
	
	/**
	 * getTreeList：(根据父节点id和组织id,查询其下的所有子节点)
	 *
	 * @param paramMap 参数集合
	 * @return 父节点下的所有子节点
	 * */
	public List<Map> getTreeList(Map<String ,Object> paramMap);
	
	/**
	 * findById:(根据id,查询节点记录)
	 *
	 * @param id 节点id
	 * @return
	 * */
	public KnowledgeTree findById(Long id);
	
	/**
	 * insertTree:(新增节点记录)
	 *
	 * @param k 节点信息
	 * @return
	 * */
	public boolean insertTree(KnowledgeTree k);
	

	
	/**
	 * updateTree:(修改节点记录)
	 *
	 * @param k 节点信息
	 * @return
	 * */
	public boolean updateTree(KnowledgeTree k);
	

	
	/**
	 * getTreeList：(根据父节点id和组织id,查询其下的所有子节点,若节点下规则，则过滤掉)
	 *
	 * @param paramMap 参数集合
	 * @return 父节点下的所有子节点
	 * */
	public List<KnowledgeTree> getTreeDataForEngine(Map<String ,Object> paramMap);

	List<KnowledgeTree> getFolderList(KnowledgeTreeListParam param);
}
