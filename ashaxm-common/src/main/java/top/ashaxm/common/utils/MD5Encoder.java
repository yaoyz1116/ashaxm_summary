package top.ashaxm.common.utils;

import org.springframework.security.crypto.password.PasswordEncoder;


public class MD5Encoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence arg0) {
		return MD5.convertToMD5(arg0.toString());
	}

	@Override
	public boolean matches(CharSequence arg0, String arg1) {
		return encode(arg0).equalsIgnoreCase(arg1);
	}

}
