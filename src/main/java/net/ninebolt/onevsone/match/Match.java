package net.ninebolt.onevsone.match;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.ninebolt.onevsone.arena.Arena;

public class Match {
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;

	private Arena arena;
	private MatchState state;
	private Player[] players;

	private MatchData data;

	/**
	 * 引数で指定したアリーナを使用するMatchを作成します。
	 * @param arena このマッチに使われるアリーナ
	 */
	public Match(Arena arena) {
		this.arena = arena;
		initMatch();
	}

	public void initMatch() {
		this.players = new Player[2];
		this.state = MatchState.WAITING;

		data = new MatchData();
	}

	/**
	 * マッチの現在のMatchDataを返します。
	 * @return このマッチの{@link MatchData}
	 */
	public MatchData getMatchData() {
		return data;
	}

	/**
	 * Matchの現在の状態を返します。
	 * @see MatchState
	 * @return マッチの状態
	 */
	public MatchState getState() {
		return state;
	}

	/**
	 * Matchの状態を設定します。
	 * @param state マッチの状態
	 */
	public void setState(MatchState state) {
		this.state = state;
	}

	/**
	 * Matchに参加しているプレイヤーを返します。
	 * @return 要素数2の配列 Player[2]。プレイヤーが参加していない場合、その要素は{@code null}になる
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Matchにプレイヤーを追加します。配列の空いている要素に追加されます。
	 * 既にプレイヤーが2人参加している場合、何もしません。
	 * @param player Matchに追加するプレイヤー
	 */
	public void addPlayer(Player player) {
		int index;
		if(players[0] == null) {
			index = 0;
		} else if(players[1] == null) {
			index = 1;
		} else {
			return;
		}

		players[index] = player;
	}

	/**
	 * Matchからプレイヤーを削除します。
	 * 引数で指定したプレイヤーがMatchに参加していない場合、何もしません。
	 * @param player Matchから削除するプレイヤー
	 */
	public void removePlayer(Player player) {
		int index;
		if(player.equals(players[0])) {
			index = 0;
		} else if(player.equals(players[1])) {
			index = 1;
		} else {
			return;
		}

		players[index] = null;
	}

	/**
	 * 引数で指定したプレイヤーの相手プレイヤーを取得します。
	 * @param player 相手を取得したいプレイヤー
	 * @return 相手が存在した場合 {@link org.bukkit.entity.Player}。プレイヤーがMatchに参加していない、もしくは相手が参加していない場合 {@code null}
	 */
	public Player getOpponent(Player player) {
		if(player.equals(players[0])) {
			return players[1];
		}

		if(player.equals(players[1])) {
			return players[0];
		}

		return null;
	}

	/**
	 * 引数で指定したプレイヤーの、マッチにおける識別番号を取得します。
	 * @param player 番号を取得するプレイヤー
	 * @return {@link Match#PLAYER_ONE}もしくは{@link Match#PLAYER_TWO}。プレイヤーがMatchに参加していない場合 {@code -1}
	 */
	public int getPlayerNumber(Player player) {
		if(player.equals(players[0])) {
			return PLAYER_ONE;
		}

		if(player.equals(players[1])) {
			return PLAYER_TWO;
		}

		return -1;
	}

	/**
	 * Matchで使われるArenaを取得します。
	 * @return Arenaのインスタンス
	 */
	public Arena getArena() {
		return arena;
	}

	/**
	 * Matchが一杯かどうかを返します。
	 * @return Matchに空きがある場合は{@code true}。それ以外は{@code false}
	 */
	public boolean isFull() {
		return (players[0] != null) && (players[1] != null);
	}

	/**
	 * プレイヤーがMatchに参加できるかどうかを返します。
	 * @return Arenaが有効で、かつMatchStateが待機中の場合{@code true}。それ以外は{@code false}
	 */
	public boolean canJoin() {
		return arena.isEnabled() && !isFull() && (state == MatchState.WAITING);
	}

	/**
	 * Matchが開始できるかどうかを返します。
	 * @return {@link MatchState}が{@code WAITING}の状態でプレイヤーが2人揃っている場合は{@code true}。それ以外は{@code false}
	 */
	public boolean isReadyToStart() {
		return isFull() && (state == MatchState.WAITING);
	}

	/**
	 * Matchに参加しているプレイヤー全員に向けて、メッセージを送信します。
	 * @param message 送信するメッセージ
	 * @see org.bukkit.command.CommandSender#sendMessage(String)
	 */
	public void sendMessage(String message) {
		for(Player player : players) {
			if(player != null) {
				player.sendMessage(message);
			}
		}
	}

	/**
	 * Matchに参加しているプレイヤー全員に向けて、音を再生します。
	 * @param sound 再生する音
	 * @param volume 音量
	 * @param pitch 音の高さ
	 * @see org.bukkit.entity.Player#playSound(Location, Sound, float, float)
	 */
	public void playSound(Sound sound, float volume, float pitch) {
		for(Player player : players) {
			player.playSound(player.getLocation(), sound, volume, pitch);
		}
	}
}
