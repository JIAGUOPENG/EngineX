package com.risk.riskmanage.datamanage.controller.v2;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.basefactory.BaseController;
import com.risk.riskmanage.common.constants.CommonConst;
import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.datamanage.common.ValueType;
import com.risk.riskmanage.datamanage.model.Field;
import com.risk.riskmanage.datamanage.model.FieldCond;
import com.risk.riskmanage.datamanage.model.FieldType;
import com.risk.riskmanage.datamanage.model.request.FieldSaveParam;
import com.risk.riskmanage.datamanage.model.request.FieldTreeParam;
import com.risk.riskmanage.datamanage.vo.FieldEnumVo;
import com.risk.riskmanage.datamanage.vo.FieldFormulaVo;
import com.risk.riskmanage.datamanage.vo.FieldSubCondVo;
import com.risk.riskmanage.logger.ArchivesLog;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.CollectionUtil;
import com.risk.riskmanage.util.SessionManager;
import com.risk.riskmanage.util.StringUtil;
import com.risk.riskmanage.common.model.requestParam.UpdateFolderParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 提供field相关接口
 *
 * @apiDefine field 2.指标管理
 */
@Controller("fieldControllerV2")
@RequestMapping("/v2/datamanage/field")
@ResponseBody
public class FieldController extends BaseController {

