package com.risk.riskmanage.datamanage.mapper;

import java.util.List;

import com.risk.riskmanage.datamanage.model.FieldCond;
import com.risk.riskmanage.common.mapper.BaseMapper;

public interface FieldCondMapper extends BaseMapper<FieldCond> {
	
	/**
	 * createFieldCond:(生成条件关系). <br/>
	 *
	 * @param
	 * @return 字段列表
	 */
	public boolean createFieldCond(List<FieldCond> fieldCondVoList);
	
	/**
	 * getFieldCondList:(找出字段条件设置(去重)). <br/>
	 *
	 * @param
	 * @return 字段列表
	 */
	public List<FieldCond> getFieldCondList(Long fieldId);
	
	/**
	 * deleteFieldCondById:(删除字段的条件设置). <br/>
	 *
	 * @param
	 * @return 是否删除成功
	 */
	public boolean deleteFieldCondById(Long id);
	
	
	
}
