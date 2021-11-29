package com.risk.riskmanage.datamanage.mapper;

import java.util.List;
import java.util.Map;

import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.datamanage.model.FieldType;
import com.risk.riskmanage.datamanage.model.request.FieldTreeParam;

public interface FieldTypeMapper extends BaseMapper<FieldType> {

	/**
	 * getFieldTypeList:(查找用户的字段类型列表). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public List<FieldType> getFieldTypeList(Map<String,Object> paramMap);
	
	/**
	 * getSubFieldTypeList:(根据传入的字段父类型查找子类型列表). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public List<FieldType> getSubFieldTypeList(Map<String,Object> paramMap);

	/**
	 * findFieldTypeById:(根据传入的字段类型ID查找字段类型名). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 字段类型列表
	 */
	public FieldType findFieldTypeById(Map<String,Object> paramMap);
	
	/**
	 * findTypeIdByParentId:(根据传入的字段类型父ID查找子类型ID). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 子字段类型ID
	 */
	public String findTypeIdByParentId(Map<String,Object> paramMap);
	
	/**
	 * findTypeIdByParentId:(根据传入的字段类型类型ID查找父ID). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 子字段类型ID
	 */
	public String findParentIdByTypeId(Map<String,Object> paramMap);
	
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
	 * @param fieldTypeVo 字段类型实体类
	 * @return 插入成功
	 */
	public boolean createFieldType(FieldType fieldTypeVo);
	
	/**
	 * findIdByFieldType:(新增字段类型). <br/>
	 *
	 * @param paramMap paramMap
	 * @return 字段类型编号
	 */
	public long findIdByFieldType(Map<String,Object> paramMap);
	
	/**
	 * updateFieldType:(更新字段类型名). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新成功
	 */
	public boolean updateFieldType(FieldTreeParam param);
	
	/**
	 * updateFieldTypeByTypeIds:(更新字段类型为删除状态(-1)). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新成功
	 */
	public boolean updateFieldTypeByTypeIds(Map<String, Object> paramMap);
	
	/**
	 * deleteFieldTypeByTypeIds:(删除字段类型下没有字段的空节点)). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新成功
	 */
	public boolean deleteFieldTypeByTypeIds(Map<String, Object> paramMap);
	
	/**
	 * backFieldTypeByTypeIds:(更新字段类型状态为启用状态(1)). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新成功
	 */
	public boolean backFieldTypeByTypeIds(Map<String, Object> paramMap);
	
	/**
	 * isExists:(查找字段名是否存在). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 存在的记录条数
	 */
	public int isExists(Map<String,Object> paramMap);
	
	
	/**
	 * isExistsDefaultTreeName:(查找默认节点名是否存在). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 存在的记录条数
	 */
	public int isExistsDefaultTreeName(Map<String,Object> paramMap);
	
	List<FieldType> selectFieldTypeList(FieldTreeParam param);
}
