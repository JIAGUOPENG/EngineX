package com.risk.riskmanage.engine.model.response.param;

import lombok.Data;
import java.util.List;

@Data
public class ListDbInfoOutputResponse {

    /**
     * 名单库id
     */
    private Long id;

    /**
     * 名单库名称
     */
    private String listName;

    /**
     *  节点策略输出字段集合
     */
    private List<NodeStrategyOutputResponse> listDbOutputList;
}
