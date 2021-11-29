package com.risk.riskmanage.common.model.requestParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryListParam<T> {
    private Integer pageNum = 1;  // 第几页
    private Integer pageSize = 10;  // 每页的数量
    private T entity;//查询实体对象
}
