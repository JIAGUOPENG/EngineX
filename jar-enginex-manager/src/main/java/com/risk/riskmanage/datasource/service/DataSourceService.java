package com.risk.riskmanage.datasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.risk.riskmanage.datasource.model.DataSource;
import com.risk.riskmanage.datasource.model.request.DataSourceListParam;
import com.risk.riskmanage.datasource.model.vo.DataSourceVo;

import java.util.Map;

public interface DataSourceService extends IService<DataSource> {

    Integer saveDataSource(DataSourceVo dataSource);

    Integer updateDataSource(DataSourceVo dataSource);

    DataSourceVo getDataSourceById(Integer id);

    Map<String, Object> getDataSourceList(DataSourceListParam param);

    Integer deleteDataSourceById(Integer id);
}
