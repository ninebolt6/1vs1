package net.ninebolt.onevsone.arena;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;

import net.ninebolt.onevsone.match.Match;

public class Arena implements ConfigurationSerializable {

	private String displayName;
	private boolean enabled;
	private Inventory inventory;
	private Location[] spawns;

	public Arena(String name) {
		displayName = name;
		enabled = false;
		inventory = null;
		spawns = null;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Location getSpawnLoc(int playerNum) {
		if(playerNum != Match.PLAYER_ONE && playerNum != Match.PLAYER_TWO) {
			throw new NumberFormatException();
		} else {
			return spawns[playerNum];
		}
	}

	public Location[] getSpawns() {
		return spawns;
	}

	public void setSpawnLoc(Location loc, int playerNum) {
		if(playerNum != Match.PLAYER_ONE && playerNum != Match.PLAYER_TWO) {
			throw new NumberFormatException();
		} else {
			spawns[playerNum] = loc;
		}
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("displayName", displayName);
		map.put("enabled", enabled);
		map.put("inventory", inventory);
		map.put("spawns", spawns);
		return map;
	}

	public static Arena deserialize(File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		Arena arena = config.getObject("arena", Arena.class);
		return arena;
	}

}
