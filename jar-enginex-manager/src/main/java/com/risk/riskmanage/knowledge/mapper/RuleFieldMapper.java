package com.risk.riskmanage.knowledge.mapper;

import java.util.List;
import java.util.Map;

import com.risk.riskmanage.knowledge.model.RuleField;
import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.engine.model.NodeKnowledge;

/**
 * ClassName:RuleFieldMapper </br>
 * Description:规则字段mapper
 * @see 
 * */
public interface RuleFieldMapper extends BaseMapper<RuleField>{
	
	/**
	 * getFieldList : (根据规则id,，获取规则下的所有字段)
	 *
	 * @param  ruleId 规则id
	 * @return 规则下的所有字段
	 * */
	public  List<RuleField> getFieldList(Long ruleId); 
	
	/**
	 * insertField : (批量新增字段记录)
	 *
	 * @param  rlist 字段信息集合
	 * @return 
	 * */
	public int insertField(List<RuleField> ruleFieldlist);
	
	/**
	 * updateField : (批量修改字段记录)
	 *
	 * @param rlist 字段信息集合
	 * @return 
	 * */
	public boolean updateField(List<RuleField> rlist);
	
	/**
	 * deleteField : (批量删除字段记录)
	 *
	 * @param  rlist 字段信息集合
	 * @return 
	 * */
	public boolean deleteField(List<RuleField> rlist);
	
	
	/**
	 * getNodeByList : (根据引擎节点得到所用字段)
	 * @author wenyu.cao
	 * @param  nodeid 节点编号
	 * @return  返回字段list
	 * */
	public  List<RuleField> getNodeByList(NodeKnowledge knowledge);
	
	/**
	 * 
	 * 根据规则得到规则引用字段
	 * @param nodeKnowledge
	 * @return 
	 * @see
	 */
	public List<RuleField> selectNodeByRuleList( NodeKnowledge nodeKnowledge);
	/**
	 * 
	 * 根据规则id得到规则引用字段
	 * @param paramMap 规则id集合
	 * @return 
	 * @see
	 */
	public  List<RuleField> selectByRuleList(Map<String,Object> paramMap);
}
