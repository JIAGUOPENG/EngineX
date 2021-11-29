package com.risk.riskmanage.rule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.risk.riskmanage.common.model.requestParam.StatusParam;
import com.risk.riskmanage.rule.model.RuleVersion;
import com.risk.riskmanage.rule.model.vo.RuleVersionVo;

import java.util.List;

public interface RuleVersionService extends IService<RuleVersion> {
    RuleVersionVo queryById(Long id);

    List<RuleVersionVo> queryVersionListByRuleId(Long RuleId);



    List<String> queryFieldEnByVersionId(Long versionId);

    int addVersionList(List<RuleVersionVo> versionList);
    boolean addVersion(RuleVersionVo version);

    boolean copyVersion(RuleVersionVo version);

    boolean updateVersion(RuleVersionVo version);

    boolean updateStatus(StatusParam statusParam);
}
