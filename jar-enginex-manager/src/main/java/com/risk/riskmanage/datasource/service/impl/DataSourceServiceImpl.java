package com.risk.riskmanage.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.datasource.mapper.DataSourceMapper;
import com.risk.riskmanage.datasource.model.DataSource;
import com.risk.riskmanage.datasource.model.request.DataSourceListParam;
import com.risk.riskmanage.datasource.model.vo.DataSourceVo;
import com.risk.riskmanage.datasource.service.DataSourceService;
import com.risk.riskmanage.system.mapper.UserMapper;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

    @Resource
    public DataSourceMapper dataSourceMapper;

    @Resource
    public UserMapper userMapper;

    @Override
    public Integer saveDataSource(DataSourceVo dataSourceVo) {
        User user = SessionManager.getLoginAccount();
        Long organId = user.getOrganId();
        DataSource dataSource = new DataSource();
        BeanUtils.copyProperties(dataSourceVo, dataSource);
        dataSource.setCreator(user.getUserId().intValue());
        dataSource.setModifier(user.getUserId().intValue());
        dataSource.setOrganId(organId.intValue());
        return dataSourceMapper.insert(dataSource);
    }

    @Override
    public Integer updateDataSource(DataSourceVo dataSourceVo) {
        User user = SessionManager.getLoginAccount();
        DataSource dataSource = new DataSource();
        BeanUtils.copyProperties(dataSourceVo, dataSource);
        dataSource.setModifier(user.getUserId().intValue());
        return dataSourceMapper.updateById(dataSource);
    }

    @Override
    public DataSourceVo getDataSourceById(Integer id) {
        DataSource dataSource = dataSourceMapper.selectById(id);
        DataSourceVo dataSourceVo = new DataSourceVo();
        BeanUtils.copyProperties(dataSource, dataSourceVo);
        User creator = userMapper.findUserById(dataSource.getCreator());
        User modifier = userMapper.findUserById(dataSource.getModifier());
        dataSourceVo.setCreatorName(creator.getAccount());
        dataSourceVo.setModifierName(modifier.getAccount());
        return dataSourceVo;
    }

    @Override
    public Map<String, Object> getDataSourceList(DataSourceListParam param) {
        Map<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<DataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataSource::getStatus, 1);
        if(param.getTypeList()!=null&&!param.getTypeList().isEmpty()){
            queryWrapper.in(DataSource::getType, param.getTypeList());
        }
        queryWrapper.orderByDesc(DataSource::getUpdateTime);
        if (param.getPageNo()>0 && param.getPageSize()>0){
            PageHelper.startPage(param.getPageNo(), param.getPageSize());
        }
        List<DataSource> dataSourceList = dataSourceMapper.selectList(queryWrapper);
        PageInfo pageInfo = new PageInfo<>(dataSourceList);
        pageInfo.setList(null);
        result.put("pager", pageInfo);

        List<DataSourceVo> dataSourceVoList = new ArrayList<>();
        for (DataSource dataSource : dataSourceList) {
            DataSourceVo dataSourceVo = new DataSourceVo();
            BeanUtils.copyProperties(dataSource, dataSourceVo);
            User creator = userMapper.findUserById(dataSource.getCreator());
            User modifier = userMapper.findUserById(dataSource.getModifier());
            dataSourceVo.setCreatorName(creator.getAccount());
            dataSourceVo.setModifierName(modifier.getAccount());
            dataSourceVoList.add(dataSourceVo);
        }
        result.put("data", dataSourceVoList);

        return result;
    }

    @Override
    public Integer deleteDataSourceById(Integer id) {
        DataSource dataSource = new DataSource();
        dataSource.setId(id);
        dataSource.setStatus(0);
        return dataSourceMapper.updateById(dataSource);
    }

}
