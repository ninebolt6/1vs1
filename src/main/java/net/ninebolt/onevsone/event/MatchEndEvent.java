package net.ninebolt.onevsone.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.ninebolt.onevsone.match.Match;
import net.ninebolt.onevsone.match.MatchEndCause;

public class MatchEndEvent extends Event {

	private Match match;
	private MatchEndCause cause;
	private static final HandlerList HANDLERS = new HandlerList();

	public MatchEndEvent(Match match, MatchEndCause cause) {
		this.match = match;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	public Match getMatch() {
		return match;
	}

	public MatchEndCause getCause() {
		return cause;
	}
}
