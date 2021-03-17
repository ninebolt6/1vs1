package net.ninebolt.onevsone.player;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class StatsManager {
	private File statsFolder;

	// コンストラクタに依存しない形で作れるのならSingleton化してもいいかも？
	public StatsManager(File statsFolder) {
		this.statsFolder = statsFolder;
		if(!statsFolder.exists()) {
			statsFolder.mkdirs();
		}
	}

	public Stats getStats(String uuid) {
		File file = new File(statsFolder, uuid + ".yml");
		if(!file.exists()) {
			return null;
		}
		return deserialize(file);
	}

	public Stats deserialize(File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config.getObject("stats", Stats.class);
	}

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
