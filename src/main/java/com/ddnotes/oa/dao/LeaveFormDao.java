package com.ddnotes.oa.dao;

import com.ddnotes.oa.entity.LeaveForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaveFormDao {
    public void insert(LeaveForm leaveForm);
    public List<Map> selectByParams(@Param("pf_state") String pfState, @Param("pf_operator_id") Long operatorId);
    public LeaveForm selectById(Long formId);
    public void update(LeaveForm form);
}
