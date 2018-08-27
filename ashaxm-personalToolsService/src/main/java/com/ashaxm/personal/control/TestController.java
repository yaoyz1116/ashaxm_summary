package com.ashaxm.personal.control;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ≤‚ ‘
 * yaoyz    2018.6.23
 */
@RestController
public class TestController {

	Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/test/testHello", method = RequestMethod.POST)
	public Map<String, Integer> testHello() {
		Map<String, Integer> retMap = new HashMap<String, Integer>();
		log.info("----testHello-----");
		return retMap;
	}
}
