package com.risk.riskmanage.rule.consts;

public class RuleConst {
    public static final int STATUS_ENABLED = 1;//启用状态，默认
    public static final int STATUS_DEAD = 0;//停用状态
    public static final int STATUS_DELETE = -1;//删除状态


    public static final int TYPE_SYSTEM = 0;//系统规则
    public static final int TYPE_ORGAN = 1;//组织规则，默认
    public static final int TYPE_ENGINE = 2;//引擎规则

    public static final int RULE_TYPE_TERMINATION = 0;//终止
    public static final int RULE_TYPE_SCORING = 1;//计分

    public static final int RULE_AUDIT_TERMINATION = 2;//终止
    public static final int RULE_AUDIT_SCORING = 5;//继续


}
