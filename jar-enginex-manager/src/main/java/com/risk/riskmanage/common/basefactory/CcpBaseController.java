package com.risk.riskmanage.common.basefactory;

import javax.annotation.Resource;

/**
 * @ClassName: CcpBaseController <br/>
 * @Description: TODO ADD FUNCTION. <br/>
 */
public abstract class CcpBaseController {
	/**
	 * 使用s可以获得所有service
	 */
	@Resource
	public ServiceFactory s;
}
