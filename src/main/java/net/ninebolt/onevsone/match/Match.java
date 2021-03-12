package net.ninebolt.onevsone.match;

import org.bukkit.entity.Player;

import net.ninebolt.onevsone.arena.Arena;
import net.ninebolt.onevsone.arena.ArenaState;

public class Match {
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;

	private Arena arena;
	private ArenaState state;
	private Player[] players;
	private boolean canJoin;

	public Match(Arena arena) {
		this.arena = arena;
		this.players = new Player[2];
		this.state = ArenaState.WAITING;
		this.canJoin = arena.isEnabled();
	}

	public ArenaState getState() {
		return state;
	}

	public void setState(ArenaState state) {
		this.state = state;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Arena getArena() {
		return arena;
	}

	public boolean canJoin() {
		return canJoin;
	}

	public void start() {

	}

	public void stop() {

	}
}
