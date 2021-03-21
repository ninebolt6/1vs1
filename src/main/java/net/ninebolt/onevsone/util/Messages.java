package net.ninebolt.onevsone.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import net.ninebolt.onevsone.stats.Stats;

public class Messages {

	private static final char altColorChar = '&';

	private static YamlConfiguration externalConfig;
	private static YamlConfiguration internalConfig;
	private static File langFolder;

	/**
	 * JAR内部の言語ファイルを読み込みます。
	 * @param input 言語ファイルのInputStream
	 * @throws FileNotFoundException JARファイル内に言語ファイルが見つからなかった場合に呼ばれる例外
	 * @throws UnsupportedEncodingException UTF-8(BOM無し)以外の文字コードでファイルが保存されていた場合に呼ばれる例外
	 */
	public static void initInternalConfig(InputStream input) throws FileNotFoundException, UnsupportedEncodingException {
		if(input == null) {
			throw new FileNotFoundException("Internal language file not found. Check if your JAR file includes a language file.");
		}
		internalConfig = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(input, "UTF-8")));
	}

	/**
	 * 引数で指定されたフォルダに含まれている、"[languageで指定した文字列].yml"ファイルを読み込みます。
	 * @param languageFolder メッセージが記述されたymlファイルが格納されているフォルダ
	 * @param language 読み込む言語
	 */
	public static void initExternalConfig(File languageFolder, String language) {
		langFolder = languageFolder;
		File file = new File(langFolder, language + ".yml");
		if(file.exists()) {
			externalConfig = YamlConfiguration.loadConfiguration(file);
		} else {
			externalConfig = null;
		}
	}

	public static void reloadExternalConfig(String language) {
		initExternalConfig(langFolder, language);
	}

	/**
	 * 引数で指定された文字列の、色変換後の文字列を返します。
	 * "{@value #altColorChar}[0-9,A-F,a-f,K-O,k-o,R|r]"で表されたカラーコードが、内部的なChatColor.COLOR_CODEに置き換えられます。
	 * @param text 色変換したい文字列
	 * @return 色変換後の文字列
	 * @see org.bukkit.ChatColor#translateAlternateColorCodes(char, String)
	 */
	public static String getColoredText(String text) {
		return ChatColor.translateAlternateColorCodes(altColorChar, text);
	}

	/**
	 * 言語ファイルから文字列を取得します。通常JAR内蔵のlangファイルから読み込まれますが、
	 * /lang/[lang].ymlにpathで指定したStringが定義されている場合は、そちらの内容が優先的に読み込まれます。
	 * 内蔵langファイル、外部langファイルどちらにも定義されていない場合に、nullを返します。
	 * @param path 読み込む文字列が定義されているPath
	 * @return 言語ファイルから読み込まれた文字列。見つからない場合はnull
	 */
	public static String getString(String path) {
		if(externalConfig != null && externalConfig.contains(path)) {
			return externalConfig.getString(path);
		}
		return internalConfig.getString(path);
	}

	/**
	 * 言語ファイルから文字列を取得します。通常jar内蔵のlangファイルから読み込まれますが、
	 * /lang/[lang].ymlにpathで指定したStringが定義されている場合は、そちらの内容が優先的に読み込まれます。
	 * @param path 読み込む文字列リストが定義されているパス
	 * @return 言語ファイルから読み込まれた文字列。見つからない場合は空のList
	 */
	public static List<String> getStringList(String path) {
		if(externalConfig != null && externalConfig.contains(path)) {
			return externalConfig.getStringList(path);
		}
		return internalConfig.getStringList(path);
	}

	/**
	 * 言語ファイルからPrefixを取得する
	 * @return 色変換なしPrefix
	 */
	public static String getPrefix() {
		return getString("prefix");
	}

	/*
	 * Information messages
	 */

	public static String[] formattedStats(Stats stats) {
		List<String> list = getStringList("statsMessage");
		for(int i=0; i<list.size(); i++) {
			list.set(i, getColoredText(list.get(i)));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String notPermitted() {
		String message = getString("notPermitted");
		return getColoredText(getPrefix() + message);
	}

	public static String cannotExecuteFromConsole() {
		String message = getString("cannotExecuteFromConsole");
		return getColoredText(getPrefix() + message);
	}

	public static String arenaNotFound(String arenaName) {
		String message = getString("arenaNotFound");
		message = message.replace("%arena%", arenaName);
		return getColoredText(getPrefix() + message);
	}

	public static String leaveMatch() {
		String message = getString("leaveMatch");
		return getColoredText(getPrefix() + message);
	}

	public static String notInMatch() {
		String message = getString("notInMatch");
		return getColoredText(getPrefix() + message);
	}

	public static String roundCountdown(int count) {
		String message = getString("roundCountdown");
		message = message.replace("%num%", Integer.toString(count));
		return getColoredText(getPrefix() + message);
	}

	public static String roundStart() {
		String message = getString("roundStart");
		return getColoredText(getPrefix() + message);
	}

	/*
	 * System error messages
	 */

	public static String arenaFormatError(String fileName) {
		String message = getString("arenaFormatError");
		message = message.replace("%fileName%", fileName);
		return message;
	}
}
