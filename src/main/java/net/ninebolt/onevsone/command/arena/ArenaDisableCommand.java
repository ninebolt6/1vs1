package net.ninebolt.onevsone.command.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.command.ISubCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaDisableCommand implements ISubCommand {

	private static final String NAME = "disable";
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
		// /1vs1 arena disable [arenaName]
		if(args.length != 3) {
			// show usage
			sender.sendMessage(ChatColor.RED + "/1vs1 arena disable [arena]");
			return true;
		}

		ArenaManager manager = OneVsOne.getArenaManager();
		if(!manager.contains(args[2])) {
			sender.sendMessage(Messages.arenaNotFound(args[2]));
			return true;
		}

		Arena arena = manager.getArena(args[2]);
		if(!arena.isEnabled()) {
			sender.sendMessage(ChatColor.GREEN + "そのアリーナはすでに無効になっています！");
			return true;
		}

		arena.setEnabled(false);
		manager.save(arena, args[2]);
		sender.sendMessage(ChatColor.GREEN + "アリーナ: " + args[2] + " を無効にしました");
		return true;
	}

}
