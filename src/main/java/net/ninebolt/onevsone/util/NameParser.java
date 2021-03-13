package net.ninebolt.onevsone.util;

public class NameParser {
	public static String getNameWithoutExtension(final String fileName) {
		int dotPoint = fileName.lastIndexOf('.');
		if(dotPoint == -1) {
			return null;
		}
		return fileName.substring(0, dotPoint);
	}
}
