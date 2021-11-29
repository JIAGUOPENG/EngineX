package com.risk.riskmanage.tactics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.risk.riskmanage.tactics.model.TacticsOutput;


import java.util.List;


public interface TacticsOutputService extends IService<TacticsOutput> {
    boolean insertTacticsOutput(Long tacticsId,List<TacticsOutput> list);

    boolean insertTacticsOutput(Long tacticsId,List<TacticsOutput> list,String outType);


    List<TacticsOutput> queryByTactics(TacticsOutput entity);

    boolean updateTacticsOutput(Long tacticsId, List<TacticsOutput> list,String tacticsType);

    boolean updateTacticsOutput(Long tacticsId, List<TacticsOutput> successList, List<TacticsOutput> failList,String tacticsType);

    boolean deleteByTactics(TacticsOutput entity);
}
