package net.ninebolt.onevsone.util;

import java.io.File;

import net.ninebolt.onevsone.Configurable;

public class UUIDCache extends Configurable {

	public UUIDCache(File dataFolder) {
		super(new File(dataFolder, "UUIDCache.yml"));
	}

	public String getUUIDByName(String playerName) {
		return getConfig().getString(playerName);
	}

	public void set(String playerName, String uuid) {
		getConfig().set(playerName, uuid);
	}
}
