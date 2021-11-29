package com.risk.riskmanage.interfacemanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.common.model.requestParam.QueryListParam;
import com.risk.riskmanage.interfacemanage.mapper.InterfaceMapper;
import com.risk.riskmanage.interfacemanage.model.InterfaceInfo;
import com.risk.riskmanage.interfacemanage.model.request.InterfaceListParam;
import com.risk.riskmanage.interfacemanage.model.vo.InterfaceVo;
import com.risk.riskmanage.interfacemanage.service.InterfaceService;
import com.risk.riskmanage.system.mapper.UserMapper;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterfaceServiceImpl extends ServiceImpl<InterfaceMapper,InterfaceInfo> implements InterfaceService {
    @Resource
    InterfaceMapper interfaceMapper;

    @Resource
    UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${runner.url}")
    private String runnerUrl;

    @Override
    public String getHttpResponse(InterfaceInfo interfaceInfo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置请求头
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // 封装请求体
        JSONObject body = JSONObject.parseObject(JSONObject.toJSONString(interfaceInfo));
        // 封装参数和头信息
        HttpEntity<JSONObject> httpEntity = new HttpEntity(body, httpHeaders);
        String url = runnerUrl + "/manager/invokeInterface";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        return responseEntity.getBody();
    }

    @Override
    public Boolean deleteInterfaceInfo(InterfaceVo interfaceVo) {
        interfaceMapper.deleteById(interfaceVo.getId());
        return true;
    }

    @Override
    public InterfaceVo updateInterfaceInfo(InterfaceVo interfaceVo) {
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceVo,interfaceInfo);
        User user = SessionManager.getLoginAccount();
        //设置创建者和修改者id
        interfaceInfo.setModifier(user.getUserId());
        interfaceMapper.updateById(interfaceInfo);
        return interfaceVo;
    }

    @Override
    @Transactional
    public boolean updateStatus(Long[] ids, Integer status) {
        int updateNum = interfaceMapper.updateStatus(ids, status);
        if (updateNum>0){
            return true;
        }
        return false;
    }

    //添加接口信息
    @Override
    public InterfaceVo inserInterfaceInfo(InterfaceVo interfaceVo) {
        //拷贝VO到Info对象
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceVo, interfaceInfo);
        User user = SessionManager.getLoginAccount();
        //设置创建者和修改者id
        interfaceInfo.setCreator(user.getUserId());
        interfaceInfo.setModifier(user.getUserId());
        interfaceInfo.setOrganId(user.getOrganId());
        interfaceInfo.setStatus(1);
        //插入并获取insert后实体对象返回id
        boolean save = this.save(interfaceInfo);
        if (!save) {
            throw new ApiException(ErrorCodeEnum.INTERFACE_SAVE_ERROR.getCode(), ErrorCodeEnum.INTERFACE_SAVE_ERROR.getMessage());
        }
        return interfaceVo;
    }

    @Override
    public PageInfo queryByEntity(QueryListParam<InterfaceInfo>  interfaceListParam) {
         InterfaceInfo interfaceInfo = interfaceListParam.getEntity();


        Integer pageNo = interfaceListParam.getPageNum();
        Integer pageSize = interfaceListParam.getPageSize();
        if (pageNo > 0 && pageSize > 0) {
            PageHelper.startPage(pageNo, pageSize);
        }
        Wrapper<InterfaceInfo> wrapper = createWrapper(interfaceListParam.getEntity());
        List<InterfaceInfo> interfaceList = interfaceMapper.selectList(wrapper);
        PageInfo pageInfo = new PageInfo(interfaceList);


//        级联操作完成拼装
        List<InterfaceVo> interfaceVos = new ArrayList<>();

        User user = SessionManager.getLoginAccount();
        for(InterfaceInfo info: interfaceList){
            InterfaceVo interfaceVo = new InterfaceVo();
            BeanUtils.copyProperties(info,interfaceVo);
            interfaceVo.setUsername(user.getNickName());
            //设置创建者昵称
            interfaceVo.setCreatorName(userMapper.findNickNameById(info.getCreator()));
            //设置修改者昵称
            interfaceVo.setModifierName(userMapper.findNickNameById(info.getModifier()));
            interfaceVos.add(interfaceVo);
        }
        pageInfo.setList(interfaceVos);


        return pageInfo;
    }

    @Override
    public InterfaceVo getInterfaceById(Long id) {
        InterfaceInfo info = interfaceMapper.selectById(id);
        InterfaceVo interfaceVo = new InterfaceVo();
        BeanUtils.copyProperties(info,interfaceVo);
        User user = SessionManager.getLoginAccount();
        interfaceVo.setUsername(user.getNickName());
        //设置创建者昵称
        interfaceVo.setCreatorName(userMapper.findNickNameById(info.getCreator()));
        //设置修改者昵称
        interfaceVo.setModifierName(userMapper.findNickNameById(info.getModifier()));
        return interfaceVo;
    }

    //新插入数据的准备工作
//    private InterfaceVo initParam(InterfaceVo interfaceVo) {
//        //加入用户信息
//        User user = SessionManager.getLoginAccount();
//        interfaceVo.setOrganId(user.getOrganId());
//        interfaceVo.setUsername(user.getNickName());
//
//        return interfaceVo;
//    }

    //唯一性检查
    private boolean checkUniqueness(InterfaceVo vo) {
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setName(vo.getName());
        InterfaceInfo info = this.getOne(new QueryWrapper<>(interfaceInfo));
        if (info != null) {
            throw new ApiException(ErrorCodeEnum.INTERFACE_NAME_REPEAT.getCode(), ErrorCodeEnum.INTERFACE_NAME_REPEAT.getMessage());
        }
        return true;
    }

    //创建查询器
    private Wrapper<InterfaceInfo> createWrapper(InterfaceInfo query){
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        if (query!=null){
            if (StringUtils.isNotBlank(query.getName())){
                wrapper.like(InterfaceInfo::getName,query.getName());
            }
            if (StringUtils.isNotBlank(query.getMethod())){
                wrapper.eq(InterfaceInfo::getMethod,query.getMethod());
            }
            if (StringUtils.isNotBlank(query.getUrl())){
                wrapper.eq(InterfaceInfo::getUrl,query.getUrl());
            }
            if (query.getStatus()!=null){
                wrapper.eq(InterfaceInfo::getStatus,query.getStatus());
            }else {
                wrapper.ne(InterfaceInfo::getStatus,-1);
            }
        }else {
            wrapper.ne(InterfaceInfo::getStatus,-1);
        }
        wrapper.orderByDesc(InterfaceInfo::getId);
        return wrapper;
    }

}
