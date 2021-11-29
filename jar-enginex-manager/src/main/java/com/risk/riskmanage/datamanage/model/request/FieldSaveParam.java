package com.risk.riskmanage.datamanage.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    // http://47.102.125.25/apidoc/index.html#api-field-PostV2DatamanageFieldSave

    private String searchKey;  //
    private Long id;
    private String formula;
    private String formulaShow;
    private Long engineId;
    private String fieldEn;
    private String fieldCn;
    private Long fieldTypeId;
    private Integer valueType;
    private Integer isDerivative;
    private Integer isOutput;
    private String valueScope;
    private String fieldCondList;  //
    private String formulaHidden;  //
    private Boolean isUseSql;
    private Integer dataSourceId;
    private String sqlStatement;
    private String sqlVariable;
    private Boolean isInterface;
    private Integer interfaceId;
    private String interfaceParseField;
    private String jsonValue;
    private String dictVariable;

//
//    public Field toField() {
//        Field field = new Field();
//
//        field.setId(this.getId());
//        field.setFormula(this.getFormula());
//        field.setFormulaShow(this.getFormulaShow());
//        field.setEngineId(this.getEngineId());
//        field.setFieldEn(this.getFieldEn());
//        field.setFieldCn(this.getFieldCn());
//        field.setFieldTypeId(this.getFieldTypeId());
//        field.setValueType(this.getValueType());
//        field.setIsDerivative(this.getIsDerivative());
//        field.setIsOutput(this.getIsOutput());
//        field.setValueScope(this.getValueScope());
//
//        field.setUseSql(this.getIsUseSql());
//        field.setDataSourceId(this.getDataSourceId());
//        field.setSqlStatement(this.getSqlStatement());
//
//        return field;
//    }
//
//    public Map toMap() {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("searchKey", this.getSearchKey());
//        hashMap.put("id", this.getId());
//        hashMap.put("formula", this.getFormula());
//        hashMap.put("formulaShow", this.getFormulaShow());
//        hashMap.put("engineId", this.getEngineId());
//        hashMap.put("formulaFields", this.getFormulaFields());
//        hashMap.put("fieldEn", this.getFieldEn());
//        hashMap.put("fieldCn", this.getFieldCn());
//        hashMap.put("fieldTypeId", this.getFieldId());
//        hashMap.put("valueType", this.getValueType());
//        hashMap.put("isDerivative", this.getIsDerivative());
//        hashMap.put("isOutput", this.getIsOutput());
//        hashMap.put("valueScope", this.getValueScope());
//        hashMap.put("fieldContent", this.getFieldContent());
//        hashMap.put("formulaHidden", this.getFormulaHidden());
//        hashMap.put("derType", this.getDerType());
//        hashMap.put("fieldContent2", this.getFieldContent2());
//        hashMap.put("conditionValue", this.getConditionValue());
//        hashMap.put("fieldId", this.getFieldId());
//        hashMap.put("operator", this.getOperator());
//        hashMap.put("fieldValue", this.getFieldValue());
//        hashMap.put("idx", this.getIdx());
//
//        hashMap.put("isUseSql", this.getIsUseSql());
//        hashMap.put("dataSourceId", this.getDataSourceId());
//        hashMap.put("sqlStatement", this.getSqlStatement());
//
//        return hashMap;
//    }
//
}
