package com.pactera.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {

	private static String basePath;

	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		basePath = rb.getString("vsts.temp.file.path");
	}

	public static List<File> getFiles(String parent) throws IOException {
		List<File> list = new ArrayList<File>();
		try {
			getFiles(getAbsoluteFile(parent, false));
			list.addAll(temp_files);
			return list;
		} finally {
			temp_files.clear();
		}
	}

	private static List<File> temp_files = new ArrayList<File>();

	private static void getFiles(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					getFiles(child);
				}
			} else if (file.isFile()) {
				temp_files.add(file);
			}
		}
	}

	public static File getAbsoluteFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public static File getAbsoluteFile(String path, boolean createAlias)
			throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			getAbsoluteFolder(file.getParent());
			file.createNewFile();
		} else {
			if (createAlias) {
				file = getAbsoluteFile(file.getParent(),
						getAlias(file.getName()), createAlias);
			}
		}
		return file;
	}

	public static File getAbsoluteFile(String parent, String child,
			boolean createAlias) throws IOException {
		File file = new File(parent, child);
		if (!file.exists()) {
			getAbsoluteFolder(file.getParent());
			file.createNewFile();
		} else {
			if (createAlias) {
				file = getAbsoluteFile(parent, getAlias(child), createAlias);
			}
		}
		return file;
	}

	public static File getRelativeFolder(String path) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return getAbsoluteFolder(basePath + path);
	}

	public static File getRelativeFile(String path) throws IOException {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return getAbsoluteFile(basePath + path, true);
	}

	public static File getRelativeTempFile(String path) throws IOException {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		return getRelativeFile("/temp" + path);
	}

	public static String getAlias(String name) {
		return FilenameUtils.getBaseName(name) + "_"
				+ Calendar.getInstance().getTimeInMillis() + "."
				+ FilenameUtils.getExtension(name);
	}

	/**
	 * @Description 获取\classes路径
	 * @return
	 */
	public static String getPath() {
		URL url = FileUtil.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String filePath = null;
		try {
			filePath = URLDecoder.decode(url.getPath(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (filePath.endsWith(".jar")) {
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		}
		File file = new File(filePath);
		filePath = file.getAbsolutePath();
		return filePath;
	}

	public static String getBasePath() {
		return basePath;
	}

	public static void setBasePath(String basePath) {
		FileUtil.basePath = basePath;
	}

}
