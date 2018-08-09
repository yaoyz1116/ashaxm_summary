package top.ashaxm.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * 检查给定IP、电话的所属区域
 * @author yaoyz
 * 2018年1月8日
 */
public class CheckLocation {
	// 日志文件
	protected final static Logger log = LoggerFactory.getLogger(CheckLocation.class);

	/**
	 * 利用百度地图的API去检测一个ip是否在指定的区域
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean getIpLocation(String ip, long code) {
		try {
			String BAIDU_APP_KEY = "lLoA4GYr2jQRzq5pNjSId0dTFrZPqTyY";
			String API_URL = "http://api.map.baidu.com/location/ip?ip=" + ip
					+ "&ak=" + BAIDU_APP_KEY + "&coor=bd09ll";
			System.out.println("API_URL    "+API_URL);
			URL resjson = new URL(API_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					resjson.openStream()));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			in.close();
			String str = sb.toString();

			JSONObject object = JSONObject.fromObject(str);
//			log.info(object.toString());
//			{"address":"CN|江苏|南京|None|CMNET|0|0","content":{"address":"江苏省南京市",
//				"address_detail":{"city":"南京市","city_code":315,"district":"","province":"江苏省","street":"","street_number":""},
//				"point":{"x":"118.77807441","y":"32.05723550"}},"status":0}
			System.out.println(object.toString());
			if (object.getInt("status") == 0) {
				System.out.println("aimcode  "+object.getJSONObject("content").getJSONObject("address_detail").getInt("city_code"));
				if (object.getJSONObject("content")
						.getJSONObject("address_detail").getInt("city_code") == code) {
					return true;
				}
			}

		} catch (Exception e) {
			log.error("getIpLocation", e);
		}

		return false;
	}

	/**
	 * 利用聚合数据的API
	 * 查询一个电话号码是否在指定的地区
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean isValidateMobileAreaCode(String mobile,
			String areaCode) {
		String APPKEY = "6257707e3f1123fe745ae407676c00c8";

		String geturl = "http://apis.juhe.cn/mobile/get?phone=MOBILE&key="
				+ APPKEY;
		try {
			String retjson = HttpRequest.sendGet(
					geturl.replace("MOBILE", mobile), null);
			System.out.println("retjson  "+retjson);
			JSONObject object = JSONObject.fromObject(retjson);

			if (object.getInt("resultcode") == 200) {

				String[] codeArr = areaCode.split(",");
				for (int i = 0; i < codeArr.length; i++) {
					if (object.getJSONObject("result").getString("areacode")
							.startsWith(codeArr[i])) {
						return true;
					}
				}

			}
		} catch (Exception e) {
			log.error("checkMobileAreaCode", e);
		}

		return false;
	}

	/**
	 * 利用聚合数据的API
	 * 查询电话号码归属的地区编码
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean isValidateMobileZipCode(String mobile, String zipCode) {
		String APPKEY = "6257707e3f1123fe745ae407676c00c8";

		String geturl = "http://apis.juhe.cn/mobile/get?phone=MOBILE&key="
				+ APPKEY;
		try {
			if (mobile != null && mobile.length() == 11) {
				String retjson = HttpRequest.sendGet(
						geturl.replace("MOBILE", mobile), null);
				System.out.println(retjson);
				JSONObject object = JSONObject.fromObject(retjson);

				if (object.getInt("resultcode") == 200) {
					String[] zipArr = zipCode.split(",");
					for (int i = 0; i < zipArr.length; i++) {
						if (object.getJSONObject("result").getString("zip")
								.startsWith(zipArr[i])) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("checkMobileZone", e);
		}

		return false;
	}
	
	/**
	 * 利用聚合数据API
	 * 查询电话和地区
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean isValidateMobileArea(String mobile, String area) {
		String APPKEY = "6257707e3f1123fe745ae407676c00c8";

		String geturl = "http://apis.juhe.cn/mobile/get?phone=MOBILE&key="
				+ APPKEY;
		try {
			if (mobile != null && mobile.length() == 11) {
				String retjson = HttpRequest.sendGet(
						geturl.replace("MOBILE", mobile), null);
				log.info("mobile=" + mobile + " area=" + area +" " + retjson);
				JSONObject object = JSONObject.fromObject(retjson);

				if (object.getInt("resultcode") == 200) {
					String[] areaArr = area.split(",");
					for (int i = 0; i < areaArr.length; i++) {
						if (object.getJSONObject("result").getString("province")
								.indexOf(areaArr[i])!=-1) {//判断省
							return true;
						}else if(object.getJSONObject("result").getString("city")
								.indexOf(areaArr[i])!=-1){//判断市
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("checkMobileZone", e);
		}

		return false;
	}

	/**
	 * 利用聚合数据API
	 * 查询IP和地区的关系
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean getIpLocByJuhe(String ip, String area) {
		try {
			String getIpUrl = "http://apis.juhe.cn/ip/ip2addr?ip=IP&key=d4970d0e49a919d83aad39c05576eed2";

			String retjson = HttpRequest.sendGet(getIpUrl.replace("IP", ip),
					null);

			log.info("ip="+ip + " area=" + area + " " + retjson);

			JSONObject object = JSONObject.fromObject(retjson);

			if (object.getInt("resultcode") == 200) {
				String[] areas = area.split(",");
				String target = object.getJSONObject("result")
						.getString("area");
				for (int i = 0; i < areas.length; i++) {
					if (target.indexOf(areas[i])!=-1) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			log.error("getIpLocByJuhe", e);
		}

		return false;
	}
	
	public static void main(String[] args) {
//		getIpLocation("223.104.147.184", 123);
		isValidateMobileZipCode("18226195598", "安徽");
	}
}
