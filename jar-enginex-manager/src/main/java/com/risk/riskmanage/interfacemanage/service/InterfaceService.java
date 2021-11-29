package com.risk.riskmanage.interfacemanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.risk.riskmanage.common.model.requestParam.QueryListParam;
import com.risk.riskmanage.interfacemanage.model.InterfaceInfo;
import com.risk.riskmanage.interfacemanage.model.request.InterfaceListParam;
import com.risk.riskmanage.interfacemanage.model.vo.InterfaceVo;

public interface InterfaceService extends IService<InterfaceInfo> {
    InterfaceVo getInterfaceById(Long id);

//    Map<String, Object> getInterfaceList(PageDto pageDto);

    PageInfo queryByEntity( QueryListParam<InterfaceInfo> param);

    InterfaceVo inserInterfaceInfo(InterfaceVo interfaceVo);

    InterfaceVo updateInterfaceInfo(InterfaceVo interfaceVo);

    //更新接口
    boolean updateStatus(Long[] ids, Integer status);

    //删除接口
    Boolean deleteInterfaceInfo(InterfaceVo interfaceVo);

//    public PageBean<InterfaceVo> pageQuery(int currentPage, int pageSize);

    //http请求
    String getHttpResponse(InterfaceInfo interfaceInfo);
}
