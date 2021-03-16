package net.ninebolt.onevsone.command.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaInventory;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.command.SubCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaSetInventoryCommand implements SubCommand {

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

		PlayerInventory inv = ((Player)sender).getInventory();
		Arena arena = manager.getArena(args[3]);
		ArenaInventory arenaInv = new ArenaInventory();

		arenaInv.setContents(inv.getContents());
		arenaInv.setArmorContents(inv.getArmorContents());
		arenaInv.setExtraContents(inv.getExtraContents());

		arena.setInventory(arenaInv);
		manager.save(arena);
		sender.sendMessage(ChatColor.GREEN + "アリーナ: " + args[3] + " のインベントリを設定しました");
		return true;
	}

}
