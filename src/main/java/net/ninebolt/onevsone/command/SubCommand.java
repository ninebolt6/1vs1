package net.ninebolt.onevsone.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {
	/**
	 * このコマンドのエイリアスを取得します。
	 * @return コマンドのエイリアス
	 */
	public String getName();

	/**
	 * このコマンドの権限ノードを取得します。
	 * @return 権限ノード
	 */
	public String getPermissionNode();

	/**
	 * コマンドが実行された際に呼び出されます。
	 * @param sender コマンドの実行者
	 * @param args コマンドの引数
	 * @return 正しくコマンドが実行されたらtrue。それ以外はfalse
	 */
	public boolean execute(CommandSender sender, String[] args);
}
