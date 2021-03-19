package net.ninebolt.onevsone.match;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
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
			Arena arena = match.getArena();
			if(match.canJoin()) {
				arenaItem = createItem(Material.GREEN_WOOL, arena.getDisplayName(), "参加可能", match.getState().toString());
			} else {
				arenaItem = createItem(Material.RED_WOOL, arena.getDisplayName(), "参加不可", match.getState().toString());
			}
			setArenaMeta(arenaItem, arena);
			inventory.addItem(arenaItem);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public static Collection<InventoryView> getInventoryViewList() {
		return openList.values();
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

		PersistentDataContainer container = clickedItem.getItemMeta().getPersistentDataContainer();
		NamespacedKey key = new NamespacedKey(OneVsOne.getInstance(), "arenaName");
		if(!container.has(key, PersistentDataType.STRING)) {
			return;
		}

		ArenaManager arenaManager = OneVsOne.getArenaManager();
		Arena arena = arenaManager.getArena(container.get(key, PersistentDataType.STRING));
		MatchManager matchManager = MatchManager.getInstance();
		Match match = matchManager.getMatch(arena);
		matchManager.join(player, match);
		if(match.canStart()) {
			match.start();
		}
	}

	@EventHandler
	public void onSelectorClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if(openList.containsKey(player) && (event.getView().equals(openList.get(player)))) {
			openList.remove(player);
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

	protected void setArenaMeta(ItemStack item, final Arena arena) {
		NamespacedKey key = new NamespacedKey(OneVsOne.getInstance(), "arenaName");
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, arena.getName());
		item.setItemMeta(meta);
	}
}
