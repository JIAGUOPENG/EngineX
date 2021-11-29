package com.risk.riskmanage.datasource.model.request;

import com.risk.riskmanage.common.model.PageDto;
import lombok.Data;

import java.util.List;

@Data
public class DataSourceListParam extends PageDto {

    /**
     * 数据源类型
     */
    private List<String> typeList;
}
