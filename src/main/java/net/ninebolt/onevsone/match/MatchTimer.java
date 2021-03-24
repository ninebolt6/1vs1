package net.ninebolt.onevsone.match;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import net.ninebolt.onevsone.util.Messages;

public class MatchTimer {
	private Match match;

	/**
	 * Match用のタイマーを取得するためのインスタンスを作成します。
	 * @param match カウントダウンをするMatch
	 */
	public MatchTimer(Match match) {
		this.match = match;
	}

	/**
	 * カウントダウン用クラスのインスタンスを取得します。
	 * @param count カウントダウンの秒数
	 * @return {@link MatchCountdown}のインスタンス
	 */
	public MatchCountdown getCountdownTimer(int count) {
		return new MatchCountdown(match, count);
	}
}

/**
 * カウントダウン用クラス
 * @author ninebolt6
 *
 */
class MatchCountdown extends BukkitRunnable {

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
		if(counter == 0) {
			match.playSound(Sound.BLOCK_BELL_USE, 3.0f, 2.0f);
			match.sendMessage(Messages.roundStart());
			this.cancel();
		} else {
			match.playSound(Sound.UI_BUTTON_CLICK, 3.0f, 1.0f);
			match.sendMessage(Messages.roundCountdown(counter));
			counter--;
		}
	}
}