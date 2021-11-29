package com.risk.riskmanage.datamanage.model;

import com.risk.riskmanage.common.model.BasePage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Field extends BasePage implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 * */
	private Long id;
	
	/**
	 * 字段英文名
	 * */
	private String fieldEn;
	
	/**
	 * 字段中文名
	 * */
	private String fieldCn;
	
	/**
	 * 字段类型编号
	 * */
	private Long fieldTypeId;
	
	/**
	 * 字段类型名
	 * */
	private String fieldType;
	
	/**
	 * 字段存值类型
	 * */
	private Integer valueType;

	/**
	 * 字段存值类型中文
	 * */
	private String valueTypeName;
	
	/**
	 * 字段约束范围
	 * */
	private String valueScope;
	
	/**
	 * 是否衍生字段
	 * */
	private Integer isDerivative;

	/**
	 * 是否衍生字段
	 * */
	private String isDerivativeName;
	
	/**
	 * 是否输出字段
	 * */
	private Integer isOutput;

	/**
	 * 是否输出字段
	 * */
	private String isOutputName;
	
	/**
	 * 是否组织定义的通用字段
	 * */
	private Integer isCommon;
	
	/**
	 * 衍生字段公式
	 * */
	private String formula;
	
	/**
	 * 衍生字段公式回显信息
	 * */
	private String formulaShow;
	
	/**
	 * 衍生字段引用的字段id
	 * */
	private String usedFieldId;
	
	/**
	 * 衍生字段引用的原生字段id
	 * */
	private String origFieldId;
	
	/**
	 * 创建人
	 * */
	private Long author;
	
	/**
	 * 创建人昵称
	 * */
	private String nickName;
	
	/**
	 * 创建时间
	 * */
	private Date created;
	
	/**
	 * 归属的引擎ID
	 * */
	private Long engineId;
	
	/**
	 * 归属的引擎名称
	 * */
	private String engineName;
	
	/**
	 * 字段状态（启用、停用、删除、未知）
	 * */
	private String status;
	
	/**
	 * 字段条件设置集合
	 * */
	private List<FieldCond> fieldCondList;
	
	/**
	 * 字段用户关系编号
	 * */
	private Long fieldRelId;

	/**
	 * 是否使用sql获取指标
	 */
	private Boolean isUseSql;

	/**
	 * 使用sql获取指标时对应的数据源
	 */
	private Integer dataSourceId;

	/**
	 * 使用sql获取指标时对应的sql语句
	 */
	private String sqlStatement;

	/**
	 * sql变量配置
	 */
	private String sqlVariable;

	//是否使用接口
	private Boolean isInterface;

	//接口id
	private Integer interfaceId;

	//接口解析指标
	private String interfaceParseField;

	//json类型对应的json值
	private String jsonValue;
	//字典变量如：日期字符串
	private String dictVariable;

	public String getDictVariable() {
		return dictVariable;
	}

	public void setDictVariable(String dicVariable) {
		this.dictVariable = dicVariable;
	}

	/**
	 * 该字段归属的组织编号
	 * */
	private Long organId;

	public String getInterfaceParseField() {
		return interfaceParseField;
	}

	public void setInterfaceParseField(String interfaceParseField) {
		this.interfaceParseField = interfaceParseField;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFieldEn() {
		return fieldEn;
	}
	public void setFieldEn(String fieldEn) {
		this.fieldEn = fieldEn;
	}
	public String getFieldCn() {
		return fieldCn;
	}
	public void setFieldCn(String fieldCn) {
		this.fieldCn = fieldCn;
	}
	public Long getFieldTypeId() {
		return fieldTypeId;
	}
	public void setFieldTypeId(Long fieldTypeId) {
		this.fieldTypeId = fieldTypeId;
	}
	public Integer getValueType() {
		return valueType;
	}
	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}
	public String getValueScope() {
		return valueScope;
	}
	public void setValueScope(String valueScope) {
		this.valueScope = valueScope;
	}

	public Integer getIsDerivative() {
		return isDerivative;
	}
	public void setIsDerivative(Integer isDerivative) {
		this.isDerivative = isDerivative;
	}
	
	public Integer getIsOutput() {
		return isOutput;
	}
	public void setIsOutput(Integer isOutput) {
		this.isOutput = isOutput;
	}
	public Integer getIsCommon() {
		return isCommon;
	}
	public void setIsCommon(Integer isCommon) {
		this.isCommon = isCommon;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getFormulaShow() {
		return formulaShow;
	}
	public void setFormulaShow(String formulaShow) {
		this.formulaShow = formulaShow;
	}
	public String getUsedFieldId() {
		return usedFieldId;
	}
	public void setUsedFieldId(String usedFieldId) {
		this.usedFieldId = usedFieldId;
	}
	public String getOrigFieldId() {
		return origFieldId;
	}
	public void setOrigFieldId(String origFieldId) {
		this.origFieldId = origFieldId;
	}
	public Long getAuthor() {
		return author;
	}
	public void setAuthor(Long author) {
		this.author = author;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Long getEngineId() {
		return engineId;
	}
	public void setEngineId(Long engineId) {
		this.engineId = engineId;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getFieldRelId() {
		return fieldRelId;
	}
	public void setFieldRelId(Long fieldRelId) {
		this.fieldRelId = fieldRelId;
	}
	public List<FieldCond> getFieldCondList() {
		return fieldCondList;
	}
	public void setFieldCondList(List<FieldCond> fieldCondList) {
		this.fieldCondList = fieldCondList;
	}
	public String getEngineName() {
		return engineName;
	}
	public void setEngineName(String engineName) {
		this.engineName = engineName;
	}

	public String getValueTypeName() {
		return valueTypeName;
	}

	public void setValueTypeName(String valueTypeName) {
		this.valueTypeName = valueTypeName;
	}

	public String getIsDerivativeName() {
		return isDerivativeName;
	}

	public void setIsDerivativeName(String isDerivativeName) {
		this.isDerivativeName = isDerivativeName;
	}

	public String getIsOutputName() {
		return isOutputName;
	}

	public void setIsOutputName(String isOutputName) {
		this.isOutputName = isOutputName;
	}

	public Boolean getUseSql() {
		return isUseSql;
	}

	public void setUseSql(Boolean useSql) {
		isUseSql = useSql;
	}

	public Integer getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}

	@Override
	public String toString() {
		return "Field{" +
				"id=" + id +
				", fieldEn='" + fieldEn + '\'' +
				", fieldCn='" + fieldCn + '\'' +
				", fieldTypeId=" + fieldTypeId +
				", fieldType='" + fieldType + '\'' +
				", valueType=" + valueType +
				", valueTypeName='" + valueTypeName + '\'' +
				", valueScope='" + valueScope + '\'' +
				", isDerivative=" + isDerivative +
				", isDerivativeName='" + isDerivativeName + '\'' +
				", isOutput=" + isOutput +
				", isOutputName='" + isOutputName + '\'' +
				", isCommon=" + isCommon +
				", formula='" + formula + '\'' +
				", formulaShow='" + formulaShow + '\'' +
				", usedFieldId='" + usedFieldId + '\'' +
				", origFieldId='" + origFieldId + '\'' +
				", author=" + author +
				", nickName='" + nickName + '\'' +
				", created=" + created +
				", engineId=" + engineId +
				", engineName='" + engineName + '\'' +
				", status='" + status + '\'' +
				", fieldCondList=" + fieldCondList +
				", fieldRelId=" + fieldRelId +
				", isUseSql=" + isUseSql +
				", dataSourceId=" + dataSourceId +
				", sqlStatement='" + sqlStatement + '\'' +
				", isInterface=" + isInterface +
				", interfaceId=" + interfaceId +
				'}';
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Boolean getInterface() {
		return isInterface;
	}

	public void setInterface(Boolean anInterface) {
		isInterface = anInterface;
	}

	public Integer getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getSqlVariable() {
		return sqlVariable;
	}

	public void setSqlVariable(String sqlVariable) {
		this.sqlVariable = sqlVariable;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}
}
