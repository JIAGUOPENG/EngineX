package com.risk.riskmanage.knowledge.mapper;

import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.engine.model.NodeKnowledge;
import com.risk.riskmanage.knowledge.model.Rule;

import java.util.List;
import java.util.Map;

/**
 * ClassName:OrganRuleMapper </br>
 * Description:规则mapper
 * @see
 * */
public interface RuleMapper extends BaseMapper<Rule>{

	/**
	 * getRuleList:(获取规则集合)
	 *
	 * @param  paramMap 参数集合
	 * @return 规则集合
	 * */
	public List<Rule> getRuleList(Map<String, Object> paramMap);

	/**
	 * updateRuleStatus:(批量修改规则状态记录)
	 *
	 * @param paramMap 参数集合
	 * @return
	 * */
	public int updateRuleStatus(Map<String, Object> paramMap);
	/**
	 * getNodeByRuleList : (根据引擎节点得到所用规则)
	 * @author wenyu.cao
	 * @param  nodeid 节点编号
	 * @return  返回字段list
	 * */
	public List<Rule> getNodeByRuleList(NodeKnowledge knowledge);

	/**
	 * 根据规则类型查询规则
	 * @param list 规则编号
	 * @return
	 * @see
	 */
	public List<Rule> selectnodeByInRoleid(List<Long> list);

	/**
	 * 根据父节点id查找,节点下所有规则id的集合
	 * @param list 规则编号
	 * @return
	 * @see
	 */
	public List<Long> getRuleIdsByParentId(Map<String,Object> param);

	/**
	 * getRuleList:(查找引用了某些字段的规则集合)
	 *
	 * @param  paramMap 参数集合
	 * @return 规则集合
	 * */
	public List<Rule> checkByField(Map<String, Object> paramMap);

	/**
	 * 效验规则名称唯一性
	 * @param  param 参数集合
	 * @return
	 * @see
	 */
	public int countOnlyRuleName(Map<String,Object> param);

	/**
	 * 效验规则代码唯一性
	 * @param param 参数集合
	 * @return
	 * @see
	 */
	public int countOnlyRuleCode(Map<String,Object> param);

	/**
	 * getFieldIdsByRuleId:(根据规则id,获取规则所用字段id和Key)
	 *
	 * @param  idList 规则id集合
	 * @return
	 * */
	public List<String>  getFieldIdsByRuleId(List<Long> idList);

	public List<Rule> getRuleListByType(Map<String, Object> paramMap);

	public List<Rule> getNodeAddOrSubRulesByNodeId(Long nodeId);

	List<Rule> getRuleListByIds(Map<String, Object> xxx);

	List<Rule> getAllCodeNameParentId();
//	Set<String> getAllName();
}
