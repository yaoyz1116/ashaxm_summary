package com.cloud.remote;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloRemoteHystrix implements HelloRemote{

	@Override
	public String hello(@RequestParam(value = "name") String name) {
		return "hello,"+name+" the service is fail!!!!";
	}

}
