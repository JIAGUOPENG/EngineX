
package com.risk.riskmanage.system.mapper;

import com.risk.riskmanage.common.mapper.BaseMapper;
import com.risk.riskmanage.system.model.Department;

/**
 * ClassName:DepartmentMapper <br/>
 * Description: 部门mapper. <br/>
 * @see 	 
 */
public interface DepartmentMapper extends BaseMapper<Department>  {
    
	/**
	 * isExist:(根据相应的条件判断是否存在重复值). <br/>
	 * @return 返回行数
	 */
	Integer isExist(Department department);
	
	/**
	 * deleteDept:(根据部门ids删除部门信息). <br/>
	 */
	void deleteDept(Long []deletIds);
}

