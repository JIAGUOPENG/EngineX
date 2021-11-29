package com.risk.riskmanage.engine.model;

import java.io.Serializable;
import java.util.List;

public class EngineVersion implements Comparable<EngineVersion>, Serializable {

    private static final long serialVersionUID = 2923432053414979455L;
    /**
	 * 版本编号
	 */
    private Long versionId;

    /**
     * 引擎编号
     */
    private Long engineId;

    /**
     * 版本号
     */
    private Integer version;
    
    /**
     * 子版本
     */
    private Integer subVersion;

    /**
     * 部署状态
     */
    private Integer bootState;

    /**
     * 版本状态
     */
    private Integer status;

    /**
     * 布局方式
     */
    private Integer layout;

    /**
     * 创建者
     */
    private Long userId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private Long latestUser;

    /**
     * 最后修改时间
     */
    private String latestTime;
    
    /**
     * 节点集合
     * */
    private List<EngineNode> engineNodeList;

    /**
     * 引擎名称
     * */
    private String engineName;
    
    /**
     * 引擎描述
     * */
    private String engineDesc;
    
    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getEngineId() {
        return engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getBootState() {
        return bootState;
    }

    public void setBootState(Integer bootState) {
        this.bootState = bootState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public Long getLatestUser() {
        return latestUser;
    }

    public void setLatestUser(Long latestUser) {
        this.latestUser = latestUser;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime == null ? null : latestTime.trim();
    }

	public List<EngineNode> getEngineNodeList() {
		return engineNodeList;
	}

	public void setEngineNodeList(List<EngineNode> engineNodeList) {
		this.engineNodeList = engineNodeList;
	}

	public Integer getSubVersion() {
		return subVersion;
	}

	public void setSubVersion(Integer subVersion) {
		this.subVersion = subVersion;
	}

	public String getEngineName() {
		return engineName;
	}

	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

	public String getEngineDesc() {
		return engineDesc;
	}

	public void setEngineDesc(String engineDesc) {
		this.engineDesc = engineDesc;
	}

	@Override
	public int compareTo(EngineVersion o) {
		if(version!=o.getVersion()){
            return version-o.getVersion();
        }else if(!(subVersion == o.getSubVersion())){
            return subVersion - o.getSubVersion();
        }else {
            return version-o.getVersion();
        }
	}
	
	
}