package com.varcher.dubbo.demo.provider;
import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Provider {

	public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("com/varcher/dubbo/demo/provider/dubboProvider.xml");
        context.start();
 
        System.in.read(); // 按任意键退出
	}

}
