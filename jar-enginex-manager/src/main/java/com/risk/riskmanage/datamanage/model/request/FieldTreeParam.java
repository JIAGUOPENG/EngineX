package com.risk.riskmanage.datamanage.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldTreeParam {
    //获取需要
    private Integer type;
    //删除需要
    private Long id;//文件夹的id
    private String fieldType;//文件夹名字
    private Integer status;//状态 -1 删除
    //新建时需要
    private String parentId;//上层文件夹id
    private Integer isCommon;//

    private Long organId;//指标相关的组织id
    private Long userId;//用户id
}
