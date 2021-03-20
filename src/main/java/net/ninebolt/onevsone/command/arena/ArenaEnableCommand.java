package net.ninebolt.onevsone.command.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaManager;
import net.ninebolt.onevsone.command.SubCommand;
import net.ninebolt.onevsone.match.Match;
import net.ninebolt.onevsone.util.Messages;

public class ArenaEnableCommand implements SubCommand {

	private static final String NAME = "enable";
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
		// /1vs1 arena enable [arenaName]
		if(args.length != 3) {
			// show usage
			sender.sendMessage(ChatColor.RED + "/1vs1 arena enable [arena]");
			return true;
		}

		ArenaManager manager = OneVsOne.getArenaManager();
		if(!manager.contains(args[2])) {
			sender.sendMessage(Messages.arenaNotFound(args[2]));
			return true;
		}

		Arena arena = manager.getArena(args[2]);
		if(arena.isEnabled()) {
			sender.sendMessage(ChatColor.RED + "そのアリーナはすでに有効になっています！");
			return true;
		}

		// すべての項目が設定されていることを確認
		List<String> errorMsg = new ArrayList<String>();
		if(arena.getArenaSpawn().getLocation(Match.PLAYER_ONE) == null) {
			errorMsg.add("ERROR P1 SPAWN");
		}

		if(arena.getArenaSpawn().getLocation(Match.PLAYER_TWO) == null) {
			errorMsg.add("ERROR P2 SPAWN");
		}

		if(errorMsg.size() != 0) {
			sender.sendMessage(errorMsg.toArray(new String[errorMsg.size()]));
			return true;
		}

		/*if(arena.getInventory().isEmpty()) {
			sender.sendMessage("color yellow: inventory is empty");
		}*/

		arena.setEnabled(true);
		manager.save(arena);
		sender.sendMessage(ChatColor.GREEN + "アリーナ: " + args[2] + " を有効にしました");
		return true;
	}

}
