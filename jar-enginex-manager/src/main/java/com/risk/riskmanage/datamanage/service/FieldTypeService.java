package com.risk.riskmanage.datamanage.service;

import java.util.List;
import java.util.Map;

import com.risk.riskmanage.datamanage.model.FieldType;
import com.risk.riskmanage.datamanage.model.request.FieldTreeParam;

public interface FieldTypeService {

	/**
	 * getFieldTypeList:(查找用户的字段类型列表). 支持查询组织通用字段类型、子类型 支持查询引擎自定义字段类型、子类型 <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public List<FieldType> getFieldTypeList(Map<String,Object> paramMap);
	
	/**
	 * findFieldTypeById:(根据传入的字段类型ID查找字段类型名). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public FieldType findFieldTypeById(Map<String,Object> paramMap);
	
	/**
	 * findFieldType:(查找用户可用的字段类型列表，通用组织所有，引擎只有自定义). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public List<FieldType> findFieldType(Map<String,Object> paramMap);
	
	/**
	 * createFieldType:(新增字段类型). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 插入成功
	 */
	public boolean createFieldType(FieldType fieldTypeVo,Map<String, Object> paramMap);
	
	/**
	 * updateFieldType:(更新字段类型名). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新成功
	 */
	public boolean updateFieldType(FieldTreeParam param);
	
	/**
	 * findNodeIds:(查找引擎在用的节点集合). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 
	 */
	public String findNodeIds(Map<String, Object> paramMap);

	List<FieldType> getTreeList(FieldTreeParam param);
}
