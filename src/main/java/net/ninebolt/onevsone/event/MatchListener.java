package net.ninebolt.onevsone.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.ninebolt.onevsone.arena.ArenaState;
import net.ninebolt.onevsone.match.Match;
import net.ninebolt.onevsone.match.MatchManager;

public class MatchListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		MatchManager manager = MatchManager.getInstance();

		if(!manager.isPlaying(player)) {
			return;
		}

		if(!block.getType().equals(Material.FIRE)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		MatchManager manager = MatchManager.getInstance();

		if(!manager.isPlaying(player)) {
			return;
		}

		if(block.getType().equals(Material.FIRE)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		MatchManager manager = MatchManager.getInstance();

		if(!manager.isPlaying(player)) {
			return;
		}

		event.getDrops().clear();
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		MatchManager manager = MatchManager.getInstance();

		if(!manager.isPlaying(player)) {
			return;
		}

		Match match = manager.getMatch(player);
		event.setRespawnLocation(match.getArena().getArenaSpawn().getLocation(match.getPlayerNumber(player)));
	}

	@EventHandler
	public void onDamageWhileCountdown(EntityDamageEvent event) {
		MatchManager manager = MatchManager.getInstance();

		if(event.getEntity() instanceof Player) {
			Player defender = (Player) event.getEntity();
			if(manager.isPlaying(defender)) {
				Match match = manager.getMatch(defender);
				if(match.getState().equals(ArenaState.WAITING)) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onQuitWhileMatch(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		MatchManager manager = MatchManager.getInstance();

		if(manager.isPlaying(player)) {
			Match match = manager.getMatch(player);
			match.stop();
		}
	}
}
