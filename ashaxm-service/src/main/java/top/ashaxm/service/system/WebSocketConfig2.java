package top.ashaxm.service.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

//@Configuration
public class WebSocketConfig2 {
	@Bean  
    public ServerEndpointExporter serverEndpointExporter() {
		System.out.println("-----------------初始化---------------------------");
        return new ServerEndpointExporter();  
    } 
}

