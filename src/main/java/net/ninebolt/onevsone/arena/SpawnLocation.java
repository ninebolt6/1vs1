package net.ninebolt.onevsone.arena;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import net.ninebolt.onevsone.match.Match;

public class SpawnLocation implements ConfigurationSerializable {

	private Location spawn1;
	private Location spawn2;

	public SpawnLocation(Location spawn1, Location spawn2) {
		this.spawn1 = spawn1;
		this.spawn2 = spawn2;
	}

	public SpawnLocation(Map<String, Object> map) {
		this.spawn1 = (Location) map.get("spawn1");
		this.spawn2 = (Location) map.get("spawn2");
	}

	public Location getSpawn(int playerNum) {
		if(playerNum == Match.PLAYER_ONE) {
			return spawn1;
		}

		if(playerNum == Match.PLAYER_TWO) {
			return spawn2;
		}

		return null;
	}

	public void setSpawn(int playerNum, Location location) {
		if(playerNum == Match.PLAYER_ONE) {
			spawn1 = location;
		}

		if(playerNum == Match.PLAYER_TWO) {
			spawn2 = location;
		}
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spawn1", spawn1);
		map.put("spawn2", spawn2);
		return map;
	}

}
