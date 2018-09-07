package com.ashaxm.top.download;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ashaxm.top.download.impl.ConnectionImpl;
import com.ashaxm.top.download.impl.ConnectionManagerImpl;

public class DownloadThread extends Thread{

	ConnectionImpl conn;
	int startPos;
	int endPos;
	String url;

	public DownloadThread( ConnectionImpl conn, String url, int startPos, int endPos){
		this.conn = conn;		
		this.startPos = startPos;
		this.endPos = endPos;
		this.url = url;
	}
	
	public void run(){	
		try {
			String file = ConnectionManagerImpl.AIM_FILE;
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.seek(startPos);
			URL remoteUrl = new URL(url);
			HttpURLConnection Uconn = (HttpURLConnection) remoteUrl.openConnection();
			Uconn.setConnectTimeout(5000);
			Uconn.setRequestMethod("GET");
			//设置头信息,表明要读取网络资源的从哪个地方开始-哪个地方结束
			System.out.println("下载过程中-----"+startPos+"----"+endPos);
			Uconn.setRequestProperty("Range", "bytes="+startPos+"-"+endPos);
			if(Uconn.getResponseCode()==206){
				InputStream inputStream = Uconn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len=inputStream.read(buffer))!=-1) {
					accessFile.write(buffer,0,len);
				}
				accessFile.close();
			}
			accessFile.close();
			System.out.println("线程已经下载完成------"+startPos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

