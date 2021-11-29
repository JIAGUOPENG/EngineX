package com.risk.riskmanage.rule.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.risk.riskmanage.rule.model.vo.RuleConditionVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 规则条件表(t_rule_condition)实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors
@TableName("`t_rule_condition`")
public class RuleConditionInfo
        implements Serializable {
    private static final long serialVersionUID = -55937038829167862L;

    @TableId(type = IdType.AUTO)
    private Long id;//主键ID

    private String logical;//关系节点的逻辑符号：&&（并关系），||（或关系）

    private Long fieldId;//表达式节点对应的字段id

    private String fieldEn;//字段的en或者json类型的key
    private Integer fieldType;//字段的类型：1中间变量 2 入参

    private String operator;//表达式节点的操作符

    private Integer variableType;//变量类型

    private String fieldValue;//表达式节点对应字段的限定值

    private String executionLogic;//表达式执行逻辑

    private Long ruleId;//规则表的id

    private Long versionId;//规则版本的id

    private Long parentId;//父节点的id

    private Integer conditionType;//规则节点的类型：1-关系节点，2-表达式节点

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    @TableField(exist = false)
    private String insertTempId;//插入时临时id

    @TableField(exist = false)
    private String TempParentId;//插入时临时父id

    @TableField(exist = false)
    private Integer valueType;//字段值类型

    @TableField(exist = false)
    private List<RuleLoopGroupAction> loopGroupActions = new ArrayList<>();//循环组对应的条件

    @TableField(exist = false)
    private RuleConditionVo loopResultCondition;//for对应的结果条件的计算条件树
    @TableField(exist = false)
    private RuleConditionVo condGroupResultCondition;//条件组对应的结果计算条件树
}
