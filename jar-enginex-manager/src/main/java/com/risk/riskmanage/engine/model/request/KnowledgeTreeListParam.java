package com.risk.riskmanage.engine.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeTreeListParam {
    private String treeType;//树形分类：0：基础规则树  1：评分卡的树 2：回收站的树 3：决策表树，4：复杂规则树（逗号分割）
    private Long organId;//组织id
    private Long userId;//用户id
    private Integer status;//状态 -1删除
    private Integer type;//1组织通用
}
