
package com.risk.riskmanage.system.service.impl;

import java.util.List;

import com.risk.riskmanage.system.service.DepartmentService;
import org.springframework.stereotype.Service;

import com.risk.riskmanage.common.basefactory.BaseService;
import com.risk.riskmanage.system.model.Department;

/**
 * ClassName:DepartmentServiceImpl <br/>
 * Description: TODO ADD FUNCTION. <br/>
 */
@Service
public class DepartmentServiceImpl extends BaseService implements DepartmentService {
	
	@Override
	public List<Department> getDeptList(Department department) {
		return departmentMapper.selectByExample(department);
	}

	@Override
	public int getDeptCount(Department department) {
		return departmentMapper.countByExample(department);
	}

	@Override
	public void insertDept(Department department) {
		departmentMapper.insertSelective(department);
	}

	@Override
	public void updateDept(Department department) {
		departmentMapper.updateByPrimaryKeySelective(department);
	}

	@Override
	public boolean isExistByDeptName(String deptName,Long deptId) {
		 if(deptName != null && !("".equals(deptName))){
			 Department department = new Department();
			 department.setId(deptId);
			 department.setDeptName(deptName);
			 int rowCount=departmentMapper.isExist(department);
			 if(rowCount>0){
				 return true;
			 }
		 }
		return false;
	}

	@Override
	public boolean isExistByDeptCode(String deptCode,Long deptId) {
		 if(deptCode != null && !("".equals(deptCode))){
			 Department department = new Department();
			 department.setDeptCode(deptCode);
			 department.setId(deptId);
			 int rowCount=departmentMapper.isExist(department);
			 if(rowCount>0){
				 return true;
			 }
		 }
		return false;
	}

	@Override
	public void deleteDept(Long []deletIds) {
		departmentMapper.deleteDept(deletIds);
	}

}

