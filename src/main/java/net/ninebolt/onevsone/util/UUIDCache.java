package net.ninebolt.onevsone.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class UUIDCache {

	private static final String FILE_NAME = "uuidcache.yml";
	private File dataFolder;
	private YamlConfiguration cache;

	public UUIDCache(File dataFolder) {
		this.dataFolder = dataFolder;
		File cacheFile = new File(dataFolder, FILE_NAME);
		if(!cacheFile.exists()) {
			try {
				cacheFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.cache = YamlConfiguration.loadConfiguration(cacheFile);
	}

	public String getUUIDByName(String playerName) {
		return cache.getString(playerName);
	}

	public void save() {
		File cacheFile = new File(dataFolder, FILE_NAME);
		try {
			cache.save(cacheFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void set(String playerName, String uuid) {
		cache.set(playerName, uuid);
	}
}
