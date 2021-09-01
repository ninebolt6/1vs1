package net.ninebolt.onevsone.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.ninebolt.onevsone.match.Match;

public class MatchStartEvent extends Event {

	private Match match;
	private static final HandlerList HANDLERS = new HandlerList();

	public MatchStartEvent(Match match) {
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
}
