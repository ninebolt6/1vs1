package net.ninebolt.onevsone.arena;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;

public class Arena implements ConfigurationSerializable {

	private String name;
	private String displayName;
	private boolean enabled;
	private Inventory inventory;
	private ArenaSpawn spawn;

	public Arena(String name) {
		this.name = name;
		displayName = name;
		enabled = false;
		inventory = null;
		spawn = new ArenaSpawn(null, null);
	}

	public Arena(Map<String, Object> map) {
		name = (String) map.get("displayName");
		displayName = (String) map.get("displayName");
		enabled = (boolean) map.get("enabled");
		inventory = (Inventory) map.get("inventory");
		spawn = (ArenaSpawn) map.get("spawn");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public ArenaSpawn getArenaSpawn() {
		return spawn;
	}

	public void setArenaSpawn(ArenaSpawn spawn) {
		this.spawn = spawn;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("displayName", displayName);
		map.put("enabled", enabled);
		map.put("inventory", inventory);
		map.put("spawn", spawn);
		return map;
	}

}
