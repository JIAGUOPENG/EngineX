
package com.risk.riskmanage.system.model;

import java.io.Serializable;
import java.util.Date;



public class SysOrganization implements Serializable {

    private static final long serialVersionUID = -1L;
    
    private long id;//组织编号
    private String name;//组织名称
    private String code;//组织代号
    private String email;
    private String telephone;
    private int status;//0禁用1启用
    private String author;//创建者
    private Date birth;//创建时间
    private String token;//唯一标识
    
    
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@Override
	public String toString() {
		return "SysOrganization [id=" + id + ", name=" + name + ", versionCode=" + code
				+ ", email=" + email + ", telephone=" + telephone + ", status="
				+ status + ", author=" + author + ", birth=" + birth
				+ ", token=" + token + "]";
	}
	
	
    
}
