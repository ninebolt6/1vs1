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

	public ArenaInventory() {
		contents = new ItemStack[27];
		armorContents = new ItemStack[4];
		extraContents = new ItemStack[2];
	}

	public ArenaInventory(Map<String, Object> map) {
		List<?> contentsList = (List<?>) map.get("contents");
		List<?> armorContentsList = (List<?>) map.get("armorContents");
		List<?> extraContentsList = (List<?>) map.get("extraContents");

		this.contents = contentsList.toArray(new ItemStack[contentsList.size()]);
		this.armorContents = armorContentsList.toArray(new ItemStack[armorContentsList.size()]);
		this.extraContents = extraContentsList.toArray(new ItemStack[extraContentsList.size()]);
	}

	public void setContents(ItemStack[] contents) {
		this.contents = contents;
	}

	public ItemStack[] getContents() {
		return contents;
	}

	public void setArmorContents(ItemStack[] armorContents) {
		this.armorContents = armorContents;
	}

	public ItemStack[] getArmorContents() {
		return armorContents;
	}

	public void setExtraContents(ItemStack[] extraContents) {
		this.extraContents = extraContents;
	}

	public ItemStack[] getExtraContents() {
		return extraContents;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contents", contents);
		map.put("armorContents", armorContents);
		map.put("extraContents", extraContents);
		return map;
	}

}
