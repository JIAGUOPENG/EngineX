package com.risk.riskmanage.system.model;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {

    private static final long serialVersionUID = -1L;
    
    private long id;//用户（主键）
    private long organId;//组织编号
    private String employeeId;//员工编号
    private String account;//账户
    private String password;
    private String nickName;//昵称
    private String email;
    private String cellphone;
    private String qq;
    private String latestTime;
    private String latestIp;
    private int status;
    private Date birth;//创建时间
    private String author;//创建人
    private SysRole sysRole;//角色对象
    private SysOrganization sysOrgan;//公司对象
    
    
    
    
  
	public SysOrganization getSysOrgan() {
		return sysOrgan;
	}
	public void setSysOrgan(SysOrganization sysOrgan) {
		this.sysOrgan = sysOrgan;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrganId() {
		return organId;
	}
	public void setOrganId(long organId) {
		this.organId = organId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getLatestTime() {
		return latestTime;
	}
	public void setLatestTime(String latestTime) {
		this.latestTime = latestTime;
	}
	public String getLatestIp() {
		return latestIp;
	}
	public void setLatestIp(String latestIp) {
		this.latestIp = latestIp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public SysRole getSysRole() {
		return sysRole;
	}
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@Override
	public String toString() {
		return "SysUser [id=" + id + ", organId=" + organId + ", employeeId="
				+ employeeId + ", account=" + account + ", password=" + password
				+ ", nickName=" + nickName + ", email=" + email + ", cellphone="
				+ cellphone + ", qq=" + qq + ", latestTime=" + latestTime
				+ ", latestIp=" + latestIp + ", status=" + status + ", birth="
				+ birth + ", author=" + author + ", sysOrgan="+sysOrgan+", sysRole=" + sysRole + "]";
	}
    
	
}
