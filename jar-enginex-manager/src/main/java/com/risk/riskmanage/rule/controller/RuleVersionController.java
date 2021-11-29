package com.risk.riskmanage.rule.controller;

import com.risk.riskmanage.common.constants.OpTypeConst;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.model.ResponseEntityDto;
import com.risk.riskmanage.common.model.requestParam.StatusParam;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.logger.ArchivesLog;
import com.risk.riskmanage.rule.model.vo.RuleVersionVo;
import com.risk.riskmanage.rule.service.RuleVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/v3/ruleVersion")
public class RuleVersionController {

    @Autowired
    private RuleVersionService versionService;
    /**
     * 查询指定版本下的内容
     * @param versionId
     * @return
     */
    @PostMapping("/getRuleVersionInfo/{versionId}")
    public ResponseEntityDto getRuleVersionInfo(@PathVariable Long versionId) {
        RuleVersionVo version =versionService.queryById(versionId);
        return ResponseEntityBuilder.buildNormalResponse(version);
    }

    /**
     * 新增一个版本
     * @param version
     * @return
     */
    @PostMapping("/addRuleVersion")
    @ArchivesLog(operationType = OpTypeConst.SAVE_RULE_VERSION)
    public ResponseEntityDto addRuleVersion(@RequestBody  RuleVersionVo version){
        boolean b = versionService.addVersion(version);
        if (!b){
            return ResponseEntityBuilder.buildErrorResponse(ErrorCodeEnum.PARAMS_EXCEPTION);
        }
        List< RuleVersionVo> ruleVersionList = versionService.queryVersionListByRuleId(version.getRuleId());
        return ResponseEntityBuilder.buildNormalResponse(ruleVersionList);
    }

    /**
     * 复制版本
     * @param version
     * @return
     */
    @PostMapping("/copyRuleVersion")
    @ArchivesLog(operationType = OpTypeConst.COPY_RULE_VERSION)
    public ResponseEntityDto copyRuleVersion(@RequestBody  RuleVersionVo version){
        boolean b = versionService.copyVersion(version);
        List< RuleVersionVo> ruleVersionList = versionService.queryVersionListByRuleId(version.getRuleId());
        return ResponseEntityBuilder.buildNormalResponse(ruleVersionList);
    }

    /**
     * 修改版本
     * @param version
     * @return
     */
    @PostMapping("/updateRuleVersion")
    @ArchivesLog(operationType = OpTypeConst.UPDATE_RULE_VERSION)
    public ResponseEntityDto updateRuleVersion(@RequestBody RuleVersionVo version){
        boolean b = versionService.updateVersion(version);
        List<RuleVersionVo> ruleVersionList = versionService.queryVersionListByRuleId(version.getRuleId());
        return ResponseEntityBuilder.buildNormalResponse(ruleVersionList);
    }

    /**
     * 修改状态
     * @param statusParam
     * @return
     */
    @RequestMapping(value = "/updateRuleVersionStatus", method = RequestMethod.POST)
    @ArchivesLog(operationType = OpTypeConst.UPDATE_RULE_VERSION_STATUS)
    public ResponseEntityDto<Object> updateStatus(@RequestBody StatusParam statusParam) {
        versionService.updateStatus(statusParam);
        List<RuleVersionVo> ruleVersionList = versionService.queryVersionListByRuleId(statusParam.getTacticsId());
        return ResponseEntityBuilder.buildNormalResponse(ruleVersionList);
    }

}
