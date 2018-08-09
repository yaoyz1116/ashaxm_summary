package top.ashaxm.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * 二维码的相关操作
 * @author yaoyz
 * 2018年1月8日
 */
public class QRCodeTool {
	
	/**
	 * 生成二维码
	 * content  二维码中间的文字
	 * qrcodeImagePath 生成二维码的完整路径
	 * ccbPath 二维码中图片的路径   没作用？？？
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static int createQRCode(String content, String qrcodeImagePath,
			String ccbPath) {
		try {

			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);

			byte[] contentBytes = content.getBytes("gb2312");
			BufferedImage bufImg = new BufferedImage(140, 140,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			gs.setBackground(Color.WHITE);

			gs.clearRect(0, 0, 200, 200);

			// 设定图像颜色 > BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 > 二维码
			if (contentBytes.length > 0 && contentBytes.length < 1200) {

				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
			}
			else {
				//System.err.println("QRCode content bytes length = "	+ contentBytes.length + " not in [ 0,1200 ]. ");
				return -1;

			}
			//Image img = ImageIO.read(new File(ccbPath));// 实例化一个Image对象。
			//gs.drawImage(img, 44, 44, null);
			gs.dispose();
			bufImg.flush();
			// 生成二维码QRCode图片

			File imgFile = new File(qrcodeImagePath);
			ImageIO.write(bufImg, "png", imgFile);

		} catch (Exception e) {
			e.printStackTrace();
			return -100;
		}

		return 0;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String c="1234567890abcdefghijklnmopqrstuvwxyq1234567890abcdefghijklnmopqrstuvwxyq1234567890abcdefghijklnmopqrstuvwxyq";
		String c="this is what";
		QRCodeTool.createQRCode(c, "C:\\Users\\Administrator\\Desktop\\图片\\logo.png", "C:\\Users\\Administrator\\Desktop\\图片\\500.png");
	}

}
