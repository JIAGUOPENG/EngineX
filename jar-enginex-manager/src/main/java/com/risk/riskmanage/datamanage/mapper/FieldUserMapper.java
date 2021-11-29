package com.risk.riskmanage.datamanage.mapper;

import java.util.Map;

import com.risk.riskmanage.datamanage.model.FieldUser;
import com.risk.riskmanage.common.mapper.BaseMapper;

public interface FieldUserMapper extends BaseMapper<FieldUser> {

	/**
	 * createFieldUserRel:(绑定字段和用户关系). <br/>
	 *
	 * @param  fieldUser 用户字段实体类
	 * @return 插入成功 
	 * */
	public boolean createFieldUserRel(FieldUser fieldUserVo);
	
	/**
	 * batchCreateFieldUserRel:(批量导入字段信息后批量绑定字段和用户关系). <br/>
	 *
	 * @param  paramMap 参数集合
	 * @return 插入成功 
	 * */
	public boolean batchCreateFieldUserRel(Map<String,Object> paramMap);
	
	/**
	 * batchBindEngineFieldUserRel:(把一批通用字段id中未绑定的字段id批量绑定到引擎). <br/>
	 *
	 * @param  paramMap 参数集合
	 * @return 插入成功 
	 * */
	public boolean batchBindEngineFieldUserRel(Map<String,Object> paramMap);
	
	/**
	 * batchCreateEngineFieldUserRel:(把id、英文名、中文名不重复的组织字段批量绑定到引擎). <br/>
	 *
	 * @param  paramMap 参数集合
	 * @return 插入成功 
	 * */
	public boolean batchCreateEngineFieldUserRel(Map<String,Object> paramMap);
	
	/**
	 * updateFieldUserRel:(更新字段). <br/>
	 *
	 * @param  paramMap 参数集合
	 * @return 更新成功 
	 * */
	public boolean updateFieldUserRel(Map<String,Object> paramMap);
	
	/**
	 * updateStatus:(单个或批量更新用户字段关系). <br/>
	 *
	 * @param  paramMap 参数集合
	 * @return 更新成功 
	 * */
	public boolean updateStatus(Map<String,Object> paramMap);
	
	/**
	 * deleteFieldByIds:(批量修改字段启用状态为删除状态(-1)). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新是否成功
	 */
	public boolean deleteFieldByIds(Map<String,Object> paramMap);
	
	/**
	 * deleteFieldByIds:(批量修改字段删除状态为启用状态(1)). <br/>
	 *
	 * @param paramMap 参数集合
	 * @return 更新是否成功
	 */
	public boolean backFieldByIds(Map<String,Object> paramMap);
	
}
