package com.monda.demo.util;

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
}

