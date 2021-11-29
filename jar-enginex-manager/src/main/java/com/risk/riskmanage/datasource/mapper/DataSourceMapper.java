package com.risk.riskmanage.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.risk.riskmanage.datasource.model.DataSource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataSourceMapper extends BaseMapper<DataSource> {
}