package com.ddnotes.oa.service;

import com.ddnotes.oa.dao.EmployeeDao;
import com.ddnotes.oa.entity.Employee;
import com.ddnotes.oa.utils.MybatisUtils;

/**
 * 员工服务
 */
public class EmployeeService {
    /**
     * 按编号查找员工
     * @param employeeId 员工编号
     * @return 员工对象,不存在时返回null
     */
    public Employee selectById(Long employeeId){
        return (Employee)MybatisUtils.executeQuery(sqlSession -> {
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            return employeeDao.selectById(employeeId);
        });
    }
}

