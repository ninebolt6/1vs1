package net.ninebolt.onevsone.match;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ninebolt.onevsone.util.Messages;

public class MatchSelector implements Listener {

	private static final int ROW = 3;
	private static final String INVENTORY_NAME = "&bGame Selector";

	private static Map<Player, InventoryView> openList = new HashMap<Player, InventoryView>();
	private Inventory inventory;

	public MatchSelector() {
		inventory = Bukkit.createInventory(null, ROW*9, Messages.getColoredText(INVENTORY_NAME));
		initContents();
	}

	public void initContents() {
		MatchManager matchManager = MatchManager.getInstance();
		for(Match match : matchManager.getMatches()) {
			ItemStack arenaItem;
			if(match.canJoin()) {
				arenaItem = createItem(Material.GREEN_WOOL, match.getArena().getDisplayName(), "参加可能");
			} else {
				arenaItem = createItem(Material.RED_WOOL, match.getArena().getDisplayName(), "参加不可");
			}
			inventory.addItem(arenaItem);
		}
	}

	protected ItemStack createItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void open(Player player) {
		InventoryView view = player.openInventory(inventory);
		openList.put(player, view);
	}

	@EventHandler
	public void onSelectorClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clickedItem = event.getCurrentItem();

		if(!openList.containsKey(player)) {
			return;
		}

		if(clickedItem == null || !event.getView().equals(openList.get(player))) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onSelectorClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if(openList.containsKey(player) && (event.getView().equals(openList.get(player)))) {
			openList.remove(player);
		}
	}
}
