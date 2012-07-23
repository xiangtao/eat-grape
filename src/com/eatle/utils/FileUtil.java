/**
 * shijin
 *
 * FileUtil.java, Create on 2011-7-22
 */
package com.eatle.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.CRC32;

import org.apache.commons.io.FileUtils;

/**
 * 文件工具类
 * 
 * @author shijin
 */
public class FileUtil extends FileUtils {
	private static final String EMPTY = "";

	/** 计算机硬盘空间单位进制 1024 */
	public static final int CONVERT_UNIT = 1024;

	/** 计算机硬盘空间1KB单位 1024 */
	public static final int KB_SIZE = 1024;

	/** 计算机硬盘空间1MB单位 1024 * 1024 */
	public static final int MB_SIZE = 1024 * 1024;

	/** 计算机硬盘空间1G单位 1024 * 1024 * 1024 */
	public static final int G_SIZE = 1024 * 1024 * 1024;

	/** 文件分隔符 / */
	public static final String FILE_SPLIT_1 = "/"; //$NON-NLS-1$
	/** 文件分隔符 \ */
	public static final String FILE_SPLIT_2 = "\\"; //$NON-NLS-1$

	public static final String URL_DELIM = "/"; //$NON-NLS-1$
	public static final String DOT = "."; //$NON-NLS-1$
	public static final String RW_MODE = "rw"; //$NON-NLS-1$
	public static final String R_MODE = "r"; //$NON-NLS-1$

	private FileUtil() {
	}

	/**
	 * 文件得到一个校验值
	 * 
	 * 如果内容相同的文件将会视为同一个文件，采用的是crc32校验
	 * 
	 * 
	 * @param file
	 * @return 如果文件有问题，例如无法读取或不是文件则返回-1，正确返回正常值
	 */
	public static long checksum(File file) {
		if (file == null || !file.exists() || !file.isFile())
			return -1;
		else {
			CRC32 crc32 = new CRC32();
			try {
				crc32.update(readFileToByteArray(file));
				return crc32.getValue();
			} catch (IOException e) {
				return -1;
			} finally {
				crc32 = null;
			}
		}
	}

	/**
	 * 将字节计算为G
	 * 
	 * @param byteUnitNum
	 * @return
	 */
	public static float computeG(long byteUnitNum) {
		return (float) byteUnitNum / (float) (G_SIZE);
	}

	/**
	 * 将字节计算为KB
	 * 
	 * @param byteUnitNum
	 * @return
	 */
	public static float computeKB(long byteUnitNum) {
		return (float) byteUnitNum / (float) (CONVERT_UNIT);
	}

	/**
	 * 将字节计算为MB
	 * 
	 * @param byteUnitNum
	 * @return
	 */
	public static float computeMB(long byteUnitNum) {
		return (float) byteUnitNum / (float) (MB_SIZE);
	}

