package com.ashaxm.top.download.impl;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ashaxm.top.download.api.ConnectionManager;

public class ConnectionManagerImpl implements ConnectionManager {
	
	private static List<ConnectionImpl> poll;
	private static int DEFAULT_CAPICATY;
	private static int OCCOPY_CAPICATY;
	private static int THREAD_SIZE;
	public static String AIM_FILE;
	public static int CONTENT_LENGTH=0;
	
	public ConnectionManagerImpl() {
		OCCOPY_CAPICATY = 0;
		poll = new ArrayList<>();
		THREAD_SIZE = 10240;
		AIM_FILE = "/Users/yaoyuzhao/Documents/temp/imgs/"+new Date().getTime()+".png";
		System.out.println("ConnectionManagerImpl      初始化完成");
	}

	@Override
	public void open(String url) throws Exception {
		if(CONTENT_LENGTH==0) {
			System.out.println("url----"+url);
			URL remoteUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) remoteUrl.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200){
				//网络资源长度
				CONTENT_LENGTH = conn.getContentLength();
				System.out.println("length       "+CONTENT_LENGTH);
				File file = new File(AIM_FILE);
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.setLength(CONTENT_LENGTH);
				accessFile.close();
				DEFAULT_CAPICATY = CONTENT_LENGTH%THREAD_SIZE==0?CONTENT_LENGTH/THREAD_SIZE:CONTENT_LENGTH/THREAD_SIZE+1;
				System.out.println("block     "+DEFAULT_CAPICATY);	
				for(int i=0; i<DEFAULT_CAPICATY; i++) {
					int tempLength = 0;
					if(i==DEFAULT_CAPICATY-1) {
						tempLength = CONTENT_LENGTH-THREAD_SIZE*i;
					}else {
						tempLength = THREAD_SIZE;
					}
					ConnectionImpl temp = new ConnectionImpl();
					temp.setContentLength(tempLength);
					temp.setStartPos(i*THREAD_SIZE);
					poll.add(temp);
				}
			}else{
				System.out.println("下载失败");
			}
		}
	}
	
	public static List<ConnectionImpl> getPoll() {
		return poll;
	}

	@Override
	public ConnectionImpl getConn() {
		ConnectionImpl ret = null;
		synchronized (ret) {
			if(OCCOPY_CAPICATY<DEFAULT_CAPICATY) {
				ret = poll.get(OCCOPY_CAPICATY);
				OCCOPY_CAPICATY++;
			}
			
		}
		return ret;
	}

}
