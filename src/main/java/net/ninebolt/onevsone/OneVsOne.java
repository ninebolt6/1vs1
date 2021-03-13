package net.ninebolt.onevsone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.arena.ArenaSpawn;
import net.ninebolt.onevsone.command.GameGUICommand;
import net.ninebolt.onevsone.command.RootCommand;
import net.ninebolt.onevsone.event.CacheUniqueIdListener;
import net.ninebolt.onevsone.event.MatchListener;
import net.ninebolt.onevsone.match.Match;
import net.ninebolt.onevsone.match.MatchManager;
import net.ninebolt.onevsone.match.MatchSelector;
import net.ninebolt.onevsone.player.Stats;
import net.ninebolt.onevsone.util.Messages;
import net.ninebolt.onevsone.util.UUIDCache;

public class OneVsOne extends JavaPlugin {
	private static OneVsOne instance;
	private static UUIDCache cache;
	private static ArenaManager arenaManager;

	private RootCommand rootCommand;
	private GameGUICommand guiCommand;

	public static OneVsOne getInstance() {
		return instance;
	}

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

		ConfigurationSerialization.registerClass(ArenaSpawn.class);
		ConfigurationSerialization.registerClass(Arena.class);
		ConfigurationSerialization.registerClass(Stats.class);

		// 各種クラスのインスタンス生成
		cache = new UUIDCache(getDataFolder());
		arenaManager = new ArenaManager(new File(getDataFolder(), "/arena/"));

		rootCommand = new RootCommand();
		guiCommand = new GameGUICommand();

		// イベントリスナーの登録
		getServer().getPluginManager().registerEvents(new MatchSelector(), this);
		getServer().getPluginManager().registerEvents(new CacheUniqueIdListener(), this);
		getServer().getPluginManager().registerEvents(new MatchListener(), this);
	}

	public static UUIDCache getUUIDCache() {
		return cache;
	}

	public static ArenaManager getArenaManager() {
		return arenaManager;
	}

	public void onDisable() {
		MatchManager matchManager = MatchManager.getInstance();
		// すべてのマッチを安全に無効化
		for(Match match : matchManager.getMatches()) {
			match.stop();
		}
	}

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
