package com.ddnotes.oa.dao;

import com.ddnotes.oa.entity.ProcessFlow;
import com.ddnotes.oa.utils.MybatisUtils;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.*;

public class ProcessFlowDaoTest {

    @Test
    public void insert() {
        MybatisUtils.executeUpdate(sqlSession -> {
            ProcessFlowDao dao = sqlSession.getMapper(ProcessFlowDao.class);
            ProcessFlow processFlow = new ProcessFlow();
            processFlow.setFormId(3l);
            processFlow.setOperatorId(2l);
            processFlow.setAction("audit");
            processFlow.setResult("approved");
            processFlow.setReason("approved");
            processFlow.setCreateTime(new Date());
            processFlow.setAuditTime(new Date());
            processFlow.setOrderNo(1);
            processFlow.setState("ready");
            processFlow.setIsLast(1);
            dao.insert(processFlow);
            return null;
        });
    }
}