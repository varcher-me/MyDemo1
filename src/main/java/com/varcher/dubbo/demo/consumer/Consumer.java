package com.varcher.dubbo.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.varcher.dubbo.demo.DemoService;

public class Consumer{


	 
    public static void main(String[] args) throws Exception {
    	int inputCharacter;
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/varcher/dubbo/demo/consumer/dubboConsumer.xml");
        context.start();
 
        DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理
 
        do
        {
	        System.out.println( demoService.sayHello("Alpha") ); 
	        System.out.println( demoService.sayHello("Beta") ); 
	        System.out.println( demoService.sayHello("Gammar") ); 
	        System.out.println( demoService.sayBye("Omega") ); 
        
	        inputCharacter	=	System.in.read();
	        
        }	while	(99 != inputCharacter ) ;// 按任意键退出
    }
 

}
