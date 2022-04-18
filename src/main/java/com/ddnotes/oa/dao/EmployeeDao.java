package com.ddnotes.oa.dao;

import com.ddnotes.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeDao {
    public Employee selectById(Long employeeId);
    public Employee selectLeader(@Param("emp") Employee employee);
}
