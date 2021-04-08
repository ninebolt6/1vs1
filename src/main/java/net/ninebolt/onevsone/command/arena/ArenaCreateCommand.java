package net.ninebolt.onevsone.command.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.command.SubCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaCreateCommand implements SubCommand {

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
			sender.sendMessage(Messages.arenaAlreadyExists(args[2]));
			return true;
		}

		Arena arena = new Arena(args[2]);
		manager.register(arena);
		sender.sendMessage(Messages.createdArena(args[2]));
		return true;
	}

}
