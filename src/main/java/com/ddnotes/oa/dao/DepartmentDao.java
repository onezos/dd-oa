package com.ddnotes.oa.dao;

import com.ddnotes.oa.entity.Department;

public interface DepartmentDao {
    public Department selectById(Long departmentId);
}
