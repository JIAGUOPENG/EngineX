package com.risk.riskmanage.engine.model.response.param;

import lombok.Data;

/**
 * 节点策略输出字段
 */
@Data
public class NodeStrategyOutputResponse {

    /**
     * 字段英文名
     * */
    private String fieldEn;

    /**
     * 字段中文名
     * */
    private String fieldCn;
    /**
     * 字段类型 1数字，2字符串，6json
     */
    private Integer valueType;
}
