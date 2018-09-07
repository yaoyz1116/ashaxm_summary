package com.ashaxm.top.download.api;

import com.ashaxm.top.download.impl.ConnectionImpl;

public interface ConnectionManager {
	public void open(String url) throws Exception;
	public ConnectionImpl getConn();
}
