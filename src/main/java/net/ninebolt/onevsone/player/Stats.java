package net.ninebolt.onevsone.player;

import java.io.Serializable;

public class Stats implements Serializable {

	private static final long serialVersionUID = 1L;

	private String playerName;
	//private String uuid;
	private int kills;
	private int deaths;
	private int wins;
	private int defeats;

	public Stats(String uuid, String playerName) {
		//super(new File(OneVsOne.getInstance().getDataFolder(), "/arena/" + uuid + ".yml"));
		this.playerName = playerName;
		//this.uuid = uuid;
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
		this.defeats = 0;
	}

	public Stats(String uuid) {
		//super(new File(OneVsOne.getInstance().getDataFolder(), "/arena/" + uuid + ".yml"));
		//this.playerName = playerName;
		//this.uuid = uuid;
		this.kills = 0;
		this.deaths = 0;
		this.wins = 0;
		this.defeats = 0;
	}

	/*public String getUUID() {
		return uuid;
	}*/

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public static Stats getStats(String uuid2) {
		return null;
	}

	public static boolean exists(String string) {
		// TODO
		return false;
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

	public void save() {

	}

}
