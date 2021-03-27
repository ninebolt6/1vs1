package net.ninebolt.onevsone.stats;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Stats implements ConfigurationSerializable {

	private String playerName;
	private int kills;
	private int deaths;
	private int wins;
	private int defeats;

	/**
	 * 空のStatsが入ったインスタンスを作成します。
	 * @param playerName プレイヤー名
	 */
	public Stats(String playerName) {
		this.playerName = playerName;
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
		this.defeats = 0;
	}

	/**
	 * Mapに保存された情報をStatsオブジェクトにデシリアライズします。
	 * @param map Statsの情報が保存されているMap
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	public Stats(Map<String, Object> map) {
		this.playerName = (String) map.get("playerName");
		this.kills = (int) map.get("kills");
		this.deaths = (int) map.get("deaths");
		this.wins = (int) map.get("wins");
		this.defeats = (int) map.get("defeats");
	}

	/**
	 * このStatsを持っているプレイヤーの名前を返します。
	 * @return プレイヤー名
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * プレイヤー名をセットします。
	 * {@link StatsManager#save(String, Stats)}を実行せずにサーバーが再起動または停止した場合、変更した内容は破棄されます。
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * このStatsを持っているプレイヤーの勝利数を返します。
	 * @return 勝利数
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * 引数で指定された数だけ、勝利数を追加します。
	 * {@link StatsManager#save(String, Stats)}を実行せずにサーバーが再起動または停止した場合、変更した内容は破棄されます。
	 */
	public void addWins(int num) {
		wins += num;
	}

	/**
	 * このStatsを持っているプレイヤーの敗北数を返します。
	 * @return 敗北数
	 */
	public int getDefeats() {
		return defeats;
	}

	/**
	 * 引数で指定された数だけ、敗北数を追加します。
	 * {@link StatsManager#save(String, Stats)}を実行せずにサーバーが再起動または停止した場合、変更した内容は破棄されます。
	 */
	public void addDefeats(int num) {
		defeats += num;
	}

	/**
	 * このStatsを持っているプレイヤーのキル数を返します。
	 * @return キル数
	 */
	public int getKills() {
		return kills;
	}

	/**
	 * 引数で指定された数だけ、キル数を追加します。
	 * {@link StatsManager#save(String, Stats)}を実行せずにサーバーが再起動または停止した場合、変更した内容は破棄されます。
	 */
	public void addKills(int num) {
		kills += num;
	}

	/**
	 * このStatsを持っているプレイヤーのデス数を返します。
	 * @return デス数
	 */
	public int getDeaths() {
		return deaths;
	}

	/**
	 * 引数で指定された数だけ、デス数を追加します。
	 * {@link StatsManager#save(String, Stats)}を実行せずにサーバーが再起動または停止した場合、変更した内容は破棄されます。
	 */
	public void addDeaths(int num) {
		deaths += num;
	}

	/**
	 * Statsオブジェクトをシリアライズします。
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
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
