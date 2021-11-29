package com.risk.riskmanage.rule.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class RuleUpdateStatusParam {
    private String ids;//id
    private Integer status;//状态
}
