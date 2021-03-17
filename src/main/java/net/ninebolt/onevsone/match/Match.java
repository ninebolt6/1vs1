package net.ninebolt.onevsone.match;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.arena.Arena;

public class Match {
	public static final int PLAYER_ONE = 1;
	public static final int PLAYER_TWO = 2;

	private Arena arena;
	private MatchState state;
	private Player[] players;
	private boolean canJoin;

	private Location[] locCache;
	private PlayerInventory[] invCache;

	public Match(Arena arena) {
		this.arena = arena;
		this.players = new Player[2];
		this.state = MatchState.WAITING;
		this.canJoin = arena.isEnabled();

		locCache = new Location[2];
		invCache = new PlayerInventory[2];
	}

	public MatchState getState() {
		return state;
	}

	public void setState(MatchState state) {
		this.state = state;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		if(players[0] != null && players[1] != null) {
			return;
		}

		if(players[0] == null) {
			players[0] = player;
			invCache[0] = player.getInventory();
			locCache[0] = player.getLocation();
			player.teleport(getArena().getArenaSpawn().getLocation(PLAYER_ONE));
		} else if(players[1] == null) {
			players[1] = player;
			invCache[1] = player.getInventory();
			locCache[1] = player.getLocation();
			player.teleport(getArena().getArenaSpawn().getLocation(PLAYER_TWO));
		}

		if(players[0] != null && players[1] != null) {
			System.out.println("2p sorotta");
			start();
		}

		// inventory set
	}

	public void removePlayer(Player player) {
		if(players[0] != null && players[0].equals(player)) {
			players[0] = null;
		}

		if(players[1] != null && players[1].equals(player)) {
			players[1] = null;
		}
	}

	public int getPlayerNumber(Player player) {
		if(players[0].equals(player)) {
			return PLAYER_ONE;
		}

		if(players[1].equals(player)) {
			return PLAYER_TWO;
		}

		return -1;
	}

	public Arena getArena() {
		return arena;
	}

	public boolean canJoin() {
		return canJoin;
	}

	public void setJoinable(boolean canJoin) {
		this.canJoin = canJoin;
	}

	public void sendMessage(String message) {
		for(Player player : players) {
			if(player != null) {
				player.sendMessage(message);
			}
		}
	}

	public void playSound(Sound sound, float volume, float pitch) {
		for(Player player : players) {
			player.playSound(player.getLocation(), sound, volume, pitch);
		}
	}

	public void start() {
		if(players.length != 2) {
			// error
		}

		for(Player player : players) {
			player.getInventory().clear();
			player.getInventory().setContents(getArena().getInventory().getContents());
			player.getInventory().setArmorContents(getArena().getInventory().getArmorContents());
			player.getInventory().setExtraContents(getArena().getInventory().getExtraContents());
		}

		MatchTimer timer = new MatchTimer(this);
		timer.getCountdownTimer().runTaskTimerAsynchronously(OneVsOne.getInstance(), 0, 20);
		state = MatchState.INGAME;
	}

	public void stop() {
		for(int i=0; i<2; i++) {
			if(players[i] != null) {
				players[i].getInventory().clear();
				players[i].getInventory().setContents(invCache[i].getContents());
				players[i].getInventory().setArmorContents(invCache[i].getArmorContents());
				players[i].getInventory().setExtraContents(invCache[i].getExtraContents());
				players[i].teleport(locCache[i]);
			}
		}
		sendMessage("Ended");
		state = MatchState.WAITING;
		players = new Player[2];
	}

	public void lose(Player player) {
		player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		// set inventory
		player.teleport(getArena().getArenaSpawn().getLocation(getPlayerNumber(player)));
		player.sendMessage("u r dead");
	}
}
