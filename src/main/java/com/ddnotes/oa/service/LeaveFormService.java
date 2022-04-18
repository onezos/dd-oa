package com.ddnotes.oa.service;

import com.ddnotes.oa.dao.EmployeeDao;
import com.ddnotes.oa.dao.LeaveFormDao;
import com.ddnotes.oa.dao.ProcessFlowDao;
import com.ddnotes.oa.entity.Employee;
import com.ddnotes.oa.entity.LeaveForm;
import com.ddnotes.oa.entity.ProcessFlow;
import com.ddnotes.oa.utils.MybatisUtils;

import java.util.Date;

/**
 * 请假流程服务
 */
public class LeaveFormService {
    /**
     * 创建请假单
     * @param form 前端输入的请假单数据
     * @return 持久化后的请假单对象
     */
    public LeaveForm createLeaveForm(LeaveForm form){
        LeaveForm saveForm = (LeaveForm)MybatisUtils.executeUpdate(sqlSession -> {
            //1.持久化form表单数据，8级一下员工表单状态为processing，8级状态为approved
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            Employee employee = employeeDao.selectById(form.getEmployeeId());
            if(employee.getLevel() == 8){
                form.setState("approved");
            }else{
                form.setState("processing");
            }
            LeaveFormDao leaveFormDao = sqlSession.getMapper(LeaveFormDao.class);
            leaveFormDao.insert(form);
            //2.增加第一条流程数据，说明表单已提交，状态为complete
            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);
            ProcessFlow flow1 = new ProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(employee.getEmployeeId());
            flow1.setAction("apply");
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            flow1.setState("complete");
            flow1.setIsLast(0);
            processFlowDao.insert(flow1);
            //3.分情况创建企业流程数据
            //3.1 7级以下员工，生成部门经理审批任务，请假时间大于36小时，还需生成总经理审批任务

            if(employee.getLevel() < 7){
                Employee dmanager = employeeDao.selectLeader(employee);
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(dmanager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                flow2.setState("process");
                Long diff = form.getEndTime().getTime() - form.getStartTime().getTime();
                float hours = diff/(1000*60*60) * 1f;
                if(hours >= BussinessConstants.MANAGER_AUDIT_HOUR){
                    flow2.setIsLast(0);
                    processFlowDao.insert(flow2);
                    Employee manager = employeeDao.selectLeader(dmanager);
                    ProcessFlow flow3 = new ProcessFlow();
                    flow3.setFormId(form.getFormId());
                    flow3.setOperatorId(manager.getEmployeeId());
                    flow3.setAction("audit");
                    flow3.setCreateTime(new Date());
                    flow3.setOrderNo(3);
                    flow3.setState("ready");
                    flow3.setIsLast(1);
                    processFlowDao.insert(flow3);
                }else{
                    flow2.setIsLast(1);
                    processFlowDao.insert(flow2);
                }
            }else if(employee.getLevel() == 7){
                //3.2 7级员工，生成总经理审批任务
                Employee manager = employeeDao.selectLeader(employee);
                ProcessFlow flow = new ProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(manager.getEmployeeId());
                flow.setAction("audit");
                flow.setCreateTime(new Date());
                flow.setOrderNo(2);
                flow.setState("process");
                flow.setIsLast(1);
                processFlowDao.insert(flow);
            } else if(employee.getLevel() == 8){
                //3.3 8级员工，生成总经理审批任务，系统自动通过
                ProcessFlow flow = new ProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(employee.getEmployeeId());
                flow.setAction("audit");
                flow.setResult("approved");
                flow.setReason("auto pass");
                flow.setCreateTime(new Date());
                flow.setAuditTime(new Date());
                flow.setOrderNo(2);
                flow.setState("complete");
                flow.setIsLast(1);
                processFlowDao.insert(flow);
            }
            return form;
        });
        return  saveForm;
    }
}
