package net.ninebolt.onevsone.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Stats implements ConfigurationSerializable {

	private String uuid;
	private String playerName;
	private int kills;
	private int deaths;
	private int wins;
	private int defeats;

	public Stats(String uuid, String playerName) {
		this.uuid = uuid;
		this.playerName = playerName;
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
		this.defeats = 0;
	}

	public String getUUID() {
		return uuid;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getWins() {
		return wins;
	}

	public void addWins(int num) {
		wins += num;
	}

	public int getDefeats() {
		return defeats;
	}

	public void addDefeats(int num) {
		defeats += num;
	}

	public int getKills() {
		return kills;
	}

	public void addKills(int num) {
		kills += num;
	}

	public int getDeaths() {
		return deaths;
	}

	public void addDeaths(int num) {
		deaths += num;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("playerName", playerName);
		map.put("kills", kills);
		map.put("deaths", deaths);
		map.put("wins", wins);
		map.put("defeats", defeats);
		return map;
	}

}
