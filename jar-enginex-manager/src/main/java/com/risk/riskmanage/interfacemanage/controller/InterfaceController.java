package com.risk.riskmanage.interfacemanage.controller;

import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.model.requestParam.QueryListParam;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.interfacemanage.model.InterfaceInfo;
import com.risk.riskmanage.interfacemanage.model.request.InterfaceListParam;
import com.risk.riskmanage.interfacemanage.model.request.InterfaceUpdateStatusParam;
import com.risk.riskmanage.interfacemanage.model.vo.InterfaceVo;
import com.risk.riskmanage.interfacemanage.service.InterfaceService;
import com.risk.riskmanage.logger.ArchivesLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 提供interface相关接口
 *
 * @apiDefine interface 2.接口管理
 */
@RestController
@RequestMapping("v3/interface")
public class InterfaceController {

    @Autowired
    InterfaceService interfaceService;

    @RequestMapping("testInterface")
    public String test(){
        String str = "{\n" +
                "    \"status\": \"1\",\n" +
                "    \"error\": \"00000000\",\n" +
                "    \"msg\": null,\n" +
                "    \"data\": {\n" +
                "        \"name\": \"张三\",\n" +
                "        \"age\": 25\n" +
                "    }\n" +
                "}";
        return str;
    }

    /**
     * @api {POST} /v3/interface/getInterfaceInfo/{id} 3.19. V3获取Interface信息
     * @apiGroup knowledge
     * @apiVersion 2.0.0
     * @apiParam {Long} id :  规则id
     * @apiSuccess {String} status 状态：1成功，0失败
     * @apiParamExample {json} 请求示例：
     * {}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {"status":"1","error":"00000000","msg":null,"data":{"id":666,"name":"2021_4_6测试接口","versionCode":"test2021_4_6第二次输入","description":"接口测试","priority":0,"parentId":0,"author":135,"userId":135,"organId":46,"engineId":null,"status":1,"type":1,"isNon":null,"content":"test","created":"2021-04-06T08:38:17.000+00:00","updated":"2021-04-07T05:12:52.000+00:00","ruleType":0,"ruleAudit":2,"score":0,"lastLogical":"","ruleConditionVo":{"id":14,"logical":"&&","fieldId":null,"operator":null,"fieldValue":null,"ruleId":666,"parentId":0,"conditionType":1,"createTime":"2021-04-06T08:38:26.000+00:00","updateTime":null,"insertTempId":null,"children":[{"id":15,"logical":null,"fieldId":null,"operator":">","fieldValue":"1000","ruleId":666,"parentId":14,"conditionType":1,"createTime":"2021-04-06T08:38:26.000+00:00","updateTime":null,"insertTempId":null,"children":[],"tempParentId":null},{"id":16,"logical":null,"fieldId":null,"operator":"<","fieldValue":"10000","ruleId":666,"parentId":14,"conditionType":1,"createTime":"2021-04-06T08:38:26.000+00:00","updateTime":null,"insertTempId":null,"children":[],"tempParentId":null}],"tempParentId":null},"ruleContentInfoList":[{"id":16,"fieldId":10000,"fieldValue":"test01","ruleId":666},{"id":17,"fieldId":10000,"fieldValue":"test02","ruleId":666}]}}
     */
    @RequestMapping(value = "getInterfaceInfo/{id}", method = RequestMethod.POST)
    public ResponseEntityDto<InterfaceVo> getInterfaceById(@PathVariable Long id) {
        if (id==null){
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(),ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        InterfaceVo interfaceVo = interfaceService.getInterfaceById(id);
        ResponseEntityDto<InterfaceVo> ruleResponseEntityDto = ResponseEntityBuilder.buildNormalResponse(interfaceVo);
        return ruleResponseEntityDto;
    }

    //测试http请求，并获得响应
    @PostMapping("getHttpResponse")
    public ResponseEntityDto getHttpResponse(@RequestBody InterfaceInfo interfaceInfo) {
        String result = interfaceService.getHttpResponse(interfaceInfo);
        ResponseEntityDto interfaceResponseEntityDto = ResponseEntityBuilder.buildNormalResponse(result);
        return interfaceResponseEntityDto;
    }

    //查询所有接口
    @PostMapping("getInterfaceList")
    public ResponseEntityDto getInterfaceList(@RequestBody QueryListParam<InterfaceInfo> param) {
//        PageBean<InterfaceVo> pageBean = interfaceService.pageQuery(param.getPageNo(), param.getPageSize());
//        ResponseEntityDto ruleResponseEntityDto = ResponseEntityBuilder.buildNormalResponse(pageBean);

        PageInfo pageInfo = interfaceService.queryByEntity(param);
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("pageInfo",pageInfo);
        responseMap.put("klist",pageInfo.getList());
        pageInfo.setList(null);
        ResponseEntityDto interfaceResponseEntityDto = ResponseEntityBuilder.buildNormalResponse(responseMap);

        return interfaceResponseEntityDto;
    }

    //添加接口
    @PostMapping("addInterface")
    @ArchivesLog(operationType = OpTypeConst.ADD_INTERFACE)
    public ResponseEntityDto<InterfaceVo> addInterface(@RequestBody InterfaceVo interfaceVo ) {
        InterfaceVo insert = interfaceService.inserInterfaceInfo(interfaceVo);
        ResponseEntityDto<InterfaceVo> ruleResponseEntityDto = ResponseEntityBuilder.buildNormalResponse(insert);
        return ruleResponseEntityDto;
    }

    //删除接口
    @PostMapping("deleteInterface")
    @ArchivesLog(operationType = OpTypeConst.DELETE_INTERFACE)
    public ResponseEntityDto<Boolean> deleteInterface(@RequestBody InterfaceVo interfaceVo ) {
        Boolean bool = interfaceService.deleteInterfaceInfo(interfaceVo);
        ResponseEntityDto<Boolean> ruleResponseEntityDto = ResponseEntityBuilder.buildNormalResponse(bool);
        return ruleResponseEntityDto;
    }

    //编辑接口
    @PostMapping("updateInterface")
    @ArchivesLog(operationType = OpTypeConst.UPDATE_INTERFACE)
    public ResponseEntityDto<InterfaceVo> updateInterface(@RequestBody InterfaceVo interfaceVo ) {
        InterfaceVo insert = interfaceService.updateInterfaceInfo(interfaceVo);
        ResponseEntityDto<InterfaceVo> ruleResponseEntityDto = ResponseEntityBuilder.buildNormalResponse(insert);
        return ruleResponseEntityDto;
    }

    //批量更新接口状态
    @PostMapping("updateInterfaceStatus")
    @ArchivesLog(operationType = OpTypeConst.UPDATE_INTERFACE_STATUS)
    public ResponseEntityDto updateStatus(@RequestBody InterfaceUpdateStatusParam param) {
        Long[] ids = param.getIds();
        Integer status = param.getStatus();
        if (ids==null||ids.length==0||status==null){
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(),ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        boolean updateResult = interfaceService.updateStatus(ids, status);
        if (updateResult){
            return ResponseEntityBuilder.buildNormalResponse(updateResult);
        }else {
            return ResponseEntityBuilder.buildErrorResponse(ErrorCodeEnum.SERVER_ERROR);
        }
    }

}
