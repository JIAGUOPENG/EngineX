package com.risk.riskmanage.interfacemanage.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class InterfaceUpdateStatusParam {
    private Long[] ids;//id
    private Integer status;//状态
}
