package com.risk.riskmanage.datamanage.model;

import com.risk.riskmanage.common.model.BasePage;

import java.io.Serializable;

public class FieldType extends BasePage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 字段类型名
     */
    private String fieldType;

    /**
     * 父节点编号
     */
    private Integer parentId;

    /**
     * 是否组织定义的通用字段类型
     */
    private Integer isCommon;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 字段类型的子类集合
     */
    private FieldType[] children;

    /**
     * 是否为父类
     */
    private String isParent = "true";

    /**
     * 引擎编号
     */
    private Integer engineId;

    /**
     * 文件夹图片路径
     */
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsCommon() {
        return isCommon;
    }

    public void setIsCommon(Integer isCommon) {
        this.isCommon = isCommon;
    }

    public FieldType[] getChildren() {
        return children;
    }

    public void setChildren(FieldType[] children) {
        this.children = children;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Integer getEngineId() {
        return engineId;
    }

    public void setEngineId(Integer engineId) {
        this.engineId = engineId;
    }

    public String getIcon() {
//		if(engineId!=null)
//			icon = "../../resource/images/authority/folder.png";
//		else
        icon = "../resource/images/authority/folder.png";
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


}
