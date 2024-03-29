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

	/**
	 * MatchManagerのインスタンスを作成します。
	 * 外部からのインスタンス取得には{@link #getInstance()}を使用してください。
	 * @see MatchManager#getInstance()
	 */
	private MatchManager() {
		matches = new ArrayList<Match>();
		playerMap = new HashMap<Player, Match>();
		initMatches();
	}

	/**
	 * MatchManagerのインスタンスを取得します。
	 * @return MatchManagerのインスタンス
	 */
	public static MatchManager getInstance() {
		return INSTANCE;
	}

	/**
	 * ArenaManagerに登録されているArenaを元に、Matchを作成し管理の準備をします。
	 */
	protected void initMatches() {
		ArenaManager arenaManager = OneVsOne.getArenaManager();
		for(Arena arena : arenaManager.getArenaList()) {
			matches.add(new Match(arena));
		}
	}

	/**
	 * MatchManagerに登録されているMatchを取得します。
	 * @return 管理されているMatchのList
	 */
	public List<Match> getMatches() {
		return matches;
	}

	/**
	 * プレイヤーがMatchに参加しているかどうかを返します。
	 * @param player 調べたいプレイヤー
	 * @return Matchに参加している場合{@code true}。参加していない場合{@code false}
	 */
	public boolean isPlaying(Player player) {
		return playerMap.containsKey(player);
	}

	/**
	 * プレイヤーが参加しているMatchを取得します。
	 * @param player 対象のプレイヤー
	 * @return プレイヤーがMatchに参加している場合はその{@code Match}。参加していない場合は{@code null}
	 */
	public Match getMatch(Player player) {
		if(!isPlaying(player)) {
			return null;
		}
		return playerMap.get(player);
	}

	/**
	 * 引数で指定したArenaが使われているMatchを取得します。
	 * @param arena Matchを取得したいArena
	 * @return Arenaが使われているMatch
	 */
	public Match getMatch(Arena arena) {
		for(Match match : matches) {
			if(match.getArena().equals(arena)) {
				return match;
			}
		}
		return null;
	}

	/**
	 * Matchに参加します。
	 * @param player 参加するプレイヤー
	 * @param match 参加するMatch
	 */
	public void join(Player player, Match match) {
		if(isPlaying(player)) {
			return;
		}

		playerMap.put(player, match);
		match.addPlayer(player);
	}

	/**
	 * Matchから退出します。プレイヤーがどのMatchにも参加していない場合、何もしません。
	 * @param player 退出するプレイヤー
	 */
	public void leave(Player player) {
		if(!isPlaying(player)) {
			return;
		}

		getMatch(player).removePlayer(player);
		playerMap.remove(player);
	}

	/**
	 * Statsを保存します。Matchに参加していない場合、何もしません。
	 * @param player
	 */
	public void saveStats(Player player) {
		if(!isPlaying(player)) {
			return;
		}
	}
}
