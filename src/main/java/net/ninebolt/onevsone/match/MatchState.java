package net.ninebolt.onevsone.match;

public enum MatchState {
	/**
	 * Matchが待機中であることを表します。
	 */
	WAITING,

	/**
	 * Matchがラウンド開始時のカウントダウン中であることを表します。
	 */
	COUNTDOWN,

	/**
	 * Matchがゲーム中であることを表します。
	 */
	INGAME,
}
