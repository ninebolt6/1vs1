package net.ninebolt.onevsone.command.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.command.ISubCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaSetKitCommand implements ISubCommand {

	private static final String NAME = "inv";
	private static final String PERMISSION_NODE = "1vs1.arena.set." + NAME;

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
		// /1vs1 arena set kit [arenaName]
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.cannotExecuteFromConsole());
			return true;
		}

		if(args.length != 4) {
			sender.sendMessage(ChatColor.RED + "/1vs1 arena set inv [arenaName]");
			return true;
		}

		ArenaManager manager = OneVsOne.getArenaManager();
		if(!manager.contains(args[3])) {
			sender.sendMessage(Messages.arenaNotFound(args[3]));
			return true;
		}

		Arena arena = manager.getArena(args[3]);
		arena.setInventory(((Player)sender).getInventory());
		manager.save(arena, args[3]);
		sender.sendMessage(ChatColor.GREEN + "アリーナ: " + args[3] + " のインベントリを設定しました");
		return true;
	}

}
