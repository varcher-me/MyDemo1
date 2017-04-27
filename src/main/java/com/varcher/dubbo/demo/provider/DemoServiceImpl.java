package com.varcher.dubbo.demo.provider;

import com.varcher.dubbo.demo.DemoService;

public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {
		return "Greetings, " + name;
	}

	public String sayBye(String name) {
		return "Bye bye, " + name;
	}

}
