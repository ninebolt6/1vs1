package net.ninebolt.onevsone.match;

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