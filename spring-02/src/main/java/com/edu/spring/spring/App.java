package com.edu.spring.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = 
        		new AnnotationConfigApplicationContext("com.edu");
        
        context.getBean("user", User.class).show();
        System.out.println(context.getBean("createUser"));
        
        context.getBean("book", Book.class).show();
        
        //context.getBean("bank", Bank.class).show();
        
        context.getBean("echoBeanPostProcessor");
        
        //利用BeanDefinitionRegistryProcessor接口动态向Spring容器中添加bean
        System.out.println(context.getBean("person1", Person.class).getName());
    }
}
