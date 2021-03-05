package net.ninebolt.onevsone.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;

public class MatchManager {
	private static List<Match> matches;
	private static Map<Player, Match> playerMap;

	public static void initMatches() {
		matches = new ArrayList<Match>();
		playerMap = new HashMap<Player, Match>();

		for(Arena arena : ArenaManager.getArenaList()) {
			matches.add(new Match(arena));
		}
	}

	public static List<Match> getMatches() {
		return matches;
	}

	public static boolean isPlaying(Player player) {
		return playerMap.containsKey(player);
	}

	public static Match getMatch(Player player) {
		if(!isPlaying(player)) {
			return null;
		}
		return getPlayerMap().get(player);
	}

	public static Map<Player, Match> getPlayerMap() {
		return playerMap;
	}
}
