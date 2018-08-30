package com.ashaxm.springboot.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@RequestMapping(value="demo/test",method=RequestMethod.GET)
	public Map<String, Object> demo(){
		Map<String, Object> ret = new HashMap<>();
		ret.put("status", 1);
		ret.put("name", "yaoyz");
		return ret;
	}
}
