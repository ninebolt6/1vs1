package net.ninebolt.onevsone.match;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import net.ninebolt.onevsone.util.Messages;

public class MatchTimer {
	private Match match;

	public MatchTimer(Match match) {
		this.match = match;
	}

	public MatchCountdown getCountdownTimer() {
		return new MatchCountdown(match);
	}
}

class MatchCountdown extends BukkitRunnable {

	private int counter;
	private Match match;

	MatchCountdown(Match match) {
		counter = 3;
		this.match = match;
	}

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