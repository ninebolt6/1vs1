package net.ninebolt.onevsone.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ninebolt.onevsone.match.MatchSelector;
import net.ninebolt.onevsone.util.Messages;

public class GameGUICommand {
	private static final String PERMISSION_NODE = "1vs1.matchselector";

	/**
	 * このコマンドの権限ノードを取得します。
	 * @return 権限ノード
	 */
	public String getPermissionNode() {
		return PERMISSION_NODE;
	}

	/**
	 * コマンドが実行された際に呼び出されます。
	 * @param sender コマンドの実行者
	 * @param args コマンドの引数
	 * @return 正しくコマンドが実行されたらtrue。それ以外はfalse
	 */
	public boolean execute(CommandSender sender, String label) {
		if(!sender.hasPermission(PERMISSION_NODE)) {
			sender.sendMessage(Messages.notPermitted());
			return true;
		}

		if(!(sender instanceof Player)) {
			sender.sendMessage(Messages.cannotExecuteFromConsole());
			return true;
		}

		MatchSelector selector = new MatchSelector();
		selector.open((Player)sender);
		return true;
	}
}
