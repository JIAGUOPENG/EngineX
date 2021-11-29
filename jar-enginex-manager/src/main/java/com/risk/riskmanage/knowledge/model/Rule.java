package com.risk.riskmanage.knowledge.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName:OrganRuleVo <br/>
 * Description: 规则实体类. <br/>
 * @see
 */
public class Rule implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 * */
	private Long id;
	
	/**
	 * 名称
	 * */
	private String name;
	
	/**
	 * 代码
	 * */
	private String code;
	
	/**
	 * 描述
	 * */
	private String description;

	/**
	 * 优先级
	 * */
	private Integer priority;
	
	/**
	 * 父节点id
	 * */
	private Long parentId;
	
	/**
	 *修改人id
	 * */
	private Long userId;
	
	/**
	 *创建人id
	 * */
	private Long author;
	
	/**
	 *创建人名称
	 * */
	private String authorName;
	
	/**
	 * 组织id
	 * */
	private Long organId;
	
	/**
	 * 引擎id
	 * */
	private Long engineId;
	
	/**
	 * 规则类型  0 : 系统的规则  1：组织的规则 2： 引擎的规则
	 * */
	private Integer type;
	
	/**
	 * 逻辑关系"非" 0:不是非 1：是非
	 * */
	private Integer isNon;
	
	/**
	 * 状态   0 :停用 ，1 : 启用，-1：删除
	 * */
	private Integer status;
	/**
	 * 审批规则 5 :通过 ，2 : 拒绝，3：人工审批 4：简化流程  
	 */
	public int ruleAudit;
	/**
	 * 规则字段集合
	 * */
	private List<RuleField> ruleFieldList;
	
	/**
	 * 规则内容集合
	 * */
	private List<RuleContent> ruleContentList;
	
	/**
	 * 创建日期
	 * */
	private Date created;
	
	/**
	 * 修改日期
	 * */
	private Date updated;
	
	/**
	 * 规则具体内容
	 * */
	public String content;
	
	/**
	 * 0硬性拒绝规则1加减分规则
	 */
	private Integer ruleType;
	
	/**
	 *得分
	 */
	private Integer score;
	
	/**
	 *逻辑关系符，存储条件区域最后一个逻辑符号，值有')'、'))'、'-1'
	 */
	private String lastLogical;
	
	/**
	 * 引擎名
	 * */
	private String engineName;
	
	/**
	 * 规则节点名称
	 * */
	private String engineNodeName;
	
	/**
	 * 规则节点名称
	 * */
	private Long engineNodeId;
	
	/**
	 * 区分规则集和规则
	 * */
	private int  showType = 0;

	private Integer difficulty;//规则难度：1-简单规则，2复杂规则

	private String resultFieldEn;//存放是否命中的字段

	private String scoreFieldEn;//存放得分的字段en

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getRuleAudit() {
		return ruleAudit;
	}

	public void setRuleAudit(int ruleAudit) {
		this.ruleAudit = ruleAudit;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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
	
	public Long getAuthor() {
		return author;
	}

	public void setAuthor(Long author) {
		this.author = author;
	}
	
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
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

	public List<RuleField> getRuleFieldList() {
		return ruleFieldList;
	}

	public void setRuleFieldList(List<RuleField> ruleFieldList) {
		this.ruleFieldList = ruleFieldList;
	}

	public List<RuleContent> getRuleContentList() {
		return ruleContentList;
	}

	public void setRuleContentList(List<RuleContent> ruleContentList) {
		this.ruleContentList = ruleContentList;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Integer getIsNon() {
		return isNon;
	}

	public void setIsNon(Integer isNon) {
		this.isNon = isNon;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRuleType() {
		if(ruleAudit == 2) {
			ruleType = 0;
		}else{
			ruleType = 1;
		}
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getLastLogical() {
		return lastLogical;
	}

	public void setLastLogical(String lastLogical) {
		this.lastLogical = lastLogical;
	}

	public String getEngineName() {
		return engineName;
	}

	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}
	
	

	public String getEngineNodeName() {
		return engineNodeName;
	}

	public void setEngineNodeName(String engineNodeName) {
		this.engineNodeName = engineNodeName;
	}

	public Long getEngineNodeId() {
		return engineNodeId;
	}

	public void setEngineNodeId(Long engineNodeId) {
		this.engineNodeId = engineNodeId;
	}
	
	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	 public Object clone() throws CloneNotSupportedException {  
	        // TODO Auto-generated method stub  
	        return super.clone();  
	 }

	public String getResultFieldEn() {
		return resultFieldEn;
	}

	public void setResultFieldEn(String resultFieldEn) {
		this.resultFieldEn = resultFieldEn;
	}

	public String getScoreFieldEn() {
		return scoreFieldEn;
	}

	public void setScoreFieldEn(String scoreFieldEn) {
		this.scoreFieldEn = scoreFieldEn;
	}

	@Override
	public String toString() {
		return "EngineRule [id=" + id + ", name=" + name + ", versionCode=" + code + ", description=" + description + ", priority="
				+ priority + ", parentId=" + parentId + ", userId=" + userId + ", author=" + author + ", authorName="
				+ authorName + ", organId=" + organId + ", engineId=" + engineId + ", type=" + type + ", isNon=" + isNon
				+ ", status=" + status + ", ruleAudit=" + ruleAudit + ", ruleFieldList=" + ruleFieldList
				+ ", ruleContentList=" + ruleContentList + ", created=" + created + ", updated=" + updated
				+ ", content=" + content + ", ruleType=" + ruleType + ", score=" + score + ", lastLogical="
				+ lastLogical + ", engineName=" + engineName + ", engineNodeName=" + engineNodeName + ", engineNodeId="
				+ engineNodeId + ", showType=" + showType + "]";
	}

	

	
}
