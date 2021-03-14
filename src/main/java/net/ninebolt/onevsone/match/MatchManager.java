package net.ninebolt.onevsone.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;

public class MatchManager {

	private static final MatchManager INSTANCE = new MatchManager(); // Singleton

	private static List<Match> matches;
	private static Map<Player, Match> playerMap;

	private MatchManager() {
		matches = new ArrayList<Match>();
		playerMap = new HashMap<Player, Match>();
		initMatches();
	}

	public static MatchManager getInstance() {
		return INSTANCE;
	}

	protected void initMatches() {
		ArenaManager arenaManager = OneVsOne.getArenaManager();
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

	public Match getMatch(Arena arena) {
		for(Match match : matches) {
			if(match.getArena().equals(arena)) {
				return match;
			}
		}
		return null;
	}

	public void play(Player player, Match match) {
		playerMap.put(player, match);
		match.addPlayer(player);
	}

	public void leave(Player player) {
		if(!playerMap.containsKey(player)) {
			return;
		}
		getMatch(player).removePlayer(player);
		playerMap.remove(player);
	}

	public Map<Player, Match> getPlayerMap() {
		return playerMap;
	}
}
