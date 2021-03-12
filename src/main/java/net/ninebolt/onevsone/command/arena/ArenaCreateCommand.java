package net.ninebolt.onevsone.command.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.command.ISubCommand;

public class ArenaCreateCommand implements ISubCommand {

	private static final String NAME = "create";
	private static final String PERMISSION_NODE = "1vs1.arena." + NAME;

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
		if(args.length != 3) {
			sender.sendMessage(ChatColor.RED + "/1vs1 arena create [arenaName]");
			return true;
		}

		ArenaManager manager = OneVsOne.getArenaManager();
		if(manager.contains(args[2])) {
			sender.sendMessage(ChatColor.RED + "そのアリーナはすでに存在しています");
			return true;
		}

		Arena arena = new Arena(args[2]);
		manager.register(args[2], arena);
		sender.sendMessage(ChatColor.GREEN + "アリーナ: " + args[2] + " を作成しました");
		return true;
	}

}
