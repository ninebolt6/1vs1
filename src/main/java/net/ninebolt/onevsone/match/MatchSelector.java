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
	private static final String INVENTORY_NAME = "&bMatch Selector";

	private static Map<Player, InventoryView> openList = new HashMap<Player, InventoryView>();
	private Inventory inventory;

	public MatchSelector() {
		inventory = Bukkit.createInventory(null, ROW*9, Messages.getColoredText(INVENTORY_NAME));
		initContents(inventory);
	}

	/**
	 * Matchの状態を元に、インベントリにMatch選択用のアイテムを追加する
	 */
	public void initContents(Inventory inv) {
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
			inv.addItem(arenaItem);
		}
	}

	/**
	 * Match選択に使われるインベントリを取得します。
	 * @return Match選択用のインベントリ
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * 今開かれているMatchSelectorのCollection<InventoryView>を返します。
	 * @return InventoryViewのCollection
	 * @see org.bukkit.inventory.InventoryView
	 */
	public static Collection<InventoryView> getInventoryViewList() {
		return openList.values();
	}

	/**
	 * 引数で指定したプレイヤーに対してMatch選択インベントリを開き、PlayerとInventoryViewの組を{@link #openList}に登録します。
	 * @param player インベントリを開くプレイヤー
	 */
	public void open(Player player) {
		InventoryView view = player.openInventory(inventory);
		openList.put(player, view);
	}

	/**
	 * MatchSelectorがクリックされたときに呼び出されるメソッドです。
	 * @param event インベントリをクリックした際に呼び出されるイベント
	 */
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

		if(matchManager.isPlaying(player)) {
			return;
		}

		matchManager.join(player, match);
		if(match.isReadyToStart()) {
			match.start();
		}
	}

	/**
	 * MatchSelectorが閉じられたときに呼び出されるメソッドです。
	 * @param event インベントリを閉じた際に呼び出されるイベント
	 */
	@EventHandler
	public void onSelectorClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if(openList.containsKey(player) && (event.getView().equals(openList.get(player)))) {
			openList.remove(player);
		}
	}

	/**
	 * 引数で指定した情報に基づきItemStackを作成します。
	 * @param material アイテムの材料
	 * @param name アイテムの名前
	 * @param lore アイテムの説明
	 * @return 作成されたアイテムのItemStack
	 */
	protected ItemStack createItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * 引数で指定されたItemStackに対して、Arenaの情報を紐付けるデータを付与します。
	 * @param item Matchを示すアイテム
	 * @param arena 紐付けるArena
	 */
	protected void setArenaMeta(ItemStack item, final Arena arena) {
		NamespacedKey key = new NamespacedKey(OneVsOne.getInstance(), "arenaName");
		ItemMeta meta = item.getItemMeta();
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, arena.getName());
		item.setItemMeta(meta);
	}
}
