package net.ninebolt.onevsone.match;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;

public class Match {
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;
	private final int MAX_ROUND = 2;

	private Arena arena;
	private MatchState state;
	private Player[] players;

	private MatchData data;
	private Location[] locCache;
	private PlayerInventory[] invCache;

	/**
	 * 引数で指定したアリーナを使用するMatchを作成します。
	 * @param arena このマッチに使われるアリーナ
	 */
	public Match(Arena arena) {
		this.arena = arena;
		this.players = new Player[2];
		this.state = MatchState.WAITING;

		data = new MatchData();
		locCache = new Location[2];
		invCache = new PlayerInventory[2];
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
		if(players[0] != null && players[1] != null) {
			return;
		}

		if(players[0] == null) {
			players[0] = player;
			invCache[0] = player.getInventory();
			locCache[0] = player.getLocation();
			player.teleport(getArena().getArenaSpawn().getLocation(PLAYER_ONE));
		} else if(players[1] == null) {
			players[1] = player;
			invCache[1] = player.getInventory();
			locCache[1] = player.getLocation();
			player.teleport(getArena().getArenaSpawn().getLocation(PLAYER_TWO));
		}
	}

	/**
	 * Matchからプレイヤーを削除します。
	 * 引数で指定したプレイヤーがMatchに参加していない場合、何もしません。
	 * @param player Matchから削除するプレイヤー
	 */
	public void removePlayer(Player player) {
		// TODO player.equals(players[0])に変更する(この場合null判定はいらない)
		if(players[0] != null && players[0].equals(player)) {
			players[0].getInventory().clear();
			players[0].getInventory().setContents(invCache[0].getContents());
			players[0].getInventory().setArmorContents(invCache[0].getArmorContents());
			players[0].getInventory().setExtraContents(invCache[0].getExtraContents());
			players[0].teleport(locCache[0]);
			players[0] = null;
		}

		if(players[1] != null && players[1].equals(player)) {
			players[1].getInventory().clear();
			players[1].getInventory().setContents(invCache[1].getContents());
			players[1].getInventory().setArmorContents(invCache[1].getArmorContents());
			players[1].getInventory().setExtraContents(invCache[1].getExtraContents());
			players[1].teleport(locCache[1]);
			players[1] = null;
		}
	}

	/**
	 * 引数で指定したプレイヤーの相手プレイヤーを取得します。
	 * @param player 相手を取得したいプレイヤー
	 * @return 相手が存在した場合 {@link org.bukkit.entity.Player}。プレイヤーがMatchに参加していない、もしくは相手が参加していない場合 {@code null}
	 */
	public Player getOpponent(Player player) {
		if(players[0] != null && players[0].equals(player)) {
			return players[1];
		}

		if(players[1] != null && players[1].equals(player)) {
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
		if(players[0].equals(player)) {
			return PLAYER_ONE;
		}

		if(players[1].equals(player)) {
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
	 * プレイヤーがMatchに参加できるかどうかを返します。
	 * @return Arenaが有効で、かつMatchStateが待機中の場合{@code true}。それ以外は{@code false}
	 */
	public boolean isJoinable() {
		return (arena.isEnabled() && state.equals(MatchState.WAITING));
	}

	/**
	 * Matchが開始できるかどうかを返します。
	 * @return {@link MatchState}が{@code WAITING}の状態でプレイヤーが2人揃っている場合は{@code true}。それ以外は{@code false}
	 */
	public boolean isReadyToStart() {
		return (players[0] != null && players[1] != null && getState() == MatchState.WAITING);
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

	/**
	 * マッチを開始します。プレイヤーが2人参加していることを{@link #isReadyToStart()}で確認してから開始してください。
	 */
	public void start() {
		for(Player player : players) {
			player.getInventory().clear();
			player.getInventory().setContents(getArena().getInventory().getContents());
			player.getInventory().setArmorContents(getArena().getInventory().getArmorContents());
			player.getInventory().setExtraContents(getArena().getInventory().getExtraContents());
			player.setGameMode(GameMode.SURVIVAL);
			getMatchData().initData(player);
		}

		MatchTimer timer = new MatchTimer(this);
		timer.getCountdownTimer(3).runTaskTimerAsynchronously(OneVsOne.getInstance(), 0, 20);
		state = MatchState.INGAME;
		getMatchData().setRound(1);
	}

	/**
	 * マッチを停止します。プレイヤーは参加直前に居た状態(インベントリ、位置)に戻ります。
	 * @see #removePlayer(Player)
	 */
	public void stop() {
		sendMessage("マッチ終了！");
		for(Player player : players) {
			if(player != null) {
				removePlayer(player);
			}
		}
		state = MatchState.WAITING;
		data = new MatchData();
	}

	/**
	 * 次のラウンドを開始します。最後のラウンドが終わり呼び出された場合には、
	 * {@link #stop()}が呼び出されマッチが終了します。
	 */
	public void startNextRound() {
		if(getMatchData().getRound() > MAX_ROUND) {
			stop();
			return;
		}
		getMatchData().setRound(getMatchData().getRound()+1);
		sendMessage("Match " + getMatchData().getRound() + " start!");

		for(Player player : players) {
			player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			// set inventory
			player.teleport(getArena().getArenaSpawn().getLocation(getPlayerNumber(player)));
		}
	}
}
