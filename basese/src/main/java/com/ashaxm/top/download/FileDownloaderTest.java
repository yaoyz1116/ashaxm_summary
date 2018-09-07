package com.ashaxm.top.download;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ashaxm.top.download.api.ConnectionManager;
import com.ashaxm.top.download.api.DownloadListener;
import com.ashaxm.top.download.impl.ConnectionManagerImpl;


public class FileDownloaderTest {
	boolean downloadFinished = false;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDownload() {
		
		String url = "http://localhost:80/content/tywh/107.png";
		
		FileDownloader downloader = new FileDownloader(url);

	
		ConnectionManager cm = new ConnectionManagerImpl();
		downloader.setConnectionManager(cm);
		
		downloader.setListener(new DownloadListener() {
			@Override
			public void notifyFinished() {
				downloadFinished = true;
			}

		});

		System.out.println("downloader     准备执行execute方法");
		downloader.execute();
		System.out.println("downloader     执行完成");
		
		// 等待多线程下载程序执行完毕
		while (!downloadFinished) {
			try {
				System.out.println("还没有下载完成，休眠五秒");
				//休眠5秒
				Thread.sleep(5000);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		}
		System.out.println("下载完成！");
	}

}


//public static void download(String urlString, String filename) throws Exception {
//    // 构造URL
//    URL url = new URL(urlString);
//    // 打开连接
//    URLConnection con = url.openConnection();
//    // 输入流
//    InputStream is = con.getInputStream();
//    // 1K的数据缓冲
//    byte[] bs = new byte[1024];
//    // 读取到的数据长度
//    int len;
//    // 输出的文件流
//    OutputStream os = new FileOutputStream(filename);
//    // 开始读取
//    while ((len = is.read(bs)) != -1) {
//        os.write(bs, 0, len);
//    }
//    // 完毕，关闭所有链接
//    os.close();
//    is.close();
//}

