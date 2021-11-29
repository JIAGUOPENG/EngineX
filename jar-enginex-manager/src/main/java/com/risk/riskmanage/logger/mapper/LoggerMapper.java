package com.risk.riskmanage.logger.mapper;

import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.logger.model.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * ClassName:LoggerMapper </br>
 * Description:日志mapper
 * */
public interface LoggerMapper extends BaseMapper<Logger>{
	
	/**
	 * getLogList :(获取日志集合)
	 *
	 * @param param 参数集合
	 * @return 
	 * */
	public List<Logger> getLogList(Map<String,Object> param);

	/**
	 * 查询最后登录时间
	 * @param userId
	 * @return
	 */
	public List<Date> getLastLoginInfo(Long userId);

}
