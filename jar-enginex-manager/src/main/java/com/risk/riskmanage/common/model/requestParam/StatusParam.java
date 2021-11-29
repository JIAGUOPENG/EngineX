package com.risk.riskmanage.common.model.requestParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusParam<T> {
    private static final long serialVersionUID = 8131487634836541557L;

    private Integer status;//状态

    private List<Long> ids;//主键id

    private Long tacticsId;//相关策略id

    private List<T> list;//针对需要指定类型的需要传入实体类
}
