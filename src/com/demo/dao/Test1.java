package com.demo.dao;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.entity.UserInfo;

public class Test1 {
	private static ApplicationContext context = null;

	@Test
	public void addUser() {

		UserInfo user = new UserInfo();
		user.setPassword("123456");
		user.setUsername("test1");

		try {
			context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
			SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
			Session currentSession = sessionFactory.openSession();
			Transaction txTransaction=currentSession.beginTransaction();

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
				.addClasspathResource("process/leave.bpmn")
				.deploy();

		System.out.println("部署Id：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
}
