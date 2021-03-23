package net.ninebolt.onevsone.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import net.ninebolt.onevsone.command.arena.ArenaCreateCommand;
import net.ninebolt.onevsone.command.arena.ArenaDeleteCommand;
import net.ninebolt.onevsone.command.arena.ArenaDisableCommand;
import net.ninebolt.onevsone.command.arena.ArenaEnableCommand;
import net.ninebolt.onevsone.command.arena.ArenaSetCommand;
import net.ninebolt.onevsone.util.Messages;

public class ArenaRootCommand implements SubCommand {

	private static final String NAME = "arena";
	private static final String PERMISSION_NODE = "1vs1." + NAME;

	private List<SubCommand> commandList;

	/**
	 * このコマンドを実行するためのインスタンスを作成します。
	 */
	public ArenaRootCommand() {
		/*
		 * #execute(CommandSender, String, String[])が呼び出された際に
		 * 参照されるコマンドのリスト
		 */
		commandList = new ArrayList<SubCommand>();

		commandList.add(new ArenaSetCommand());
		commandList.add(new ArenaCreateCommand());
		commandList.add(new ArenaDeleteCommand());
		commandList.add(new ArenaEnableCommand());
		commandList.add(new ArenaDisableCommand());
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
		if(args.length <= 1) {
			// usage
			return true;
		}

		for(SubCommand command : commandList) {
			if(command.getName().equalsIgnoreCase(args[1])) {
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
