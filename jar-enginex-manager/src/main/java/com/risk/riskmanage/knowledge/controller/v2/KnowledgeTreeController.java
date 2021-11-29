package com.risk.riskmanage.knowledge.controller.v2;

import com.risk.riskmanage.common.basefactory.BaseController;
import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.knowledge.common.consts.Status;
import com.risk.riskmanage.knowledge.model.KnowledgeTree;
import com.risk.riskmanage.knowledge.model.Rule;
import com.risk.riskmanage.logger.ArchivesLog;
import com.risk.riskmanage.rule.service.RuleService;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.SessionManager;
import com.risk.riskmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 提供 knowledge 相关接口
 *
 * @apiName knowledge
 * @apiDefine knowledge 3.规则管理
 */
@Controller("KnowledgeTreeControllerV2")
@RequestMapping("/v2/knowledge/tree")
@ResponseBody
public class KnowledgeTreeController extends BaseController {
    @Autowired
    private RuleService ruleService;


    /**
     * @api {POST} /v2/knowledge/tree/list 3.01. 节点 获取目录 list：规则集、物流、银行、回收站等
     * @apiGroup knowledge
     * @apiVersion 2.0.0
     * @apiParam {Integer} parentId parentId
     * @apiParam {String} tree_type 树形分类（默认为0）  0:规则树，1:评分卡的树，2:回收站的树。（多个逗号分割）
     * @apiParam {Integer} [type] 目录类型（默认为1）  0:系统的目录、1:组织的目录、2:引擎的目录
     * @apiSuccess {String} status 状态：1成功, 0失败
     * @apiParamExample {json} 请求示例：
     * {"parentId":0,"tree_type":"0,2","type":1}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {"status":"1","error":"00000000","msg":null,"data":{"parentId":1155,"tree_type":[0,2],"engineId":null,"type":1,"userId":135,"organId":46,"sort":true,"kArray":[{"id":1038,"name":"规则集","parentId":0,"userId":1,"organId":46,"engineId":null,"created":1498722046000,"type":1,"treeType":0,"status":1,"updated":1610332377000,"children":[{"id":1052,"name":"准入规则","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1498725927000,"type":1,"treeType":0,"status":1,"updated":1498725963000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1055,"name":"首贷反欺诈1","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1498725941000,"type":1,"treeType":0,"status":1,"updated":1505019204000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1056,"name":"反欺诈2","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1498725943000,"type":1,"treeType":0,"status":1,"updated":1498725975000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1057,"name":"反欺诈3","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1498725945000,"type":1,"treeType":0,"status":1,"updated":1498725980000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1058,"name":"复贷反欺诈1","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1505021066000,"type":1,"treeType":0,"status":1,"updated":1505021076000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1070,"name":"储蓄卡","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1520228395000,"type":1,"treeType":0,"status":1,"updated":1520228402000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1079,"name":"有盾","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1523523861000,"type":1,"treeType":0,"status":1,"updated":1523523866000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1080,"name":"同盾","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1526268525000,"type":1,"treeType":0,"status":1,"updated":1526268530000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1081,"name":"富数","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1526537754000,"type":1,"treeType":0,"status":1,"updated":1526537758000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1082,"name":"通讯录","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1527823577000,"type":1,"treeType":0,"status":1,"updated":1527823583000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1083,"name":"规则集MS","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1527823577000,"type":1,"treeType":0,"status":1,"updated":1527823583000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1084,"name":"规则集MX","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1559825319000,"type":1,"treeType":0,"status":1,"updated":1559825347000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1087,"name":"规则集TZ","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1559825352000,"type":1,"treeType":0,"status":1,"updated":1559825370000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1088,"name":"规则集YD","parentId":1038,"userId":135,"organId":46,"engineId":null,"created":1559825373000,"type":1,"treeType":0,"status":1,"updated":1559825378000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1122,"name":"规则集DY","parentId":1038,"userId":136,"organId":46,"engineId":null,"created":1610701146000,"type":1,"treeType":0,"status":1,"updated":1610703786000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1}],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1103,"name":"物流","parentId":0,"userId":141,"organId":46,"engineId":null,"created":1609742341000,"type":1,"treeType":0,"status":1,"updated":1611304985000,"children":[{"id":1104,"name":"网点余额不足风险","parentId":1103,"userId":141,"organId":46,"engineId":null,"created":1610332019000,"type":1,"treeType":0,"status":1,"updated":1610332055000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1}],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1109,"name":"银行","parentId":0,"userId":142,"organId":46,"engineId":null,"created":1610503557000,"type":1,"treeType":0,"status":1,"updated":1610503562000,"children":[{"id":1110,"name":"信用卡","parentId":1109,"userId":142,"organId":46,"engineId":null,"created":1610503567000,"type":1,"treeType":0,"status":1,"updated":1610503576000,"children":[{"id":1111,"name":"客服智能营销服务","parentId":1110,"userId":142,"organId":46,"engineId":null,"created":1610503699000,"type":1,"treeType":0,"status":1,"updated":1610503702000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1112,"name":"智能短信服务","parentId":1110,"userId":142,"organId":46,"engineId":null,"created":1610503750000,"type":1,"treeType":0,"status":1,"updated":1610503752000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1113,"name":"信用卡新户礼客户准入","parentId":1110,"userId":142,"organId":46,"engineId":null,"created":1610503758000,"type":1,"treeType":0,"status":1,"updated":1610503760000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1}],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1}],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1039,"name":"回收站","parentId":0,"userId":1,"organId":46,"engineId":null,"created":1498722046000,"type":1,"treeType":2,"status":1,"updated":1498722046000,"children":[{"id":1123,"name":"新建文件夹","parentId":1039,"userId":136,"organId":46,"engineId":null,"created":1610701170000,"type":1,"treeType":0,"status":-1,"updated":1611643120000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1129,"name":"新建文件夹","parentId":1039,"userId":143,"organId":46,"engineId":null,"created":1611545787000,"type":1,"treeType":0,"status":-1,"updated":1611643122000,"children":[{"id":1130,"name":"新建文件夹","parentId":1129,"userId":143,"organId":46,"engineId":null,"created":1611545828000,"type":1,"treeType":0,"status":1,"updated":1611545828000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1}],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1131,"name":"新建文件夹","parentId":1039,"userId":144,"organId":46,"engineId":null,"created":1611573817000,"type":1,"treeType":0,"status":-1,"updated":1611643123000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1132,"name":"新建文件夹","parentId":1039,"userId":144,"organId":46,"engineId":null,"created":1611575638000,"type":1,"treeType":0,"status":-1,"updated":1612409450000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1},{"id":1155,"name":"新建文件夹","parentId":1039,"userId":145,"organId":46,"engineId":null,"created":1612494472000,"type":1,"treeType":0,"status":-1,"updated":1612494477000,"children":[],"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1}],"isParent":"true","icon":"../resource/images/datamanage/cabage.png","isLastNode":"true","directoryType":1}]}}
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntityDto<Object> list(@RequestBody Map<String, Object> param) {
        List<Map> list = s.knowledgeTreeService.getTreeList(param);
        return ResponseEntityBuilder.buildNormalResponse(list);
    }
    /**
     * save:(保存新增节点)
     * @param param 请求参数集合
     * @return
     *
     */
    /**
     * @api {POST} /v2/knowledge/tree/save 3.02. 节点 新增
     * @apiGroup knowledge
     * @apiVersion 2.0.0
     * @apiParam {Integer} name 节点名字
     * @apiParam {Integer} parentId parentId
     * @apiParam {Integer} treeType 树形分类  0:规则树、1:评分卡的树、2:回收站的树
     * @apiParam {Integer} [type] 默认值为1，表示组织的目录
     * @apiParam {Integer} engineId null
     * @apiSuccess {String} status 状态: 1成功, 0失败
     * @apiParamExample {json} 请求示例：
     * {"name":"我是文件夹1047","parentId":"0","treeType":"0","type":"1","engineId":""}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {"status":"1","error":"00000000","msg":null,"data":{"name":"我是文件夹1047","parentId":"0","treeType":"0","type":"1","engineId":"","userId":135,"organId":46,"result":1,"node":{"id":1162,"name":"我是文件夹1047","parentId":0,"userId":135,"organId":46,"engineId":null,"created":null,"type":1,"treeType":0,"status":1,"updated":null,"children":null,"isParent":"true","icon":"../resource/images/authority/folder.png","isLastNode":"","directoryType":1}}}
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.SAVE_KNOWLEDGE_TREE)
    public ResponseEntityDto<Object> save(@RequestBody Map<String, Object> param) {
        KnowledgeTree k = new KnowledgeTree();
        Map<String, Object> paramMap = getParam(param);
        k.setName((String) param.get("name"));
        if (param.containsKey("treeType")
                && !StringUtil.isBlank(param.get("treeType").toString())) {
            k.setTreeType(Integer.parseInt(param.get("treeType").toString()));
        }
        // 加
        if (null == paramMap.get("type") || "".equals(paramMap.get("type"))) {
            paramMap.put("type", 1);
        }
        if (paramMap.containsKey("type")
                && !StringUtil.isBlank(param.get("type").toString())) {
            k.setType(Integer.parseInt(paramMap.get("type").toString()));
        }
        if (paramMap.containsKey("organId")
                && !StringUtil.isBlank(param.get("organId").toString())) {
            k.setOrganId(Long.parseLong(paramMap.get("organId").toString()));
        }
        if (paramMap.containsKey("engineId")
                && !StringUtil.isBlank(param.get("engineId").toString())) {
            k.setEngineId(Long.parseLong(paramMap.get("engineId").toString()));
        }
        if (param.containsKey("parentId")
                && !StringUtil.isBlank(param.get("parentId").toString())) {
            k.setParentId(Long.parseLong(param.get("parentId").toString()));
        }
        if (paramMap.containsKey("userId")
                && !StringUtil.isBlank(param.get("userId").toString())) {
            k.setUserId(Long.parseLong(paramMap.get("userId").toString()));
        }
        k.setStatus(Status.ENABLED);
        boolean flag = s.knowledgeTreeService.insertTree(k);
        if (flag) {
            param.put("result", 1);
        } else {
            param.put("result", -1);
        }
        param.put("node", k);
        return ResponseEntityBuilder.buildNormalResponse(param);
    }

    /**
     * @api {POST} /v2/knowledge/tree/update 3.03. 节点 修改/删除
     * @apiGroup knowledge
     * @apiVersion 2.0.0
     * @apiParam {Integer} id 节点id
     * @apiParam {String} name 节点名称
     * @apiParam {Integer} engineId engineId
     * @apiSuccess {String} status 状态: 1成功, 0失败
     * @apiParamExample {json} 请求示例：
     * 修改：
     * {"id":1162,"name":"文件夹1127","engineId":""}
     * 删除：
     * {"id":1162,"status":"-1","type":"1","engineId":"","parentId":0}
     * @apiSuccessExample {json} 成功返回数据示例：
     * {"status":"1","error":"00000000","msg":null,"data":{"id":1162,"name":"文件夹1127","engineId":"","userId":135,"organId":46,"result":1}}
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDATE_KNOWLEDGE_TREE)
    public ResponseEntityDto<Object> update(@RequestBody Map<String, Object> param) {
        if (null == param.get("type") || "".equals(param.get("type"))) {
            param.put("type", 1);
        }
        Long id = Long.parseLong(param.get("id").toString());
        KnowledgeTree k = s.knowledgeTreeService.findById(id);
        param.putAll(getParam(param));
        if (param.containsKey("name")) {
            k.setName((String) param.get("name"));
        }
        if (param.containsKey("status")) {
            int status = Integer.parseInt(param.get("status").toString());
            k.setStatus(status);
            List<Long> idList = new ArrayList<Long>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("parentIds", StringUtil.toLongList(k.getId().toString()));
            map.put("type", param.get("type"));
            map.put("engineId", param.get("engineId"));
            map.put("organId", k.getOrganId());
            if (status == -1) {
                map.put("status", StringUtil.toLongList("0,1"));
            }
            if (status == 1) {
                map.put("status", StringUtil.toLongList("-1"));
            }
            if (k.getTreeType() == 0) {
                List<Rule> rlist = s.ruleService.getRuleList(map);
                if (rlist != null && rlist.size() > 0) {
                    for (Rule r : rlist) {
                        idList.add(r.getId());
                    }
                    map.put("status", status);
                    map.put("idList", idList);
//                    s.ruleService.updateRuleStatus(map);
                    ruleService.updateStatus(idList, status);
                }
            }
            if (k.getTreeType() == 3) {
//                decisionTablesService.updateStatus(idList.toArray(new Long[idList.size()]),status);
            }
        }
        if (param.containsKey("parentId")) {
            k.setParentId(Long.parseLong(param.get("parentId").toString()));
        }
        boolean flag = s.knowledgeTreeService.updateTree(k);
        if (flag) {
            param.put("result", 1);
        } else {
            param.put("result", -1);
        }
        return ResponseEntityBuilder.buildNormalResponse(param);
    }

    /**
     * getParam:(根据用户角色或权限,获取所需参数集合)
     *
     * @return paramMap
     */
    private Map<String, Object> getParam(Map<String, Object> paramMap) {
        User user = SessionManager.getLoginAccount();
        paramMap.put("userId", user.getUserId());
        paramMap.put("organId", user.getOrganId());
        return paramMap;
    }


}
