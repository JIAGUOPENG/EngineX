package com.risk.riskmanage.engine.model.response.param;

import lombok.Data;
import java.util.List;

@Data
public class ListDbOutputResponse {

    /**
     * 名单库的统计信息
     */
    private List<NodeStrategyOutputResponse> statisticsOutputList;

    /**
     * 名单库信息
     */
    private List<ListDbInfoOutputResponse> listDbInfoOutput;
}
