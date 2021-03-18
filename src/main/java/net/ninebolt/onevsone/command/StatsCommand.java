package net.ninebolt.onevsone.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.stats.Stats;
import net.ninebolt.onevsone.stats.StatsManager;
import net.ninebolt.onevsone.util.Messages;

public class StatsCommand implements SubCommand {

	private static final String NAME = "stats";
	private static final String PERMISSION_NODE = "1vs1." + NAME;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getPermissionNode() {
		return PERMISSION_NODE;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(args.length == 1) {
			// show stats for player self
			if(!sender.hasPermission("1vs1.command.stats.self")) {
				sender.sendMessage(Messages.notPermitted());
				return true;
			}

			if(!(sender instanceof Player)) {
				sender.sendMessage(Messages.cannotExecuteFromConsole());
				return true;
			}

			StatsManager manager = OneVsOne.getStatsManager();
			Stats stats = manager.getStats(((Player)sender).getUniqueId().toString());
			sender.sendMessage(Messages.formattedStats(stats));
			return true;

		} else if(args.length == 2) {
			if(!sender.hasPermission("1vs1.command.stats.others")) {
				sender.sendMessage(Messages.notPermitted());
				return true;
			}

			//プレイヤー名からUUIDを取得 from UUIDCache
			String uuid = OneVsOne.getUUIDCache().getUUIDByName(args[1]);
			if(uuid == null) {
				sender.sendMessage("戦績が見つかりませんでした。");
				return true;
			}

			StatsManager manager = OneVsOne.getStatsManager();
			Stats stats = manager.getStats(uuid);
			sender.sendMessage(Messages.formattedStats(stats));
			return true;

		} else {
			// show usage
			sender.sendMessage("/1vs1 stats | /1vs1 stats [playerName]");
			return true;
		}
	}

}
