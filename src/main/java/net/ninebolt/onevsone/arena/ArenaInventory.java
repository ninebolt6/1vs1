package net.ninebolt.onevsone.arena;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class ArenaInventory implements ConfigurationSerializable {

	private ItemStack[] contents;
	private ItemStack[] armorContents;
	private ItemStack[] extraContents;

	/**
	 * 空のArenaInventoryを作成します。
	 */
	public ArenaInventory() {
		contents = new ItemStack[27];
		armorContents = new ItemStack[4];
		extraContents = new ItemStack[2];
	}

	/**
	 * Mapに保存された情報をArenaInventoryオブジェクトにデシリアライズします。
	 * @param map ArenaInventoryの情報が保存されているMap
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	public ArenaInventory(Map<String, Object> map) {
		List<?> contentsList = (List<?>) map.get("contents");
		List<?> armorContentsList = (List<?>) map.get("armorContents");
		List<?> extraContentsList = (List<?>) map.get("extraContents");

		this.contents = contentsList.toArray(new ItemStack[contentsList.size()]);
		this.armorContents = armorContentsList.toArray(new ItemStack[armorContentsList.size()]);
		this.extraContents = extraContentsList.toArray(new ItemStack[extraContentsList.size()]);
	}

	/**
	 * インベントリのアイテムを設定します。
	 * @param contents インベントリのアイテムが入った配列 ItemStack[]
	 * @see org.bukkit.inventory.PlayerInventory#setContents(ItemStack[])
	 */
	public void setContents(ItemStack[] contents) {
		this.contents = contents;
	}

	/**
	 * インベントリのアイテムを取得します。
	 * アーマーとエクストラスロット以外のアイテムが対象です。
	 * @return インベントリのアイテムが入った配列 ItemStack[]
	 * @see org.bukkit.inventory.PlayerInventory#getContents()
	 */
	public ItemStack[] getContents() {
		return contents;
	}

	/**
	 * アーマーのアイテムを設定します。
	 * @param armorContents アーマーのアイテムが入った配列 ItemStack[]
	 * @see org.bukkit.inventory.PlayerInventory#setArmorContents(ItemStack[])
	 */
	public void setArmorContents(ItemStack[] armorContents) {
		this.armorContents = armorContents;
	}

	/**
	 * アーマーのアイテムを取得します。
	 * @return アーマースロットのアイテム
	 * @see org.bukkit.inventory.PlayerInventory#getArmorContents()
	 */
	public ItemStack[] getArmorContents() {
		return armorContents;
	}

	/**
	 * エクストラスロット(盾など)のアイテムを設定します。
	 * @param extraContents エクストラスロットのアイテムが入った配列 ItemStack[]
	 * @see org.bukkit.inventory.PlayerInventory#setExtraContents(ItemStack[])
	 */
	public void setExtraContents(ItemStack[] extraContents) {
		this.extraContents = extraContents;
	}

	/**
	 * エクストラスロット(盾など)のアイテムを取得します。
	 * @return エクストラスロットのアイテム
	 * @see org.bukkit.inventory.PlayerInventory#getExtraContents()
	 */
	public ItemStack[] getExtraContents() {
		return extraContents;
	}

	/**
	 * ArenaInventoryオブジェクトをシリアライズします。
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contents", contents);
		map.put("armorContents", armorContents);
		map.put("extraContents", extraContents);
		return map;
	}

}
