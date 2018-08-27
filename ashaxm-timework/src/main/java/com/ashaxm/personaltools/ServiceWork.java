package com.ashaxm.personaltools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.ashaxm.personaltools.miniApp.WxProperty;
import com.ashaxm.personaltools.personalTool.ScanTaskPersonal;;


public class ServiceWork {
	private static Logger logger = Logger.getLogger(ServiceWork.class);

	public static void main(String[] args) {
		//读取配置文件
		String path = System.getProperty("user.dir");
		PropertyConfigurator.configure(path + "/config/log4j.properties");

		System.setProperty("com.mchange.v2.c3p0.cfg.xml", path+ "/config/c3p0-config.xml");

		System.out.println("user.dir = " + System.getProperty("user.dir"));
		System.out.println("resource = " + ServiceWork.class.getClassLoader().getResource(""));
		
		try {
			WxProperty.init();
			ScheduledExecutorService reportTaskPool = Executors.newScheduledThreadPool(2);
			reportTaskPool.scheduleWithFixedDelay(new ScanTaskPersonal(), 0, 30, TimeUnit.SECONDS);		
		} catch (Exception err) {
			logger.error(err);
		}
	}

}
