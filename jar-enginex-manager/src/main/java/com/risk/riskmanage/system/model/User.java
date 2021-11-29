package com.risk.riskmanage.system.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.risk.riskmanage.common.model.BasePage;

/**
 * @ClassName: UserVo <br/>
 * @Description: 后台用户实体类. <br/>
 */
public class User  extends BasePage implements Serializable  {

	/**
	 * serialVersionUID:TODO(序列化对象使用)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 关系id
	 */
	private Long userId;
	/**
	 * 企业id
	 */
	private Long organId;
	/**
	 * 用户id
	 */
	private String account;
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 电话
	 */
	private String cellphone;
	/**
	 * qq
	 */
	private String qq;
	/**
	 * 
	 */
	private String latestTime;
	/**
	 * 最后一次登录IP
	 */
	private String latestIp;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date birth;
	/**
	 * 创建时间
	 */
	private Long parentId;
	/**
	 * 删除ids数组
	 */
	private Long []deletIds;
	
	public Long[] getDeletIds() {
		return deletIds;
	}

	public void setDeletIds(Long[] deletIds) {
		this.deletIds = deletIds;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", organId=" + organId + ", account="
				+ account + ", password=" + password + ", nickName=" + nickName
				+ ", email=" + email + ", cellphone=" + cellphone + ", qq=" + qq
				+ ", latestTime=" + latestTime + ", latestIp=" + latestIp
				+ ", status=" + status + ", birth=" + birth + ", parentId="
				+ parentId + ", deletIds=" + Arrays.toString(deletIds) + "]";
	}
	
	

	
}
