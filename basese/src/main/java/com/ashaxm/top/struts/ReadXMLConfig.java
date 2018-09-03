package com.ashaxm.top.struts;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @读取配置文件获得配置
 * yaoyz
 */
public class ReadXMLConfig {

    // 单例模式ReadXMLConfig类中维护本来对象
    private static ReadXMLConfig readXMLConfig;

    // 存储xml中的键值对
    private HashMap<String, Object> actionMap;

    // 定义为private防止类外调用构造函数生成对象
    private ReadXMLConfig() {
        try {
            initReadConfig("com" + File.separator + "ashaxm" + File.separator + "top" + File.separator + "struts" + File.separator
                    + "struts.xml");
            System.out.println("---------" + "com" + File.separator + "ashaxm" + File.separator + "top" + File.separator + "struts" + File.separator
                    + "struts.xml");
        } catch (Exception e) {
            System.out.println("init config.xml failure!");
        }
    }

    // 单例模式通过getInstance始终返回同一个readXMLConfig对象
    public static ReadXMLConfig getInstance() {
        if (null == readXMLConfig) {
            readXMLConfig = new ReadXMLConfig();
        }
        return readXMLConfig;
    }

    // 读取配置文件并读入至hashmap中
    private void initReadConfig(String xmlFileName) throws Exception {
        InputStream input = ReadXMLConfig.class.getClassLoader()
                .getResourceAsStream(xmlFileName);
        if (input == null) {
            input = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(xmlFileName);
        }
        if (input == null) {
            throw new Exception(xmlFileName + "not found!");
        }
        try {
            // 使用DOM方式解析xml文件，并将结果存至hashmap中
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(input));
            Document m_config = parser.getDocument();
            Element root = m_config.getDocumentElement();

            NodeList nodeList = root.getElementsByTagName("action");

            int confNum = nodeList.getLength();
            actionMap = new HashMap<String, Object>();
            for (int i = 0; i < confNum; i++) {
            	Map<String, String> rmap = new HashMap<>();
                Element ele = (Element) nodeList.item(i);
                if (!ele.getAttribute("name").equals("")) {
                	rmap.put("class", ele.getAttribute("class"));
                }
                //将action对应的result也写入数组中
                NodeList resultList = ele.getElementsByTagName("result");
                int resultSize = resultList.getLength();
                for(int resultIdx = 0; resultIdx < resultSize; resultIdx++) {
                	Element rEle = (Element)resultList.item(resultIdx);
                	if(!rEle.getAttribute("name").equals("")) {
                		rmap.put(rEle.getAttribute("name"), rEle.getTextContent());        		
                	}
                }
                actionMap.put(ele.getAttribute("name"), rmap);
            }
        } catch (Exception e) {
            System.out.println("init config.xml failure!");
        } finally {
            input.close();
        }
    }   
    
   // 通过类中的该方法获取hashmap中的值
    public HashMap<String, String> getConfigValue(String key) {
        if (actionMap == null) {
            return null;
        } else {
            @SuppressWarnings("unchecked")
			HashMap<String, String> result = (HashMap<String, String>) actionMap.get(key);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
    
    public HashMap<String, Object> getMap(){
    	return actionMap;
    }
    
    public static void printMap(HashMap<String, String> map) {
    	for(Entry<String, String> set : map.entrySet()) {
    		System.out.println(set.getKey()+"-------"+set.getValue());
    	}
    }
    
    public static void main(String[] args) {
    	HashMap<String, String> login = getInstance().getConfigValue("login");
    	HashMap<String, String> logout = getInstance().getConfigValue("logout");
    	printMap(login);
    	printMap(logout);
    	
	}
}
