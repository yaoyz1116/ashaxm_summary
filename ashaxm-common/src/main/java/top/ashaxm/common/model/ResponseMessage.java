package top.ashaxm.common.model;

/**
 * websocket中用来表示服务器发出去的消息
 */
public class ResponseMessage {

	private String responsemsg;
	
	public ResponseMessage(String responsemsg){
		this.responsemsg=responsemsg;
	}
	
	public void setResponsemsg(String responsemsg) {
		this.responsemsg = responsemsg;
	}
	public String getResponsemsg() {
		return responsemsg;
	}
}
