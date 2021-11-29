package com.risk.riskmanage.common.enums;

public enum ErrorCodeEnum {

    SERVER_ERROR(ErrorCodeEnum.ERROR_CODE + 101, "服务繁忙,请稍后再试!"),
    LOGIN_ERROR(ErrorCodeEnum.ERROR_CODE + 102, "登录失败"),
    ERROR_TOKEN_EXPIRE(ErrorCodeEnum.ERROR_CODE + 103, "登录授权码已过期"),
    FIELD_TYPE_REPEAT(ErrorCodeEnum.ERROR_CODE + 104, "字段类型已存在"),
    FIELD_EN_REPEAT(ErrorCodeEnum.ERROR_CODE + 105, "字段英文名已存在"),
    FIELD_CN_REPEAT(ErrorCodeEnum.ERROR_CODE + 106, "字段中文名已存在"),
    FIELD_BE_USERD(ErrorCodeEnum.ERROR_CODE + 107, "字段被使用，无法修改"),
    LIST_DB_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 108, "黑白名单名称已存在"),
    RULE_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 109, "规则名称已存在"),
    RULE_CODE_REPEAT(ErrorCodeEnum.ERROR_CODE + 110, "规则代码已存在"),
    SCORECARD_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 111, "评分卡名称已存在"),
    SCORECARD_CODE_REPEAT(ErrorCodeEnum.ERROR_CODE + 112, "评分卡代码已存在"),
    UN_PERMISSION(ErrorCodeEnum.ERROR_CODE + 113, "没有访问权限"),
    CREATE_USER_NAME_ERROR(ErrorCodeEnum.ERROR_CODE + 114, "姓名不能为超级管理员"),
    CREATE_USER_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 115, "账号或员工编号已存在"),
    CREATE_ROLE_ADMIN_REPEAT(ErrorCodeEnum.ERROR_CODE + 116, "每个公司只能创建一个公司管理员"),
    CREATE_ROLE_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 117, "角色名已存在"),
    CREATE_MENU_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 118, "名称或编号已存在"),
    CREATE_ORGAN_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 119, "名称或编号已存在"),

    UPDATE_RULE_ERROR(ErrorCodeEnum.ERROR_CODE+120,"修改规则内容失败"),
    RULE_CONDITION_TYPE_ERROR(ErrorCodeEnum.ERROR_CODE+121,"规则条件类型错误"),
    RULE_SAVE_ERROR(ErrorCodeEnum.ERROR_CODE+122,"规则保存失败"),

    RULE_UPLOAD_ERROR(ErrorCodeEnum.ERROR_CODE+123,"规则导入失败"),
    DECISION_TABLES_CODE_REPEAT(ErrorCodeEnum.ERROR_CODE + 124, "决策表代码已存在"),
    DECISION_TABLES_SAVE_ERROR(ErrorCodeEnum.ERROR_CODE+125,"决策表保存失败"),
    DECISION_TABLES_UPDATE_ERROR(ErrorCodeEnum.ERROR_CODE+126,"决策表修改状态失败"),
    DECISION_TABLES_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 127, "决策表名称已存在"),
    DECISION_TREE_SAVE_ERROR(ErrorCodeEnum.ERROR_CODE+130,"决策树保存失败"),
    DECISION_TREE_UPDATE_ERROR(ErrorCodeEnum.ERROR_CODE+131,"决策树修改状态失败"),
    LIST_OPERATION_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE+132,"集合操作名称重复"),
    LIST_OPERATION_Code_REPEAT(ErrorCodeEnum.ERROR_CODE+133,"集合操作代码重复"),

    PARAMS_EXCEPTION(ErrorCodeEnum.ERROR_CODE + 555, "参数异常"),
    CLASS_CAST_EXCEPTION(ErrorCodeEnum.ERROR_CODE + 556, "类型转换异常"),
    JSON_CAST_EXCEPTION(ErrorCodeEnum.ERROR_CODE + 557, "JSON转换异常"),
    NULL_POINT_EREXCEPTION(ErrorCodeEnum.ERROR_CODE + 558, "NPE问题，请联系管理员"),
    DATA_IS_NOT_EXIST(ErrorCodeEnum.ERROR_CODE + 559, "数据不存在"),
    SECTION_ERROR(ErrorCodeEnum.ERROR_CODE + 560, "区间有误"),

    SCORECARD_NOT_SESECT(ErrorCodeEnum.ERROR_CODE + 561, "在本条路径上，评分卡节点有空值"),
    NODECHILD_NOT_SESECT(ErrorCodeEnum.ERROR_CODE + 562, "在本条路径上，子引擎节点有空值"),
    DECISION_TABLES_NOT_SELECT(ErrorCodeEnum.ERROR_CODE + 563, "在本条路径上，决策表节点有空值"),
    FOLDER_NOT_EXIST(ErrorCodeEnum.ERROR_CODE+564,"文件夹不存在"),

    UPDATE_INTERFACE_ERROR(ErrorCodeEnum.ERROR_CODE+565,"修改接口内容失败"),
    INTERFACE_CONDITION_TYPE_ERROR(ErrorCodeEnum.ERROR_CODE+566,"接口条件类型错误"),
    INTERFACE_SAVE_ERROR(ErrorCodeEnum.ERROR_CODE+567,"接口保存失败"),
    INTERFACE_NAME_REPEAT(ErrorCodeEnum.ERROR_CODE + 568, "接口名称已存在"),
    FAIL_IN_LINK(ErrorCodeEnum.ERROR_CODE+600,"失败"),
    SQL_FIELD_HAVE_RISK(ErrorCodeEnum.ERROR_CODE+601,"存在有风险sql关键词" ),

    FILE_UPLOAD_ERROR(ErrorCodeEnum.ERROR_CODE+602,"导入失败");

    /**
     * 默认ERROR_CODE.<br>
     * 按公司要求8位长度，前两位产品。
     */
    public static final String ERROR_CODE = "01000";

    private String code;
    private String message;

    private ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}