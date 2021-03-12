package net.ninebolt.onevsone.arena;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;

public class Arena implements ConfigurationSerializable {

	private String displayName;
	private boolean enabled;
	private Inventory inventory;
	private SpawnLocation spawns;

	public Arena(String name) {
		displayName = name;
		enabled = false;
		inventory = null;
		spawns = new SpawnLocation(null, null);
	}

	public Arena(Map<String, Object> map) {
		displayName = (String) map.get("displayName");
		enabled = (boolean) map.get("enabled");
		inventory = (Inventory) map.get("inventory");
		spawns = (SpawnLocation) map.get("spawns");
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

	public SpawnLocation getSpawnLocations() {
		return spawns;
	}

	public void setSpawnLocation(SpawnLocation spawns) {
		this.spawns = spawns;
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

}
