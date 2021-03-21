package net.ninebolt.onevsone.util;

public class NameParser {
	/**
	 * 拡張子つきのファイル名(example.ex)から拡張子を除いた文字列(example)を取得するメソッド
	 * @param fileName 拡張子つきのファイル名
	 * @return 拡張子を除いた際の文字列
	 */
	public static String getNameWithoutExtension(final String fileName) {
		int dotPoint = fileName.lastIndexOf('.');
		if(dotPoint == -1) {
			return null;
		}
		return fileName.substring(0, dotPoint);
	}
}
