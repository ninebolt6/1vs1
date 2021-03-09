package net.ninebolt.onevsone.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.ninebolt.onevsone.arena.ArenaState;
import net.ninebolt.onevsone.match.Match;
import net.ninebolt.onevsone.match.MatchManager;

public class IngameListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		MatchManager manager = MatchManager.getInstance();

		if(manager.isPlaying(player)) {
			Match match = manager.getMatch(player);
			if(match.getState() == ArenaState.INGAME) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		MatchManager manager = MatchManager.getInstance();

		if(manager.isPlaying(player)) {
			Match match = manager.getMatch(player);
			if(match.getState().equals(ArenaState.INGAME)) {
				event.getDrops().clear();
			}
		}
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
	public void onQuitWhileIngame(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		MatchManager manager = MatchManager.getInstance();

		if(manager.isPlaying(player)) {
			Match match = manager.getMatch(player);
			match.stop();
		}
	}
}
