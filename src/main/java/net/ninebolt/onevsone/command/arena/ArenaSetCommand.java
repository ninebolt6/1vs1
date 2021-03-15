package net.ninebolt.onevsone.command.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import net.ninebolt.onevsone.command.SubCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaSetCommand implements SubCommand {

	private static final String NAME = "set";
	private static final String PERMISSION_NODE = "1vs1.arena." + NAME;

	private List<SubCommand> commandList;

	public ArenaSetCommand() {
		commandList = new ArrayList<SubCommand>();
		commandList.add(new ArenaSetInventoryCommand());
		commandList.add(new ArenaSetSpawnCommand());
	}

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
		for(SubCommand command : commandList) {
			if(command.getName().equalsIgnoreCase(args[2])) {
				if(!sender.hasPermission(command.getPermissionNode())) {
					sender.sendMessage(Messages.notPermitted());
					return true;
				}
				return command.execute(sender, args);
			}
		}
		return false;
	}

}
