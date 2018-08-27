package top.ashaxm.service.system;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import top.ashaxm.common.system.SystemProperty;

/**
 * webxml启动的时候加载的类
 * @author yaoyz
 * 2018年1月8日
 */
public class ServiceSystemProperty extends SystemProperty {
	
	private static Properties props = null;
	
	public static String MOB_APPKEY = "4db0dfe1b752";

    public static void init(){
    	SystemProperty.init();
    	
    	Resource resource = new ClassPathResource("/application.properties");
    	try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			ServiceSystemProperty.MOB_APPKEY = props.getProperty("mob.appkey");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
