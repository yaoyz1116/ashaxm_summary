package com.ashaxm.top.download.impl;

import java.io.IOException;

import com.ashaxm.top.download.api.Connection;


public class ConnectionImpl implements Connection{
	
	private int contentLength;
	private int startPos;
	
	@Override
	public byte[] read(long startPos, long endPos) throws IOException {
		return null;
	}

	@Override
	public int getContentLength() {
		return this.contentLength;
	}

	@Override
	public void close() {
			
	}
	
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getStartPos() {
		return startPos;
	}
	
	
	

}

