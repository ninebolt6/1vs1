package net.ninebolt.onevsone.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;

public class MatchManager {

	private static final MatchManager instance = new MatchManager();

	private static List<Match> matches;
	private static Map<Player, Match> playerMap;
	private static ArenaManager arenaManager;

	private MatchManager() {
		matches = new ArrayList<Match>();
		playerMap = new HashMap<Player, Match>();
		arenaManager = ArenaManager.getInstance();
		initMatches();
	}

	public static MatchManager getInstance() {
		return instance;
	}

	protected void initMatches() {
		matches.clear();
		for(Arena arena : arenaManager.getArenaList()) {
			matches.add(new Match(arena));
		}
	}

	public List<Match> getMatches() {
		return matches;
	}

	public boolean isPlaying(Player player) {
		return playerMap.containsKey(player);
	}

	public Match getMatch(Player player) {
		if(!isPlaying(player)) {
			return null;
		}
		return getPlayerMap().get(player);
	}

	public Map<Player, Match> getPlayerMap() {
		return playerMap;
	}
}
