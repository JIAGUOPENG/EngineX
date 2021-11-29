package com.risk.riskmanage.logger.service;

import com.risk.riskmanage.logger.model.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * ClassName:LogService </br>
 * Description:日志接口
 * */
public interface LogService {
	
	/**
	 * getLogList :(获取日志集合)
	 *
	 * @param param 参数集合
	 * @return 
	 * */
	public List<Logger> getLogList(Map<String,Object> param);
	
	
	/**
	 * findById :(根据id获取日志)
	 *
	 * @param id 日志记录id
	 * @return 
	 * */
	public Logger findById(Long id);

	/**
	 * 查询最后登录时间
	 * @param userId
	 * @return
	 */
	public List<Date> getLastLoginInfo(Long userId);

}
