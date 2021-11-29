package com.risk.riskmanage.rule.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("`t_rule_version`")
public class RuleVersion implements Serializable {
    private static final long serialVersionUID = -1850194333747447612L;
    /**
     * 规则版本主键id
     */
    @TableId( type = IdType.AUTO)
    private Long id;
    /**
     * 规则id
     */
    private Long ruleId;
    /**
     * 规则版本号
     */
    private String versionCode;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 状态：-1 删除 0停用 1启用
     */
    private Integer status;
    /**
     * 规则结果en(命中情况)
     */
    private String resultFieldEn;
    /**
     * 规则得分
     */
    private Integer score;
    /**
     * 规则得分的en
     */
    private String scoreFieldEn;
    /**
     * 组织id
     */
    private Long organId;
    /**
     * 创建者id
     */
    private Long createUserId;
    /**
     * 修改者id
     */
    private Long updateUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 快照信息
     */
    private String snapshot;
}
