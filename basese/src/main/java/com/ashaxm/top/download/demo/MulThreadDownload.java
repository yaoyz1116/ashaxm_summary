package com.ashaxm.top.download.demo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MulThreadDownload {

	public static void main(String[] args) throws Exception {
		String path = "http://localhost:80/content/tywh/1.psd";
		new MulThreadDownload().download(path,3);
	}
	
	/**
	 * ************************
	 * *0*1*2*3*4*5*6*7*8*9*10* 文件字节
	 * ************************
	 * threadid:0 1 2
	 * int block = 4 每一个下载多少
	 * int start = threadid*block 开始位置
	 * int end = (threadid+1)*block-1 结束位置
	 */
	public void download(String path,int threadsize) throws Exception{
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode()==200){
			//网络资源长度
			int length = conn.getContentLength();
			System.out.println("length       "+length);
			//new File中的参数如果直接写名字,那么表示绝对路径,会存放在当前项目下;
			File file = new File("/Users/yaoyuzhao/Documents/temp/imgs/1.psd");
			//RandomAccessFile是用来访问那些保存数据记录的文件的,其大小和位置必须是可知的,它可以指定seek,搜索指定的位置,在指定位置写入数据;
			//rwd 参数是权限,表示将读取到的资源立马写入文件中;
			//多线程下载先要生成一个大小一样的文件,在进行写入;
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.setLength(length);
			accessFile.close();
			int block = length%threadsize==0?length/threadsize:length/threadsize+1;
			for (int threadid = 0; threadid < threadsize; threadid++) {
				new DownloadThread(threadid,block,url,file).start();
			}		
		}else{
			System.out.println("下载失败");
		}
		
	}
	
	private class DownloadThread extends Thread{
		private int threadid;
		private int block;
		private URL url;
		private File file;
		public DownloadThread(int threadid, int block, URL url, File file) {
			this.block=block;
			this.threadid=threadid;
			this.url=url;
			this.file=file;
		}
		@Override
		public void run() {
			int start = threadid*block;
			int end = (threadid+1)*block-1;
			try {
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.seek(start);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				//设置头信息,表明要读取网络资源的从哪个地方开始-哪个地方结束
				conn.setRequestProperty("Range", "bytes="+start+"-"+end);
				if(conn.getResponseCode()==200){
					InputStream inputStream = conn.getInputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len=inputStream.read(buffer))!=-1) {
						accessFile.write(buffer,0,len);
					}
					accessFile.close();
				}
				accessFile.close();
				System.out.println("第"+threadid+"线程已经下载完成");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	private String getFilename(String path) {	
		System.out.println("filepath-------"+path.substring(path.lastIndexOf("/")+1));
		return path.substring(path.lastIndexOf("/")+1);
	}
}
