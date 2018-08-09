package top.ashaxm.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import top.ashaxm.common.system.SystemProperty;

/**
 * 对文件的一些操作
 * @author yaoyz
 * 2018年1月8日
 */
public class FileUtils {
	static final int BUFFER = 8192;
	protected final static Logger log = LoggerFactory.getLogger(FileUtils.class);

	private static File zipFile;

	/**
	 * 删除文件或者目录，会将子文件等全部删除
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static void deleteFolder(String[] filePath) {
		for (String srcpath : filePath) {
			File file = new File(srcpath);
			// 判断目录或文件是否存在
			if (!file.exists()) { // 不存在返回 false
				log.info("file is not exist");
			} else {
				// 判断是否为文件
				if (file.isFile()) { // 为文件时调用删除文件方法
					deleteFile(srcpath);
				} else { // 为目录时调用删除目录方法
					deleteDirectory(srcpath);
				}
			}
		}
	}

	/**
	 * 删除文件
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean deleteDirectory(String filePath) {
		boolean flag = false;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		File dirFile = new File(filePath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 压缩任意个文件，包括文件和目录
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static void compress(String zipPath, String[] srcPathName) {
		// 压缩后的文件对象
		zipFile = new File(zipPath);
		try {
			// 创建写出流操作
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			for (String srcPath : srcPathName) {
				// 创建需要压缩的文件对象
				File file = new File(srcPath);
				if (!file.exists()) {
					throw new RuntimeException(srcPath + "不存在！");
				}
				/*
				 * (1)如果在zip压缩文件中不需要一级文件目录，定义String basedir = "";
				 * 下面的compress方法中当判断文件file是目录后不需要加上basedir = basedir +
				 * file.getName() + File.separator;
				 * (2)如果只是想在压缩后的zip文件里包含一级文件目录，不包含二级以下目录， 直接在这定义String basedir =
				 * file.getName() + File.separator;
				 * 下面的compress方法中当判断文件file是目录后不需要加上basedir = basedir +
				 * file.getName() + File.separator;
				 * (3)如果想压缩后的zip文件里包含一级文件目录，也包含二级以下目录，即zip文件里的目录结构和原文件一样
				 * 在此定义String basedir = "";
				 * 下面的compress方法中当判断文件file是目录后需要加上basedir = basedir +
				 * file.getName() + File.separator;
				 */
				// String basedir = file.getName() + File.separator;
				String basedir = "";
				compress(file, out, basedir);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void compress(File file, ZipOutputStream out, String basedir) {
		//判断是目录还是文件
		if (file.isDirectory()) {
			basedir += file.getName() + File.separator;
			compressDirectory(file, out, basedir);
		} else {
			compressFile(file, out, basedir);
		}
	}

	/**
	 * 压缩一个目录
	 */
	private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists()) {
			return;
		}
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir);
		}
	}

	/**
	 * 压缩一个文件
	 */
	private static void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			// 创建Zip实体，并添加进压缩包
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			// 读取待压缩的文件并写进压缩包里
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 打开一个文件，准备写入CSV
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static BufferedWriter openFile(File file, BufferedWriter bw) {
		try {
			return bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (UnsupportedEncodingException te) {
			log.error(te.getMessage());
		}
		return bw;
	}

	/**
	 * 将字符数组转换成为CSV
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static boolean exportCsv(BufferedWriter bw, String[] datas) {
		boolean isSucess = false;
		try {
			for (int i = 0; i < datas.length; i++) {
				String data = (datas[i] == null ? "" : datas[i]);
				if (i == (datas.length - 1)) {
					bw.append("\"" + data + "\"");
				} else {
					bw.append("\"" + data + "\",");
				}
			}
			bw.newLine();
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
			log.error(e.getMessage());
		}
		return isSucess;
	}

	/**
	 * 关闭生成的CSV文件
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static void closeFile(BufferedWriter bw) {
		try {
			if (bw != null) {
				bw.close();
				bw = null;
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 用UTF16的方式把文件写成CSV
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static void exportCsvByUtf16(BufferedOutputStream bw, String[] datas) {
		try {
			bw.write(join(datas, "\t").getBytes("utf-16le"));
			bw.write("\n".getBytes("utf-16le"));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 打开csv文件的写入流,并且给文件默认首先写入两个字节
	 * 2017.7.25    yaoyz
	 * @param filepath    文件路径
	 * @return
	 */
	public static BufferedOutputStream openBufferedCsvByUtf16(String filepath) {
		try {
			BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(filepath));
			byte[] bom = { (byte) 0xFF, (byte) 0xFE };
			bw.write(bom);
			return bw;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 关闭导出csv文件的写入刘
	 * 
	 * @param bw
	 */
	public static void closeCsvByUtf16(BufferedOutputStream bw) {
		try {
			if (bw != null) {
				bw.close();
				bw = null;
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 将文件写入csv，利用utf16的编码格式的时候使用到的辅助接口
	 * 
	 * @param strArr
	 * @param delim
	 * @return
	 */
	public static String join(String[] strArr, String delim) {
		StringBuilder sb = new StringBuilder();
		for (String s : strArr) {
			sb.append(s);
			sb.append(delim);
		}
		String ret;
		if (strArr.length > 1) {
			ret = sb.substring(0, sb.length() - 1);
		} else {
			ret = sb.toString();
		}
		return ret;
	}
	
	/**
	 * 上传文件
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static String upload(MultipartFile mpFile, String outputName) {
		// 获得文件名：
		String origFileName = mpFile.getOriginalFilename();
		String outputFile = outputName + "." + getFileSuffix(origFileName);
		try {
			upload(mpFile.getInputStream(), new File(SystemProperty.FILE_PATH
					+ outputFile));
		} catch (IOException e) {
			e.printStackTrace();
			outputFile = null;
		}
		return outputFile;
	}
	public static String getFileSuffix(String fileName) {
		// 取得后缀名
		String[] strArr = fileName.split("\\.");
		if (strArr.length < 2) {
			// 如果是安卓版本，在线裁剪的，传过来是bolb，没有后缀，默认是png
			return "png";
		}
		return strArr[strArr.length - 1];
	}
	public static void upload(File input, File output) throws IOException {

		InputStream is = new FileInputStream(input);
		upload(is, output);
		is.close();
	}
	public static void upload(InputStream is, File output) throws IOException {
		OutputStream os = new FileOutputStream(output);
		byte[] b = new byte[1024];
		while (is.read(b) != -1) {
			os.write(b);
			os.flush();
		}
		os.close();
	}

	public static void main(String[] args) {

	}
}
