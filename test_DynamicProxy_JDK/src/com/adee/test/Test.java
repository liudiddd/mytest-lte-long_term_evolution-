package com.adee.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
	public interface IHello{
        void sayHello();
        String getHello(String name);
    }
    static class Hello implements IHello{
        public void sayHello() {
            System.out.println("Hello world!!");
        }
        @Override
        public String getHello(String name) {
        	return "hello " + name;
        }
    }
    //�Զ���InvocationHandler
    static  class HWInvocationHandler implements InvocationHandler{
        //Ŀ�����
        private Object target;
        public HWInvocationHandler(Object target){
            this.target = target;
        }
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("------����ǰ��֪ͨ����-------------");
            //ִ����Ӧ��Ŀ�귽��
            Object rs = method.invoke(target,args);
            System.out.println("------������ô������-------------");
            return rs;
        }
    }
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //����$Proxy0��class�ļ�
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //��ȡ��̬������
        Class proxyClazz = Proxy.getProxyClass(IHello.class.getClassLoader(),IHello.class);
        //��ô�����Ĺ��캯�����������������InvocationHandler.class
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);
        //ͨ�����캯����������̬������󣬽��Զ����InvocationHandlerʵ������
        IHello iHello = (IHello) constructor.newInstance(new HWInvocationHandler(new Hello()));
        //ͨ������������Ŀ�귽��
        iHello.sayHello();
    }
}
