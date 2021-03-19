package net.ninebolt.onevsone.match;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class MatchData {
	private int round;
	private Map<Player, Integer> killMap;
	private Map<Player, Integer> deathMap;

	public MatchData() {
		this.round = 0;
		this.killMap = new HashMap<Player, Integer>();
		this.deathMap = new HashMap<Player, Integer>();
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;;
	}

	public int getKill(Player player) {
		return killMap.get(player);
	}

	public int getDeath(Player player) {
		return deathMap.get(player);
	}

	public void setKill(Player player, int kill) {
		killMap.put(player, kill);
	}

	public void setDeath(Player player, int death) {
		deathMap.put(player, death);
	}
}
