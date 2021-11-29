package com.risk.riskmanage.rule.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.risk.riskmanage.common.model.requestParam.StatusParam;
import com.risk.riskmanage.datamanage.mapper.FieldMapper;
import com.risk.riskmanage.rule.mapper.RuleVersionMapper;
import com.risk.riskmanage.rule.model.RuleConditionInfo;
import com.risk.riskmanage.rule.model.RuleVersion;
import com.risk.riskmanage.rule.model.vo.RuleConditionVo;
import com.risk.riskmanage.rule.model.vo.RuleVersionVo;
import com.risk.riskmanage.rule.service.RuleConditionService;
import com.risk.riskmanage.rule.service.RuleVersionService;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.tactics.consts.TacticsType;
import com.risk.riskmanage.tactics.model.TacticsOutput;
import com.risk.riskmanage.tactics.service.TacticsOutputService;
import com.risk.riskmanage.util.CustomValueUtils;
import com.risk.riskmanage.util.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RuleVersionServiceImpl extends ServiceImpl<RuleVersionMapper,RuleVersion> implements RuleVersionService  {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RuleVersionMapper versionMapper;
    @Autowired
    private RuleConditionService conditionService;
    @Autowired
    private TacticsOutputService outputService;
    @Autowired
    private FieldMapper fieldMapper;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public RuleVersionVo queryById(Long id) {
        RuleVersion ruleVersion = versionMapper.selectById(id);
        RuleVersionVo result = new  RuleVersionVo();
        if (ruleVersion==null){
            return result;
        }
        BeanUtils.copyProperties(ruleVersion,result);
        //查询ruleCondition组装成树形结构
        RuleConditionVo ruleConditionVo = conditionService.queryByVersionId(id);
        List<TacticsOutput>  tacticsOutputList = outputService.queryByTactics(new TacticsOutput(id, TacticsType.COMPLEX_RULE,TacticsType.OutType.SUCCESS_OUT));
        List<TacticsOutput>  failOutputList = outputService.queryByTactics(new TacticsOutput(id, TacticsType.COMPLEX_RULE,TacticsType.OutType.FAIL_OUT));
        result.setRuleConditionVo(ruleConditionVo);
        result.setTacticsOutputList(tacticsOutputList);
        result.setFailOutputList(failOutputList);
        return result;
    }

    @Override
    public List<RuleVersionVo> queryVersionListByRuleId(Long RuleId) {
        LambdaQueryWrapper<RuleVersion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RuleVersion::getRuleId,RuleId);
        queryWrapper.eq(RuleVersion::getStatus,1);
        queryWrapper.orderByDesc(RuleVersion::getId);
        List<RuleVersion> ruleVersionList = versionMapper.selectList(queryWrapper);
        List<RuleVersionVo> ruleVersionVoList = new ArrayList<>();
        for (RuleVersion ruleVersion :  ruleVersionList) {
            RuleVersionVo versionVo = new RuleVersionVo();
            BeanUtils.copyProperties(ruleVersion,versionVo);
            ruleVersionVoList.add(versionVo);
        }
        return ruleVersionVoList;
    }

    @Override
    public List<String> queryFieldEnByVersionId(Long versionId) {
        Set<String>  fieldEns= new HashSet<>();
        Set<Long>  fieldIds= new HashSet<>();
        RuleConditionVo ruleConditionVo = conditionService.queryByVersionId(versionId);
        List<RuleConditionInfo> ruleConditionInfoList = conditionService.disassemble(ruleConditionVo, versionId, false);
        for (RuleConditionInfo info : ruleConditionInfoList) {
            if (info.getFieldEn()!=null&&info.getFieldType()!=1&&!info.getFieldEn().startsWith("%")){
                if (info.getFieldEn().contains(".")){
                    fieldEns.add(info.getFieldEn().split("\\.")[0]);
                }
                else {
                    fieldEns.add(info.getFieldEn());
                }
            }else if(info.getFieldId()!=null){
                fieldIds.add(info.getFieldId());
            }
            if (info.getVariableType()!=null){
                if (info.getVariableType()==2&&info.getFieldValue()!=null&&!info.getFieldValue().contains("%")){
                    fieldEns.add(info.getFieldValue());
                }else if (info.getVariableType()==3){
                    fieldEns.addAll(CustomValueUtils.getFieldEnSet(info.getFieldValue()));
                }
            }
        }
        for (Long fieldId : fieldIds) {
            String fieldName= fieldMapper.findFieldNameById(fieldId);
            fieldEns.add(fieldName);
        }
        return new ArrayList<>(fieldEns);
    }

    @Override
    @Transactional
    public int addVersionList(List<RuleVersionVo> versionList) {
        int result = 0;
        for (RuleVersionVo versionVo : versionList) {
            boolean b = addVersion(versionVo);
            if (b){
                result++;
            }
        }
        return result;
    }

    @Override
    @Transactional
    public boolean addVersion(RuleVersionVo version) {
        User loginUser = SessionManager.getLoginAccount();
        version.setOrganId(loginUser.getOrganId());
        version.setCreateUserId(loginUser.getUserId());
        version.setUpdateUserId(loginUser.getUserId());
        version.setCreateTime(null);
        version.setUpdateTime(null);
        version.setStatus(1);
        if (version.getVersionCode()==null){
            version.setVersionCode("V:0");
        }
        if (version.getDescription()==null){
            version.setDescription("初始版本");
        }
        int insert = versionMapper.insert(version);
        if (insert>0){
            this.addVersionDetail(version);
            this.saveSnapshot(version.getId());
            return true;
        }else {
            logger.error("新增规则版本失败{}",version);
        }
        return false;
    }
    @Transactional
    public boolean addVersionDetail(RuleVersionVo version){
        RuleConditionVo ruleConditionVo = version.getRuleConditionVo();
        ruleConditionVo.setVersionId(version.getId());
        //添加条件信息
        conditionService.insertRuleCondition(ruleConditionVo,version.getRuleId());
        //添加输出字段
        List<TacticsOutput> tacticsOutputList = version.getTacticsOutputList();
        if (tacticsOutputList!=null&&tacticsOutputList.size()>0){
            outputService.insertTacticsOutput(version.getId(),tacticsOutputList );
        }
        List<TacticsOutput> failOutputList = version.getFailOutputList();
        if (failOutputList!=null&&failOutputList.size()>0){
            outputService.insertTacticsOutput(version.getId(),failOutputList );
        }
        return true;
    }

    @Override
    @Transactional
    public boolean copyVersion(RuleVersionVo version) {
        RuleVersionVo versionVo = this.queryById(version.getId());
        versionVo.setId(null);
        versionVo.setVersionCode(version.getVersionCode());
        versionVo.setDescription(version.getDescription());
        return this.addVersion(versionVo);
    }

    @Override
    @Transactional
    public boolean updateVersion(RuleVersionVo version) {
        Long versionId = version.getId();
        if (versionId==null){
            return false;
        }
        User loginUser = SessionManager.getLoginAccount();
        version.setUpdateUserId(loginUser.getUserId());
        //修改版本主表
        versionMapper.updateById(version);
        //修改条件表
        conditionService.updateRuleCondition(version.getRuleId(),version.getRuleConditionVo());
        //修改策略输出
        outputService.updateTacticsOutput(versionId,version.getTacticsOutputList(),version.getFailOutputList(),TacticsType.COMPLEX_RULE);
        this.saveSnapshot(versionId);
        return true;
    }


    @Override
    @Transactional
    public boolean updateStatus(StatusParam statusParam) {
        LambdaQueryWrapper<RuleVersion> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.in(RuleVersion::getId,statusParam.getIds());
        updateWrapper.eq(RuleVersion::getRuleId,statusParam.getTacticsId());
        RuleVersion ruleVersion = new RuleVersion();
        ruleVersion.setStatus(statusParam.getStatus());
        boolean update = this.update(ruleVersion, updateWrapper);
        return update;
    }


    private boolean saveSnapshot(Long versionId){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LambdaUpdateWrapper<RuleVersion> wrapper = new  LambdaUpdateWrapper<>();
                RuleVersionVo versionVo = queryById(versionId);
                versionVo.setSnapshot(null);
                wrapper.eq(RuleVersion::getId,versionId).set(RuleVersion::getSnapshot, JSON.toJSONString(versionVo));
                versionMapper.update(null,wrapper);
            }
        });
        return true;
    }
}
