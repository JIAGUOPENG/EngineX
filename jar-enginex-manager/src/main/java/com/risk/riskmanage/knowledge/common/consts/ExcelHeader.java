package com.risk.riskmanage.knowledge.common.consts;

/**
 * ClassName:ExcelHeader <br/>
 * Description: Excel表头配置. <br/>
 */
public class ExcelHeader {
	
	/**
	 * 规则表头名称
	 * */
	public static final String[] RULE_HEADER = {"规则名称","规则代码","规则描述","优先级","条件","输出"};
	
	/**
	 * 规则表头对应属性名称
	 * */
	public static final String[] RULE_ClASS = {"name","versionCode","description","priority","fieldContent","content"};
	
	/**
	 * 评分卡表头名称
	 * */
	public static final String[] SCORECARD_HEADER = {"评分卡名称","评分卡代码","评分卡描述","版本号","指标详情","评分卡规则内容"};
	
	/**
	 * 评分卡表头对应属性名称
	 * */
	public static final String[] SCORECARD_ClASS = {"name","versionCode","description","version","fieldContent","content"};
	
	
}
