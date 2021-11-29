package com.risk.riskmanage.tactics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.risk.riskmanage.tactics.consts.TacticsType;
import com.risk.riskmanage.tactics.mapper.TacticsOutputMapper;
import com.risk.riskmanage.tactics.model.TacticsOutput;
import com.risk.riskmanage.tactics.service.TacticsOutputService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TacticsOutputServiceImpl extends ServiceImpl<TacticsOutputMapper, TacticsOutput> implements TacticsOutputService {
    @Transactional
    @Override
    public boolean insertTacticsOutput(Long tacticsId, List<TacticsOutput> list) {
        if (tacticsId == null) {
            return false;
        }
        if (list == null || list.size() == 0) {
            return true;
        }
        for (TacticsOutput tacticsOutput : list) {
            tacticsOutput.setTacticsId(tacticsId);
        }
        boolean saveResult = this.saveBatch(list);
        return saveResult;
    }

    @Override
    public boolean insertTacticsOutput(Long tacticsId, List<TacticsOutput> list, String outType) {
        if (tacticsId == null || StringUtils.isBlank(outType)) {
            return false;
        }
        if (list == null || list.size() == 0) {
            return true;
        }
        for (TacticsOutput tacticsOutput : list) {
            tacticsOutput.setTacticsId(tacticsId);
            tacticsOutput.setOutType(outType);
        }
        boolean saveResult = this.saveBatch(list);
        return saveResult;
    }

    @Override
    public List<TacticsOutput> queryByTactics(TacticsOutput entity) {
        return this.list(new QueryWrapper<>(entity));
    }

    @Transactional
    @Override
    public boolean updateTacticsOutput(Long tacticsId, List<TacticsOutput> list, String tacticsType) {
        TacticsOutput tacticsOutput = new TacticsOutput(tacticsId, tacticsType);
        boolean delete = this.deleteByTactics(tacticsOutput);
        if (!delete && this.queryByTactics(tacticsOutput).size() != 0) {
            return false;
        }
        return this.insertTacticsOutput(tacticsId, list);
    }

    @Transactional
    @Override
    public boolean updateTacticsOutput(Long tacticsId, List<TacticsOutput> successList, List<TacticsOutput> failList, String tacticsType) {
        TacticsOutput tacticsOutput = new TacticsOutput(tacticsId, tacticsType);
        boolean delete = this.deleteByTactics(tacticsOutput);
        if (!delete && this.queryByTactics(tacticsOutput).size() != 0) {
            return false;
        }
        if (successList != null && !successList.isEmpty()) {
            this.insertTacticsOutput(tacticsId, successList, TacticsType.OutType.SUCCESS_OUT);
        }
        if (failList != null && !failList.isEmpty()) {
            this.insertTacticsOutput(tacticsId, failList, TacticsType.OutType.FAIL_OUT);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean deleteByTactics(TacticsOutput entity) {
        return this.remove(new QueryWrapper<>(entity));
    }


}
