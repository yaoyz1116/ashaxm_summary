package top.ashaxm.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * @author kong
 * @version 0.1
 * @data 2014-3-3
 * 这个是原始的MD5加密，之前用户秘盒app的登录的，MD5不是标准的
 */
public class MD5 {//sha1
	
	/**
	 * 转换成MD5
	 * @param str
	 * @return
	 */
	public static String convertToMD5(String str){
		try {
			char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};//十六进制表
			byte[] strBytes = str.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strBytes);
			md.update(strBytes);
			md.update(strBytes);
			byte[] secret = md.digest(strBytes);
			
			int j = secret.length;
            char secretChar[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = secret[i];
                secretChar[k++] = hexDigits[b >>> 4 & 0xf];	//高四位，先存
                secretChar[k++] = hexDigits[b & 0xf];		//低四位
            }
            return new String(secretChar);
				
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return str;
	}
	
	public static String convertToMD5D(String str){
		try {
			char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6'};//十六进制表
			byte[] strBytes = str.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strBytes);
			byte[] secret = md.digest(strBytes);
			
			int j = secret.length;
            char secretChar[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = secret[i];
                secretChar[k++] = hexDigits[b >>> 4 & 0xf];	//高四位，先存
                secretChar[k++] = hexDigits[b & 0xf];		//低四位
            }
            return new String(secretChar);
				
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return str;
	}
	
	public static void main(String[] args){
		String str = "ashaxm";
		
		System.out.println(convertToMD5(str));
		System.out.println(convertToMD5D(str));
	}
	
}
