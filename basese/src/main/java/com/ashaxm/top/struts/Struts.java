package com.ashaxm.top.struts;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Struts {
	
	private static HashMap<String, Object> map;

    public static View runAction(String actionName, Map<String,String> parameters) {

        /*
		0. 读取配置文件struts.xml
 		
 		1. 根据actionName找到相对应的class ， 例如LoginAction,   通过反射实例化（创建对象）
		据parameters中的数据，调用对象的setter方法， 例如parameters中的数据是 
		("name"="test" ,  "password"="1234") ,     	
		那就应该调用 setName和setPassword方法
		
		2. 通过反射调用对象的exectue 方法， 并获得返回值，例如"success"
		
		3. 通过反射找到对象的所有getter方法（例如 getMessage）,  
		通过反射来调用， 把值和属性形成一个HashMap , 例如 {"message":  "登录成功"} ,  
		放到View对象的parameters
		
		4. 根据struts.xml中的 <result> 配置,以及execute的返回值，  确定哪一个jsp，  
		放到View对象的jsp字段中。
        
        */
    	//读取xml文件，获取对应的map
    	map =  ReadXMLConfig.getInstance().getMap();
    	@SuppressWarnings("unchecked")
		HashMap<String, String> actionMap = (HashMap<String, String>) map.get(actionName);
    	
    	try {
    		//根据classname获取到对应的实例
    		String className = actionMap.get("class");
    		String exeRet = "";
            Class<?> aClass = Class.forName(className);
            Object newInstance = aClass.newInstance();
            Method[] methods = aClass.getDeclaredMethods();
            //执行传进来的参数的set方法
            for(Entry<String, String> set : parameters.entrySet()) {
            	String aimMethod = "set"+set.getKey();
            	for(Method m : methods) {
            		if(aimMethod.equalsIgnoreCase(m.getName())) {
            			m.invoke(newInstance, set.getValue());
            		}
            	}
            }
            //执行excute方法
            for(Method m : methods) {
            	if(m.getName().equalsIgnoreCase("execute")) {
            		exeRet = m.invoke(newInstance, null).toString();
            	}
            }
            //根据对应的私有变量，获取对应私有变量执行get方法时的参数值
            Field[] fields = aClass.getDeclaredFields();
            Map<String, String> pMap = new HashMap<>();
            for(Field f : fields) {
            	String aimMethod = "get"+f.getName();
            	for(Method m : methods) {
            		if(aimMethod.equalsIgnoreCase(m.getName())) {
            			pMap.put(f.getName(), m.invoke(newInstance, null).toString());
            		}
            	}
            }
            //创建view对象，传入对应的参数，并返回view对象
            View view = new View();
            String jsp = actionMap.get(exeRet);
            view.setJsp(jsp).setParameters(pMap);
            return view;
        } catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }  

}