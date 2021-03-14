package net.ninebolt.onevsone.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public class RootCommand
{
	private List<SubCommand> commandList;
	public RootCommand() {
		commandList = new ArrayList<SubCommand>();
		commandList.add(new ArenaRootCommand());
		commandList.add(new StatsCommand());
		commandList.add(new LeaveCommand());
	}

	public boolean execute(CommandSender sender, String label, String[] args) {
		for(SubCommand command : commandList) {
			if(command.getName().equalsIgnoreCase(args[0])) {
				return command.execute(sender, args);
			}
		}
		return false;
	}
}
