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

import net.ninebolt.onevsone.player.Stats;

public class Messages {

	private static YamlConfiguration externalConfig;
	private static YamlConfiguration internalConfig;
	private static File langFolder;

	public static void initInternalConfig(InputStream input) throws FileNotFoundException, UnsupportedEncodingException {
		if(input == null) {
			throw new FileNotFoundException("Internal language file not found. Check if your JAR file includes a language file.");
		}
		internalConfig = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(input, "UTF-8")));
	}

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

	public static String getColoredText(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String getString(String path) {
		/*
		 * /lang/[lang].ymlにpathで指定したStringが定義されている場合は、そちらを読み込む
		 * 定義されていない、もしくは[lang].ymlが存在しない場合は、jar内蔵のlangファイルから読み込む
		 */

		if(externalConfig != null && externalConfig.contains(path)) {
			return externalConfig.getString(path);
		}
		return internalConfig.getString(path);
	}

	public static List<String> getStringList(String path) {
		/*
		 * /lang/[lang].ymlにpathで指定したStringが定義されている場合は、そちらを読み込む
		 * 定義されていない、もしくは[lang].ymlが存在しない場合は、jar内蔵のlangファイルから読み込む
		 */

		if(externalConfig != null && externalConfig.contains(path)) {
			return externalConfig.getStringList(path);
		}
		return internalConfig.getStringList(path);
	}

	public static String getPrefix() {
		return getString("prefix");
	}

	public static String[] formattedStats(Stats stats) {
		List<String> list = getStringList("message.stats");
		for(int i=0; i<list.size(); i++) {
			list.set(i, getColoredText(list.get(i)));
		}
		return (String[]) list.toArray();
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

	/*
	 * System error messages
	 */

	public static String arenaFormatError(String fileName) {
		String message = getString("arenaFormatError");
		message = message.replace("%fileName%", fileName);
		return message;
	}
}
