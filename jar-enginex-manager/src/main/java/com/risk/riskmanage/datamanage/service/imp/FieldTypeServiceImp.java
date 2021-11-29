package com.risk.riskmanage.datamanage.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.risk.riskmanage.datamanage.model.FieldType;
import com.risk.riskmanage.datamanage.model.request.FieldTreeParam;
import com.risk.riskmanage.util.SessionManager;
import org.springframework.stereotype.Service;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.datamanage.service.FieldTypeService;

import javax.annotation.Resource;

@Service
public class FieldTypeServiceImp extends BaseService implements
		FieldTypeService {
	@Resource

	@Override
	public List<FieldType> getFieldTypeList(Map<String, Object> paramMap) {
		return fieldTypeMapper.getFieldTypeList(paramMap);
	}

	@Override
	public boolean createFieldType(FieldType fieldTypeVo,
			Map<String, Object> paramMap) {
		// 检查字段类型是否存在
		//if (fieldTypeMapper.findIdByFieldType(paramMap) == 0) {
			if (fieldTypeMapper.createFieldType(fieldTypeVo)) {
				paramMap.put("fieldTypeId", fieldTypeVo.getId());
				if (fieldTypeUserMapper.createFieldTypeUserRel(paramMap)) {
					return true;
				} else
					return false;
			} else
				return false;
//		} else
//			return false;
	}

	@Override
	public boolean updateFieldType(FieldTreeParam param) {
		param.setOrganId(SessionManager.getLoginAccount().getOrganId());
		param.setUserId(SessionManager.getLoginAccount().getUserId());
		fieldTypeMapper.updateFieldType(param);
		fieldTypeUserMapper.updateFieldTypeUserRel(param);
		return true;
	}

	@Override
	public FieldType findFieldTypeById(Map<String, Object> paramMap) {
		return fieldTypeMapper.findFieldTypeById(paramMap);
	}

	@Override
	public String findNodeIds(Map<String, Object> paramMap) {
		return fieldTypeUserMapper.findNodeIds(paramMap);
	}

	@Override
	public List<FieldType> findFieldType(Map<String, Object> paramMap) {
		return fieldTypeMapper.findFieldType(paramMap);
	}
	@Override
	public List<FieldType> getTreeList(FieldTreeParam param) {
		param.setOrganId(SessionManager.getLoginAccount().getOrganId());
		param.setUserId(SessionManager.getLoginAccount().getUserId());
		List<FieldType> fieldTypes = fieldTypeMapper.selectFieldTypeList(param);
		List<FieldType> collect = fieldTypes.stream().filter(fieldType -> fieldType.getParentId() == 0).collect(Collectors.toList());
		for (FieldType fieldType : collect) {
			fieldType.setChildren(this.assembleTreeList(fieldTypes,fieldType));
		}
		return collect;
	}

	private FieldType[] assembleTreeList(List<FieldType> fieldTypes,FieldType root){
		List<FieldType> children = new ArrayList();
		for (FieldType fieldType : fieldTypes) {
			if (fieldType.getParentId().equals(root.getId())){
				fieldType.setChildren(this.assembleTreeList(fieldTypes,fieldType));
				children.add(fieldType);
			}
		}
		if (children.size()==0){
			return new FieldType[0];
		}
		return children.toArray( new FieldType[children.size()]);
	}
}
