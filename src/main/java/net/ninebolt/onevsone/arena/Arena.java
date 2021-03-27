package net.ninebolt.onevsone.arena;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Arena implements ConfigurationSerializable {

	private String name;
	private String displayName;
	private ArenaInventory arenaInventory;
	private boolean enabled;

	private ArenaSpawn spawn;

	/**
	 * 空のArenaを作成します。
	 * @param name Arena名
	 */
	public Arena(String name) {
		this.name = name;
		displayName = name;
		enabled = false;
		arenaInventory = new ArenaInventory();
		spawn = new ArenaSpawn(null, null);
	}

	/**
	 * Mapに保存された情報をArenaオブジェクトにデシリアライズします。
	 * @param map Arenaの情報が保存されているMap
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	public Arena(Map<String, Object> map) {
		name = (String) map.get("displayName");
		displayName = (String) map.get("displayName");
		enabled = (boolean) map.get("enabled");
		arenaInventory = (ArenaInventory) map.get("inventory");
		spawn = (ArenaSpawn) map.get("spawn");
	}

	/**
	 * Arenaの名前(拡張子なしファイル名)を取得します。
	 * @return Arenaの名前(拡張子なしファイル名)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Arenaの名前(拡張子なしファイル名)を設定します。
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Arenaの表示名を取得します。カラーコード処理は別に行う必要があります。
	 * @return
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Arenaの表示名を設定します。
	 * @param displayName Arenaの表示名
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Arenaが有効かどうかを返します。
	 * @return Arenaが有効なら{@code true}。そうでなければ {@code false}
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Arenaの有効化状態を設定します。
	 * @param enabled 有効かどうか
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Arenaで使われるインベントリの情報が保持されているArenaInventoryを取得します。
	 * @return インベントリの情報
	 */
	public ArenaInventory getInventory() {
		return arenaInventory;
	}

	/**
	 * Arenaで使われるインベントリの情報を設定します。
	 * @param inventory インベントリの情報
	 */
	public void setInventory(ArenaInventory inventory) {
		this.arenaInventory = inventory;
	}

	/**
	 * Arenaのスポーンを取得します
	 * @return スポーン情報
	 */
	public ArenaSpawn getArenaSpawn() {
		return spawn;
	}

	/**
	 * このArenaのスポーンを設定します。
	 * @param spawn スポーンの情報
	 */
	public void setArenaSpawn(ArenaSpawn spawn) {
		this.spawn = spawn;
	}

	/**
	 * Arenaオブジェクトをシリアライズします。
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("displayName", displayName);
		map.put("enabled", enabled);
		map.put("inventory", arenaInventory);
		map.put("spawn", spawn);
		return map;
	}

}
