package com.risk.riskmanage.engine.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 */
@Data
public class Engine implements Serializable {
	private static final long serialVersionUID = -6611916471057697499L;

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 引擎编号
	 */
	private String code;
	/**
	 * 引擎名称
	 */
	private String name;
	/**
	 * 引擎描述
	 */
	private String description;
	/**
	 * 引擎状态
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createDatetime;
	/**
	 * 修改时间
	 */
	private Date updateDatetime;
	/**
	 * 创建人
	 */
	private Long creator;
	/**
	 * 修改人
	 */
	private Long userId;

	/**
	 * 公司编号
	 */
	private Long organId;
	
	/**
	 * 查询字段
	 */
	private String searchString;
	
	/**
	 * 引擎版本集合
	 * */
	private List<EngineVersion> engineVersionList;

	/**
	 * 运行状态
	 */
	private int runState;

	/**
	 * 是否被选中
	 */
	private boolean checked;

	/**
	 * 调用方式 1：同步，2：异步
	 */
	private Integer callbackType;

	/**
	 * 回调地址
	 */
	private String callbackUrl;

	/**
	 * 异常回调地址
	 */
	private String exceptionCallbackUrl;

}
