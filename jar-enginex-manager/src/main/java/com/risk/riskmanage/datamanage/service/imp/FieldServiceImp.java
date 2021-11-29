package com.risk.riskmanage.datamanage.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.datamanage.common.ExcelUtil;

import com.risk.riskmanage.datamanage.common.Status;
import com.risk.riskmanage.datamanage.model.*;
import com.risk.riskmanage.datamanage.service.FieldService;
import com.risk.riskmanage.knowledge.model.Rule;
import com.risk.riskmanage.util.SessionManager;
import com.risk.riskmanage.util.StringUtil;
import com.risk.riskmanage.common.model.requestParam.UpdateFolderParam;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.dmg.pmml.scorecard.Scorecard;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FieldServiceImp extends BaseService implements FieldService {

    protected static final Set<String> KEY_WORDS = new HashSet<String>(){{
        add("DELETE ");
        add("DROP ");
        add("TRUNCATE ");
        add("UPDATE ");
        add("ALTER ");
        add("INSERT ");
        add("CREATE ");
        add("RENAME ");
    }};
    /*
     * 公共方法：去掉id串里重复id
     */
    public StringBuffer getUniqueStr(String usedFieldStr) {

        String arrUsedFieldStr[] = usedFieldStr.split(",");
        Set<String> usedFieldSet = new HashSet<>();
        for (int k = 0; k < arrUsedFieldStr.length; k++) {
            usedFieldSet.add(arrUsedFieldStr[k]);
        }
        String[] arrUsedField = (String[]) usedFieldSet.toArray(new String[usedFieldSet.size()]);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arrUsedField.length; i++) {
            if (i != arrUsedField.length - 1)
                sb.append(arrUsedField[i]).append(",");
            else
                sb.append(arrUsedField[i]);
        }
        return sb;
    }

    @Override
    public boolean createField(Field fieldVo, Map<String, Object> paramMap) {

        String formulaHidden = "";

        //获取衍生字段公式编辑区域引用字段的原生字段
        if (paramMap.containsKey("formulaHidden") && !paramMap.get("formulaHidden").equals("")) {

            formulaHidden = (String) paramMap.get("formulaHidden");
            fieldVo.setFormula(formulaHidden);

            List<Object> formulaList = new ArrayList<>();
            formulaList = JSONObject.parseArray(formulaHidden);

            JSONArray jsonArrayFormula = new JSONArray();

            String origFieldStr = "";
            String usedFieldStr = "";

            for (int i = 0; i < formulaList.size(); i++) {

                JSONObject f = ((JSONArray) formulaList).getJSONObject(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fvalue", f.getString("fvalue"));
                jsonObject.put("formula", f.getString("formula"));
                jsonObject.put("idx", f.getString("idx"));
                jsonArrayFormula.add(jsonObject);

                List<Object> farrList = new ArrayList<>();
//				if(!f.getString("farr").equals("")&&f.getString("farr")!=null){
//					farrList = JSONObject.parseArray(f.getString("farr"));
//					for(int j = 0; j < farrList.size(); j++){
//						JSONObject field = ((JSONArray) farrList).getJSONObject(j);
//						Map<String, Object> fieldMap = new HashMap<String, Object>();
//						fieldMap.put("userId",paramMap.get("userId"));
//						fieldMap.put("engineId",paramMap.get("engineId"));
//						fieldMap.put("fieldCn", field.get("fieldCN"));
//						
//						//公式里的字段对象
//						Field subField = fieldMapper.findByFieldCn(fieldMap);
//						if(subField.getOrigFieldId()==null){
//							if(origFieldStr.equals("")){
//								origFieldStr = Long.toString(subField.getId());
//							}else{
//								origFieldStr = origFieldStr + "," + subField.getId();
//							}
//						}else{
//							if(origFieldStr.equals("")){
//								origFieldStr = subField.getOrigFieldId();
//							}else{
//								origFieldStr = origFieldStr + "," + subField.getOrigFieldId();
//							}
//						}
//						usedFieldStr = usedFieldStr + subField.getId() + ","; //拼凑该字段引用的字段id
//					}
//				}else{//只从公式里提取字段   "formula":":在网时长: >=0 && :在网时长: <6"
                //从公式里提取所用字段及原生字段
                String formula = f.getString("formula");
                Pattern pattern = Pattern.compile("@[a-zA-Z0-9_\u4e00-\u9fa5()（）-]+@");
                Matcher matcher = pattern.matcher(formula);
                while (matcher.find()) {
                    String fieldCN = matcher.group(0).replace("@", "");
                    Map<String, Object> fieldMap = new HashMap<String, Object>();
                    fieldMap.put("userId", paramMap.get("userId"));
                    fieldMap.put("engineId", paramMap.get("engineId"));
                    fieldMap.put("fieldCn", fieldCN);

                    Field field = fieldMapper.findByFieldCn(fieldMap);

                    if (field.getOrigFieldId() == null) {
                        if (origFieldStr.equals("")) {
                            origFieldStr = Long.toString(field.getId());
                        } else {
                            origFieldStr = origFieldStr + "," + field.getId();
                        }
                    } else {
                        if (origFieldStr.equals("")) {
                            origFieldStr = field.getOrigFieldId();
                        } else {
                            origFieldStr = origFieldStr + "," + field.getOrigFieldId();
                        }
                    }
                    usedFieldStr = usedFieldStr + field.getId() + ","; //拼凑该字段引用的字段id
                }
//				}
            }

            fieldVo.setFormulaShow(JSON.toJSONString(jsonArrayFormula));

            //合并原生字段id
            if (!origFieldStr.equals("")) {
                fieldVo.setOrigFieldId(getUniqueStr(origFieldStr).toString());
            }

            //合并引用字段id
            if (!usedFieldStr.equals(",") && !usedFieldStr.equals("")) {
                usedFieldStr = usedFieldStr.substring(0, usedFieldStr.length() - 1);
                fieldVo.setUsedFieldId(getUniqueStr(usedFieldStr).toString());
            }

        } else if (paramMap.containsKey("fieldCondList") && !paramMap.get("fieldCondList").equals("")) {
            //条件区域的使用字段和原生字段获取
			
			/*
			  fieldContent=[{"fieldContent2":"[{\"fieldId\":\"3\",\"operator\":\">\",\"fieldValue\":\"200\",\"logical\":\"&&\"}
				  							  ,{\"fieldId\":\"11\",\"operator\":\"<\",\"fieldValue\":\"50\"}]","conditionValue":"5","fieldValue":"50"}
						   ,{"fieldContent2":"[{\"fieldId\":\"12\",\"operator\":\"in\",\"fieldValue\":\"z\",\"logical\":\"&&\"}
				  							  ,{\"fieldId\":\"11\",\"operator\":\">\",\"fieldValue\":\"200\",\"logical\":\"&&\"}
				  							  ,{\"fieldId\":\"31\",\"operator\":\">\",\"fieldValue\":\"1000\"}]","conditionValue":"8","fieldValue":"1000"}
						   ,{"fieldContent2":"[{\"fieldId\":\"31\",\"operator\":\">\",\"fieldValue\":\"4000\"}]","conditionValue":"9","fieldValue":"4000"}]
			*/
            String fieldContent = (String) paramMap.get("fieldCondList");
            List<Object> fieldContentList = new ArrayList<>();
            fieldContentList = JSONObject.parseArray(fieldContent);

            String origFieldStr = "";
            String usedFieldStr = "";

            for (int i = 0; i < fieldContentList.size(); i++) {
                JSONObject fc = ((JSONArray) fieldContentList).getJSONObject(i);
                List<Object> farrList = new ArrayList<>();
                if (!fc.getString("fieldSubCond").equals("") && fc.getString("fieldSubCond") != null) {
                    farrList = JSONObject.parseArray(fc.getString("fieldSubCond"));
                    for (int j = 0; j < farrList.size(); j++) {
                        JSONObject ObjField = ((JSONArray) farrList).getJSONObject(j);
                        usedFieldStr = usedFieldStr + ObjField.get("fieldId") + ",";

                        Map<String, Object> fieldMap = new HashMap<String, Object>();
                        fieldMap.put("userId", paramMap.get("userId"));
                        fieldMap.put("engineId", paramMap.get("engineId"));
                        fieldMap.put("id", ObjField.get("fieldId"));
                        Field field = fieldMapper.findByFieldId(fieldMap);

                        if (field.getOrigFieldId() == null) {
                            if (origFieldStr.equals("")) {
                                origFieldStr = Long.toString(field.getId());
                            } else {
                                origFieldStr = origFieldStr + "," + field.getId();
                            }
                        } else {
                            if (origFieldStr.equals("")) {
                                origFieldStr = field.getOrigFieldId();
                            } else {
                                origFieldStr = origFieldStr + "," + field.getOrigFieldId();
                            }
                        }


                    }
                }
            }

            //合并引用字段id
            if (!usedFieldStr.equals(",") && !usedFieldStr.equals("")) {
                usedFieldStr = usedFieldStr.substring(0, usedFieldStr.length() - 1);
                fieldVo.setUsedFieldId(getUniqueStr(usedFieldStr).toString());
            }

            //合并原生字段id
            if (!origFieldStr.equals("")) {
                fieldVo.setOrigFieldId(getUniqueStr(origFieldStr).toString());
            }
        }

        if (fieldMapper.isExists(paramMap) == 0) {
            fieldMapper.createField(fieldVo);
            FieldUser fieldUserVo = new FieldUser();
            fieldUserVo.setFieldId(fieldVo.getId());
            fieldUserVo.setOrganId((Long) paramMap.get("organId"));
            if (paramMap.get("engineId") != null) {
                fieldUserVo.setEngineId(Long.valueOf((String) paramMap.get("engineId")));
            }
            fieldUserVo.setUserId((Long) paramMap.get("userId"));
            fieldUserVo.setStatus(Status.enable.value);
            fieldUserMapper.createFieldUserRel(fieldUserVo);

            //可能衍生字段只有公式,没有条件设置
            if (paramMap.containsKey("fieldCondList")) {

                String fieldContent = (String) paramMap.get("fieldCondList");
                if (!fieldContent.equals("")) {
                    List<FieldCond> fieldCondVoList = new ArrayList<FieldCond>();
                    List<Object> condList = new ArrayList<>();
                    condList = JSONObject.parseArray(fieldContent);
                    for (int i = 0; i < condList.size(); i++) {
                        JSONObject cond = ((JSONArray) condList).getJSONObject(i);
                        List<Object> subCondList = new ArrayList<>();
                        if (!cond.getString("fieldSubCond").equals("")) {
                            subCondList = JSONObject.parseArray(cond.getString("fieldSubCond"));
                            for (int j = 0; j < subCondList.size(); j++) {
                                JSONObject subCond = ((JSONArray) subCondList).getJSONObject(j);
                                FieldCond fieldCondVo = new FieldCond();
                                fieldCondVo.setFieldId(fieldVo.getId());
                                fieldCondVo.setConditionValue(cond.getString("conditionValue"));
                                fieldCondVo.setContent(cond.getString("fieldSubCond"));
                                fieldCondVo.setCondFieldId(Long.valueOf(subCond.getString("fieldId")));
                                fieldCondVo.setCondFieldOperator(subCond.getString("operator"));
                                fieldCondVo.setCondFieldValue(subCond.getString("fieldValue"));
                                fieldCondVo.setCondFieldLogical(subCond.getString("logical"));
                                fieldCondVoList.add(fieldCondVo);
                            }
                        }
                        fieldCondMapper.createFieldCond(fieldCondVoList);
                    }

                }
            }


            //可能衍生字段只有公式,没有条件设置
/*			if(paramMap.containsKey("formulaFields")){
				String formulaFields = (String) paramMap.get("formulaFields");
				Long fieldId = fieldVo.getId();
				if(!formulaFields.equals("")){
					String[] idArray = null;   
					idArray = formulaFields.split(",");
					List<FormulaField> formulaFieldVoList = new ArrayList<FormulaField>();
					 for(int i=0;i<idArray.length;i++){
						 FormulaField formulaFieldVo = new FormulaField();
						 formulaFieldVo.setFieldId(fieldId);
						 formulaFieldVo.setFormulaFieldId(Long.parseLong(idArray[i]));
						 formulaFieldVoList.add(formulaFieldVo);
					 }
			        formulaFieldMapper.createFormulaField(formulaFieldVoList);
				}
			}*/

            return true;

        } else
            return false;
    }

    @Override
    public boolean updateField(Map<String, Object> paramMap) {

        String formulaHidden = "";

        if (paramMap.containsKey("formulaHidden") && !paramMap.get("formulaHidden").equals("")) {

            formulaHidden = (String) paramMap.get("formulaHidden");
            paramMap.put("formula", formulaHidden);

            List<Object> formulaList = new ArrayList<>();
            formulaList = JSONObject.parseArray(formulaHidden);

            JSONArray jsonArrayFormula = new JSONArray();

            String origFieldStr = "";
            String usedFieldStr = "";

            for (int i = 0; i < formulaList.size(); i++) {

                JSONObject f = ((JSONArray) formulaList).getJSONObject(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fvalue", f.getString("fvalue"));
                jsonObject.put("formula", f.getString("formula"));
                jsonObject.put("idx", f.getString("idx"));
                jsonArrayFormula.add(jsonObject);

                List<Object> farrList = new ArrayList<>();
//				if(!f.getString("farr").equals("")&&f.getString("farr")!=null){
//					farrList = JSONObject.parseArray(f.getString("farr"));
//					for(int j = 0; j < farrList.size(); j++){
//						JSONObject field = ((JSONArray) farrList).getJSONObject(j);
//						Map<String, Object> fieldMap = new HashMap<String, Object>();
//						fieldMap.put("userId",paramMap.get("userId"));
//						fieldMap.put("engineId",paramMap.get("engineId"));
//						fieldMap.put("fieldCn", field.get("fieldCN"));
//						
//						//公式里的字段对象
//						Field subField = fieldMapper.findByFieldCn(fieldMap);
//						if(subField.getOrigFieldId()==null){
//							if(origFieldStr.equals("")){
//								origFieldStr = Long.toString(subField.getId());
//							}else{
//								origFieldStr = origFieldStr + "," + subField.getId();
//							}
//						}else{
//							if(origFieldStr.equals("")){
//								origFieldStr = subField.getOrigFieldId();
//							}else{
//								origFieldStr = origFieldStr + "," + subField.getOrigFieldId();
//							}
//						}
//						usedFieldStr = usedFieldStr + subField.getId() + ","; //拼凑该字段引用的字段id
//					}
//				}else{//只从公式里提取字段   "formula":":在网时长: >=0 && :在网时长: <6"
                //从公式里提取所用字段及原生字段
                String formula = f.getString("formula");
                Pattern pattern = Pattern.compile("@[a-zA-Z0-9_\u4e00-\u9fa5()（）-]+@");
                Matcher matcher = pattern.matcher(formula);
                while (matcher.find()) {
                    String fieldCN = matcher.group(0).replace("@", "");
                    Map<String, Object> fieldMap = new HashMap<String, Object>();
                    fieldMap.put("userId", paramMap.get("userId"));
                    fieldMap.put("engineId", paramMap.get("engineId"));
                    fieldMap.put("fieldCn", fieldCN);

                    Field field = fieldMapper.findByFieldCn(fieldMap);

                    if (field.getOrigFieldId() == null) {
                        if (origFieldStr.equals("")) {
                            origFieldStr = Long.toString(field.getId());
                        } else {
                            origFieldStr = origFieldStr + "," + field.getId();
                        }
                    } else {
                        if (origFieldStr.equals("")) {
                            origFieldStr = field.getOrigFieldId();
                        } else {
                            origFieldStr = origFieldStr + "," + field.getOrigFieldId();
                        }
                    }
                    usedFieldStr = usedFieldStr + field.getId() + ","; //拼凑该字段引用的字段id
                }
//				}
            }

            paramMap.put("formulaShow", JSON.toJSONString(jsonArrayFormula));
            //合并原生字段id
            if (!origFieldStr.equals("")) {
				
/*				String arrOrigFieldStr[] = origFieldStr.split(",");
				Set<String> origFieldSet = new HashSet<>();
		        for(int k=0;k<arrOrigFieldStr.length;k++){
		        	origFieldSet.add(arrOrigFieldStr[k]); 
		        }
		        String[] arrOrigField = (String[]) origFieldSet.toArray(new String[origFieldSet.size()]);
		        StringBuffer sb = new StringBuffer();
		        for(int i = 0; i < arrOrigField.length; i++){
		            if(i!=arrOrigField.length-1)
		                sb.append(arrOrigField[i]).append(",");
		            else
		                sb.append(arrOrigField[i]);
		        }
		        //paramMap.put("origFieldId",sb.toString());
		        paramMap.put("origFieldId",StringUtils.join(arrOrigField,","));*/
                paramMap.put("origFieldId", getUniqueStr(origFieldStr).toString());
            }

            //合并引用字段id
            if (!usedFieldStr.equals(",") && !usedFieldStr.equals("")) {
                usedFieldStr = usedFieldStr.substring(0, usedFieldStr.length() - 1);
                paramMap.put("usedFieldId", getUniqueStr(usedFieldStr).toString());
            }

        } else if (paramMap.containsKey("fieldCondList") && !paramMap.get("fieldCondList").equals("")) {
            //条件区域的使用字段和原生字段获取
			
			/*
			  fieldContent=[{"fieldContent2":"[{\"fieldId\":\"3\",\"operator\":\">\",\"fieldValue\":\"200\",\"logical\":\"&&\"}
				  							  ,{\"fieldId\":\"11\",\"operator\":\"<\",\"fieldValue\":\"50\"}]","conditionValue":"5","fieldValue":"50"}
						   ,{"fieldContent2":"[{\"fieldId\":\"12\",\"operator\":\"in\",\"fieldValue\":\"z\",\"logical\":\"&&\"}
				  							  ,{\"fieldId\":\"11\",\"operator\":\">\",\"fieldValue\":\"200\",\"logical\":\"&&\"}
				  							  ,{\"fieldId\":\"31\",\"operator\":\">\",\"fieldValue\":\"1000\"}]","conditionValue":"8","fieldValue":"1000"}
						   ,{"fieldContent2":"[{\"fieldId\":\"31\",\"operator\":\">\",\"fieldValue\":\"4000\"}]","conditionValue":"9","fieldValue":"4000"}]
			*/
            String fieldContent = (String) paramMap.get("fieldCondList");
            List<Object> fieldContentList = new ArrayList<>();
            fieldContentList = JSONObject.parseArray(fieldContent);

            String origFieldStr = "";
            String usedFieldStr = "";

            for (int i = 0; i < fieldContentList.size(); i++) {
                JSONObject fc = ((JSONArray) fieldContentList).getJSONObject(i);
                List<Object> farrList = new ArrayList<>();
                if (!fc.getString("fieldSubCond").equals("") && fc.getString("fieldSubCond") != null) {
                    farrList = JSONObject.parseArray(fc.getString("fieldSubCond"));
                    for (int j = 0; j < farrList.size(); j++) {
                        JSONObject ObjField = ((JSONArray) farrList).getJSONObject(j);
                        usedFieldStr = usedFieldStr + ObjField.get("fieldId") + ",";

                        Map<String, Object> fieldMap = new HashMap<String, Object>();
                        fieldMap.put("userId", paramMap.get("userId"));
                        fieldMap.put("engineId", paramMap.get("engineId"));
                        fieldMap.put("id", ObjField.get("fieldId"));
                        Field field = fieldMapper.findByFieldId(fieldMap);

                        if (field.getOrigFieldId() == null) {
                            if (origFieldStr.equals("")) {
                                origFieldStr = Long.toString(field.getId());
                            } else {
                                origFieldStr = origFieldStr + "," + field.getId();
                            }
                        } else {
                            if (origFieldStr.equals("")) {
                                origFieldStr = field.getOrigFieldId();
                            } else {
                                origFieldStr = origFieldStr + "," + field.getOrigFieldId();
                            }
                        }
                    }
                }
            }
            //合并引用字段id
            if (!usedFieldStr.equals(",") && !usedFieldStr.equals("")) {
                usedFieldStr = usedFieldStr.substring(0, usedFieldStr.length() - 1);
                paramMap.put("usedFieldId", getUniqueStr(usedFieldStr).toString());
            }
            //合并原生字段id
            if (!origFieldStr.equals("")) {
                paramMap.put("origFieldId", getUniqueStr(origFieldStr).toString());
            }
        }

        Long id = Long.valueOf(paramMap.get("id").toString());
        //检查字段id是否归属该用户存在
        Field oldFieldVo = new Field();
        oldFieldVo = fieldMapper.findByFieldId(paramMap);
        if (!oldFieldVo.getId().equals(null)) {
            fieldMapper.updateField(paramMap);

            fieldCondMapper.deleteFieldCondById(id);

            String fieldContent = (String) paramMap.get("fieldCondList");
            List<FieldCond> fieldCondVoList = new ArrayList<FieldCond>();
            List<Object> condList = new ArrayList<>();
            if (!fieldContent.equals("")) {
                condList = JSONObject.parseArray(fieldContent);
                for (int i = 0; i < condList.size(); i++) {
                    JSONObject cond = ((JSONArray) condList).getJSONObject(i);
                    List<Object> subCondList = new ArrayList<>();
                    subCondList = JSONObject.parseArray(cond.getString("fieldSubCond"));
                    for (int j = 0; j < subCondList.size(); j++) {
                        JSONObject subCond = ((JSONArray) subCondList).getJSONObject(j);
                        FieldCond fieldCondVo = new FieldCond();
                        fieldCondVo.setFieldId(id);
                        fieldCondVo.setConditionValue(cond.getString("conditionValue"));
                        fieldCondVo.setContent(cond.getString("fieldSubCond"));
                        fieldCondVo.setCondFieldId(Long.valueOf(subCond.getString("fieldId")));
                        fieldCondVo.setCondFieldOperator(subCond.getString("operator"));
                        fieldCondVo.setCondFieldValue(subCond.getString("fieldValue"));
                        fieldCondVo.setCondFieldLogical(subCond.getString("logical"));
                        fieldCondVoList.add(fieldCondVo);
                    }
                }
                fieldCondMapper.createFieldCond(fieldCondVoList);
            }

            //判断衍生字段的公式是否发生改变，改变了再执行相应操作
/*			if(paramMap.get("formula").equals(oldFieldVo.getFormula())){
				//do nothing.
			}else if(paramMap.get("formula").equals("")){
				formulaFieldMapper.deleteFormulaField(paramMap);
			}else {
				if(!oldFieldVo.getFormula().equals("")){
					formulaFieldMapper.deleteFormulaField(paramMap);
				}
				String formulaFields = (String) paramMap.get("formulaFields");
				Long fieldId = (Long) paramMap.get("id");
				String[] idArray = null;   
				idArray = formulaFields.split(",");
				List<FormulaField> formulaFieldVoList = new ArrayList<FormulaField>();
				for(int i=0;i<idArray.length;i++){
				    FormulaField formulaFieldVo = new FormulaField();
					formulaFieldVo.setFieldId(fieldId);
					formulaFieldVo.setFormulaFieldId(Long.parseLong(idArray[i]));
					formulaFieldVoList.add(formulaFieldVo);
				}
			    formulaFieldMapper.createFormulaField(formulaFieldVoList);
			}*/
            return true;
        } else
            return false;
    }

    /*	*//**
     * 查找某字段被引用的字段id并拼成逗号分隔的字符串
     * @return
     *//*
    public Map<String, Object> getField(String usedFieldId){
    	
    	//String fieldIds = "";
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	Long userId = SessionManager.getLoginAccount().getUserId();
    	paramMap.put("userId", userId);
    	paramMap.put("fieldId", usedFieldId);
    	
    	List<Field> fieldList = fieldMapper.findUsedFieldId(paramMap);
		for (Iterator iterator = fieldList.iterator(); iterator.hasNext();) {
			Field fieldVo = (Field) iterator.next();
			paramMap.put("fieldId", fieldVo.getId());
		}
    	paramMap.put("fieldList",fieldList);
		return paramMap;
    }*/

    /**
     * 查找继承自某字段的所有字段id拼成逗号分隔的字符串返回
     *
     * @return
     */
    public String getField(String fieldIds, String usedFieldId, String engineId) {

        Map<String, Object> param = new HashMap<String, Object>();
//    	Long userId = SessionManager.getLoginAccount().getUserId();
        Long userId = SessionManager.getLoginAccount().getUserId();
        param.put("userId", userId);
        param.put("fieldId", usedFieldId);
        param.put("engineId", engineId);

        fieldIds = "";

        String str = fieldMapper.checkField(param);

        if (str != null && str.length() >= 0) {

            String arrIds[] = str.split(",");
            for (int i = 0; i < arrIds.length; i++) {
                if (fieldIds.equals("")) {
                    fieldIds = getField("", arrIds[i], engineId);
                } else {
                    fieldIds = fieldIds + "," + getField("", arrIds[i], engineId);
                }

            }
        } else {
            return usedFieldId;
        }
        return fieldIds;
    }

    /**
     * 查找某字段的所有组成字段id并与该字段一起拼成逗号分隔的字符串返回，用于拷贝字段时检查组成该字段的所有子字段
     *
     * @return
     */
    @Override
    public String getSourceField(String fieldIds, String fieldId) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        Long userId = SessionManager.getLoginAccount().getUserId();
        paramMap.put("userId", userId);
        paramMap.put("fieldId", fieldId);

        fieldIds = "";

        //String origFieldId = param.get("origFieldId");
        String usedFieldId = fieldMapper.getSourceField(paramMap);

        if (usedFieldId != null && usedFieldId.length() >= 0) {
            //fieldIds = usedFieldId;
            String arrIds[] = usedFieldId.split(",");
            for (int i = 0; i < arrIds.length; i++) {
                if (fieldIds.equals(""))
                    fieldIds = getSourceField("", arrIds[i]);
                else
                    fieldIds = fieldIds + "," + getSourceField("", arrIds[i]);
            }
        } else {
            return fieldId;
        }

        return fieldIds;
    }

    /**
     * 公共检查字段方法 供删除、停用、编辑提交时校验用
     *
     * @return
     */
    @Override
    public Map<String, Object> checkField(Map<String, Object> paramMap) {

        boolean beUsed = false;

        List<Field> fieldList = new ArrayList<Field>();
        List<Rule> ruleList = new ArrayList<Rule>();

        String fieldIds = "";

        String fieldId = (String) paramMap.get("fieldId");
        String s = getField("", fieldId, (String) paramMap.get("engineId"));

        //如果字段没有被引用不做字段有效性校验
        if (!s.equals("") && !s.equals(fieldId)) {
            fieldIds = getUniqueStr(s).toString();
            List<Long> Ids = new ArrayList<Long>();
            Ids = StringUtil.toLongList(fieldIds);
            paramMap.put("Ids", Ids);
            if (!fieldIds.equals("") && fieldIds != null) {
                //校验字段，命中则b改为true
                fieldList = fieldMapper.findFieldByIdsForCheckField(paramMap);
                if (fieldList.size() > 0)
                    beUsed = true;
            }
            s = fieldId + "," + s; //把自身字段加入检查序列，为后续检查做准备
        } else {
            s = fieldId;
        }

        fieldIds = getUniqueStr(s).toString();
        List<Long> Ids = new ArrayList<Long>();
        Ids = StringUtil.toLongList(fieldIds);
        paramMap.put("Ids", Ids);

        //校验规则管理的规则，命中则b改为true
        paramMap.put("fieldIds", Ids);
        ruleList = ruleMapper.checkByField(paramMap);
        if (ruleList.size() > 0)
            beUsed = true;
        //<待完善>校验引擎管理-决策流节点（决策选项-客户分群）


        paramMap.put("fieldList", fieldList);
        paramMap.put("ruleList", ruleList);
        paramMap.put("beUsed", beUsed);

        return paramMap;

    }

    @Override
    public Map<String, Object> updateStatus(Map<String, Object> paramMap) {

        boolean result = false;

        List<Long> Ids = (List<Long>) paramMap.get("Ids");
        paramMap.put("Ids", Ids);

        if (paramMap.containsKey("status") && !paramMap.get("status").equals("1")) {//停用、删除特殊处理需要增加校验

            for (Iterator iterator = Ids.iterator(); iterator.hasNext(); ) {
                Long Id = (Long) iterator.next();
                paramMap.put("fieldId", Id.toString());
                checkField(paramMap);
                if ((boolean) paramMap.get("beUsed")) {
                    break; // 遇到第一个被使用的就跳出循环来
                }
            }

            if (!(boolean) paramMap.get("beUsed")) {
                paramMap.put("Ids", Ids);
                result = fieldUserMapper.updateStatus(paramMap);
            }

        } else if (paramMap.containsKey("listType") && paramMap.get("listType").equals("cabage")
                && paramMap.get("status").equals("1")) {//回收站里删除状态变为启用状态

            result = backEngFieldType(paramMap);

        } else {//停用变启用
            result = fieldUserMapper.updateStatus(paramMap);
        }

        paramMap.put("result", result);

        return paramMap;
    }

    @Override
    public List<Field> findByFieldType(Map<String, Object> paramMap) {

        if (!paramMap.containsKey("fType")) {
            return null;
            // throw new ApiException(ErrorCodeEnum.SERVER_ERROR.getVersionCode(), ErrorCodeEnum.SERVER_ERROR.getMessage());
        }
        Integer fType = Integer.valueOf(paramMap.get("fType").toString());

        switch (fType) {
            case 1:
                // paramMap.put("useSql", false);
                // paramMap.put("derivative", false);
                break;
            case 2:
                // paramMap.put("useSql", true);
                break;
            case 3:
                // paramMap.put("derivative", true);
                break;
            case 4:
                // paramMap.put("interface", true);
                break;
            default:
                throw new ApiException(ErrorCodeEnum.SERVER_ERROR.getCode(), ErrorCodeEnum.SERVER_ERROR.getMessage());
        }

        return fieldMapper.findByFieldType(paramMap);
    }

    @Override
    public int isExists(Map<String, Object> paramMap) {
        return fieldMapper.isExists(paramMap);
    }


    @Override
    public Field findByFieldId(Map<String, Object> paramMap) {
        return fieldMapper.findByFieldId(paramMap);
    }

    @Override
    public List<Field> findByUser(Map<String, Object> paramMap) {
        return fieldMapper.findByUser(paramMap);
    }

    @Override
    public Long findFieldTypeId(Map<String, Object> paramMap) {
        return fieldMapper.findFieldTypeId(paramMap);
    }

    @Override
    public List<Field> findExcelByFieldType(Map<String, Object> paramMap) {
        return fieldMapper.findExcelByFieldType(paramMap);
    }
    @Override
    public List<Field> getFieldList(Map<String, Object> paramMap) {
        return fieldMapper.getFieldList(paramMap);
    }

    @Override
    public List<Field> searchByName(Map<String, Object> paramMap) {
        return fieldMapper.searchByName(paramMap);
    }

    @Override
    public List<Field> findFieldByIds(Map<String, Object> paramMap) {
        return fieldMapper.findFieldByIds(paramMap);
    }

    @Override
    public Field findByFieldEn(Map<String, Object> paramMap) {
        return fieldMapper.findByFieldEn(paramMap);
    }

    @Override
    public boolean createEngineField(Map<String, Object> paramMap) {

        fieldTypeUserMapper.batchBindEngineFieldTypeUserRel(paramMap);
        fieldUserMapper.batchCreateEngineFieldUserRel(paramMap);

        return true;
    }

    @Override
    public boolean bindEngineField(Map<String, Object> paramMap) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        Long organId = SessionManager.getLoginAccount().getOrganId();
        paramMap.put("userId", userId);
        paramMap.put("organId", organId);

        //获取所有字段id
        String iFieldIds = (String) paramMap.get("fieldIds");
        String oFieldIds = iFieldIds;
        if (iFieldIds != null && iFieldIds.length() >= 0) {
            String arrIds[] = iFieldIds.split(",");
            for (int i = 0; i < arrIds.length; i++) {
                oFieldIds = oFieldIds + "," + getSourceField("", arrIds[i]);
            }
        }
        String strFieldIds = getUniqueStr(oFieldIds).toString();

        //把不存在字段关系绑定在一起
        if (!strFieldIds.equals("") && strFieldIds != null) {
            //获取所有字段类型id
            List<Long> fieldIds = StringUtil.toLongList(strFieldIds);
            paramMap.put("fieldIds", fieldIds);
            fieldUserMapper.batchBindEngineFieldUserRel(paramMap);
        }


        String strFieldTypeIds = fieldMapper.findOrgFieldTypeIdsByIds(paramMap);
        if (!strFieldTypeIds.equals("") && strFieldTypeIds != null) {

            String parentFieldTypeIds = "";
            //查所有字段类型id的父id
            if (!strFieldTypeIds.equals("")) {
                strFieldTypeIds = getUniqueStr(strFieldTypeIds).toString();
                String arrIds[] = strFieldTypeIds.split(",");

                for (int i = 0; i < arrIds.length; i++) {
                    if (parentFieldTypeIds.equals("")) {
                        parentFieldTypeIds = getAllParentFieldTypeId("", arrIds[i], "");
                    } else {
                        parentFieldTypeIds = parentFieldTypeIds + "," + getAllParentFieldTypeId("", arrIds[i], "");
                    }
                }
            }

            if (!parentFieldTypeIds.equals("")) {
                strFieldTypeIds = strFieldTypeIds + "," + parentFieldTypeIds;
            }
            List<Long> fieldTypeIds = StringUtil.toLongList(strFieldTypeIds);

            paramMap.put("fieldTypeIds", fieldTypeIds);
            fieldTypeUserMapper.batchBindEngineFieldTypeUserRel(paramMap);
        }

        return true;
    }

    /**
     * 判断表达式的运算结果是否数值型的公共方法
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^(-|\\+)?\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> importExcel(String url, Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();

        InputStream is = null;
        Workbook Workbook = null;
        Sheet Sheet;
        try {
            is = new FileInputStream(url);
            Workbook = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Field> fieldVoList = new ArrayList<Field>();
        List<String> fieldEnList = new ArrayList<>();
        int sucRows = 0; // 导入成功行数
        int failRows = 0; // 导入失败行数
        int repeatRows = 0; // 重复行数
        int existRows = 0; // 已存在的字段

        // 循环工作表 Sheet
        for (int numSheet = 0; numSheet < Workbook.getNumberOfSheets(); numSheet++) {
            Sheet = Workbook.getSheetAt(numSheet);
            if (Sheet == null) {
                continue;
            }
            // 循环行 Row
            for (int rowNum = 1; rowNum <= Sheet.getLastRowNum(); rowNum++) {
                try {
                    Row Row = Sheet.getRow(rowNum);
                    if (Row == null) {
                        continue;
                    }
                    Field fieldVo = new Field();
                    fieldVo.setAuthor(Long.valueOf(paramMap.get("author").toString()));
                    fieldVo.setIsCommon(Integer.valueOf(paramMap.get("isCommon").toString()));

                    // 循环单元格 Cell
                    for (int cellNum = 0; cellNum <= Row.getLastCellNum(); cellNum++) {
                        Cell cell = Row.getCell(cellNum);
                        String cellStr = ExcelUtil.getCellValue(cell).trim();
                        switch (cellNum) { // 逐单元格处理

                            case 0:
                                fieldVo.setFieldEn(cellStr);
                                break;
                            case 1:
                                fieldVo.setFieldCn(cellStr);
                                break;
                            case 2:
                                paramMap.put("fieldType", cellStr);
                                Long fieldTypeId = fieldTypeMapper.findIdByFieldType(paramMap);
                                if (fieldTypeId != 0)
                                    fieldVo.setFieldTypeId(fieldTypeId);
                                else
                                    fieldVo.setFieldTypeId(new Long(0)); //异常1：如果字段类型没法匹配，如何处理？
                                break;
                            case 3:
                                Integer valueType = 0;
                                if (cellStr.equals("数值型")) {
                                    valueType = 1;
                                }
                                if (cellStr.equals("字符型")) {
                                    valueType = 2;
                                }
                                if (cellStr.equals("枚举型")) {
                                    valueType = 3;
                                }
                                if (cellStr.equals("小数型")) {
                                    valueType = 4;
                                }
                                fieldVo.setValueType(valueType);
                                break;
                            case 4:
                                fieldVo.setValueScope(cellStr);
                                break;
                            case 5:
                                if (ExcelUtil.getCellValue(cell).equals("Y")) {
                                    fieldVo.setIsDerivative(1);
                                } else {
                                    fieldVo.setIsDerivative(0);
                                }
                                break;
                            case 6:
                                if (cellStr.equals("Y")) {
                                    fieldVo.setIsOutput(1);
                                } else if (cellStr.equals("N")) {
                                    fieldVo.setIsOutput(0);
                                }
                                break;
                            case 7://这里只处理公式原始值，不确定回显字段及字段绑定
                                fieldVo.setFormula(cellStr);
                                break;
                            default:
                                break;
                        }
                    }
                    if (fieldVo.getFieldEn() != null) {
                        paramMap.put("fieldEn", fieldVo.getFieldEn());
                        Field OldFieldVo = fieldMapper.findByFieldName(paramMap);
                        if (OldFieldVo != null) {
                            existRows++;
                            // fieldVo.setId(OldFieldVo.getId());
                            // 不能直接更新字段，需先修改已使用到的地方
                            // fieldMapper.updateField(paramMap);
                        } else {
                            // 防止重复字段
                            if (fieldEnList.contains(fieldVo.getFieldEn())) {
                                repeatRows++;
                            } else {
                                sucRows++;
                                // 加入到list，等待批量更新
                                fieldVoList.add(fieldVo);
                                fieldEnList.add(fieldVo.getFieldEn());
                            }
                        }
                    }
                } catch (Exception e) {
                    failRows++;
                    e.printStackTrace();
                }
            }// end for Row
        }// end first sheet
        if (fieldVoList.size() > 0) {
            fieldMapper.batchCreateField(fieldVoList);
            paramMap.put("status", 1);// 导入后字段状态默认启用
            fieldUserMapper.batchCreateFieldUserRel(paramMap);
        }
        resultMap.put("sucRows", sucRows);
        resultMap.put("failRows", failRows);
        resultMap.put("repeatRows", repeatRows);
        resultMap.put("existRows", existRows);
        return resultMap;
    }

    @Override
    public Field findByFieldCn(Map<String, Object> paramMap) {
        return fieldMapper.findByFieldCn(paramMap);
    }

    /**
     * 查找某字段类型所有的<子类型>拼成逗号分隔的字符串
     *
     * @return
     */
    public String getAllFieldTypeId(String ids, String pid, String engineId) {

        Map<String, Object> param = new HashMap<String, Object>();
        Long userId = SessionManager.getLoginAccount().getUserId();
        param.put("userId", userId);
        param.put("engineId", engineId);
        param.put("parentId", pid);

        String sid = fieldTypeMapper.findTypeIdByParentId(param);
        if (sid != null && sid.length() > 0) {
            if (ids.equals(""))
                ids = sid;
            else
                ids = ids + "," + sid;

            String arrIds[] = sid.split(",");
            for (int i = 0; i < arrIds.length; i++) {
                String str = getAllFieldTypeId("", arrIds[i], engineId);
                if (!str.equals(""))
                    ids = ids + "," + str;
            }
        }
        return ids;
    }

    /**
     * 查找某字段类型所有的<父类型>拼成逗号分隔的字符串
     *
     * @return
     */
    public String getAllParentFieldTypeId(String ids, String id, String engineId) {

        Map<String, Object> param = new HashMap<String, Object>();
        Long userId = SessionManager.getLoginAccount().getUserId();
        param.put("userId", userId);
        if (engineId == null || engineId.equals("")) {
            engineId = null;
        }
        param.put("engineId", engineId);
        param.put("fieldTypeId", id);

        String pid = fieldTypeMapper.findParentIdByTypeId(param);
        String s = "";
        if (!pid.equals("0")) {
            ids = id + "," + getAllParentFieldTypeId("", pid, engineId);
        } else {
            return id;
        }

        return ids;
    }

    /**
     * 公共检查字段类型下的所有字段是否被使用  供删除时校验用
     *
     * @return
     */
    @Override
    public Map<String, Object> checkFieldType(Map<String, Object> paramMap) {

        String pid = (String) paramMap.get("pid");
        String engineId = (String) paramMap.get("engineId");

        String strfieldTypeIds = getAllFieldTypeId("", pid, engineId);


        if (strfieldTypeIds != null && strfieldTypeIds.length() > 0)
            strfieldTypeIds = pid + "," + strfieldTypeIds; //加上当前节点
        else
            strfieldTypeIds = pid;

        List<Long> fieldTypeIds = StringUtil.toLongList(strfieldTypeIds);
        paramMap.put("fieldTypeIds", fieldTypeIds);

        //获取所有字段类型下的所有启用和停用状态的字段id，逗号分割
        String fieldIds = "";
        fieldIds = fieldMapper.findFieldIdsByTypeIds(paramMap);

        if (fieldIds != null) {
            String arrIds[] = fieldIds.split(",");
            for (int i = 0; i < arrIds.length; i++) {

                paramMap.put("fieldId", arrIds[i]);

                checkField(paramMap);
                if ((boolean) paramMap.get("beUsed")) {
                    break; // 遇到第一个字段存在被使用的情况就跳出循环来
                }
            }
        }
        paramMap.put("fieldTypeIds", strfieldTypeIds);
        paramMap.put("fieldIds", fieldIds);
        return paramMap;
    }

    public Map<String, Object> deleteNode(@RequestParam Map<String, Object> paramMap) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        paramMap.put("userId", userId);

        String strFieldIds = "";
        strFieldIds = (String) paramMap.get("fieldIds");
        List<Long> fieldIds = StringUtil.toLongList(strFieldIds);
        paramMap.put("fieldIds", fieldIds);

        List<Long> fieldTypeIds = StringUtil.toLongList((String) paramMap.get("fieldTypeIds"));
        paramMap.put("fieldTypeIds", fieldTypeIds);

        //先更新字段状态状态为-1
        boolean f = false;
        if (!strFieldIds.equals(""))
            f = fieldUserMapper.deleteFieldByIds(paramMap);
        else
            f = true;
        //再更新字段类型树状态为-1
        boolean ft = fieldTypeMapper.updateFieldTypeByTypeIds(paramMap);
        //删除没有字段的空节点
        boolean ftd = fieldTypeMapper.deleteFieldTypeByTypeIds(paramMap);

        paramMap.put("fieldUpdate", f);
        paramMap.put("fieldTypeUpdate", ft);

        Integer result = -1;
        if (f && ft) {
            result = 1;
        }
        paramMap.put("result", result);

        return paramMap;
    }

    /**
     * 从回收站还原一个或多个同类型或不同类型字段同时还原字段类型
     *
     * @return
     */
    public boolean backEngFieldType(Map<String, Object> paramMap) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        paramMap.put("userId", userId);

        String basicFieldTypeIds = fieldMapper.findFieldTypeIdsByFieldId(paramMap);

        String strFieldTypeIds = basicFieldTypeIds;

        String arrIds[] = basicFieldTypeIds.split(",");
        for (int i = 0; i < arrIds.length; i++) {
            String str = getAllParentFieldTypeId("", arrIds[i], (String) paramMap.get("engineId"));
            if (!str.equals("")) {
                strFieldTypeIds = strFieldTypeIds + "," + str;
            }
        }

        //更新指定字段状态为1
        boolean f = fieldUserMapper.backFieldByIds(paramMap);

        //更新指定类型节点状态为1
        List<Long> fieldTypeIds = StringUtil.toLongList(strFieldTypeIds);
        paramMap.put("fieldTypeIds", fieldTypeIds);
        boolean ft = fieldTypeMapper.backFieldTypeByTypeIds(paramMap);
        //ft 有两种情况：true是通过删除树节点还原会更新，如果通过删除字段还原，执行结果是false.
        boolean result = false;
        if (f)
            result = true;

        return result;

    }

    @Override
    public int isExistsFieldType(Map<String, Object> paramMap) {
        return fieldTypeMapper.isExists(paramMap);
    }

    @Override
    public int isExistsDefaultTreeName(Map<String, Object> paramMap) {
        return fieldTypeMapper.isExistsDefaultTreeName(paramMap);
    }

    @Override
    public String findFieldIdsByTypeIds(Map<String, Object> paramMap) {
        return fieldMapper.findFieldIdsByTypeIds(paramMap);
    }

    @Override
    public String findOrgFieldIdsByTypeIds(Map<String, Object> paramMap) {
        return fieldMapper.findOrgFieldIdsByTypeIds(paramMap);
    }

    /**
     * 生成引擎批量测试的样本数据
     *
     * @return
     */
    @Override
    public String createEngineTestData(@RequestParam Map<String, Object> paramMap) {

        Long userId = SessionManager.getLoginAccount().getUserId();
        paramMap.put("userId", userId);

        //构造虚拟测试数据 输入字段id既是原生字段

//    	String strIds = (String) paramMap.get("Ids");
//    	List<Long> Ids = StringUtil.toLongList(strIds);
//    	paramMap.put("Ids", Ids);
//    	List<Field> fieldList = fieldMapper.findFieldByIds(paramMap);

        List<Field> fieldList = (List<Field>) paramMap.get("fieldList");

        Integer fieldCt = fieldList.size(); //获取字段个数

        Long rowCt = Long.valueOf((String) paramMap.get("rowCt")).longValue(); //样本数
        double nullCtRatio = new Double((String) paramMap.get("nullCtRatio"));
        Long nullCt = (long) (nullCtRatio * rowCt * fieldCt / 100); //所有样本参数中参数为空的总个数
        double elseCtRatio = new Double((String) paramMap.get("elseCtRatio"));
        Long elseCt = (long) (elseCtRatio * rowCt * fieldCt / 100); //所有样本参数中超界参数的总个数

        int iNullCt = 0; //初始化参数为空的计数器
        int iElseCt = 0; //初始化参数超界的计数器

        Random random = new Random();
        StringBuffer testData = new StringBuffer();
        for (int i = 0; i < rowCt; i++) {
            int colNums = fieldList.size();

            int ct = 0;
            for (Iterator iterator = fieldList.iterator(); iterator.hasNext(); ) {

                boolean flag = false; //异常开关

                Field field = (Field) iterator.next();

                String fieldEn = field.getFieldEn();
                Integer valueType = field.getValueType();
                String valueScope = field.getValueScope();

                testData.append("\"").append(fieldEn).append("\"").append(":");

                //优先按照随机情况赋值
                int nullKey = random.nextInt(2); //nullKey=1时表示用null
                if (nullKey == 1 && iNullCt < nullCt) {
                    testData.append("\"\"");
                    iNullCt += 1;
                    flag = true;
                }

                if (!flag) {
                    int elseKey = random.nextInt(2); //elseKey=1时表示用else
                    if (elseKey == 1 && iElseCt < elseCt && valueType != 2) { //超界的(字符类型字段没有超界)
                        if (valueType == 1) {
                            String minS = valueScope.substring(1, valueScope.indexOf(",")); //(1,5) 1
                            String maxS = valueScope.substring(valueScope.indexOf(",") + 1, valueScope.length() - 1); //(1,5) 5 (1,) 右开区间
                            if (!maxS.equals("")) {//超界 右边界存在加1
                                int v = Integer.parseInt(maxS) + 1;
                                testData.append("\"" + v + "\"");
                            } else if (maxS.equals("") && !minS.equals("")) {
                                int v = Integer.parseInt(minS) - 1;
                                testData.append("\"" + v + "\"");//超界 左边界存在减1
                            }
                        }
                        if (valueType == 3) {
                            testData.append("\"" + "-999999" + "\""); //按照统一枚举值处理 负数的最大位数
                        }
                        iElseCt += 1;
                        flag = true;
                    }
                }

                if (!flag) {
                    //按照正常取值范围赋值
                    if (valueType == 1) {//数值型
                        String minS = valueScope.substring(1, valueScope.indexOf(",")); //(1,5) 1
                        String maxS = valueScope.substring(valueScope.indexOf(",") + 1, valueScope.length() - 1); //(1,5) 5
                        testData.append("\"" + getRandomInt(minS, maxS) + "\"");
                    } else if (valueType == 2) {//字符型
                        testData.append("\"" + getRandomString(6) + "\""); //6位长度随机数字字符
                    } else if (valueType == 3) {//枚举型
                        String arrValueScope[] = valueScope.split(",");
                        int l = arrValueScope.length;
                        int k = random.nextInt(l) % (l + 1);
                        //随机取值
                        for (int j = 0; j < arrValueScope.length; j++) {
                            if (j == k) {
                                String value = arrValueScope[j];
                                String tV = value.substring(value.indexOf(":") + 1, value.length());
                                testData.append("\"" + tV + "\"");
                            }
                        }
                    }
                }

                ct += 1;
                if (ct != fieldCt)
                    testData.append(",");

            }
            //每条样本数据缺少唯一id，将来结果查询如何对应？
            testData.append("\r\n");

        }

        //把生成的数据存入txt文件并返回路径
        String fileName = "";
        String fileUrl = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            fileName = paramMap.get("engineId") + "_" + paramMap.get("versionId") + "_" + sdf.format(new Date()) + ".txt";
            fileUrl = paramMap.get("downloadDir") + "/" + fileName;
            File newFile = new File(fileUrl);
            if (newFile.createNewFile()) {
                PrintWriter p = new PrintWriter(new FileOutputStream(newFile.getAbsolutePath()));
                p.write(testData.toString());
                p.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

//    /**
//     * 引擎批量测试的结果转成pdf文件
//     *
//     * @return
//     */
//    @Override
//    public String createEngineTestResultPdf(@RequestParam Map<String, Object> paramMap) {
//
//        Integer id = Integer.valueOf(String.valueOf(paramMap.get("resultSetId")));
//        EngineResultSet resultSet = new EngineResultSet();
//        resultSet.setId(id);
//        resultSet = resultSetMapper.getResultSetById(resultSet);
//
//        //createPDFWithChinese();
//        String fileName = "";
//        String fileUrl = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
//        fileName = sdf.format(new Date()) + ".pdf";
//        fileUrl = paramMap.get("path") + fileName;
//
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(fileUrl));
//            document.open();
//
//            BaseFont bfChi = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            Font fontChi = new Font(bfChi, 12, Font.NORMAL);
//
//            document.add(new Paragraph("客户分析报告", fontChi));
//
//            document.add(new Paragraph("结果详情", fontChi));
//            PdfPTable table = new PdfPTable(2);
//            table.setWidthPercentage(100);
//            table.setWidthPercentage(100);
//            table.addCell(new Paragraph("用户ID： " + id, fontChi));
//            table.addCell(new Paragraph("引擎名称： " + resultSet.getEngine_name(), fontChi));
//
//            String result = resultSet.getResult();
//            if (result == null || result.equals("") || result.equals("1")) {
//                result = "通过";
//            } else if (result.equals("2")) {
//                result = "拒绝";
//            }
//
//            table.addCell(new Paragraph("决策建议：" + result, fontChi));
//            String score = resultSet.getScorecardscore();
//            if (score == null) score = "";
//            table.addCell(new Paragraph("信用评分： " + score + "分", fontChi));
//            document.add(table);
//
//            document.add(new Paragraph("黑名单", fontChi));
//            PdfPTable table2 = new PdfPTable(3);
//            table2.setWidthPercentage(100);
//            table2.setWidthPercentage(100);
//            table2.addCell(new Paragraph("名单类型", fontChi));
//            table2.addCell(new Paragraph("名单名称", fontChi));
//            table2.addCell(new Paragraph("名单描述", fontChi));
//
//            List<ResultSetList> resultSetLists = resultSet.getResultSetList();
//
//            //是否命中标志
//            boolean flag = false;
//            if (resultSet.getResultSetList().size() > 0) {
//                for (Iterator iterator = resultSetLists.iterator(); iterator.hasNext(); ) {
//                    ResultSetList resultSetList = (ResultSetList) iterator.next();
//                    if (resultSetList.getType() == 1) {
//                        flag = true;
//                        table2.addCell(new Paragraph(resultSetList.getVersionCode(), fontChi));
//                        table2.addCell(new Paragraph(resultSetList.getName(), fontChi));
//                        table2.addCell(new Paragraph(resultSetList.getDescription(), fontChi));
//                    }
//                }
//            }
//            if (!flag) {
//                PdfPCell cell = new PdfPCell(new Paragraph("未命中", fontChi));
//                cell.setColspan(3);
//                table2.addCell(cell);
//            }
//
//            document.add(table2);
//
//            document.add(new Paragraph("白名单", fontChi));
//            PdfPTable table3 = new PdfPTable(3);
//            table3.setWidthPercentage(100);
//            table3.setWidthPercentage(100);
//            table3.addCell(new Paragraph("名单类型", fontChi));
//            table3.addCell(new Paragraph("名单名称", fontChi));
//            table3.addCell(new Paragraph("名单描述", fontChi));
//
//            flag = false; //重置为false
//            if (resultSet.getResultSetList().size() > 0) {
//                for (Iterator iterator = resultSetLists.iterator(); iterator.hasNext(); ) {
//                    ResultSetList resultSetList = (ResultSetList) iterator.next();
//                    if (resultSetList.getType() == 2) {
//                        flag = true;
//                        table3.addCell(new Paragraph(resultSetList.getVersionCode(), fontChi));
//                        table3.addCell(new Paragraph(resultSetList.getName(), fontChi));
//                        table3.addCell(new Paragraph(resultSetList.getDescription(), fontChi));
//                    }
//                }
//            }
//            if (!flag) {
//                PdfPCell cell = new PdfPCell(new Paragraph("未命中", fontChi));
//                cell.setColspan(3);
//                table3.addCell(cell);
//            }
//
//            document.add(table3);
//
//            document.add(new Paragraph("硬性拒绝规则", fontChi));
//            PdfPTable table4 = new PdfPTable(4);
//            table4.setWidthPercentage(100);
//            table4.setWidthPercentage(100);
//            table4.addCell(new Paragraph("规则ID", fontChi));
//            table4.addCell(new Paragraph("规则名称", fontChi));
//            table4.addCell(new Paragraph("命中原因", fontChi));
//            table4.addCell(new Paragraph("指标表现", fontChi));
//
//            flag = false; //重置为false
//            if (resultSet.getResultSetList().size() > 0) {
//                for (Iterator iterator = resultSetLists.iterator(); iterator.hasNext(); ) {
//                    ResultSetList resultSetList = (ResultSetList) iterator.next();
//                    if (resultSetList.getType() == 3) {
//                        flag = true;
//                        table4.addCell(new Paragraph(resultSetList.getVersionCode(), fontChi));
//                        table4.addCell(new Paragraph(resultSetList.getName(), fontChi));
//                        table4.addCell(new Paragraph(resultSetList.getDescription(), fontChi));
//                        table4.addCell(new Paragraph("", fontChi));
//                    }
//                }
//            }
//            if (!flag) {
//                PdfPCell cell = new PdfPCell(new Paragraph("未命中", fontChi));
//                cell.setColspan(4);
//                table4.addCell(cell);
//            }
//
//            document.add(table4);
//
//            document.add(new Paragraph("加减分规则", fontChi));
//            document.add(new Paragraph());
//            PdfPTable table5 = new PdfPTable(4);
//            table5.setWidthPercentage(100);
//            table5.setWidthPercentage(100);
//            table5.addCell(new Paragraph("规则ID", fontChi));
//            table5.addCell(new Paragraph("规则名称", fontChi));
//            table5.addCell(new Paragraph("命中原因", fontChi));
//            table5.addCell(new Paragraph("指标表现", fontChi));
//
//            flag = false; //重置为false
//            if (resultSet.getResultSetList().size() > 0) {
//                for (Iterator iterator = resultSetLists.iterator(); iterator.hasNext(); ) {
//                    ResultSetList resultSetList = (ResultSetList) iterator.next();
//                    if (resultSetList.getType() == 4) {
//                        flag = true;
//                        table5.addCell(new Paragraph(resultSetList.getVersionCode(), fontChi));
//                        table5.addCell(new Paragraph(resultSetList.getName(), fontChi));
//                        table5.addCell(new Paragraph(resultSetList.getDescription(), fontChi));
//                        table5.addCell(new Paragraph("", fontChi));
//                    }
//                }
//            }
//            if (!flag) {
//                PdfPCell cell = new PdfPCell(new Paragraph("未命中", fontChi));
//                cell.setColspan(4);
//                table5.addCell(cell);
//            }
//
//            document.add(table5);
//
//            document.close();
//
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return fileName;
//
//    }

    /**
     * 生成字符型随机数
     *
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成指定范围内的随机整数
     *
     * @return
     */
    public static int getRandomInt(String minS, String maxS) {

        int min = 0, max = 0;

        if (minS.indexOf(".") >= 0) { // 3.90 | .9
            minS = minS.substring(0, minS.indexOf("."));
        }

        if (maxS.indexOf(".") >= 0) { // 3.90
            maxS = maxS.substring(0, maxS.indexOf("."));
        }

        if (maxS.equals("") && !minS.equals("")) { // (4,) 右开区间
            min = Integer.parseInt(minS);
            max = min + 10000;
        } else if (minS.equals("") && !maxS.equals("")) { // (,10) 左开区间
            max = Integer.parseInt(maxS);
            min = max - 10000;
        } else if (!minS.equals("") && !maxS.equals("")) {// (4,10) 左右闭区间
            min = Integer.parseInt(minS);
            max = Integer.parseInt(maxS);
        }

        Random random = new Random();
        int i = random.nextInt(max) % (max - min + 1) + min;

        return i;

    }

    @Override
    public int fieldValidate(String fieldEn, String fieldCn, String engineId, Long fieldId) {
        return 0;
    }

    @Override
    public int updateFieldFolder(UpdateFolderParam param) {
        int result = fieldMapper.updateFieldFolder(param);
        return result;
    }
   @Override
    public String getFieldEnById(Long id) {
        return fieldMapper.findFieldNameById(id);
    }

    @Override
    public List<Field> queryByIds(Collection<Long> ids) {
        if (ids==null||ids.size()==0){
            return new ArrayList<>();
        }
        return fieldMapper.selectByIds(ids);
    }

    @Override
    public List<Field> queryByEns(Collection<String> ens) {
        if (ens==null||ens.size()==0){
            return new ArrayList<>();
        }
        return fieldMapper.selectByEns(ens);
    }

    @Override
    public List<Field> queryByOrganAndCns(Collection<String> cns,Long organId) {
        if (cns==null||cns.size()==0){
            return new ArrayList<>();
        }
        return fieldMapper.selectByOrganCns(cns,organId);
    }

    public void sqlFieldCheck(Map map){
        if (map.containsKey("sqlStatement")){
            Object sqlStatement = map.get("sqlStatement");
            if (sqlStatement!=null&&!"".equals(sqlStatement)){
                String param = sqlStatement.toString().toUpperCase();
                for (String match :KEY_WORDS) {
                   if ( param.contains(match)){
                       throw new ApiException(ErrorCodeEnum.SQL_FIELD_HAVE_RISK.getCode(),ErrorCodeEnum.SQL_FIELD_HAVE_RISK.getMessage()+":"+match);
                   }
                }
            }
        }
    }
}
