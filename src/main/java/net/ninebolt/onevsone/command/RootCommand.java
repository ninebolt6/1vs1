package net.ninebolt.onevsone.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public class RootCommand
{
	private List<ISubCommand> commandList;
	public RootCommand() {
		commandList = new ArrayList<ISubCommand>();
		commandList.add(new ArenaRootCommand());
		commandList.add(new StatsCommand());
	}

	public boolean execute(CommandSender sender, String label, String[] args) {
		for(ISubCommand command : commandList) {
			if(command.getName().equalsIgnoreCase(args[0])) {
				return command.execute(sender, args);
			}
		}
		return false;
	}
}
