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

import net.ninebolt.onevsone.match.Match;
import net.ninebolt.onevsone.match.MatchManager;
import net.ninebolt.onevsone.match.MatchState;

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

		Match match = manager.getMatch(player);
		event.getDrops().clear();
		// 勝利・敗北処理
		match.stop();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDamageInArena(EntityDamageEvent event) {
		MatchManager manager = MatchManager.getInstance();

		if(event.getEntity() instanceof Player) {
			Player defender = (Player) event.getEntity();
			if(manager.isPlaying(defender)) {
				Match match = manager.getMatch(defender);
				if(match.getState().equals(MatchState.WAITING)) {
					event.setCancelled(true);
				}
				if(event.getFinalDamage() >= defender.getHealth()) {
					event.setCancelled(true);
					// dead
					match.lose(defender);
				}
			}
		}
	}

	@EventHandler
	public void onQuitWhileMatch(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		MatchManager manager = MatchManager.getInstance();

		if(manager.isPlaying(player)) {
			manager.leave(player);
		}
	}
}