    /**
     * @api {POST} /v2/datamanage/field/listTree 2.01. 获取节点树
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {Integer} parentId parentId, 值为 0或 空字符串都行（此时获取的是所有文件夹目录）
     * @apiParamExample {json} 请求示例：
     * {}
     * @apiSuccessExample {json} Success-Response:
     * {"data":[{"userId":135,"organId":46,"status":1,"isCommon":1,"parentId":303,"children":[{"children":[{"children":[],"fieldType":"信用卡","icon":"../resource/images/authority/folder.png","id":306,"isCommon":1,"isParent":"true","page":0,"parentId":305,"rows":0}],"fieldType":"银行","icon":"../resource/images/authority/folder.png","id":305,"isCommon":1,"isParent":"true","page":0,"parentId":0,"rows":0},{"children":[{"children":[],"fieldType":"网点余额不足风险","icon":"../resource/images/authority/folder.png","id":304,"isCommon":1,"isParent":"true","page":0,"parentId":302,"rows":0},{"children":[],"fieldType":"代收货款与到付","icon":"../resource/images/authority/folder.png","id":303,"isCommon":1,"isParent":"true","page":0,"parentId":302,"rows":0}],"fieldType":"物流","icon":"../resource/images/authority/folder.png","id":302,"isCommon":1,"isParent":"true","page":0,"parentId":0,"rows":0}],"fieldType":"通用字段","id":99999999}],"error":"00000000","status":"1"}
     */
    @RequestMapping(value = "/listTree", method = RequestMethod.POST)
    public ResponseEntityDto<Object> listTree(@RequestBody Map<String, Object> paramMap) {

        User loginAccount = SessionManager.getLoginAccount();
        paramMap.put("userId", loginAccount.getUserId());
        paramMap.put("organId", loginAccount.getOrganId());
        paramMap.put("status", 1);

        Integer isCommon = 1;
        Integer engineId = null;
        if (paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
            isCommon = null;
            engineId = Integer.valueOf((String) paramMap.get("engineId")).intValue();
            paramMap.put("engineId", engineId);
        } else {
            paramMap.put("engineId", null);
        }
        paramMap.put("isCommon", isCommon);

        List<FieldType> klist = s.fieldTypeService.getFieldTypeList(paramMap);
        for (FieldType fieldTypeVo : klist) {
            if (engineId != null && fieldTypeVo.getIsCommon() == 1) {
                String fieldType = fieldTypeVo.getFieldType();
                fieldTypeVo.setFieldType(fieldType + "（通用）");
            }
            paramMap.put("parentId", fieldTypeVo.getId());
            fieldTypeVo.setChildren(getChildren(paramMap));
        }
        FieldType[] kArray = new FieldType[klist.size()];
        kArray = klist.toArray(kArray);
        paramMap.put("children", kArray);
        paramMap.put("fieldType", "通用字段");
        paramMap.put("id", 99999999);

        ArrayList<Map> list = new ArrayList<>();
        list.add(paramMap);

        return ResponseEntityBuilder.buildNormalResponse(list);
    }
    @RequestMapping(value = "/newListTree", method = RequestMethod.POST)
    public ResponseEntityDto<Object> newListTree(@RequestBody FieldTreeParam param) {
        if (param==null||param.getType()==null){
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(),ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        List<FieldType> list = s.fieldTypeService.getTreeList(param);
        Map paramMap=new HashMap<>();
        paramMap.put("children", list);
        paramMap.put("fieldType", "通用字段");
        paramMap.put("id", 99999999);
        List response= new ArrayList<>();
        response.add(paramMap);
        return ResponseEntityBuilder.buildNormalResponse(response);
    }

    /**
     * getChildren:(获取树形节点的子节点信息)
     *
     * @param paramMap
     * @return
     */
    private FieldType[] getChildren(Map<String, Object> paramMap) {
        List<FieldType> klist = s.fieldTypeService.getFieldTypeList(paramMap);
        for (FieldType fieldTreeVo : klist) {
            paramMap.put("parentId", fieldTreeVo.getId());
            fieldTreeVo.setChildren(getChildren(paramMap));
        }
        FieldType[] kArray = new FieldType[klist.size()];
        kArray = klist.toArray(kArray);
        return kArray;
    }

    /**
     * @api {POST} /v2/datamanage/field/addTree 2.02. 添加树节点
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {String} fieldType 文件夹的名字
     * @apiParam {String} parentId parentId
     * @apiParam {Integer} [id] id可传可不传，无实际意义
     * @apiParamExample {json} Request:
     * {
     * "parentId": "302",
     * "fieldType": "测试类型"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {"status":"1","error":"00000000","msg":null,"data":{"parentId":"302","fieldType":"测试类型","userId":135,"organId":46,"engineId":null,"isCommon":1,"fieldTypeId":365,"id":2810,"result":1}}
     */
    @RequestMapping(value = "/addTree", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.SAVE_FILED_TREE)
    public ResponseEntityDto<Object> addTree(@RequestBody Map<String, Object> paramMap) {

        // fieldTypeId, parentId, id

        User loginAccount = SessionManager.getLoginAccount();
        paramMap.put("userId", loginAccount.getUserId());
        paramMap.put("organId", loginAccount.getOrganId());

        Integer isCommon = 1;
        Integer engineId = null;
        if (paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
            isCommon = 0;
            engineId = Integer.valueOf((String) paramMap.get("engineId")).intValue();
            paramMap.put("engineId", engineId);
        } else {
            isCommon = 1;
            paramMap.put("engineId", null);
        }
        paramMap.put("isCommon", isCommon);

        FieldType fieldTypeVo = new FieldType();
        fieldTypeVo.setIsCommon(isCommon);
        fieldTypeVo.setParentId(Integer.valueOf( paramMap.get("parentId").toString()));
        fieldTypeVo.setFieldType((String) paramMap.get("fieldType"));
        fieldTypeVo.setType(Integer.valueOf(paramMap.get("type").toString()));
        boolean flag = s.fieldTypeService.createFieldType(fieldTypeVo, paramMap);
        if (flag) {
            paramMap.put("result", 1);
        } else {
            paramMap.put("result", -1);
        }

        return ResponseEntityBuilder.buildNormalResponse(paramMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/updateTree 2.03. 修改树节点
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {Integer} id id
     * @apiParam {String} fieldType 文件夹的名称
     * @apiParam {Integer} fieldType 文件夹的名称
     * @apiParam {String} [status] -1表示删除
     * @apiParamExample {json} Request:
     * {
     * "parentId":302,
     * "fieldType": "测试哈哈类型",
     * "id": 365,
     * "status": 1
     * }
     * @apiSuccessExample {json} Success-Response:
     * {"status":"1","error":"00000000","msg":null,"data":{"parentId":302,"fieldType":"测试哈哈类型","id":365,"status":1,"userId":135,"engineId":null,"organId":46,"isCommon":1,"result":1}}
     */
    @RequestMapping(value = "/updateTree", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDATE_FILED_TREE)
    public ResponseEntityDto<Object> updateTree(@RequestBody FieldTreeParam param) {
        Integer status = param.getStatus();

        boolean b = s.fieldTypeService.updateFieldType(param);
        if (b){
            return ResponseEntityBuilder.buildNormalResponse();
        }
        return ResponseEntityBuilder.buildErrorResponse("修改指标文件夹错误","修改指标文件夹错误");
//        int num = this.fieldTypeAjaxValidate(paramMap);
//        if (num > 0) {
//            throw new ApiException(ErrorCodeEnum.FIELD_TYPE_REPEAT.getVersionCode(), ErrorCodeEnum.FIELD_TYPE_REPEAT.getMessage());
//        }

//        User loginAccount = SessionManager.getLoginAccount();
//        paramMap.put("userId", loginAccount.getUserId());
//        paramMap.put("organId", loginAccount.getOrganId());
//
//        Integer isCommon = 1;
//        Integer engineId = null;
//        if (paramMap.containsKey("engineId") && paramMap.get("engineId") != null && !paramMap.get("engineId").equals("")) {
//            isCommon = 0;
//            engineId = Integer.valueOf((String) paramMap.get("engineId")).intValue();
//            paramMap.put("engineId", engineId);
//        } else {
//            isCommon = 1;
//            paramMap.put("engineId", null);
//        }
//
//        paramMap.put("isCommon", isCommon);
//
//        boolean flag = s.fieldTypeService.updateFieldType(paramMap);
//        if (flag) {
//            paramMap.put("result", 1);
//        } else {
//            paramMap.put("result", -1);
//        }

    }

    private int fieldTypeAjaxValidate(Map<String, Object> paramMap) {
        Long userId = SessionManager.getLoginAccount().getUserId();
        paramMap.put("userId", userId);

        if (!paramMap.containsKey("engineId")) {
            paramMap.put("engineId", null);
        }

        return s.fieldService.isExistsFieldType(paramMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/list 2.04. 获取指标列表
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {Integer} isCommon isCommon 值为1时查询通用字段， 数据库管理不用传isCommon
     * @apiParam {String} [fieldTypeId] 指标类型id，当fieldTypeId为空或不传时，查询的时整个通用字段下面的所有指标
     * @apiParam {Integer} pageNo 第几页，默认为 1
     * @apiParam {Integer} [pageSize] 每页的条数，默认为 10
     * @apiParamExample {json} Request:
     * {
     * "isCommon": 1,
     * "fieldTypeId": 99999999,
     * "pageNo": 1,
     * "pageSize": 2
     * }
     * @apiSuccessExample {json} Success-Response:
     * {"status":"1","error":"00000000","msg":null,"data":{"fieldVos":[{"page":0,"rows":0,"total":null,"id":876,"fieldEn":"network_real_amount_derive","fieldCn":"网点余额衍生字段","fieldTypeId":302,"fieldType":"物流","valueType":1,"valueTypeName":null,"valueScope":"[-1,9999999)","isDerivative":1,"isDerivativeName":null,"isOutput":0,"isOutputName":null,"isCommon":1,"formula":"","formulaShow":"","usedFieldId":null,"origFieldId":null,"author":135,"nickName":"管理员","created":1615535468000,"engineId":null,"engineName":null,"status":"1","fieldCondList":null,"fieldRelId":null,"dataSourceId":null,"sqlStatement":null,"useSql":null},{"page":0,"rows":0,"total":null,"id":873,"fieldEn":"ziduan111733","fieldCn":"字段111733","fieldTypeId":327,"fieldType":"ddd","valueType":2,"valueTypeName":null,"valueScope":"qwer","isDerivative":0,"isDerivativeName":null,"isOutput":0,"isOutputName":null,"isCommon":1,"formula":"","formulaShow":"","usedFieldId":null,"origFieldId":null,"author":135,"nickName":"管理员","created":1615455268000,"engineId":null,"engineName":null,"status":"1","fieldCondList":null,"fieldRelId":null,"dataSourceId":null,"sqlStatement":null,"useSql":null}],"pager":{"pageNum":1,"pageSize":2,"size":2,"startRow":1,"endRow":2,"total":261,"pages":131,"list":[{"page":0,"rows":0,"total":null,"id":876,"fieldEn":"network_real_amount_derive","fieldCn":"网点余额衍生字段","fieldTypeId":302,"fieldType":"物流","valueType":1,"valueTypeName":null,"valueScope":"[-1,9999999)","isDerivative":1,"isDerivativeName":null,"isOutput":0,"isOutputName":null,"isCommon":1,"formula":"","formulaShow":"","usedFieldId":null,"origFieldId":null,"author":135,"nickName":"管理员","created":1615535468000,"engineId":null,"engineName":null,"status":"1","fieldCondList":null,"fieldRelId":null,"dataSourceId":null,"sqlStatement":null,"useSql":null},{"page":0,"rows":0,"total":null,"id":873,"fieldEn":"ziduan111733","fieldCn":"字段111733","fieldTypeId":327,"fieldType":"ddd","valueType":2,"valueTypeName":null,"valueScope":"qwer","isDerivative":0,"isDerivativeName":null,"isOutput":0,"isOutputName":null,"isCommon":1,"formula":"","formulaShow":"","usedFieldId":null,"origFieldId":null,"author":135,"nickName":"管理员","created":1615455268000,"engineId":null,"engineName":null,"status":"1","fieldCondList":null,"fieldRelId":null,"dataSourceId":null,"sqlStatement":null,"useSql":null}],"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8,"firstPage":1,"lastPage":8},"searchKey":null,"fieldTypeId":null,"engineId":null}}
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntityDto<Object> list(@RequestBody HashMap<String, Object> paramMap) {

        if (paramMap.get("fieldTypeId") != null && paramMap.get("fieldTypeId").toString().equals("99999999")) {
            paramMap.put("fieldTypeId", null);
        }

        Integer pageNo = paramMap.get("pageNo") == null ? 1 : Integer.valueOf(paramMap.get("pageNo").toString());
        Integer pageSize = paramMap.get("pageSize") == null ? 10 : Integer.valueOf(paramMap.get("pageSize").toString());

        User loginAccount = SessionManager.getLoginAccount();
        paramMap.put("userId", loginAccount.getUserId());
        paramMap.put("organId", loginAccount.getOrganId());

        String searchKey = (String) paramMap.get("searchKey");
        paramMap.put("searchKey", null);

        if (!paramMap.containsKey("status"))
            paramMap.put("status", null);
        if (!paramMap.containsKey("engineId"))
            paramMap.put("engineId", null);

        PageHelper.startPage(pageNo, pageSize);
        List<Field> fieldList = s.fieldService.findByFieldType(paramMap);
        PageInfo<Field> pageInfo = new PageInfo<>(fieldList);

        if (fieldList == null) {
            return ResponseEntityBuilder.buildNormalResponse(null);
        }

        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put("pager", pageInfo);
        modelMap.put("engineId", null);
        modelMap.put("klist",pageInfo.getList());
        modelMap.put("searchKey", searchKey);

        return ResponseEntityBuilder.buildNormalResponse(modelMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/updateStatus 2.05. 指标停用、启用、删除
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {Integer} status 0表示停用，  1表示启用，  -1表示删除
     * @apiParam {String} ids id组成的字符串，用逗号分隔
     * @apiParam {Integer} fieldTypeId fieldTypeId 文件夹的id
     * @apiParamExample {json} 请求示例：
     * {"status":0,"ids":"820,819,818","fieldTypeId":303}
     * @apiSuccessExample {json} 成功返回数据示例:
     * {"status":"1","error":"00000000","msg":null,"data":{"status":0,"ids":"820,819,818","fieldTypeId":"303","userId":135,"organId":46,"engineId":null,"Ids":[820,819,818],"fieldId":818,"fieldIds":[818],"fieldList":[],"listDbList":[],"ruleList":[],"scorecardList":[],"nodelistDbList":[],"beUsed":false,"result":true}}
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDATE_FILED_STATUS)
    public ResponseEntityDto<Object> updateStatus(@RequestBody Map<String, Object> param) {

        User loginAccount = SessionManager.getLoginAccount();
        param.put("userId", loginAccount.getUserId());
        param.put("organId", loginAccount.getOrganId());

        param.put("engineId", null);

        String idsStr = (String) param.get("ids");
        List<Long> Ids = StringUtil.toLongList(idsStr);
        param.put("Ids", Ids);

        String strFieldTypeId = String.valueOf(param.get("fieldTypeId"));
        if (strFieldTypeId == null) {
            strFieldTypeId = "";
        }
        //Long fieldTypeId = s.fieldService.findFieldTypeId(param);

        s.fieldService.updateStatus(param);

        param.put("fieldTypeId", strFieldTypeId);

        return ResponseEntityBuilder.buildNormalResponse(param);
    }

    /**
     * @api {POST} /v2/datamanage/field/save 2.06. 添加指标
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {String} searchKey searchKey
     * @apiParam {Long} [id] 指标ID
     * @apiParam {String} formula 衍生字段公式
     * @apiParam {String} formulaShow 衍生字段公式回显信息
     * @apiParam {Long} engineId 归属的引擎ID
     * @apiParam {String} fieldEn 指标英文名：拼接前缀"f_"
     * @apiParam {String} fieldCn 指标中文名
     * @apiParam {Long} fieldTypeId 字段类型编号
     * @apiParam {Integer} valueType 字段存值类型,待选(0),数值型(1),字符型(2),枚举型(3),小数型(4)
     * @apiParam {Integer} isDerivative 是否衍生字段，0代表不是，1代表是
     * @apiParam {Integer} isOutput 是否输出字段，0代表不是，1代表是
     * @apiParam {String} valueScope 字段约束范围
     * @apiParam {JSONArray} fieldCondList 条件区域内容（传字符串）
     * @apiParam (fieldCondList) {String} conditionValue 字段条件值
     * @apiParam (fieldCondList) {JSONArray} fieldSubCond 字段列表
     * @apiParam (fieldSubCond) {String} fieldId 条件字段编号
     * @apiParam (fieldSubCond) {String} operator 条件字段的运算符
     * @apiParam (fieldSubCond) {String} fieldValue 条件字段的条件设置值
     * @apiParam (fieldSubCond) {String} logical 条件字段间的逻辑符
     * @apiParam {JSONArray} formulaHidden 公式编辑、groovy脚本内容（传字符串）
     * @apiParam (formulaHidden) {String} formula 衍生字段公式
     * @apiParam (formulaHidden) {String} idx 下标
     * @apiParam (formulaHidden) {JSONArray} farr 字段列表
     * @apiParam (farr) {String} fieldCN 字段中文名
     * @apiParam (farr) {String} fieldCond 字段条件区域
     * @apiParam {Boolean} isUseSql 是否使用sql获取指标
     * @apiParam {Integer} dataSourceId 使用sql获取指标时对应的数据源
     * @apiParam {String} sqlStatement 使用sql获取指标时对应的sql语句
     * @apiSuccess {String} status 状态: 1成功, 0失败
     * @apiParamExample {json} 请求示例：
     * {"searchKey":"","fieldTypeId":"302","id":"","formula":"","formulaShow":"","engineId":"","fieldEn":"f_f_f_network_real_amount_derive","fieldCn":"网点余额衍生字段","valueType":"1","isDerivative":"1","isOutput":"0","valueScope":"[-1,9999999)","fieldCondList":[{"fieldSubCond":[{"fieldId":"824","operator":">","fieldValue":"2000","logical":"&&"},{"fieldId":"826","operator":"<","fieldValue":"5000"}],"conditionValue":"666"},{"fieldSubCond":[{"fieldId":"824","operator":"<=","fieldValue":"2000","logical":"&&"},{"fieldId":"826","operator":"<","fieldValue":"5000"}],"conditionValue":"888"}],"formulaHidden":[{"fvalue":"","formula":"@同盾_评分@ - @第三方分值@ ","idx":"0","farr":[{"fieldCN":"同盾_评分","fieldCond":""},{"fieldCN":"第三方分值 ","fieldCond":""}]}],"isUseSql":"0","dataSourceId":null,"sqlStatement":""}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {}
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.SAVE_FILED)
    public ResponseEntityDto<Object> save(@RequestBody FieldSaveParam fieldSaveParam) {

        Field fieldVo = new Field();
        BeanUtils.copyProperties(fieldSaveParam, fieldVo);
        fieldVo.setUseSql(fieldSaveParam.getIsUseSql());
        fieldVo.setInterface(fieldSaveParam.getIsInterface());//是否使用接口
        Map param = JSONObject.parseObject(JSONObject.toJSONString(fieldSaveParam), Map.class);
        s.fieldService.sqlFieldCheck(param);
        // 校验
        HashMap<String, Object> fieldEnValidateMap = new HashMap<>();
        fieldEnValidateMap.put("fieldEn", param.get("fieldEn"));
        fieldEnValidateMap.put("engineId", param.get("engineId"));
        fieldEnValidateMap.put("Id", param.get("id"));
        int fieldEnNum = fieldEnAjaxValidate(fieldEnValidateMap);
        if (fieldEnNum > 0) {
            throw new ApiException(ErrorCodeEnum.FIELD_EN_REPEAT.getCode(), ErrorCodeEnum.FIELD_EN_REPEAT.getMessage());
        }

        HashMap<String, Object> fieldCnValidateMap = new HashMap<>();
        fieldCnValidateMap.put("fieldCn", param.get("fieldCn"));
        fieldCnValidateMap.put("engineId", param.get("engineId"));
        fieldCnValidateMap.put("Id", param.get("id"));
        int fieldCnNum = fieldCnAjaxValidate(fieldCnValidateMap);
        if (fieldCnNum > 0) {
            throw new ApiException(ErrorCodeEnum.FIELD_CN_REPEAT.getCode(), ErrorCodeEnum.FIELD_CN_REPEAT.getMessage());
        }

        Long userId = SessionManager.getLoginAccount().getUserId();
        Long organId = SessionManager.getLoginAccount().getOrganId();
        param.put("userId", userId);
        fieldVo.setAuthor(userId);
        fieldVo.setOrganId(organId);

        //校验字段英文名命名
        String fieldEn = fieldVo.getFieldEn();
        Pattern.matches("\\[A-z]|_|d+", fieldEn);

        param.put("fieldEn", fieldVo.getFieldEn());
        param.put("fieldCn", fieldVo.getFieldCn());
        param.put("organId", organId);
        param.put("engineId", null);
        //组织通用字段
        Integer isCommon = 1;
        fieldVo.setIsCommon(isCommon);
//		String formulaFields = "1,2,3,4";
//		param.put("formulaFields", formulaFields);

        s.fieldService.createField(fieldVo, param);

        // model.addAttribute("engineId", null);
        // model.addAttribute("fieldTypeId", Long.valueOf(fieldVo.getFieldTypeId()));
        // model.addAttribute("isCommon", 1);
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put("engineId", null);
        modelMap.put("fieldTypeId", Long.valueOf(fieldVo.getFieldTypeId()));
        modelMap.put("isCommon", 1);

        return ResponseEntityBuilder.buildNormalResponse(modelMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/getFieldInfo/{id} 2.07. 获取指标详情
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {Integer} id 路径参数{id}：指标的id
     * @apiSuccess {String} status 状态: 1成功, 0失败
     * @apiParamExample {json} 请求示例：
     * {}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {"status":"1","error":"00000000","msg":null,"data":{"hasGroovy":null,"fieldFormulaList":[],"fieldVo":{"page":0,"rows":0,"total":null,"id":876,"fieldEn":"network_real_amount_derive","fieldCn":"网点余额衍生字段","fieldTypeId":302,"fieldType":"物流","valueType":1,"valueTypeName":null,"valueScope":"[-1,9999999)","isDerivative":1,"isDerivativeName":null,"isOutput":0,"isOutputName":null,"isCommon":1,"formula":"","formulaShow":"","usedFieldId":"824,826","origFieldId":"824,826","author":null,"nickName":null,"created":null,"engineId":null,"engineName":null,"status":null,"fieldCondList":[{"page":0,"rows":0,"total":null,"id":null,"fieldId":876,"conditionValue":"777","content":"[{\"fieldId\":\"824\",\"operator\":\">\",\"fieldValue\":\"2000\",\"logical\":\"&&\"},{\"fieldId\":\"826\",\"operator\":\"<\",\"fieldValue\":\"5000\"}]","condFieldId":null,"condFieldOperator":null,"condFieldValue":null,"condFieldLogical":null,"created":null,"fieldSubCond":[{"fieldId":824,"operator":">","fieldValue":"2000","logical":"&&","valueType":1,"valueScope":"[-1,9999999)","values":["[-1,9999999)"],"fieldCn":"网点实际余额"},{"fieldId":826,"operator":"<","fieldValue":"5000","logical":null,"valueType":1,"valueScope":"[-1,9999999)","values":["[-1,9999999)"],"fieldCn":"网点关闭余额"}]},{"page":0,"rows":0,"total":null,"id":null,"fieldId":876,"conditionValue":"999","content":"[{\"fieldId\":\"824\",\"operator\":\"<=\",\"fieldValue\":\"2000\",\"logical\":\"&&\"},{\"fieldId\":\"826\",\"operator\":\"<\",\"fieldValue\":\"5000\"}]","condFieldId":null,"condFieldOperator":null,"condFieldValue":null,"condFieldLogical":null,"created":null,"fieldSubCond":[{"fieldId":824,"operator":"<=","fieldValue":"2000","logical":"&&","valueType":1,"valueScope":"[-1,9999999)","values":["[-1,9999999)"],"fieldCn":"网点实际余额"},{"fieldId":826,"operator":"<","fieldValue":"5000","logical":null,"valueType":1,"valueScope":"[-1,9999999)","values":["[-1,9999999)"],"fieldCn":"网点关闭余额"}]}],"fieldRelId":null,"dataSourceId":null,"sqlStatement":null,"useSql":null},"scopeList":["[-1","9999999)"],"searchKey":null,"fieldTypeId":302,"hasFormula":null,"engineId":null}}
     */
    @RequestMapping(value = "/getFieldInfo/{id}", method = RequestMethod.POST)
    public ResponseEntityDto<Object> getFieldInfo(@PathVariable long id, @RequestBody Map<String, Object> param) {

        Map<String, Object> paramMap = new HashMap<>();
        Long userId = SessionManager.getLoginAccount().getUserId();
        Long organId = SessionManager.getLoginAccount().getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("organId", organId);
        paramMap.put("engineId", null);
        paramMap.put("id", id);
        paramMap.put("searchKey", param.get("searchKey"));

        Field fieldVo = s.fieldService.findByFieldId(paramMap);

        String fieldEn = fieldVo.getFieldEn();
        if (!fieldEn.equals("") && fieldEn != null) {
//            fieldEn = fieldEn.replace("f_", "");
            fieldVo.setFieldEn(fieldEn);
        }

        for (FieldCond f : fieldVo.getFieldCondList()) {

            List<FieldSubCondVo> fieldSubCondList = JSONObject.parseArray(f.getContent(), FieldSubCondVo.class);
            for (FieldSubCondVo fs : fieldSubCondList) {
                Map<String, Object> paramMap2 = new HashMap<String, Object>();
                paramMap2.put("userId", userId);
                paramMap2.put("organId", organId);
                paramMap2.put("id", fs.getFieldId());

                Field subField = s.fieldService.findByFieldId(paramMap2);

                fs.setValueType(subField.getValueType());
                fs.setValueScope(subField.getValueScope());
                fs.setFieldCn(subField.getFieldCn());
            }
            f.setFieldSubCond(fieldSubCondList);
        }

        //编辑页面返回字段列表时去掉自己
        paramMap.put("fieldId", id);
//        List<Field> flist = s.fieldService.findByUser(paramMap);
        String valueScope = fieldVo.getValueScope();
        List<String> scopeList = null;
        if(StringUtils.isNotBlank(valueScope)){
            scopeList = Arrays.asList(valueScope.split(","));
        }

        List<FieldFormulaVo> fieldFormulaList = new ArrayList<FieldFormulaVo>();
        String hasGroovy = null;
        if (fieldVo.getFormulaShow() != null && !fieldVo.getFormulaShow().equals("")) {
            fieldFormulaList = JSONObject.parseArray(fieldVo.getFormulaShow(), FieldFormulaVo.class);

            for (FieldFormulaVo fieldFormulaVo : fieldFormulaList) {
                if (fieldFormulaVo.getFormula() != null && fieldFormulaVo.getFormula().contains("def main")) {
                    hasGroovy = "y";
                }
            }
        }

        String hasFormula = null;
        if (StringUtils.isNotBlank(fieldVo.getFormula())) {
            hasFormula = "y";
        }

        // model.addAttribute("fieldVo", fieldVo);
        // model.addAttribute("hasFormula", hasFormula);
        // model.addAttribute("hasGroovy", hasGroovy);
        // model.addAttribute("engineId", null);
        // model.addAttribute("fieldTypeId", Long.valueOf(fieldVo.getFieldTypeId()));
        // model.addAttribute("searchKey", param.get("searchKey"));
        // model.addAttribute("flist", flist);
        // model.addAttribute("scopeList", scopeList);
        // model.addAttribute("fieldFormulaList", fieldFormulaList);
        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put("fieldVo", fieldVo);
        modelMap.put("hasFormula", hasFormula);
        modelMap.put("hasGroovy", hasGroovy);
        modelMap.put("engineId", null);
        modelMap.put("fieldTypeId", Long.valueOf(fieldVo.getFieldTypeId()));
        modelMap.put("searchKey", param.get("searchKey"));
//        modelMap.put("flist", flist);
        modelMap.put("scopeList", scopeList);
        modelMap.put("fieldFormulaList", fieldFormulaList);

        return ResponseEntityBuilder.buildNormalResponse(modelMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/update 2.08. 编辑指标
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {String} searchKey searchKey
     * @apiParam {Long} id 指标ID
     * @apiParam {String} formula 衍生字段公式
     * @apiParam {String} formulaShow 衍生字段公式回显信息
     * @apiParam {Long} engineId 归属的引擎ID
     * @apiParam {String} fieldEn 指标英文名：拼接前缀"f_"
     * @apiParam {String} fieldCn 指标中文名
     * @apiParam {Long} fieldTypeId 字段类型编号
     * @apiParam {Integer} valueType 字段存值类型,待选(0),数值型(1),字符型(2),枚举型(3),小数型(4)
     * @apiParam {Integer} isDerivative 是否衍生字段，0代表不是，1代表是
     * @apiParam {Integer} isOutput 是否输出字段，0代表不是，1代表是
     * @apiParam {String} valueScope 字段约束范围
     * @apiParam {JSONArray} fieldCondList 条件区域内容（传字符串）
     * @apiParam (fieldCondList) {String} conditionValue 字段条件值
     * @apiParam (fieldCondList) {JSONArray} fieldSubCond 字段列表
     * @apiParam (fieldSubCond) {String} fieldId 条件字段编号
     * @apiParam (fieldSubCond) {String} operator 条件字段的运算符
     * @apiParam (fieldSubCond) {String} fieldValue 条件字段的条件设置值
     * @apiParam (fieldSubCond) {String} logical 条件字段间的逻辑符
     * @apiParam {JSONArray} formulaHidden 公式编辑、groovy脚本内容（传字符串）
     * @apiParam (formulaHidden) {String} formula 衍生字段公式
     * @apiParam (formulaHidden) {String} idx 下标
     * @apiParam (formulaHidden) {JSONArray} farr 字段列表
     * @apiParam (farr) {String} fieldCN 字段中文名
     * @apiParam (farr) {String} fieldCond 字段条件区域
     * @apiParam {Boolean} isUseSql 是否使用sql获取指标
     * @apiParam {Integer} dataSourceId 使用sql获取指标时对应的数据源
     * @apiParam {String} sqlStatement 使用sql获取指标时对应的sql语句
     * @apiSuccess {String} status 状态: 1成功, 0失败
     * @apiParamExample {json} 请求示例：
     * {"searchKey":"","fieldTypeId":"302","id":876,"formula":"","formulaShow":"","engineId":"","fieldEn":"f_f_f_network_real_amount_derive","fieldCn":"网点余额衍生字段","valueType":"1","isDerivative":"1","isOutput":"0","valueScope":"[-1,9999999)","fieldCondList":[{"fieldSubCond":[{"fieldId":"824","operator":">","fieldValue":"2000","logical":"&&"},{"fieldId":"826","operator":"<","fieldValue":"5000"}],"conditionValue":"666"},{"fieldSubCond":[{"fieldId":"824","operator":"<=","fieldValue":"2000","logical":"&&"},{"fieldId":"826","operator":"<","fieldValue":"5000"}],"conditionValue":"888"}],"formulaHidden":[{"fvalue":"","formula":"@同盾_评分@ - @第三方分值@ ","idx":"0","farr":[{"fieldCN":"同盾_评分","fieldCond":""},{"fieldCN":"第三方分值 ","fieldCond":""}]}],"isUseSql":"0","dataSourceId":null,"sqlStatement":""}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {}
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDATE_FILED)
    public ResponseEntityDto<Object> update(@RequestBody FieldSaveParam fieldSaveParam) {

        Map paramMap = JSONObject.parseObject(JSONObject.toJSONString(fieldSaveParam), Map.class);

        // 校验
        HashMap<String, Object> fieldEnValidateMap = new HashMap<>();
        fieldEnValidateMap.put("fieldEn", paramMap.get("fieldEn"));
        fieldEnValidateMap.put("engineId", paramMap.get("engineId"));
        fieldEnValidateMap.put("Id", paramMap.get("id"));
        int fieldEnNum = fieldEnAjaxValidate(fieldEnValidateMap);
        if (fieldEnNum > 0) {
            throw new ApiException(ErrorCodeEnum.FIELD_EN_REPEAT.getCode(), ErrorCodeEnum.FIELD_EN_REPEAT.getMessage());
        }
        s.fieldService.sqlFieldCheck(paramMap);
        HashMap<String, Object> fieldCnValidateMap = new HashMap<>();
        fieldCnValidateMap.put("fieldCn", paramMap.get("fieldCn"));
        fieldCnValidateMap.put("engineId", paramMap.get("engineId"));
        fieldCnValidateMap.put("Id", paramMap.get("id"));
        int fieldCnNum = fieldCnAjaxValidate(fieldCnValidateMap);
        if (fieldCnNum > 0) {
            throw new ApiException(ErrorCodeEnum.FIELD_CN_REPEAT.getCode(), ErrorCodeEnum.FIELD_CN_REPEAT.getMessage());
        }

        HashMap<String, Object> checkFieldMap = new HashMap<>();
        checkFieldMap.put("fieldId", paramMap.get("id").toString());
        Map<String, Object> checkFieldresult = checkField(checkFieldMap);
        boolean beUsed = (boolean) checkFieldresult.get("beUsed");
        if (beUsed) {
            throw new ApiException(ErrorCodeEnum.FIELD_BE_USERD.getCode(), ErrorCodeEnum.FIELD_BE_USERD.getMessage());
        }

        Long userId = SessionManager.getLoginAccount().getUserId();
        Long organId = SessionManager.getLoginAccount().getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("organId", organId);

        paramMap.put("engineId", null);
        //组织通用字段
        Integer isCommon = 1;
        paramMap.put("isCommon", isCommon);
//		String formulaFields = "50,70,90";
//		paramMap.put("formulaFields", formulaFields);

        s.fieldService.updateField(paramMap);

        // model.addAttribute("engineId", null);
        // model.addAttribute("fieldTypeId", fieldTypeId);
        // model.addAttribute("searchKey", paramMap.get("searchKey"));
        // model.addAttribute("isCommon", 1);

        HashMap<String, Object> modelMap = new HashMap<>();
        modelMap.put("engineId", null);
        //modelMap.put("fieldTypeId",fieldTypeId);
        modelMap.put("searchKey", paramMap.get("searchKey"));
        modelMap.put("isCommon", 1);

        return ResponseEntityBuilder.buildNormalResponse(modelMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/findFieldByUser 2.09. 获当前用户的所有指标
     * @apiGroup field
     * @apiVersion 2.0.0
     * @apiParam {String} [searchKey] searchKey
     * @apiParam {String} [engineId] engineId
     * @apiParam {String} [fieldId] fieldId
     * @apiSuccess {String} status 状态: 1成功, 0失败
     * @apiParamExample {json} 请求示例：
     * {}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {"status":"1","error":"00000000","msg":null,"data":{"organId":46,"isCommon":1,"searchKey":"","userId":135,"fieldList":[{"page":0,"rows":0,"total":null,"id":587,"fieldEn":"f_hr_age","fieldCn":"年龄准入","fieldTypeId":270,"fieldType":"准入","valueType":1,"valueTypeName":null,"valueScope":"(-1,999999]","isDerivative":null,"isDerivativeName":null,"isOutput":null,"isOutputName":null,"isCommon":null,"formula":null,"formulaShow":null,"usedFieldId":null,"origFieldId":null,"author":null,"nickName":null,"created":null,"engineId":null,"engineName":null,"status":null,"fieldCondList":null,"fieldRelId":8739,"dataSourceId":null,"sqlStatement":null,"useSql":null},{"page":0,"rows":0,"total":null,"id":871,"fieldEn":"f_ziduan111428","fieldCn":"字段111428","fieldTypeId":362,"fieldType":"3月11日测试","valueType":2,"valueTypeName":null,"valueScope":"qwer","isDerivative":null,"isDerivativeName":null,"isOutput":null,"isOutputName":null,"isCommon":null,"formula":null,"formulaShow":null,"usedFieldId":null,"origFieldId":null,"author":null,"nickName":null,"created":null,"engineId":null,"engineName":null,"status":null,"fieldCondList":null,"fieldRelId":10294,"dataSourceId":null,"sqlStatement":null,"useSql":null},{"page":0,"rows":0,"total":null,"id":872,"fieldEn":"f_ziduan111518","fieldCn":"字段111518","fieldTypeId":327,"fieldType":"ddd","valueType":2,"valueTypeName":null,"valueScope":"qwer","isDerivative":null,"isDerivativeName":null,"isOutput":null,"isOutputName":null,"isCommon":null,"formula":null,"formulaShow":null,"usedFieldId":null,"origFieldId":null,"author":null,"nickName":null,"created":null,"engineId":null,"engineName":null,"status":null,"fieldCondList":null,"fieldRelId":10295,"dataSourceId":null,"sqlStatement":null,"useSql":null},{"page":0,"rows":0,"total":null,"id":873,"fieldEn":"f_ziduan111733","fieldCn":"字段111733","fieldTypeId":327,"fieldType":"ddd","valueType":2,"valueTypeName":null,"valueScope":"qwer","isDerivative":null,"isDerivativeName":null,"isOutput":null,"isOutputName":null,"isCommon":null,"formula":null,"formulaShow":null,"usedFieldId":null,"origFieldId":null,"author":null,"nickName":null,"created":null,"engineId":null,"engineName":null,"status":null,"fieldCondList":null,"fieldRelId":10296,"dataSourceId":null,"sqlStatement":null,"useSql":null},{"page":0,"rows":0,"total":null,"id":876,"fieldEn":"f_f_f_network_real_amount_derive","fieldCn":"网点余额衍生字段","fieldTypeId":302,"fieldType":"物流","valueType":1,"valueTypeName":null,"valueScope":"[-1,9999999)","isDerivative":null,"isDerivativeName":null,"isOutput":null,"isOutputName":null,"isCommon":null,"formula":null,"formulaShow":null,"usedFieldId":null,"origFieldId":null,"author":null,"nickName":null,"created":null,"engineId":null,"engineName":null,"status":null,"fieldCondList":null,"fieldRelId":10299,"dataSourceId":null,"sqlStatement":null,"useSql":null}],"engineId":null,"fieldId":""}}
     */
    @RequestMapping(value = "/findFieldByUser", method = RequestMethod.POST)
    public ResponseEntityDto<Object> findFieldByUser(@RequestBody HashMap<String, Object> paramMap) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        Long organId = SessionManager.getLoginAccount().getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("organId", organId);

        Integer isCommon = 1;
        Integer engineId = null;
        if (paramMap.containsKey("engineId") && !paramMap.get("engineId").equals("")) {
            isCommon = 0;
            engineId = Integer.valueOf((String) paramMap.get("engineId")).intValue();
            paramMap.put("engineId", engineId);
        } else {
            isCommon = 1;
            paramMap.put("engineId", null);
        }
        paramMap.put("isCommon", isCommon);

        if (!paramMap.containsKey("fieldId")) {
            paramMap.put("fieldId", null);
        }

        List<Field> fieldList = s.fieldService.findByUser(paramMap);

        paramMap.put("fieldList", fieldList);

        return ResponseEntityBuilder.buildNormalResponse(paramMap);
    }

    private Map<String, Object> checkField(Map<String, Object> param) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        Long organId = SessionManager.getLoginAccount().getOrganId();
        param.put("userId", userId);
        param.put("organId", organId);
        param.put("engineId", null);

        return s.fieldService.checkField(param);
    }

    public int fieldEnAjaxValidate(HashMap<String, Object> paramMap) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        paramMap.put("userId", userId);

        if ("".equals(paramMap.get("engineId"))) {
            paramMap.put("engineId", null);
        }

        if ("".equals(paramMap.get("Id"))) {
            paramMap.put("Id", null);
        }

        paramMap.put("fieldCn", null);

        return s.fieldService.isExists(paramMap);
    }

    public int fieldCnAjaxValidate(HashMap<String, Object> paramMap) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        paramMap.put("userId", userId);

        if ("".equals(paramMap.get("engineId"))) {
            paramMap.put("engineId", null);
        }

        if ("".equals(paramMap.get("Id"))) {
            paramMap.put("Id", null);
        }

        paramMap.put("fieldEn", null);

        return s.fieldService.isExists(paramMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/downTemplate 2.10. 指标导入模板下载
     * @apiGroup field
     * @apiVersion 2.0.0
     */
    @RequestMapping("downTemplate")
    public ResponseEntity<byte[]> downExcelTemplate() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("excleTemplate/field.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String("基础指标导入模板.xlsx".getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(inputStream), headers, HttpStatus.CREATED);
    }

    /**
     * @api {POST} /v2/datamanage/field/upload 2.11. 批量导入指标
     * @apiGroup field
     * @apiVersion 2.0.0
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public ResponseEntityDto<Object> upload(HttpServletRequest request) throws Exception {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"

        String accessUrl = "";
        String fileName = "";
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {

                    String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/fieldUpload/";
                    if (!new File(uploadDir).exists()) {
                        File dir = new File(uploadDir);
                        dir.mkdirs();
                    }
                    fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    String path = uploadDir + fileName;
                    //上传
                    file.transferTo(new File(path));
                    accessUrl = path;
                }
            }
        }

        Long userId = SessionManager.getLoginAccount().getUserId();
        Long organId = SessionManager.getLoginAccount().getOrganId();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("author", userId);
        paramMap.put("organId", organId);
        Integer isCommon = 0;
        if (paramMap.get("engineId") == null) {
            isCommon = 1;
        } else {
            isCommon = 0;
        }
        paramMap.put("isCommon", isCommon);

        Map<String, Object> resultMap = s.fieldService.importExcel(accessUrl, paramMap);
        return ResponseEntityBuilder.buildNormalResponse(resultMap);
    }

    /**
     * @api {POST} /v2/datamanage/field/getEngineFields 2.xx. 获取引擎可用字段
     * @apiGroup zzzzz01
     * @apiVersion 2.0.0
     * @apiParam {Integer} isOutput 是否输出字段，0代表不是，1代表是，默认不是(0)
     * @apiParam {Integer} [engineId] 引擎id
     * @apiSuccess {String} status 状态：1成功，0失败
     * @apiParamExample {json} 请求示例：
     * {"isOutput":0}
     * {"isOutput":1}
     * {"isOutput":0,"engineId":211}
     * {"isOutput":1,"engineId":211}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {待完善}
     */
    @RequestMapping(value = "/getEngineFields", method = RequestMethod.POST)
    public ResponseEntityDto<Object> getEngineFields(@RequestBody Map<String, Object> paramMap) {
        //通过引擎编号查询可用字段,后面可以放到缓存中
        User user = SessionManager.getLoginAccount();
        paramMap.put("userId", user.getUserId());
        paramMap.put("organId", user.getOrganId());
        List<Field> fields = s.fieldService.getFieldList(paramMap);
        if (CollectionUtil.isNotNullOrEmpty(fields)) {
            List<FieldEnumVo> fieldEnumVos = new ArrayList<FieldEnumVo>();
            FieldEnumVo fieldEnumVo = null;
            for (Field field : fields) {
                fieldEnumVo = new FieldEnumVo();
                fieldEnumVo.setField(field);
                if (field.getValueType() == ValueType.Enum.getValue()) {
                    //如果是枚举
                    String valueScope = field.getValueScope();
                    if (StringUtil.isValidStr(valueScope)) {
                        //获取枚举值
                        fieldEnumVo.setEnums(Arrays.asList(valueScope.split(CommonConst.SYMBOL_COMMA)));
                    }
                }
                fieldEnumVos.add(fieldEnumVo);
            }
            return ResponseEntityBuilder.buildNormalResponse(fieldEnumVos);
        }
        return ResponseEntityBuilder.buildNormalResponse(new ArrayList<FieldEnumVo>());
    }

    @PostMapping(value = "/updateFieldFolder")
    @ArchivesLog(operationType = OpTypeConst.UPDATE_FIELD_FOLDER)
    public ResponseEntityDto<Object> updateFieldFolder(@RequestBody UpdateFolderParam param){
        UpdateFolderParam.checkNotNull(param);
        int result = s.fieldService.updateFieldFolder(param);
        if (result>0){
            return ResponseEntityBuilder.buildNormalResponse("成功移动"+result+"条数据");
        }
        return ResponseEntityBuilder.buildErrorResponse("移动失败","");
    }
}
