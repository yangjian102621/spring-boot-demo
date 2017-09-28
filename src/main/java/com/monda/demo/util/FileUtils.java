package com.monda.demo.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件处理工具
 * Created by yangjian on 17-9-27.
 */
public class FileUtils {

	/**
	 * 从路径中获取文件名
	 * @param path
	 * @return
	 */
	public static String getFilenameFromPath(String path) {
		path = path.trim();
		return path.substring(path.lastIndexOf("/")+1);
	}

	/**
	 * 获取文件后缀名
	 * @param filename
	 * @return
	 */
	public static String getFileExtesion(String filename) {
		int pos = filename.lastIndexOf(".");
		if (pos == -1) {
			return null;
		} else {
			return  filename.substring(pos+1).toLowerCase() ;
		}
	}

	/**
	 * 获取文件大小
	 * @param filePath
	 * @return
     */
	public static long getFileSize(String filePath) {
		File file = new File(filePath);
		return file.length();
	}

	/**
	 * 获取网络文件的大小
	 * @param remoteFileUrl
	 * @return
	 * @throws IOException
     */
	public static long getRemoteFileSize(String remoteFileUrl) throws IOException {
		long size;
		URL url = new URL(remoteFileUrl);
		URLConnection conn = url.openConnection();
		size = conn.getContentLength();
		conn.getInputStream().close();
		return size;
	}
}

