package com.risk.riskmanage.datasource.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_field_data_source")
public class DataSource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 连接名称
     */
    private String name;

    /**
     * 数据源类型：MySQL、Oracle、SQLServer、Hive、Spark、Redis
     */
    private String type;

    /**
     * 主机地址
     */
    private String host;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 端口
     */
    private String port;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 状态 0：无效，1：有效
     */
    private Integer status;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 修改人
     */
    private Integer modifier;

    /**
     * 企业编号
     */
    private Integer organId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 连接地址
     */
    private String url;

    /**
     * spark路径
     */
    private String sparkHome;

    /**
     * spark应用程序的名称
     */
    private String appName;

    /**
     * spark地址
     */
    private String masterUrl;
}
