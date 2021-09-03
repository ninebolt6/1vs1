package net.ninebolt.onevsone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaInventory;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.arena.ArenaSpawn;
import net.ninebolt.onevsone.command.GameGUICommand;
import net.ninebolt.onevsone.command.RootCommand;
import net.ninebolt.onevsone.event.CacheUniqueIdListener;
import net.ninebolt.onevsone.event.CreateStatsListener;
import net.ninebolt.onevsone.event.MatchEndEvent;
import net.ninebolt.onevsone.event.MatchListener;
import net.ninebolt.onevsone.match.Match;
import net.ninebolt.onevsone.match.MatchEndCause;
import net.ninebolt.onevsone.match.MatchManager;
import net.ninebolt.onevsone.match.MatchSelector;
import net.ninebolt.onevsone.stats.Stats;
import net.ninebolt.onevsone.stats.StatsManager;
import net.ninebolt.onevsone.util.Messages;
import net.ninebolt.onevsone.util.UUIDCache;

public class OneVsOne extends JavaPlugin {
	private static OneVsOne instance;
	private static UUIDCache cache;
	private static ArenaManager arenaManager;
	private static StatsManager statsManager;

	private RootCommand rootCommand;
	private GameGUICommand guiCommand;

	/**
	 * MainクラスであるOneVsOneクラスのインスタンスを取得するメソッド
	 * @return OneVsOneクラスのインスタンス
	 */
	public static OneVsOne getInstance() {
		return instance;
	}

	/**
	 * プラグイン有効化時に呼ばれるメソッド
	 */
	@Override
	public void onEnable() {
		instance = this;

		// コンフィグの設定
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		try {
			Messages.initInternalConfig(getResource("lang/" + getConfig().getString("lang") + ".yml"));
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			getLogger().warning(e.getMessage());
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Messages.initExternalConfig(new File(getDataFolder(), "/lang/"), getConfig().getString("lang"));

		// シリアライズ登録
		ConfigurationSerialization.registerClass(ArenaSpawn.class);
		ConfigurationSerialization.registerClass(ArenaInventory.class);
		ConfigurationSerialization.registerClass(Arena.class);
		ConfigurationSerialization.registerClass(Stats.class);

		// 各種クラスのインスタンス生成
		cache = new UUIDCache(getDataFolder());
		arenaManager = new ArenaManager(new File(getDataFolder(), "/arena/"));
		statsManager = new StatsManager(new File(getDataFolder(), "/stats/"));

		rootCommand = new RootCommand();
		guiCommand = new GameGUICommand();

		// イベントリスナーの登録
		getServer().getPluginManager().registerEvents(new MatchSelector(), this);
		getServer().getPluginManager().registerEvents(new MatchListener(), this);
		getServer().getPluginManager().registerEvents(new CreateStatsListener(), this);
		getServer().getPluginManager().registerEvents(new CacheUniqueIdListener(), this);
	}

	/**
	 * プラグイン無効化時に呼ばれるメソッド
	 */
	public void onDisable() {
		MatchManager matchManager = MatchManager.getInstance();

		// MatchSelectorを閉じる
		for(InventoryView view : MatchSelector.getInventoryViewList()) {
			view.close();
		}
		// すべてのマッチを安全に無効化
		for(Match match : matchManager.getMatches()) {
			MatchEndEvent event = new MatchEndEvent(match, MatchEndCause.INTERRUPTED);
			Bukkit.getPluginManager().callEvent(event);
			for(Player player : match.getPlayers()) {
				matchManager.leave(player);
			}
		}
	}

	/**
	 * UUIDCacheのインスタンスを取得するメソッド
	 * @return UUIDCacheのインスタンス
	 */
	public static UUIDCache getUUIDCache() {
		return cache;
	}

	/**
	 * ArenaManagerのインスタンスを取得するメソッド
	 * @return ArenaManagerのインスタンス
	 */
	public static ArenaManager getArenaManager() {
		return arenaManager;
	}

	/**
	 * StatsManagerのインスタンスを取得するメソッド
	 * @return StatsManagerのインスタンス
	 */
	public static StatsManager getStatsManager() {
		return statsManager;
	}

	/**
	 * コマンド実行時はじめに呼ばれるメソッド
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equals("1vs1")) {
			if(args.length == 0) {
				return guiCommand.execute(sender, label);
			} else {
				return rootCommand.execute(sender, label, args);
			}
		}
		return false;
	}
}
