package com.edu.spring.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{
	
	//该回调方法先于postProcessBean、init方法执行
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("=========postProcessBeanFactory=========");
	}
	
	/*=========postProcessBeanFactory=========
			五月 10, 2018 8:51:39 下午 org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor <init>
			信息: JSR-330 'javax.inject.Inject' annotation found and supported for autowiring
			----------postProcessBeforeInitialization----------
			----------postProcessAfterInitialization----------
			----------postProcessBeforeInitialization----------
			----------postProcessAfterInitialization----------
			----------postProcessBeforeInitialization----------
			----------postProcessAfterInitialization----------
			----------postProcessBeforeInitialization----------
			----------postProcessAfterInitialization----------
			----------postProcessBeforeInitialization----------
			----------postProcessAfterInitialization----------
			----------postProcessBeforeInitialization----------
			----------postProcessAfterInitialization----------
			----------postProcessBeforeInitialization----------
			----------postProcessAfterInitialization----------
			before show ...
			User: null
			after show ...
			com.edu.spring.spring.LogUser@77a57272
			User: class org.springframework.context.annotation.AnnotationConfigApplicationContext*/
}
