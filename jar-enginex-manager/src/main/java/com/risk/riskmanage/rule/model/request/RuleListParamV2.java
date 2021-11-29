package com.risk.riskmanage.rule.model.request;

import com.risk.riskmanage.rule.model.RuleInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleListParamV2 {
    protected Integer pageNum = 1;  // 第几页
    protected Integer pageSize = 10;  // 每页的数量


//    protected Boolean search = false;  // 是否搜索

    protected RuleInfo ruleInfo;//查询实体对象

//    protected Integer parentId = 0;  // 文件夹的id
}
