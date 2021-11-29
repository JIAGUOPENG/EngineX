package com.risk.riskmanage.common.constants;

/**
 * 公共变量约定
 */
public class Constants {
    // token名称
    public static final String SYSTEM_KEY_TOKEN = "token";
    // token时间 单位秒
    public static final Long LOGIN_TOKEN_TIME = 7200L;
    // token最大剩余时间，需刷新 单位秒
    public static final Long LOGIN_TOKEN_REFRESH_TIME = 600L;

    // 规则集节点相关常量
    public interface ruleNode {
        // 互斥组
        int MUTEXGROUP = 1;
        // 执行组
        int EXECUTEGROUP = 2;
    }
}