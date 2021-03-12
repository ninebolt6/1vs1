package net.ninebolt.onevsone.command.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.command.ISubCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaSetSpawnCommand implements ISubCommand {

	private static final String NAME = "spawn";
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
		// /1vs1 arena set spawn [1/2] [arenaName]
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.cannotExecuteFromConsole());
			return true;
		}

		if(args.length != 5) {
			sender.sendMessage(ChatColor.RED + "/1vs1 arena set spawn [1|2] [arenaName]");
			return true;
		}

		ArenaManager manager = OneVsOne.getArenaManager();
		if(!manager.contains(args[4])) {
			sender.sendMessage(Messages.arenaNotFound(args[4]));
			return true;
		}

		int spawnNumber = 0;
		try {
			spawnNumber = Integer.parseInt(args[3]);
		} catch(NumberFormatException e) {
			sender.sendMessage("Format error: " + args[3]);
			return true;
		}

		if(spawnNumber < 1 || spawnNumber > 2) {
			sender.sendMessage("Format error2: " + args[3]);
			return true;
		}

		Arena arena = manager.getArena(args[4]);
		arena.getSpawnLocations().setSpawn(spawnNumber, ((Player)sender).getLocation());
		manager.save(arena, args[4]);
		sender.sendMessage(ChatColor.GREEN + "アリーナ: " + args[4] + " のスポーン" + args[3] + "を設定しました");
		return true;
	}

}
