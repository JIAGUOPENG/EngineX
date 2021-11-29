package com.risk.riskmanage.knowledge.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * ClassName:KnowledgeTreeVo <br/>
 * Description: 知识库树形菜单实体类. <br/>
 */
public class KnowledgeTree  implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * */
	private Long id;
	
	/**
	 * 目录名称
	 * */
	private String name;
	
	/**
	 * 父节点id
	 * */
	private Long parentId;
	
	/**
	 * 创建人id
	 * */
	private Long userId;
	
	/**
	 * 组织id
	 * */
	private Long organId;
	
	
	/**
	 * 引擎id
	 * */
	private Long engineId;
	
	/**
	 * 创建日期
	 * */
	private Date created;
	
	/**
	 * 目录类型  0 : 系统的目录  1：组织的目录 2： 引擎的目录
	 * */
	private Integer type;
	
	/**
	 * 树形分类：0：规则树  1：评分卡的树 2：回收站的树
	 * */
	private Integer treeType;
	
	/**
	 * 状态   0 :停用 ，1 : 启用，-1：删除
	 * */
	private Integer status;
	
	/**
	 * 修改日期
	 * */
	private Date updated;
	
	/**
	 * 子类集合
	 * */
	private KnowledgeTree[] children;
	
	/**
	 * 是否为父类
	 * */
	private String isParent = "true";
	
	/**
	 *文件夹图片路径
	 * */
	private String icon="";
	
	private String	isLastNode=""; 

	private  Integer directoryType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public Long getEngineId() {
		return engineId;
	}

	public void setEngineId(Long engineId) {
		this.engineId = engineId;
	}
	
	@JsonIgnore
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
    @JsonIgnore
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public KnowledgeTree[] getChildren() {
		return children;
	}

	public void setChildren(KnowledgeTree[] children) {
		this.children = children;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public Integer getTreeType() {
		return treeType;
	}

	public void setTreeType(Integer treeType) {
		this.treeType = treeType;
	}

	public String getIcon() {
//		if((int)treeType == 2 || (int)treeType == 3){
//			icon = "../resource/images/datamanage/cabage.png";
//			isLastNode ="true";
//		}else{
//			icon = "../resource/images/authority/folder.png";
//		}
		return icon;
	}

	public Integer getDirectoryType() {
		return directoryType = type ;
	}

	public String getIsLastNode() {
//		if((int)treeType == 2 || (int)treeType == 3){
//			isLastNode ="true";
//		}
		return isLastNode;
	}

	@Override
	public String toString() {
		return "KnowledgeTree [id=" + id + ", name=" + name + ", parentId="
				+ parentId + ", userId=" + userId + ", organId=" + organId
				+ ", engineId=" + engineId + ", created=" + created + ", type="
				+ type + ", treeType=" + treeType + ", status=" + status
				+ ", updated=" + updated + ", children="
				+ Arrays.toString(children) + ", isParent=" + isParent
				+ ", icon=" + icon + ", isLastNode=" + isLastNode
				+ ", directoryType=" + directoryType + "]";
	}
}
