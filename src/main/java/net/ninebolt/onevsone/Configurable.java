package net.ninebolt.onevsone;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Configurable {
	private File configFile;
	private YamlConfiguration config;

	public Configurable(File file) {
		configFile = file;
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		loadConfig();
	}

	public File getFile() {
		return configFile;
	}

	public YamlConfiguration getConfig() {
		return config;
	}

	public void loadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public void save() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
