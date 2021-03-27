package net.ninebolt.onevsone.arena;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import net.ninebolt.onevsone.match.Match;

public class ArenaSpawn implements ConfigurationSerializable {

	private Location spawn1;
	private Location spawn2;

	/**
	 * アリーナで使われるスポーン地点の組を作成します。
	 * @param spawn1 プレイヤー1用のスポーン地点
	 * @param spawn2 プレイヤー2用のスポーン地点
	 */
	public ArenaSpawn(Location spawn1, Location spawn2) {
		this.spawn1 = spawn1;
		this.spawn2 = spawn2;
	}

	/**
	 * Mapに保存された情報をArenaSpawnオブジェクトにデシリアライズします。
	 * @param map ArenaSpawnの情報が保存されているMap
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	public ArenaSpawn(Map<String, Object> map) {
		this.spawn1 = (Location) map.get("spawn1");
		this.spawn2 = (Location) map.get("spawn2");
	}

	/**
	 * 引数で指定したプレイヤー番号用のスポーン位置を取得します。
	 * @param playerNum 取得したいスポーンの番号
	 * @return 要求されたプレイヤーのスポーン位置。それ以外は {@code null}
	 */
	public Location getLocation(int playerNum) {
		if(playerNum == Match.PLAYER_ONE) {
			return spawn1;
		}

		if(playerNum == Match.PLAYER_TWO) {
			return spawn2;
		}

		return null;
	}

	/**
	 * 引数で指定したプレイヤー番号用のスポーン位置を設定します。
	 * @param playerNum 設定したいスポーンの番号
	 * @param location 設定するスポーンの位置
	 */
	public void setSpawn(int playerNum, Location location) {
		if(playerNum == Match.PLAYER_ONE) {
			spawn1 = location;
		}

		if(playerNum == Match.PLAYER_TWO) {
			spawn2 = location;
		}
	}

	/**
	 * ArenaSpawnオブジェクトをシリアライズします。
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("spawn1", spawn1);
		map.put("spawn2", spawn2);
		return map;
	}

}
