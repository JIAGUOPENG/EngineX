package com.risk.riskmanage.tactics.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_tactics_output")
public class TacticsOutput implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 699491471584300246L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 字段id
     */
    private Long fieldId;
    /**
     * 字段的en
     */
    private String fieldEn;
    /**
     * 字段值
     */
    private String fieldValue;
    /**
     * 字段值的类型：1 常量、2 变量，3.自定义
     */
    private Integer variableType;
    /**
     * 关联的策略id
     */
    private Long tacticsId;
    /**
     * 关联的策略类型 base_rule.基础规则 scorecard.评分卡 decision_tables.决策表 decision_tree.决策树 complex_rule.复杂规则 list_db.名单库 models.机器学习模型
     */
    private String tacticsType;
    /**
     * 输出条件
     */
    private String outCondition;
    /**
     * 输出类型：success为成功时候输出，fail为失败时候的输出
     */
    private String outType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    public TacticsOutput(Long tacticsId, String tacticsType) {
        this.tacticsId = tacticsId;
        this.tacticsType = tacticsType;
    }

    public TacticsOutput(Long tacticsId, String tacticsType, String outType) {
        this.tacticsId = tacticsId;
        this.tacticsType = tacticsType;
        this.outType = outType;
    }
}
