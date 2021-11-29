package com.risk.riskmanage.system.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单返回对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenuVo implements Serializable {
    private static final long serialVersionUID = 621966254529550199L;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单地址
     */
    private String index;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单
     */
    private List<SysMenuVo> subs;

}
