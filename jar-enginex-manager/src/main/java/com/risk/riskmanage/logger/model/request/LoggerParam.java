package com.risk.riskmanage.logger.model.request;

import com.risk.riskmanage.common.model.BaseParam;
import lombok.Data;

@Data
public class LoggerParam extends BaseParam {

    private String searchKey;
    private String startDate;
    private String endDate;
}
