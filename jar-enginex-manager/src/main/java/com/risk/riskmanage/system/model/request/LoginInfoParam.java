package com.risk.riskmanage.system.model.request;

import lombok.Data;

@Data
public class LoginInfoParam {
    private String account;// 账号
    private String password;// 密码
}
