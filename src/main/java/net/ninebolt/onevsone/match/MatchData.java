package net.ninebolt.onevsone.match;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class MatchData {
	private int round;
	private Map<Player, Integer> killMap;
	private Map<Player, Integer> deathMap;

	/**
	 * 空のMatchDataを作成します。
	 */
	public MatchData() {
		this.round = 0;
		this.killMap = new HashMap<Player, Integer>();
		this.deathMap = new HashMap<Player, Integer>();
	}

	public void initData(Player player) {
		killMap.put(player, 0);
		deathMap.put(player, 0);
	}

	/**
	 * 現在のラウンド数を返します。
	 * @return ラウンド数
	 */
	public int getRound() {
		return round;
	}

	/**
	 * ラウンド数を設定します。
	 * @param round 設定するラウンド数
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * 引数で指定したプレイヤーの、Match内でのキル数を取得します。
	 * @param player キル数を取得するプレイヤー
	 * @return プレイヤーのキル数
	 */
	public int getKill(Player player) {
		return killMap.get(player);
	}

	/**
	 * 引数で指定したプレイヤーの、Match内でのデス数を取得します。
	 * @param player デス数を取得するプレイヤー
	 * @return プレイヤーのデス数
	 */
	public int getDeath(Player player) {
		return deathMap.get(player);
	}

	/**
	 * 引数で指定したプレイヤーの、Match内でのキル数を設定します。
	 * @param player キル数を設定するプレイヤー
	 * @param kill プレイヤーのキル数
	 */
	public void setKill(Player player, int kill) {
		killMap.put(player, kill);
	}

	/**
	 * 引数で指定したプレイヤーの、Match内でのデス数を設定します。
	 * @param player デス数を設定するプレイヤー
	 * @param kill プレイヤーのデス数
	 */
	public void setDeath(Player player, int death) {
		deathMap.put(player, death);
	}
}
