package top.ashaxm.service.system;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration  
@EnableWebSocketMessageBroker
//通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
//1.@EnableWebSocketMessageBroker注解表示开启使用STOMP协议来传输基于代理的消息，Broker就是代理的意思。 
//2.registerStompEndpoints方法表示注册STOMP协议的节点，并指定映射的URL。 
//3.stompEndpointRegistry.addEndpoint("/endpointSang").withSockJS();这一行代码用来注册STOMP协议节点，同时指定使用SockJS协议。 
//4.configureMessageBroker方法用来配置消息代理，由于我们是实现推送功能，这里的消息代理是/topic
public class WebSocketConfig  extends AbstractWebSocketMessageBrokerConfigurer{  
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //endPoint 注册协议节点,并映射指定的URl

        //注册一个Stomp 协议的endpoint,并指定 SockJS协议
//        registry.addEndpoint("/endpointWisely").withSockJS();

        //注册一个名字为"endpointChat" 的endpoint,并指定 SockJS协议。   点对点-用
//        registry.addEndpoint("/endpointChat").withSockJS();
        System.out.println("---------------加载完毕-----------------------");
    	registry.addEndpoint("/endpointSang").withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {//配置消息代理(message broker)
        //广播式应配置一个/topic 消息代理
//        registry.enableSimpleBroker("/topic");

        //点对点式增加一个/queue 消息代理
//        registry.enableSimpleBroker("/queue","/topic");

        registry.enableSimpleBroker("/topic");

    }

} 




  