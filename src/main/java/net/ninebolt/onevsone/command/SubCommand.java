package net.ninebolt.onevsone.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {
	public String getName();
	public String getPermissionNode();

	public boolean execute(CommandSender sender, String[] args);
}
