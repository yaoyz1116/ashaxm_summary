package top.ashaxm.common.model;

/**
 * websocket中用来表示浏览器发送的消息
 */
public class RequestMessage {

	private String requestmsg;
	
	public void setRequestmsg(String requestmsg) {
		this.requestmsg = requestmsg;
	}
	public String getRequestmsg() {
		return requestmsg;
	}
}
