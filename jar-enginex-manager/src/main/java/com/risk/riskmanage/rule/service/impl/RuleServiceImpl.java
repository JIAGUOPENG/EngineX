package com.risk.riskmanage.rule.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.datamanage.mapper.FieldMapper;
import com.risk.riskmanage.datamanage.model.Field;
import com.risk.riskmanage.knowledge.common.consts.Status;
import com.risk.riskmanage.common.utils.ExcelUtil;
import com.risk.riskmanage.knowledge.mapper.RuleMapper;
import com.risk.riskmanage.knowledge.model.KnowledgeTree;
import com.risk.riskmanage.knowledge.model.Rule;
import com.risk.riskmanage.knowledge.model.response.UploadResponse;
import com.risk.riskmanage.knowledge.service.KnowledgeTreeService;
import com.risk.riskmanage.rule.consts.RuleConst;
import com.risk.riskmanage.rule.mapper.RuleInfoMapper;
import com.risk.riskmanage.rule.model.*;
import com.risk.riskmanage.rule.model.request.RuleListParamV2;
import com.risk.riskmanage.rule.model.vo.RuleVersionVo;
import com.risk.riskmanage.rule.model.vo.RuleVo;
import com.risk.riskmanage.rule.service.*;
import com.risk.riskmanage.system.mapper.UserMapper;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.tactics.consts.TacticsType;
import com.risk.riskmanage.tactics.model.TacticsOutput;
import com.risk.riskmanage.tactics.service.TacticsOutputService;
import com.risk.riskmanage.util.SessionManager;
import com.risk.riskmanage.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 组织规则维护表(RuleInfo)表服务实现类
 */
@Service("ruleService2")
public class RuleServiceImpl extends ServiceImpl<RuleInfoMapper, RuleInfo> implements RuleService {
    @Resource
    private RuleInfoMapper ruleInfoMapper;

    @Autowired
    private RuleVersionService versionService;
    @Resource
    private RuleConditionService ruleConditionService;
    @Resource
    private RuleFieldInfoService ruleFieldInfoService;

    @Resource
    private TacticsOutputService outputService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private FieldMapper fieldMapper;
    @Resource
    private RuleMapper ruleMapper;
    @Resource
    private KnowledgeTreeService knowledgeTreeService;

    @Override
    public RuleVo queryById(Long id, Integer difficulty) {
        //查询规则
        RuleInfo ruleInfo = ruleInfoMapper.selectById(id);
        if (ruleInfo == null) {
            return null;
        }
        RuleVo ruleVo = new RuleVo();
        BeanUtils.copyProperties(ruleInfo, ruleVo);
        List<TacticsOutput> tacticsOutputList = new ArrayList<>();
        if (difficulty == null || (difficulty != 1 && difficulty != 2 && difficulty != 3)) {
            difficulty = ruleInfo.getDifficulty();
        }
        switch (difficulty) {
            case 1:
                List<RuleFieldInfo> list = ruleFieldInfoService.queryByRuleId(id);
                tacticsOutputList = outputService.queryByTactics(new TacticsOutput(id, TacticsType.BASE_RULE));
                ruleVo.setTacticsOutputList(tacticsOutputList);
                ruleVo.setRuleFieldList(list);
                break;
            case 2:
                //查询版本
                List<RuleVersionVo> ruleVersionList = versionService.queryVersionListByRuleId(id);
                ruleVo.setRuleVersionList(ruleVersionList);
                break;
        }
        return ruleVo;
    }


    @Override
    public PageInfo queryByEntity(RuleListParamV2 ruleListParam) {

        RuleInfo query = ruleListParam.getRuleInfo();
        Integer pageNum = ruleListParam.getPageNum();
        Integer pageSize = ruleListParam.getPageSize();
        if (query != null && query.getName() != null && !"".equals(query.getName())) {
            query.setName("%" + query.getName() + "%");
        }
        if (pageNum > 0 && pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize);
        }
        query.setOrganId(SessionManager.getLoginAccount().getOrganId());
        List<RuleInfo> ruleInfoList = ruleInfoMapper.queryRuleList(query);
        PageInfo pageInfo = new PageInfo(ruleInfoList);

