package com.risk.riskmanage.rule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import com.risk.riskmanage.knowledge.model.response.UploadResponse;
import com.risk.riskmanage.rule.model.RuleInfo;
import com.risk.riskmanage.rule.model.request.RuleListParamV2;
import com.risk.riskmanage.rule.model.vo.RuleVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface RuleService extends IService<RuleInfo> {

    /**
     * 通过ID查询单条数据
     * @param id 主键
     * @return 实例对象
     */
    RuleVo queryById(Long id,Integer difficulty);

    /**
     * 根据实体类条件查询
     * @param ruleListParam
     * @return
     */
    public PageInfo queryByEntity(RuleListParamV2 ruleListParam);


    /**
     * 新增数据
     * @param rule 实例对象
     * @return 实例对象
     */
    RuleVo insertRuleInfo(RuleVo rule);

    /**
     * 修改数据
     * @param rule 实例对象
     * @return 实例对象
     */
    RuleVo updateRuleInfo(RuleVo rule);


    boolean updateStatus(List<Long> ids,Integer status);

    boolean updateParent(List<Long> ids,Long parentId);

    UploadResponse upload(HttpServletRequest request) throws Exception;

    List<String> queryFieldEnByRuleId(Long ruleId);
}
