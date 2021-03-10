package net.ninebolt.onevsone.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.ninebolt.onevsone.player.Stats;
import net.ninebolt.onevsone.player.StatsManager;

public class CreateStatsListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// create data for 1vs1 stats
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		String uuid = player.getUniqueId().toString();

		Stats stats = StatsManager.getStats(uuid);
		if(stats == null) {
			// create new stats
			stats = new Stats(uuid, playerName);
			stats.setPlayerName(playerName);
		}

		if(stats.getPlayerName() != playerName) {
			stats.setPlayerName(playerName);
		}
		StatsManager.save(uuid, stats);
	}
}
