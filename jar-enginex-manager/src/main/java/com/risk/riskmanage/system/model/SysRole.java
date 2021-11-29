
package com.risk.riskmanage.system.model;

import java.io.Serializable;
import java.util.Date;



public class SysRole implements Serializable {

    private static final long serialVersionUID = -1L;

    private long id;
    private long organId;
    private String roleName;
    private String roleCode;//角色代号
    private String roleDesc;
    private String author;//创建者
    private Date birth;//创建时间
    private int status;  //状态0禁用1启用
    
    
    
    
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public long getOrganId() {
		return organId;
	}
	public void setOrganId(long organId) {
		this.organId = organId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SysRole [id=" + id + ", organId=" + organId + ", roleName="
				+ roleName + ", roleCode=" + roleCode + ", roleDesc=" + roleDesc
				+ ", author=" + author + ", birth=" + birth + ", status="
				+ status + "]";
	}
	
	
    
}