        //TODO 循环查用户表，待优化
        for (RuleInfo info : ruleInfoList) {
            if (info != null && info.getAuthor() != null) {
                info.setAuthorName(userMapper.findNickNameById(info.getAuthor()));
            }
        }
        return pageInfo;
    }


    @Override
    @Transactional
    public RuleVo insertRuleInfo(RuleVo vo) {
        Integer difficulty = vo.getDifficulty();
        if (difficulty == null) {
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(), ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        //初始化基本参数
        RuleVo ruleVo = initParam(vo);
        //拷贝VO到Info对象
        RuleInfo ruleInfo = new RuleInfo();
        BeanUtils.copyProperties(ruleVo, ruleInfo);
        if (difficulty == 1) {
            ruleInfo.setContent(ruleFieldInfoService.assemblyRuleContent(vo, vo.getRuleFieldList()));
        }
        //插入并获取insert后实体对象返回id
        boolean save = this.save(ruleInfo);
        if (!save) {
            throw new ApiException(ErrorCodeEnum.RULE_SAVE_ERROR.getCode(), ErrorCodeEnum.RULE_SAVE_ERROR.getMessage());
        }
        Long ruleId = ruleInfo.getId();

        switch (difficulty) {
            case 1:
                //插入子表（ruleField）数据
                ruleFieldInfoService.insertRuleField(vo.getRuleFieldList(), ruleId);
                //插入子表自定义输出数据
                outputService.insertTacticsOutput(ruleId, ruleVo.getTacticsOutputList());
                break;
            case 2:
                //插入版本表数据
                List<RuleVersionVo> ruleVersionList = ruleVo.getRuleVersionList();
                if (ruleVersionList != null && ruleVersionList.size() > 0) {
                    for (RuleVersionVo ruleVersionVo : ruleVersionList) {
                        ruleVersionVo.setRuleId(ruleId);
                    }
                    versionService.addVersionList(ruleVersionList);
                }
                ruleVo.setRuleVersionList(versionService.queryVersionListByRuleId(vo.getId()));
                break;
        }
        return ruleVo;
    }

    /**
     * 修改数据
     *
     * @param vo 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public RuleVo updateRuleInfo(RuleVo vo) {
        if (vo.getId() == null || vo.getDifficulty() == null) {
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(), ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        Integer difficulty = vo.getDifficulty();
        RuleInfo ruleInfo = new RuleInfo();
        BeanUtils.copyProperties(vo, ruleInfo);
        //修改主表数据
        if (difficulty == 1) {
            ruleInfo.setContent(ruleFieldInfoService.assemblyRuleContent(vo, vo.getRuleFieldList()));
        }
        boolean updateResult = this.updateById(ruleInfo);
        if (!updateResult) {
            throw new ApiException(ErrorCodeEnum.SERVER_ERROR.getCode(), ErrorCodeEnum.SERVER_ERROR.getMessage());
        }
        Long ruleId = vo.getId();
        switch (difficulty) {
            case 1:
                outputService.updateTacticsOutput(ruleId, vo.getTacticsOutputList(), TacticsType.BASE_RULE);
                //插入子表（ruleField）数据
                ruleFieldInfoService.updateRuleField(vo.getRuleFieldList(), ruleId);
                break;
            case 2:
                List<RuleVersionVo> ruleVersionList = vo.getRuleVersionList();
                if (ruleVersionList != null && ruleVersionList.size() > 0) {
                    RuleVersionVo ruleVersionVo = ruleVersionList.get(0);
                    ruleVersionVo.setRuleId(vo.getId());
                    versionService.updateVersion(ruleVersionVo);
                }
                vo.setRuleVersionList(versionService.queryVersionListByRuleId(ruleId));
                break;
        }
        return vo;
    }

    /**
     * 通过主键修改状态，支持批量
     *
     * @param ids    主键id集合
     * @param status 状态代号
     * @return
     */
    @Override
    @Transactional
    public boolean updateStatus(List<Long> ids, Integer status) {
        LambdaUpdateWrapper<RuleInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(RuleInfo::getId, ids);
        RuleInfo info = new RuleInfo();
        info.setStatus(status);
        return this.update(info, wrapper);
    }

    @Override
    @Transactional
    public boolean updateParent(List<Long> ids, Long parentId) {
        LambdaUpdateWrapper<RuleInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(RuleInfo::getId, ids);
        RuleInfo info = new RuleInfo();
        info.setParentId(parentId);
        return this.update(info, wrapper);
    }

    //唯一性检查
    private boolean checkUniqueness(RuleVo vo) {
        LambdaQueryWrapper<RuleInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(RuleInfo::getName, vo.getName());
        queryWrapper.ne(RuleInfo::getStatus, -1);
        queryWrapper.eq(RuleInfo::getOrganId, SessionManager.getLoginAccount().getOrganId());
        queryWrapper.and(wrapper -> wrapper.eq(RuleInfo::getName, vo.getName()).or().eq(RuleInfo::getCode, vo.getCode()));
        RuleInfo info = this.getOne(queryWrapper);
        if (info != null) {
            if (info.getCode().equals(vo.getCode())) {
                throw new ApiException(ErrorCodeEnum.RULE_CODE_REPEAT.getCode(), ErrorCodeEnum.RULE_CODE_REPEAT.getMessage());
            } else if (info.getName().equals(vo.getName())) {
                throw new ApiException(ErrorCodeEnum.RULE_NAME_REPEAT.getCode(), ErrorCodeEnum.RULE_NAME_REPEAT.getMessage());
            }
        }
        return true;
    }

    //新插入数据的准备工作
    private RuleVo initParam(RuleVo vo) {
        this.checkUniqueness(vo);
        //加入用户信息
        User user = SessionManager.getLoginAccount();
        vo.setUserId(user.getUserId());
        vo.setOrganId(user.getOrganId());
        vo.setAuthor(user.getUserId());
        //加入状态信息
        vo.setType(RuleConst.TYPE_ORGAN);
        vo.setStatus(RuleConst.STATUS_ENABLED);
        //加入规则类型
        if (vo == null || vo.getRuleAudit() == null) {
            throw new ApiException(ErrorCodeEnum.PARAMS_EXCEPTION.getCode(), ErrorCodeEnum.PARAMS_EXCEPTION.getMessage());
        }
        switch (vo.getRuleAudit()) {
            case RuleConst.RULE_AUDIT_TERMINATION:
                vo.setRuleType(RuleConst.RULE_TYPE_TERMINATION);
                break;
            case RuleConst.RULE_AUDIT_SCORING:
                vo.setRuleType(RuleConst.RULE_TYPE_SCORING);
                break;
            default:
                vo.setRuleType(RuleConst.RULE_TYPE_TERMINATION);
        }

        return vo;
    }

    @Override
    @Transactional
    public UploadResponse upload(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator iter = multiRequest.getFileNames();
        //查出原有的规则内容
        List<Rule> rules = ruleMapper.getAllCodeNameParentId();
        //将文件夹和code结合起来
        Set<String> nameSet = new HashSet<>();
        //将文件夹和规则名结合起来
        Set<String> codeSet = new HashSet<>();
        for (Rule rule : rules) {
            codeSet.add(rule.getCode() + "/" + rule.getParentId());
            nameSet.add(rule.getName() + "/" + rule.getParentId());
        }
        //重复的code集合
        Set<String> rpCodeSet = new HashSet<>();
        //重复的名字集合
        Set<String> rpNameSet = new HashSet<>();
        //重复行数
        int repeatRows = 0;
        //失败行数
        int failRows = 0;
        //成功行数
        int sucRows = 0;
        //已存在行数
        int existRows = 0;
        //总行数
        int total = 0;
        //存放重复的list
        List<String> rpCodeList = new ArrayList();
        List<String> rpNameList = new ArrayList();
        //已存在的list
        List<String> existCodeList = new ArrayList();
        List<String> existNameList = new ArrayList();
        //文件夹不存在的List
        List<String> folderNotExistList = new ArrayList();

        Map map = new HashMap();
        //处理excel
        while (iter.hasNext()) {
            MultipartFile file = multiRequest.getFile(iter.next().toString());
            boolean isXlsx = false;
            String fileName = file.getOriginalFilename();

            if (fileName.endsWith(".xlsx")) {
                isXlsx = true;
            }
            InputStream input = file.getInputStream();
            Workbook wb = null;
            //根据文件格式(2003或者2007)来初始化
            if (isXlsx)
                wb = new XSSFWorkbook(input);
            else
                wb = new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);
//            POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            Sheet Sheet = wb.getSheetAt(0);
            User user = SessionManager.getLoginAccount();
            Long userId = user.getUserId();

            if (sheet != null) {
                try {
                    HashMap<String, Object> paramMap = new HashMap<>();
                    paramMap.put("parentId", "0");
                    paramMap.put("treeType", "0");
                    paramMap.put("type", "1");
                    List<Map> treeList = knowledgeTreeService.getTreeList(paramMap);
                    HashMap<String, Long> cache = new HashMap<>();
                    Map folder = treeList.get(0);
                    List<RuleVo> rlist = new ArrayList<RuleVo>();
                    //遍历excel,从第二行开始 即 rowNum=1,逐个获取单元格的内容,然后进行格式处理,最后插入数据库
                    for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        total++;
                        Row hssfRow = sheet.getRow(rowNum);
                        if (hssfRow == null || hssfRow.getCell(0) == null) {
                            continue;
                        }
                        RuleVo rule = new RuleVo();
                        rule.setName(ExcelUtil.formatCell(hssfRow.getCell(1)));
                        rule.setCode(ExcelUtil.formatCell(hssfRow.getCell(2)));
                        rule.setDescription(ExcelUtil.formatCell(hssfRow.getCell(3)));
                        rule.setPriority(Integer.parseInt(ExcelUtil.formatCell(hssfRow.getCell(4)).replace(".0", "")));
                        String folderName = ExcelUtil.formatCell(hssfRow.getCell(0));
                        Long folderId = this.getFolderId(folder, cache, folderName);

                        rule.setAuthor(userId);
                        rule.setUserId(userId);
                        rule.setOrganId(user.getOrganId());
                        rule.setStatus(Status.ENABLED);
                        rule.setType(1);
                        rule.setRuleType(1);
                        rule.setRuleAudit(5);
                        rule.setScore(1);
                        rule.setDifficulty(1);
                        //如果未匹配成功则不插入,
                        if (folderId == 0L && (folderName != null || !"".equals(folderName))) {
                            failRows++;
                            folderNotExistList.add(folderName);
                            continue;
                        }
                        rule.setParentId(folderId);
                        //判断规则code和name是否重复
                        if (rpCodeSet.contains(rule.getCode() + "/" + rule.getParentId())) {
                            repeatRows++;
                            rpCodeList.add(folderName + "/" + rule.getCode());
                            continue;
                        }
                        if (rpNameSet.contains(rule.getName() + "/" + rule.getParentId())) {
                            repeatRows++;
                            rpNameList.add(folderName + "/" + rule.getName());
                            continue;
                        }
                        //判断规则code和name是否在数据库中已存在
                        if (codeSet.contains(rule.getCode() + "/" + rule.getParentId())) {
                            existRows++;
                            existCodeList.add(folderName + "/" + rule.getCode());
                            continue;
                        }
                        if (nameSet.contains(rule.getName() + "/" + rule.getParentId())) {
                            existRows++;
                            existNameList.add(folderName + "/" + rule.getName());
                            continue;
                        }

                        String ruleFields = ExcelUtil.formatCell(hssfRow.getCell(5));
                        rule.setLastLogical(this.checkLastLogical(ruleFields));
                        ruleFields = this.removeLastLogical(ruleFields, rule.getLastLogical());
                        rule.setRuleFieldList(this.handExcelDataForRuleField(ruleFields, userId));
                        String ruleContents = ExcelUtil.formatCell(hssfRow.getCell(6));
//                        rule.setRuleContentInfoList(this.handExcelDataForRuleContent(ruleContents, userId));
                        rule.setTacticsOutputList(this.handExcelDataForRuleContent(ruleContents, userId));

                        rlist.add(rule);
                        rpCodeSet.add(rule.getCode() + "/" + rule.getParentId());
                        rpNameSet.add(rule.getName() + "/" + rule.getParentId());
                    }
                    map.put("existNameList", existNameList);
                    map.put("existCodeList", existCodeList);
                    map.put("rpCodeList", rpCodeList);
                    map.put("rpNameList", rpNameList);
                    map.put("folderNotExistList", folderNotExistList);
                    for (RuleVo rule : rlist) {
                        RuleVo vo = this.insertRuleInfo(rule);
                        if (vo != null) {
                            sucRows++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new UploadResponse(existRows, sucRows, repeatRows, failRows, total, map);
                }
            }
        }
        return new UploadResponse(existRows, sucRows, repeatRows, failRows, total, map);
    }

    @Override
    public List<String> queryFieldEnByRuleId(Long ruleId) {
        RuleInfo rule = this.getById(ruleId);
        if (rule == null) {
            return null;
        }
        Integer difficulty = rule.getDifficulty();
        //字段en
        Set<String> fieldEns = new HashSet<>();
        //字段id
        Set<Long> fieldIds = new HashSet<>();
        if (difficulty != null) {

            List<RuleFieldInfo> ruleFieldInfoList = ruleFieldInfoService.queryByRuleId(ruleId);
            if (ruleFieldInfoList != null && ruleFieldInfoList.size() > 0) {
                for (RuleFieldInfo ruleFieldInfo : ruleFieldInfoList) {
                    String fieldId = ruleFieldInfo.getFieldId();
                    if (fieldId != null && fieldId.contains("|")) {
                        fieldEns.add(ruleFieldInfo.getFieldId().split("\\|")[1]);
                    } else {
                        fieldIds.add(Long.valueOf(ruleFieldInfo.getFieldId()));
                    }
                }
            }
            for (Long fieldId : fieldIds) {
                String fieldName = fieldMapper.findFieldNameById(fieldId);
                fieldEns.add(fieldName);
            }
        }
        return new ArrayList<>(fieldEns);
    }


    //获取文件夹id
    private Long getFolderId(Map folder, Map<String, Long> cache, String folderName) {
        //没有文件夹名称则为0
        if (folderName == null || "".equals(folderName)) {
            return 0L;
        }
        //已经缓存则直接返回
        if (cache.containsKey(folderName)) {
            return cache.get(folderName);
        }
        String[] split = folderName.split("/");
        //类型转换：处理老表格式问题
        if (folder.get("name") != null && folder.get("name").equals(split[0]) && split.length > 1) {
            List<KnowledgeTree> children = JSON.parseArray(JSON.toJSONString(folder.get("children")), KnowledgeTree.class);

            //设置初始的父id为0；
            Long parentId = 0L;
            for (int i = 1; i < split.length; i++) {
                KnowledgeTree knowledgeTree = checkFolderId(children, split[i]);
                //未匹配成功，需要创建文件夹并且并且将children设置为null
                if (knowledgeTree == null || knowledgeTree.getChildren() == null) {
                    //todo 报错未完成创建文件夹，先报异常
                    children = null;
//                    throw new ApiException(ErrorCodeEnum.FOLDER_NOT_EXIST.getVersionCode(),folderName+"<-->文件夹不存在");
                    break;
                } else {//匹配成功直接替换
                    children = Arrays.asList(knowledgeTree.getChildren());
                    //记录父id，如果下次匹配失败则会根据这次返回的parentId来创建文件夹
                    parentId = knowledgeTree.getId();
                }
                //匹配到最后,返回id，并且将此文件夹名和对应id插入到cache中
                if (i == split.length - 1) {
                    cache.put(folderName, knowledgeTree.getId());
                    return knowledgeTree.getId();
                }
            }
        }
        return 0L;
    }

    //验证是否匹配到文件夹
    private KnowledgeTree checkFolderId(List<KnowledgeTree> folderList, String folderName) {
        if (folderList == null) {
            return null;
        }
        for (KnowledgeTree knowledgeTree : folderList) {
            if (folderName.equals(knowledgeTree.getName())) {
                return knowledgeTree;
            }
        }
        return null;
    }

    //确定最后一个逻辑字符
    private String checkLastLogical(String ruleFields) {
        String patternStr = "^(\\(+)";
        if (!ruleFields.matches(patternStr)) {
            return "-1";
        } else {
            String str = "(\\)+$)";
            Pattern pattern = Pattern.compile(str);
            Matcher matcher = pattern.matcher(ruleFields);
            if (matcher.find()) {
                return matcher.group(0);
            }
        }
        return "-1";
    }

    //删除条件中最后的括号部分
    private String removeLastLogical(String ruleFields, String lastLogical) {
        if (lastLogical != null && !"".equals(lastLogical) && !lastLogical.equals("-1")) {
            return ruleFields.substring(0, ruleFields.length() - lastLogical.length());
        }
        return ruleFields;
    }

    public List<RuleFieldInfo> handExcelDataForRuleField(String ruleFields, Long userId) {
        List<RuleFieldInfo> ruleFieldList = new ArrayList<RuleFieldInfo>();
        if (!StringUtil.isBlank(ruleFields)) {
            String patten = "(\\)*)(&&|\\|\\|)";
            ruleFields = ruleFields.replaceAll("\\n", "");
            ruleFields = ruleFields.replaceAll(patten, "\n$1$2");
            String[] field = ruleFields.trim().split("\n");
            for (int i = 0; i < field.length; i++) {
                String[] str = field[i].split("-");
                RuleFieldInfo ruleField = new RuleFieldInfo();
                ruleField.setLogical(str[0]);
                ruleField.setField(str[1]);
                ruleField.setOperator(str[2]);
                ruleField.setFieldValue(str[3]);
                Field fieldInfo = findByFieldCn(userId, ruleField.getField());
                ruleField.setFieldId(fieldInfo.getId() + "|" + fieldInfo.getFieldEn());
                ruleField.setValueType(fieldInfo.getValueType());

                ruleFieldList.add(ruleField);
            }
        }
        return ruleFieldList;
    }

    public List<TacticsOutput> handExcelDataForRuleContent(String ruleContents, Long userId) {
        List<TacticsOutput> tacticsOutputList = new ArrayList<TacticsOutput>();
        if (!StringUtil.isBlank(ruleContents)) {
            String[] content = ruleContents.trim().split("\n");
            for (int i = 0; i < content.length; i++) {
                String[] str = content[i].split("=");
                TacticsOutput tacticsOutput = new TacticsOutput();
                Field field = this.findByFieldCn(userId, str[0]);
                tacticsOutput.setFieldId(field.getId());
                tacticsOutput.setFieldEn(field.getFieldEn());
                String fieldValue = str[1];
                Integer variableType = 1;
                Pattern pattern = Pattern.compile("^(\\{)([\\s|\\S]+)(\\})$");
                Matcher matcher = pattern.matcher(fieldValue);
                if (matcher.find()) {
                    Field field1 = this.findByFieldCn(userId, matcher.group(2).trim());
                    fieldValue = field1.getId().toString() + "|" + field1.getFieldEn();
                    variableType = 2;
                }
                tacticsOutput.setFieldValue(fieldValue);
                tacticsOutput.setVariableType(variableType);
                tacticsOutput.setTacticsType("base_rule");
                tacticsOutputList.add(tacticsOutput);
            }
        }
        return tacticsOutputList;
    }

    public Field findByFieldCn(Long userId, String fieldCn) {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("userId", userId);
        fieldMap.put("fieldCn", fieldCn);
        Field field = fieldMapper.findByFieldCn(fieldMap);
        return field;
    }

}
