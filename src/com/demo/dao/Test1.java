package com.demo.dao;

import io.buji.pac4j.ClientRealm;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.entity.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {

    @Test
    public void addUser() {
        UserInfo user = new UserInfo();
        user.setPassword("123456");
        user.setUsername("test1");

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
            SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
            Session currentSession = sessionFactory.openSession();
            Transaction txTransaction = currentSession.beginTransaction();

            currentSession.save(user);
            txTransaction.commit();

            System.out.println("addUser");
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deploymentACT() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml")
                .buildProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("流程测试")
                .addClasspathResource("process/process1.bpmn")
                .deploy();

        System.out.println("部署Id：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
    }

    @Test
    public void startProcess() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml")
                .buildProcessEngine();
        Map<String, Object> variables = new HashMap<>();
        variables.put("userId", "task");
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey("myProcess", variables);//使用流程定义的key的最新版本启动流程
        System.out.println("流程实例ID：" + pi.getId());
        System.out.println("流程定义的ID：" + pi.getProcessDefinitionId());
    }

    @Test
    public void completeTask() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml")
                .buildProcessEngine();
        //任务ID
        String taskId = "110005";
        //完成任务的同时，设置流程变量，让流程变量判断连线该如何执行
        Map<String, Object> variables = new HashMap<>();
        variables.put("money", 200);
        processEngine.getTaskService().complete(taskId, variables);
        System.out.println("完成任务：" + taskId);
    }

    @Test
    public void step2manager() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml")
                .buildProcessEngine();
        System.out.println("***************审批流程开始***************");
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("usertask2").list();// 审批人的任务
        System.out.println("审批人的任务数量：" + tasks.size());

        System.out.println("***************审批流程结束***************");
    }

    @Test
    public void findPersonalTaskList() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml")
                .buildProcessEngine();
        //任务办理人
        String assignee = "task3";
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)//个人任务的查询
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID：" + task.getId());
                System.out.println("任务的办理人：" + task.getAssignee());
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务的创建时间：" + task.getCreateTime());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("#######################################");
            }
        }else{
        	System.out.println("#######################################");
        }
    }

    @Test
    public void findHisActivitiList() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml")
                .buildProcessEngine();
        String processInstanceId = "80001";
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                //.processInstanceId(processInstanceId)
                .taskAssignee("task")
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricActivityInstance hai : list) {
                System.out.println("任务ID:"+hai.getId());  
                System.out.println("流程实例ID:"+hai.getProcessInstanceId());  
                System.out.println("执行人："+hai.getAssignee());  
                System.out.println("创建时间："+hai.getStartTime());  
                System.out.println("结束时间："+hai.getEndTime());  
                System.out.println("流程名："+hai.getActivityName()); 
                System.out.println("===========================");
            }
        }
    }

    @Test
    public void findHisVariablesList() {
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml")
                .buildProcessEngine();
        String processInstanceId = "80001";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println("任务ID:"+hvi.getId());  
                System.out.println("流程实例ID:"+hvi.getProcessInstanceId());  
                System.out.println("执行人："+hvi.getVariableName());  
                System.out.println("创建时间："+hvi.getCreateTime());  
                System.out.println("结束时间："+hvi.getLastUpdatedTime());  
                System.out.println("===========================");
            }
        }
    }
}
