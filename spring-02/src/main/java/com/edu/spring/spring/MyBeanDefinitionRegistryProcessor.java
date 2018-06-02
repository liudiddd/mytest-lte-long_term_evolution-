package com.edu.spring.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryProcessor implements BeanDefinitionRegistryPostProcessor{
	
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		for(int i = 0; i < 10; i++) {
			BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(Person.class);
			bdb.addPropertyValue("name", "admin" + i);
			registry.registerBeanDefinition("person" + i, bdb.getBeanDefinition());
		}
	}
	
	
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}
}
