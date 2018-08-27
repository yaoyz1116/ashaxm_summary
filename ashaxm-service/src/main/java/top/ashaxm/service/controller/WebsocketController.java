package top.ashaxm.service.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import top.ashaxm.common.model.RequestMessage;
import top.ashaxm.common.model.ResponseMessage;


/**
 * websocket的控制类
 */
@RestController
public class WebsocketController {
	Log log = LogFactory.getLog(getClass());
	
	@MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message) {
		System.out.println("111111111111111");
        System.out.println(message.getRequestmsg());
        return new ResponseMessage("welcome," + message.getRequestmsg() + " !");
    }

}
