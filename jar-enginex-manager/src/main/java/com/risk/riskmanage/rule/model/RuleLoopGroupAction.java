package com.risk.riskmanage.rule.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (RuleLoopGroupAction)实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_rule_loop_group_action")
public class RuleLoopGroupAction implements Serializable {
    private static final long serialVersionUID = -47370055295043749L;
    /**
     * 循环组动作表主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 对应条件表中for的id
     */
    private Long conditionForId;
    /**
     * 对应条件表中条件id
     */
    private Long conditionGroupId;
    /**
     * 动作类型 1-求和，2-赋值，3-输出输出变量，4-输出常量
     */
    private Integer actionType;
    /**
     * 动作的key
     */
    private String actionKey;
    /**
     * 动作的value
     */
    private String actionValue;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


}
