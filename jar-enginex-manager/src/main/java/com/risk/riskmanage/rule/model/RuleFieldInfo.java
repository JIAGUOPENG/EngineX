package com.risk.riskmanage.rule.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_rule_field")
public class RuleFieldInfo  implements Serializable {
    private static final long serialVersionUID = -132321133324148507L;
    @TableId(type = IdType.AUTO)
    private Long id;

    private String logical;//逻辑运算符

    private String  operator;//运算符

    private String fieldValue;//字段值

    private Long ruleId;//关联的规则的id

    private String fieldId;//关联的字段的id
    @TableField(exist = false)
    private String fieldEn;//关联的字段的英文名称
    @TableField(exist = false)
    private String field;//字段内容
    @TableField(exist = false)
    private Integer valueType;//关联的字段的值类型

}
