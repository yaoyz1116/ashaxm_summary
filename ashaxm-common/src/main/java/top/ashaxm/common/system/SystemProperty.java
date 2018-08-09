package top.ashaxm.common.system;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 初始化配置文件，在ServiceSystemProperty文件中加载
 * @author yaoyz
 * 2018年1月8日
 */
public class SystemProperty {
	private static Properties props = null;
	
	public static String FILE_PATH = null;
	public static String SERVER = null;
	public static String WEBAPP = null;

    public static void init(){
    	Resource resource = new ClassPathResource("/application.properties");
    	try{
    		System.out.println(resource.getFile().getAbsolutePath());
    	}catch(Exception err){
    		err.printStackTrace();
    	}
    	try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			SystemProperty.FILE_PATH = props.getProperty("config.filepath");
			SystemProperty.SERVER = props.getProperty("config.server");
			SystemProperty.WEBAPP = props.getProperty("config.webapp");
			System.out.println("SystemProperty.FILE_PATH——————————————————————————"+SystemProperty.FILE_PATH);
			System.out.println("SystemProperty.SERVER——————————————————————————"+SystemProperty.SERVER);
			System.out.println("SystemProperty.WEBAPP————————————————————————————"+SystemProperty.WEBAPP);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
