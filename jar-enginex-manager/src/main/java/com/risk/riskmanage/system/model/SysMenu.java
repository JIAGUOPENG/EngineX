package com.risk.riskmanage.system.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SysMenu implements Serializable {

    private static final long serialVersionUID = -1L;

    private long id; 
    private long userId;//分配者
    private String name; //资源名称
    private String code;//资源代号
    private String url;//路径
    private long parentId;//父节点
    private String des;
    private Date birth;//创建时间
    private String icon;//图标
	private Integer sort; // 菜单顺序
    private int status;//状态
    private long roleId;//角色id
    private boolean checked;//菜单默认选中
    private boolean chkDisabled;//节点是否禁用
    private boolean isHidden;//节点是否隐藏

}
