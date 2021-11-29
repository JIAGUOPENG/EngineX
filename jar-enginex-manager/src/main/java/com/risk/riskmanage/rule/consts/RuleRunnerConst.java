package com.risk.riskmanage.rule.consts;

import java.util.Map;

public class RuleRunnerConst {
    public static final String RULE_FILE_HEAD = " package com.baoying.enginex.executor.drools \\r\\n" +
            " import java.util.Map;\\r\\n" +
            " import java.util.List;\\r\\n" +
            " import java.util.ArrayList;\\r\\n" +
            " import java.util.HashMap;\\r\\n" +
            " import com.baoying.enginex.executor.engineModel.model.InputParam;\\r\\n" +
            " import com.baoying.enginex.executor.engineModel.model.Result;\\r\\n" +
            " import com.baoying.enginex.executor.engineModel.model.EngineRule;\\r\\n";
    public static final String RULE_NAME_PREFIX = " rule \t";
    public static final String RULE_SALIENCE_PREFIX = "\\r\\n salience\\t ";
    public static final String RULE_CONDITION_PREFIX = "\\r\\n when \\r\\n";
    public static final String CONDITION_DETAIL_PREFIX = "\\t $inputParam : InputParam();\\r\\n Map";
    public static final String CONDITION_DETAIL_SUFFIX = " \t from $inputParam.inputParam;\\r\\n";
    public static final String RULE_DISPOSE_PREFIX = "\\t then \\r\\n";
    public static final String DISPOSE_PREFIX =
            "\\t List<Result>  resultList =$inputParam.getResult();\\r\\n" +
                    "\\t Result result =new Result(); \\r\\n" +
                    "\\t result.setResultType(\"";
    public static final String DISPOSE_INFIX = "\"); \\r\\n" +
            "\\t result.setVersionCode(\"";
    public static final String DISPOSE_SUFFIX = "\"); \\r\\n" +
            "\\t Map<String, Object> map =new HashMap<>(); \\r\\n";

    public static final String SCORE_PREFIX = "\\t map.put(\"score\",";
    public static final String SCORE_SUFFIX = "); \\r\\n";

    public static final String RULE_END = "\\t result.setMap(map); \\r\\n" +
            " resultList.add(result); \\r\\n" +
            "\\t $inputParam.setResult(resultList); \\r\\n" +
            " end\\r\\n";

    public static final int DEFAULT_TYPE = 1;

    //拼装规则执行的content
    public static String fitRuleContent(String code, Integer salience, String rule, Integer type, Integer score, Map<String,String> contentMap) {
        String content = "";
        if (salience == null || salience < 0) {
            salience = 0;
        }
        if (type == null) {
            type = DEFAULT_TYPE;
        }
        content += RULE_FILE_HEAD +
                RULE_NAME_PREFIX + code +
                RULE_SALIENCE_PREFIX + salience +
                RULE_CONDITION_PREFIX +
                CONDITION_DETAIL_PREFIX + rule +
                CONDITION_DETAIL_SUFFIX +
                RULE_DISPOSE_PREFIX +
                DISPOSE_PREFIX + type +
                DISPOSE_INFIX + code+
                DISPOSE_SUFFIX;
        if (score!=null){
            content += SCORE_PREFIX+score+SCORE_SUFFIX;
        }

        if (contentMap!=null&&!contentMap.isEmpty()){
            for (String s : contentMap.keySet()) {
                content+="\\t\\t map.put(\""+s+"\",\""+contentMap.get(s)+"\");\\n";
            }

        }
        content += RULE_END;

        return content;
    }

//    public static void main(String[] args) {
//        String versionCode = "rule1";
//        Integer salience = null;
//        String rule = "age>10";
//        Integer type = null;
//        Integer score = 10;
//        Map<String,String> contentMap = new HashMap<>();
//        contentMap.put("a","a");
//        contentMap.put("b","b");
//        contentMap.put("c","c");
//        String s = fitContent(versionCode, salience, rule, type, score,contentMap);
//        System.out.println(s);
//    }
}