	/**
	 * 创建目录树
	 * 
	 * 例如： c:/temp/file.txt<br>
	 * 会创建temp 和 file.txt两个目录<br>
	 * 如果目录存在且完整则返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void createTree(File file) throws IOException {
		if (file == null) {
			return;
		}
		if (file.exists()) {
			return;
		}
		File parent = file.getParentFile();
		if (parent != null) {
			createTree(parent);
		}
		try {
			file.mkdir();
		} catch (Exception e) {
			// System.out.println("file.mkdir()   falier");
			e.printStackTrace();
		}
	}

	/**
	 * 创建目录文件树<br>
	 * 
	 * 例如： c:/temp/file<br>
	 * 会创建temp文件夹和file文件<br>
	 * 如果这个文件存在则返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void createTreeFile(File file) throws IOException {
		if (file == null) {
			return;
		}
		if (file.exists()) {
			return;
		}
		createTree(file.getParentFile());
		try {
			file.createNewFile();
		} catch (Exception e) {
			// System.out.println("createTreeFile(File file)  falier");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件目录树
	 * 
	 * @param file
	 */
	public static void delTree(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				delTree(file2);
			}
		}
		try {
			file.delete();
		} catch (Exception e) {
			// System.out.println("delTree(File file) falier!");
			e.printStackTrace();
		}
	}

	/**
	 * 解析一个url来获得文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNameFromURL(String url) {
		try {
			URL u = new URL(url);
			url = u.getFile();
		} catch (MalformedURLException e) {
			return EMPTY;
		}
		int lastDelim = url.lastIndexOf(URL_DELIM);
		return url.substring(lastDelim + 1);
	}

	public static String getFileNameFromString(String str) {
		if (str == null || str.length() <= 0) {
			return EMPTY;
		}
		// int lastDelim = str.lastIndexOf(URL_DELIM);
		// if (lastDelim == -1) {
		// return str;
		// }
		// return str.substring(lastDelim + 1);
		File file = new File(str);
		return file.getName();
	}

	public static String getSimpleFileNameFromString(String str) {
		String filename = getFileNameFromString(str);
		int lastDot = filename.lastIndexOf(DOT);
		if (lastDot != -1) {
			return filename.substring(0, lastDot);
		} else {
			return filename;
		}
	}

	public static String getFileSuffix(File file) {
		if (file == null || file.getName() == null) {
			return EMPTY;
		}
		String name = file.getName();
		int dotIndex = name.lastIndexOf(DOT);
		if (dotIndex == -1) {
			return EMPTY;
		}
		return name.substring(dotIndex + 1);
	}

	/**
	 * 拷贝文件内容
	 * 
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public static void copy(File from, File to) throws Exception {
		FileChannel writeChannel = null;
		FileChannel readChannel = null;
		ByteBuffer buffer = ByteBuffer.allocate(CONVERT_UNIT * 10);
		try {
			readChannel = new FileInputStream(from).getChannel();
			writeChannel = new FileOutputStream(to).getChannel();
			while (readChannel.read(buffer) != -1) {
				buffer.flip();
				writeChannel.write(buffer);
				buffer.clear();
			}
		} finally {
			if (writeChannel != null) {
				writeChannel.close();
				writeChannel = null;
			}
			if (readChannel != null) {
				readChannel.close();
				readChannel = null;
			}
			buffer = null;
		}
	}

	/**
	 * 是否文件已经存在
	 * 
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 通过文件名得到文件类型
	 * 
	 * @param text
	 * @return
	 */
	public static String getFileType(String fileName) {
		if (fileName != null && fileName.length() >= 0) {
			int lastDot = fileName.lastIndexOf(DOT);
			if (lastDot != -1) {
				return fileName.substring(lastDot + 1);
			}
		}
		return EMPTY;
	}

	/**
	 * 通过文件名得到文件类型，文件名来自网络
	 * 
	 */
	public static String getFileTypeFromUrl(String filename) throws Exception {
		String ext = "";
		int pos;
		if ((pos = filename.lastIndexOf(FileUtil.DOT)) != (-1)) {
			ext = URLEncoder
					.encode(filename.substring(pos + 1).trim(), "utf-8");
		}
		return ext;
	}

	public static void writeFile(File file, byte[] content) throws Exception {
		FileOutputStream writer = null;
		try {
			writer = new FileOutputStream(file);
			writer.write(content);
			writer.flush();
		} finally {
			if (writer != null)
				writer.close();
			writer = null;
		}
	}

	public static String getHash(String fileName) throws Exception {
		InputStream fis;
		fis = new FileInputStream(fileName);
		byte[] buffer = new byte[4096];
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return toHexString(md5.digest());
	}

	private static String toHexString(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) {
			sb.append(toHex(b));
		}
		return sb.toString();
	}

	private static final String HEX = "0123456789ABCDEF";

	private static String toHex(byte one) {
		char[] result = new char[2];
		result[0] = HEX.charAt((one & 0xf0) >> 4);
		result[1] = HEX.charAt(one & 0x0f);
		return new String(result);
	}

	/**
	 * 该文件是否剩余空间写入
	 * 
	 * @param file
	 *            文件
	 * @param percent
	 *            百分比
	 * @return 剩余空间百分比大于传入的百分比
	 */
	public static boolean isSpaceFree(File file, float percent) {
		long freeSpace = file.getUsableSpace();
		long totalSpace = file.getTotalSpace();
		float used = (float) (totalSpace - freeSpace) / totalSpace;
		return used < percent;
	}
}
