package com.risk.riskmanage.common.mapper;

import java.util.List;

/**
 * 
 * @ClassName: BaseMapper
 * @Description: 公共的BaseMapper接口
 */
public abstract interface BaseMapper<IdEntity> {

	/**
	 * @Description: 根据对象删除数据
	 * @param entity 对象
	 * @return 是否删除成功
	 */
	int deleteByExample(IdEntity entity);

	/**
	 * @Description: 根据对象主键ID删除数据
	 * @param id 对象id编号
	 * @return 是否删除成功
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * @Description: 插入一条新的数据
	 * @param entity 对象 
	 * @return 是否插入成功
	 */
	int insertSelective(IdEntity entity);

	/**
	 * @Description: 根据对象主键更新对象信息
	 * @param entity 对象 
	 * @return 是否修改成功标志
	 */
	int updateByPrimaryKeySelective(IdEntity entity);
	
	/**
	 * @Description: 根据对象获取数据条数
	 * @param entity 对象
	 * @return 返回行数
	 */
	int countByExample(IdEntity entity);

	/**
	 * @Description: 根据对象主键ID获取指定数据（多个）
	 * @param entity 对象
	 * @return 对象列表
	 */
	List<IdEntity> selectByExample(IdEntity entity);

	/**
	 * @Description: 根据对象主键ID获取指定数据（单个）
	 * @param id id编号
	 * @return 返回单个对象
	 */
	IdEntity selectByPrimaryKey(Long id);
}
