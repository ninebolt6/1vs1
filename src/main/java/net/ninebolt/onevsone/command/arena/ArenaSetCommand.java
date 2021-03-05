package net.ninebolt.onevsone.command.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import net.ninebolt.onevsone.command.ISubCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaSetCommand implements ISubCommand {

	private static final String NAME = "set";
	private static final String PERMISSION_NODE = "1vs1.arena." + NAME;

	private List<ISubCommand> commandList;

	public ArenaSetCommand() {
		commandList = new ArrayList<ISubCommand>();
		commandList.add(new ArenaSetKitCommand());
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
		for(ISubCommand command : commandList) {
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
