package net.ninebolt.onevsone.stats;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class StatsManager {
	private File statsFolder;

	/**
	 * 引数で指定したフォルダのStatsを管理するためのインスタンスを作成します。
	 * @param statsFolder Statsファイルが保存されているフォルダ
	 */
	// 引数に依存しない形で作れるのならSingleton化してもいいかも？
	public StatsManager(File statsFolder) {
		this.statsFolder = statsFolder;
		if(!statsFolder.exists()) {
			statsFolder.mkdirs();
		}
	}

	/**
	 * 引数で指定されたUUIDからそのプレイヤーのStatsを取得します。
	 * @param uuid 取得したいプレイヤーのUUID
	 * @return そのプレイヤーのStatsオブジェクト。見つからない場合 null
	 */
	public Stats getStats(String uuid) {
		File file = new File(statsFolder, uuid + ".yml");
		if(!file.exists()) {
			return null;
		}
		return deserialize(file);
	}

	/**
	 * ファイルに保存されたオブジェクトを、Statsオブジェクトとして読み込みます。
	 * @param file Statsが保存されているファイル
	 * @return 読み込まれたStatsオブジェクトのインスタンス
	 */
	public Stats deserialize(File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config.getObject("stats", Stats.class);
	}

	/**
	 * 引数で指定したUUIDを、statsFolderに保存します。
	 * @param uuid プレイヤーのUUID
	 * @param stats プレイヤーのStats
	 */
	public void save(String uuid, Stats stats) {
		if(stats == null) {
			// error
			System.out.println("stats save error");
		}

		File file = new File(statsFolder, uuid + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		config.set("stats", stats);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
