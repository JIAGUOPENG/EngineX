package com.risk.riskmanage.rule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.datamanage.common.ValueType;
import com.risk.riskmanage.rule.mapper.RuleFieldInfoMapper;
import com.risk.riskmanage.rule.model.RuleFieldInfo;
import com.risk.riskmanage.rule.model.vo.RuleVo;
import com.risk.riskmanage.rule.service.RuleFieldInfoService;
import com.risk.riskmanage.tactics.model.TacticsOutput;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RuleFieldInfoServiceImpl extends ServiceImpl<RuleFieldInfoMapper, RuleFieldInfo> implements RuleFieldInfoService {
    @Resource
    private RuleFieldInfoMapper ruleFieldInfoMapper;
    @Override
    public List<RuleFieldInfo> queryByRuleId(Long ruleId) {
        RuleFieldInfo ruleFieldInfo = new RuleFieldInfo();
        ruleFieldInfo.setRuleId(ruleId);
        List<RuleFieldInfo> ruleFieldInfoList = ruleFieldInfoMapper.selectList(new QueryWrapper<>(ruleFieldInfo));

        return ruleFieldInfoList;
    }

    @Override
    @Transactional
    public boolean insertRuleField(List<RuleFieldInfo> list, Long ruleId) {
        for (RuleFieldInfo ruleFieldInfo : list) {
            ruleFieldInfo.setRuleId(ruleId);
        }
        this.saveBatch(list);
        return false;
    }

    @Override
    @Transactional
    public boolean updateRuleField(List<RuleFieldInfo> list, Long ruleId) {
        boolean b = this.deleteRuleField(ruleId);
        if (!b){
            List<RuleFieldInfo> ruleFieldInfoList = this.queryByRuleId(ruleId);
            if (ruleFieldInfoList!=null&&ruleFieldInfoList.size()>0){
                throw new ApiException("修改简单规则条件异常","修改简单规则条件异常");
            }
        }
        for (RuleFieldInfo ruleFieldInfo : list) {
            ruleFieldInfo.setRuleId(ruleId);
        }
        this.saveBatch(list);
        return false;
    }


    @Override
    @Transactional
    public boolean deleteRuleField(Long ruleId) {
        RuleFieldInfo ruleFieldInfo = new RuleFieldInfo();
        ruleFieldInfo.setRuleId(ruleId);
        return this.remove(new QueryWrapper<>(ruleFieldInfo));
    }

    public String assemblyRuleContent(RuleVo rule, List<RuleFieldInfo> fieldInfoList) {
        if (rule.getRuleType()==null){
            rule.setRuleType(1);
        }
        List<RuleFieldInfo> ruleFieldList = fieldInfoList.stream().map(item -> {
            item.setFieldEn(item.getFieldId().split("\\|")[1]);
            return item;
        }).collect(Collectors.toList());

        String ruleStr = ""; // ( this['age'] > 18  && this['age'] < 80  )
        String functionStr = "";

        // ((-车损明细名称-==-底大边（右）||-车损明细名称-==-底大边外板（右）)&&-车损明细类型-==-1)&&-车型名称-==-吉利全球鹰RX6453B01多用途乘用车
        Map<String, List<List<RuleFieldInfo>>> map = cycleRuleHandle(ruleFieldList);
        String firstLogical = "";
        if(!ruleFieldList.isEmpty()){
            firstLogical = ruleFieldList.get(0).getLogical();
            if(firstLogical.contains("&&")){
                firstLogical = firstLogical.substring(0, firstLogical.indexOf("&&"));
            } else if(firstLogical.contains("||")){
                firstLogical = firstLogical.substring(0, firstLogical.indexOf("||"));
            }
        }
        if (!map.isEmpty()) {
//            List<RuleContentInfo> ruleContents = rule.getRuleContentInfoList();
            List<TacticsOutput> tacticsOutputList = rule.getTacticsOutputList();
            for (String key : map.keySet()) {
                List<String> attributeNameSet = tacticsOutputList.stream().map(item -> {
                    String attributeName = "";
                    String fieldValue = item.getFieldValue();
                    if(item.getVariableType() == 2 && fieldValue.contains(key + "[")){
                        attributeName = fieldValue.substring(fieldValue.indexOf("[") + 1, fieldValue.lastIndexOf("]"));
                    }
                    return attributeName;
                }).filter(item -> StringUtils.isNotBlank(item)).collect(Collectors.toList());

                String attributeName = attributeNameSet.size() > 0 ? attributeNameSet.get(0) : "";

                ruleStr = " " + key + "(this['" + key + "'], $inputParam) ";
                functionStr += createDroolsFunction(key, attributeName, map.get(key), firstLogical);
            }
        }

        for (int i = 0; i < ruleFieldList.size(); i++) {
            RuleFieldInfo ruleField = ruleFieldList.get(i);
            if (!"-1".equals(ruleField.getLogical())) {
                String logical = ruleField.getLogical();
                if (i == 0 && !map.isEmpty()) {
                    logical = logical.replace(firstLogical, "");
                }
                ruleStr = ruleStr + " " + logical;
            }

            // 字符、数组型的值需要加引号
            if (ruleField.getValueType() == ValueType.Char.getValue() || ruleField.getValueType() == ValueType.Array.getValue()) {
                ruleStr = ruleStr + " this['" + ruleField.getFieldId().split("\\|")[1] + "'] " +
                        ruleField.getOperator() + " \"" + ruleField.getFieldValue() + "\" ";
            } else {
                ruleStr = ruleStr + " this['" + ruleField.getFieldId().split("\\|")[1] + "'] " +
                        ruleField.getOperator() + " " + ruleField.getFieldValue() + " ";
            }
        }

        if (map.isEmpty()) {
            if (StringUtils.isNotBlank(rule.getLastLogical()) && !"-1".equals(rule.getLastLogical())) {
                ruleStr = ruleStr + rule.getLastLogical();
            }
        }

        String enginerule = "package com.baoying.enginex.executor.drools\\r\\n";
        enginerule += "import java.util.Map;\\r\\n";
        enginerule += "import java.util.List;\\r\\n";
        enginerule += "import java.util.Set;\\r\\n";
        enginerule += "import java.util.HashSet;\\r\\n";
        enginerule += "import java.util.ArrayList;\\r\\n";
        enginerule += "import java.util.HashMap;\r\n";
        enginerule += "import com.baoying.enginex.executor.engineModel.model.InputParam;\\r\\n";
        enginerule += "import com.baoying.enginex.executor.engineModel.model.Result;\\r\\n";
        enginerule += "import com.baoying.enginex.executor.engineModel.model.EngineRule;\\r\\n";
        enginerule += "import com.alibaba.fastjson.JSONObject;\\r\\n";
        enginerule += "rule \"" + rule.getCode() + "\"\\r\\n";
        enginerule += "salience " + rule.getPriority() + "\\r\\n";
        enginerule += "when\\r\\n";
        enginerule += "\\t $inputParam : InputParam();\\r\\n";
        enginerule += "\\t Map(" + ruleStr + ") from $inputParam.inputParam;\\r\\n";
        enginerule += "then\\r\\n";
        enginerule += "\\t List<Result> resultList = $inputParam.getResult();\\r\\n";
        enginerule += "\\t Result result = new Result(); \\r\\n";
        enginerule += "\\t result.setResultType(\"" + rule.getRuleType() + "\"); \\r\\n";
        enginerule += "\\t result.setVersionCode(\"" + rule.getCode() + "\"); \\r\\n";
        enginerule += "\\t Map<String, Object> map = new HashMap<>(); \\r\\n";

        if (rule.getScore() != null) {
            enginerule += "\\t map.put(\"score\"," + rule.getScore() + "); \\r\\n";
        }

//      enginerule +=outputparam;
        enginerule += "\\t result.setMap(map); \\r\\n";
        enginerule += "\\t resultList.add(result); \\r\\n";
        enginerule += "\\t $inputParam.setResult(resultList); \\r\\n";
        enginerule += "end\\r\\n";

        enginerule += "\\r\\n" + functionStr;

        return enginerule;
    }

    /**
     * 循环规则处理
     *
     * @param ruleFieldList
     * @return
     */
    private Map<String, List<List<RuleFieldInfo>>> cycleRuleHandle(List<RuleFieldInfo> ruleFieldList) {
        List<List<RuleFieldInfo>> groupRuleFields = new ArrayList<>();
        List<RuleFieldInfo> ruleFields = new ArrayList<>();
        // ((-车损明细名称-==-底大边（右）||-车损明细名称-==-底大边外板（右）)&&-车损明细类型-==-1)&&-车型名称-==-吉利全球鹰RX6453B01多用途乘用车
        Stack<String> stack = new Stack<>();
        for (RuleFieldInfo ruleField : ruleFieldList) {
            String logical = ruleField.getLogical();

            // 纯括号
            if(!logical.contains("&&") && !logical.contains("||")){
                if(logical.contains("(")){
                    for(int i=0; i<logical.length(); i++){
                        stack.push("(");
                    }
                } else if(logical.contains(")")){
                    for(int i=0; i<logical.length(); i++){
                        if(!stack.isEmpty() && stack.peek().equals("(")){
                            stack.pop();
                        }
                    }
                }
                ruleFields.add(ruleField);
            }
            // 非纯括号
            else {
                if(logical.contains(")")){
                    String leftLogical = "";
                    if(logical.contains("&&")){
                        leftLogical = logical.substring(0, logical.indexOf("&&"));
                    } else if(logical.contains("||")){
                        leftLogical = logical.substring(0, logical.indexOf("||"));
                    }
                    for(int i=0; i<leftLogical.length(); i++){
                        if(!stack.isEmpty() && stack.peek().equals("(")){
                            stack.pop();
                        }
                    }
                }

                if(stack.isEmpty()){
                    groupRuleFields.add(new ArrayList<>(ruleFields));
                    ruleFields = new ArrayList<>();
                    ruleFields.add(ruleField);
                } else {
                    ruleFields.add(ruleField);
                }

                if(logical.contains("(")){
                    String rightLogical = "";
                    if(logical.contains("&&")){
                        rightLogical = logical.substring(logical.indexOf("&&") + 1);
                    } else if(logical.contains("||")){
                        rightLogical = logical.substring(logical.indexOf("||") + 1);
                    }
                    for(int i=0; i<rightLogical.length(); i++){
                        stack.push("(");
                    }
                }
            }

        }
        groupRuleFields.add(new ArrayList<>(ruleFields));

        // map中key为数组名称，value为该数组循环需要满足的多个条件判断单元
        Map<String, List<List<RuleFieldInfo>>> map = new HashMap<>();
        ruleFieldList.clear();
        for (List<RuleFieldInfo> ruleFields1 : groupRuleFields) {
            RuleFieldInfo ruleField = ruleFields1.get(0);
            if (ruleField.getFieldEn().contains("[") && ruleField.getFieldEn().contains("]")) {
                String key = ruleField.getFieldEn().substring(0, ruleField.getFieldEn().indexOf("["));
                if (!map.containsKey(key)) {
                    List<List<RuleFieldInfo>> ruleFieldList1 = new ArrayList<>();
                    ruleFieldList1.add(ruleFields1);
                    map.put(key, ruleFieldList1);
                } else {
                    List<List<RuleFieldInfo>> ruleFieldList1 = map.get(key);
                    ruleFieldList1.add(ruleFields1);
                }
            } else {
                // ruleFieldListNew为剔除数组外，剩余的普通字段集合
                ruleFieldList.add(ruleField);
            }
        }

        return map;
    }

    /**
     * 拼接drools自定义函数
     *
     * @param functionName
     * @param groupRuleFields
     * @return
     */
    private String createDroolsFunction(String functionName, String attributeName, List<List<RuleFieldInfo>> groupRuleFields, String groupBracket) {
        String[] variableArr = new String[groupRuleFields.size()];
        String[] conditionStrArr = new String[groupRuleFields.size()];
        for (int i = 0; i < groupRuleFields.size(); i++) {
            variableArr[i] = "unit" + i;
            String condition = "";
            List<RuleFieldInfo> groupRuleField = groupRuleFields.get(i);
            for (int j = 0; j < groupRuleField.size(); j++) {
                RuleFieldInfo ruleField = groupRuleField.get(j);
                String logical = ruleField.getLogical();
                String fieldEn = ruleField.getFieldEn();
                if (fieldEn.contains("[") && fieldEn.contains("]")) {
                    fieldEn = fieldEn.substring(fieldEn.indexOf("[") + 1, fieldEn.lastIndexOf("]"));
                }
                String operator = ruleField.getOperator();
                String fieldValue = ruleField.getFieldValue();

                if (ruleField.getValueType() == ValueType.Char.getValue()) {
                    if ("==".equals(operator) || "contains".equals(operator)) {
                        operator = operator.replace("==", "equals");
                        condition += logical + " map.get(\"" + fieldEn + "\").toString()." + operator + "(\"" + fieldValue + "\") ";
                    } else if ("!=".equals(operator) || "not contains".equals(operator) || "notContains".equals(operator)) {
                        operator = operator.replace("!=", "equals")
                                .replace("not contains", "contains")
                                .replace("notContains", "contains");
                        condition += logical + " !map.get(\"" + fieldEn + "\").toString()." + operator + "(\"" + fieldValue + "\") ";
                    }
                } else {
                    condition += logical + " Integer.valueOf(map.get(\"" + fieldEn + "\").toString()) " + operator + " " + fieldValue + " ";
                }
            }
            condition += groupBracket;

            String setStr = "";
            if(StringUtils.isNotBlank(attributeName)){
                setStr = "\\t\\t\\t set.add(" + "map.get(\"" + attributeName + "\").toString()" + ");\\r\\n";
            }
            String conditionStr = "\\t\\t if(" + condition + "){\\r\\n"
                    + "\\t\\t\\t " + variableArr[i] + " ++;\\r\\n"
                    + setStr
                    + "\\t\\t }\\r\\n";

            conditionStrArr[i] = conditionStr;
        }

        // 变量定义
        String variableStr = "";
        for (String variable : variableArr) {
            variableStr += "\\t int " + variable + " = 0;\\r\\n";
        }

        // for循环内容
        String forContent = "";
        forContent += "\\t Set set = new HashSet();\\r\\n";
        forContent += "\\t for(Object obj : objList){\\r\\n";
        forContent += "\\t\\t Map map = JSONObject.parseObject(JSONObject.toJSONString(obj), Map.class);\\r\\n";
        for (String conditionStr : conditionStrArr) {
            forContent += conditionStr;
        }
        forContent += "\\t }\\r\\n";

        // 返回结果判断
        String resultJudge = "";
        resultJudge += "\\t if(";
        for (int i = 0; i < variableArr.length; i++) {
            if (i == 0) {
                resultJudge += variableArr[i] + " > 0 ";
            } else {
                resultJudge += "&& " + variableArr[i] + " > 0 ";
            }
        }
        resultJudge += "){\\r\\n";
        resultJudge += "\\t\\t return true;\\r\\n";
        resultJudge += "\\t }\\r\\n";

        // 函数内容
        String functionStr = "";
        functionStr += "function Boolean " + functionName + "(List objList, InputParam inputParam){\\r\\n";
        functionStr += "\\t Map<String, Set<String>> outmap = inputParam.getOutputParam();\\r\\n";
        functionStr += variableStr;
        functionStr += forContent;
        if(StringUtils.isNotBlank(attributeName)){
            functionStr += "\\t outmap.put(\"" + functionName + "["+ attributeName + "]\", set);\\r\\n";
        }
        functionStr += "\\t inputParam.setOutputParam(outmap);\\r\\n";
        functionStr += resultJudge;
        functionStr += "\\t return false;\\r\\n";
        functionStr += "}\\r\\n";

        return functionStr;
    }

}
