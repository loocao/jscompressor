package me.oncereply.jscompressor.util;

import java.io.File;

public class FileUtils {

	public static boolean mkdirs(String path) {
		return mkdirs(new File(path));
	}

	public static boolean mkdirs(File file) {
		File f = new File(file.getAbsolutePath());
		if (f.exists()) {
			return true;
		} else {
			boolean b = f.mkdirs();
			return b;
		}
	}
}
