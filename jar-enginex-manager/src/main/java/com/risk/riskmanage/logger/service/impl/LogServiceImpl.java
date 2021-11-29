package com.risk.riskmanage.logger.service.impl;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.logger.model.Logger;
import com.risk.riskmanage.logger.service.LogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * ClassName:LogService </br>
 * Description:日志接口实现
 * */
@Service
public class LogServiceImpl extends BaseService implements LogService{

	@Override
	public List<Logger> getLogList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return loggerMapper.getLogList(param);
	}

	@Override
	public Logger findById(Long id) {
		// TODO Auto-generated method stub
		return loggerMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Date> getLastLoginInfo(Long userId) {
		return loggerMapper.getLastLoginInfo(userId);
	}
}
