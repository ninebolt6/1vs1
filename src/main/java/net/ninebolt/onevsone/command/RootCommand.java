package net.ninebolt.onevsone.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public class RootCommand
{
	private List<SubCommand> commandList;

	/**
	 * このコマンドを実行するためのインスタンスを作成します。
	 */
	public RootCommand() {
		/*
		 * #execute(CommandSender, String, String[])が呼び出された際に
		 * 参照されるコマンドのリスト
		 */
		commandList = new ArrayList<SubCommand>();

		commandList.add(new ArenaRootCommand());
		commandList.add(new StatsCommand());
		commandList.add(new LeaveCommand());
	}

	/**
	 * コマンドが実行された際に呼び出されます。
	 * @param sender コマンドの実行者
	 * @param label コマンドを実行した際に使われたエイリアス
	 * @param args コマンドの引数
	 * @return 正しくコマンドが実行されたらtrue。それ以外はfalse
	 */
	public boolean execute(CommandSender sender, String label, String[] args) {
		for(SubCommand command : commandList) {
			if(command.getName().equalsIgnoreCase(args[0])) {
				return command.execute(sender, args);
			}
		}
		return false;
	}
}
