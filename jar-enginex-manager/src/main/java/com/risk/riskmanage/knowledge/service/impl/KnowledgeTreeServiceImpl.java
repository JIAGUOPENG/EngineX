package com.risk.riskmanage.knowledge.service.impl;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.engine.model.request.KnowledgeTreeListParam;
import com.risk.riskmanage.knowledge.model.KnowledgeTree;
import com.risk.riskmanage.knowledge.service.KnowledgeTreeService;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.SessionManager;
import com.risk.riskmanage.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName:KnowledgeTreeServiceImpl <br/>
 * Description: 知识库目录接口实现类. <br/>
 */
@Service
public class KnowledgeTreeServiceImpl extends BaseService implements KnowledgeTreeService {


    @Override
    public List<Map> getTreeList(Map<String, Object> param) {

        try {
            if (param.get("parentId").toString().equals("99999999")) {
                param.put("parentId", 0);
            }
        } catch (Exception e) {
        }

        // if (this.isBlack(param.get("parentId"))) {
        //     param.put("parentId", 0);
        // }

        if (null == param.get("parentId") || "".equals(param.get("parentId"))) {
            param.put("parentId", 0);
        }
        if (null == param.get("type") || "".equals(param.get("type"))) {
            param.put("type", 1);
        }
        if (null == param.get("status") || "".equals(param.get("status"))) {
            param.put("status", new int[]{1});
        }

        if (param.get("treeType")!=null&&!"".equals(param.get("treeType").toString())){
            String name = param.get("treeType").toString();
            switch (name){
                case "0":
                    param.put("name", "基础规则集");
                    param.put("tree_type", "0");
                    break;
                case "1":
                    param.put("name", "评分卡");
                    param.put("tree_type", "1");
                    break;
                case "2":
                    param.put("name", "回收站");
                    param.put("tree_type", "2");
                    break;
                case "3":
                    param.put("name", "决策表");
                    param.put("tree_type", "3");
                    break;
                case "4":
                    param.put("name", "决策树");
                    param.put("tree_type", "4");
                    break;
                case "5":
                    param.put("name", "复杂规则集合");
                    param.put("tree_type", "5");
                    break;
                case "7":
                    param.put("name","脚本规则集");
                    param.put("tree_type","7");
                    break;
                case "8":
                    param.put("name","集合操作");
                    param.put("tree_type","8");
                    break;
                default:
                    throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(),ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
            }
        }else {
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(),ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        param.putAll(getParam(param));
        param.put("sort", true);
        param.put("tree_type", StringUtil.toLongList(param.get("tree_type").toString()));
        List<KnowledgeTree> klist = knowledgeTreeMapper.getTreeListV2(param);
        String engineIdStr = (String) param.get("engineId");
        for (KnowledgeTree knowledgeTree : klist) {
            param.put("parentId", knowledgeTree.getId());
            if (!StringUtil.isBlank(engineIdStr) && (int) knowledgeTree.getType() == 1 && knowledgeTree.getTreeType() == 0) {
                knowledgeTree.setName(knowledgeTree.getName() + "(通用)");
            }
            knowledgeTree.setChildren(getChildren(param));
        }
        KnowledgeTree[] kArray = new KnowledgeTree[klist.size()];
        kArray = klist.toArray(kArray);
        param.put("children", kArray);

        param.put("id", 99999999);

        ArrayList<Map> list = new ArrayList<>();
        list.add(param);
        return list;
    }

    @Override
    public KnowledgeTree findById(Long id) {
        // TODO Auto-generated method stub
        return knowledgeTreeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean insertTree(KnowledgeTree k) {
        knowledgeTreeMapper.insertSelective(k);
        return true;
    }

    @Override
    public boolean updateTree(KnowledgeTree k) {
        knowledgeTreeMapper.updateByPrimaryKeySelective(k);
        return true;
    }



    @Override
    public List<KnowledgeTree> getTreeDataForEngine(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        // return knowledgeTreeMapper.getTreeDataForEngine(paramMap);
        return knowledgeTreeMapper.getTreeDataForEngineV2(paramMap);
    }

    @Override
    public List<KnowledgeTree> getFolderList(KnowledgeTreeListParam param) {
        User user = SessionManager.getLoginAccount();
        param.setOrganId(user.getOrganId());

        List<KnowledgeTree> list = knowledgeTreeMapper.selectFolderList(param);
        return list;
    }

    private Map<String, Object> getParam(Map<String, Object> paramMap) {
        User user = SessionManager.getLoginAccount();
        paramMap.put("userId", user.getUserId());
        paramMap.put("organId", user.getOrganId());
        return paramMap;
    }

    /**
     * getChildren:(获取树形节点的子节点信息)
     *
     * @param param 请求参数集合
     * @return
     *
     */
    private KnowledgeTree[] getChildren(Map<String, Object> param) {
        List<KnowledgeTree> klist = knowledgeTreeMapper.getTreeList(param);
        String engineIdStr = (String) param.get("engineId");
        for (KnowledgeTree knowledgeTree : klist) {
            param.put("parentId", knowledgeTree.getId());
            if (!StringUtil.isBlank(engineIdStr) && (int) knowledgeTree.getType() == 1 && knowledgeTree.getTreeType() == 0) {
                knowledgeTree.setName(knowledgeTree.getName() + "(通用)");
            }
            knowledgeTree.setChildren(getChildren(param));
        }
        KnowledgeTree[] kArray = new KnowledgeTree[klist.size()];
        kArray = klist.toArray(kArray);
        return kArray;
    }
}
