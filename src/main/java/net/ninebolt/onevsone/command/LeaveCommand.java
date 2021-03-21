package net.ninebolt.onevsone.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ninebolt.onevsone.match.MatchManager;
import net.ninebolt.onevsone.util.Messages;

public class LeaveCommand implements SubCommand {

	private static final String NAME = "leave";
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
		MatchManager manager = MatchManager.getInstance();
		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.cannotExecuteFromConsole());;
			return true;
		}

		if(manager.getMatch((Player)sender) == null) {
			sender.sendMessage(Messages.notInMatch());
			return true;
		}

		manager.leave((Player)sender);
		sender.sendMessage(Messages.leaveMatch());
		return false;
	}

}
