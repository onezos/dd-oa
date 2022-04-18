package com.ddnotes.oa.dao;

import com.ddnotes.oa.entity.LeaveForm;
import com.ddnotes.oa.utils.MybatisUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class LeaveFormDaoTest {
    @Test
    public void testInsert(){
        MybatisUtils.executeUpdate(sqlSession -> {
            LeaveFormDao dao = sqlSession.getMapper(LeaveFormDao.class);
            LeaveForm leaveForm = new LeaveForm();
            leaveForm.setEmployeeId(4l);//员工编号
            leaveForm.setFormType(1);//事假
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = null;//起始时间
            Date endTime = null;//结束时间
            try {
                startTime = sdf.parse("2022-03-25 08:00:00");
                endTime = sdf.parse("2022-04-01 18:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            leaveForm.setStartTime(startTime);
            leaveForm.setEndTime(endTime);
            leaveForm.setReason("vacation");//请假事由
            leaveForm.setCreateTime(new Date());//创建时间
            leaveForm.setState("processing");//当前状态
            dao.insert(leaveForm);
            return null;
        });
    }
}