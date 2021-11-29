
package com.risk.riskmanage.system.service;

import java.util.List;

import com.risk.riskmanage.system.model.Department;

/**
 * ClassName:DepartmentService <br/>
 * Description: 部门service接口. <br/>
 */
public interface DepartmentService {
	
	/**
	 * getDeptList:(根据相应条件查询部门信息列表). <br/>
	 * @author wz
	 * @param departmentVo 
	 * @return 部门信息列表
	 */
	List<Department> getDeptList(Department department);
	
	/**
	 * getDeptCount:(根据相应条件返回记录行数). <br/>
	 * @author wz
	 * @param departmentVo 
	 * @return 返回部门记录行数
	 */	
	int getDeptCount(Department department);
	
	/**
	 * insertDept:(新增部门记录). <br/>
	 * @author wz
	 * @param departmentVo 
	 */	
	void insertDept(Department department);
	
	/**
	 * updateDept:(新增部门记录). <br/>
	 * @author wz
	 * @param departmentVo 
	 */	
	void updateDept(Department department);	
	
	/**
	 * isExistByDeptName:(判断是否存在相同部门名称). <br/>
	 * @author wz
	 * @param deptName 部门名称
	 * @param deptId 部门主键id
	 * @return true为存在相同的部门名称
	 */
	boolean isExistByDeptName(String deptName,Long deptId);
	
	/**
	 * isExistByDeptCode:(判断是否存在相同部门编号). <br/>
	 * @author wz
	 * @param deptCode 部门编号
	 * @param deptId 部门主键id
	 * @return true为存在相同的部门编号
	 */
	boolean isExistByDeptCode(String deptCode,Long deptId);
	
	/**
	 * deleteDept:(根据部门ids删除部门信息). <br/>
	 * @author wz
	 * @param deletIds 部门ids
	 */
	void deleteDept(Long []deletIds );

}

