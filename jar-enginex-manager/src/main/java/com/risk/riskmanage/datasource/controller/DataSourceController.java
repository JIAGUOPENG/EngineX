package com.risk.riskmanage.datasource.controller;

import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.datasource.model.request.DataSourceListParam;
import com.risk.riskmanage.datasource.model.vo.DataSourceVo;
import com.risk.riskmanage.datasource.service.DataSourceService;
import com.risk.riskmanage.logger.ArchivesLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/datasource")
public class DataSourceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DataSourceService dataSourceService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.DATA_SOURCE_SAVE)
    public ResponseEntityDto<Object> save(@RequestBody DataSourceVo dataSourceVo) {
        Integer result = dataSourceService.saveDataSource(dataSourceVo);
        return ResponseEntityBuilder.buildNormalResponse(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.DATA_SOURCE_MODIFY)
    public ResponseEntityDto<Object> update(@RequestBody DataSourceVo dataSourceVo) {
        Integer result = dataSourceService.updateDataSource(dataSourceVo);
        return ResponseEntityBuilder.buildNormalResponse(result);
    }

    @RequestMapping(value = "/getDataSource/{id}", method = RequestMethod.GET)
    public ResponseEntityDto<DataSourceVo> getDataSourceById(@PathVariable Integer id) {
        DataSourceVo dataSourceVo = dataSourceService.getDataSourceById(id);
        return ResponseEntityBuilder.buildNormalResponse(dataSourceVo);
    }

    @RequestMapping(value = "getDataSourceList", method = RequestMethod.POST)
    public ResponseEntityDto<Object> getDataSourceList(@RequestBody DataSourceListParam param) {
        Map<String, Object> result = dataSourceService.getDataSourceList(param);
        return ResponseEntityBuilder.buildNormalResponse(result);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ArchivesLog(operationType = OpTypeConst.DATA_SOURCE_DELETE)
    public ResponseEntityDto<Object> deleteDataSourceById(@PathVariable Integer id) {
        Integer result = dataSourceService.deleteDataSourceById(id);
        return ResponseEntityBuilder.buildNormalResponse(result);
    }

}
