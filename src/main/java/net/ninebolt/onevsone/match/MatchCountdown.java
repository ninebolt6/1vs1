package net.ninebolt.onevsone.match;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import net.ninebolt.onevsone.util.Messages;

/**
 * カウントダウン用クラス
 * @author ninebolt6
 *
 */
public class MatchCountdown extends BukkitRunnable {

	private int counter;
	private Match match;

	/**
	 * カウントダウンタイマー用のインスタンスを作成します。
	 * @param match カウントダウンをするMatch
	 * @param count カウントダウンの秒数
	 */
	MatchCountdown(Match match, int count) {
		this.counter = count;
		this.match = match;
	}

	/**
	 * カウントダウンの際に呼び出されるメソッドです。
	 */
	@Override
	public void run() {
		match.setState(MatchState.COUNTDOWN);
		if(counter == 0) {
			match.playSound(Sound.BLOCK_BELL_USE, 3.0f, 2.0f);
			match.sendMessage(Messages.roundStart());
			match.setState(MatchState.INGAME);
			this.cancel();
		} else {
			match.playSound(Sound.UI_BUTTON_CLICK, 3.0f, 1.0f);
			match.sendMessage(Messages.roundCountdown(counter));
			counter--;
		}
	}
}