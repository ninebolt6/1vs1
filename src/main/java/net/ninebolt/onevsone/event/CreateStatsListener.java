package net.ninebolt.onevsone.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.stats.Stats;
import net.ninebolt.onevsone.stats.StatsManager;

public class CreateStatsListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// create data for 1vs1 stats
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		String uuid = player.getUniqueId().toString();
		StatsManager manager = OneVsOne.getStatsManager();

		Stats stats = manager.getStats(uuid);
		if(stats == null) {
			// create new stats
			stats = new Stats(playerName);
			stats.setPlayerName(playerName);
		}

		if(stats.getPlayerName() != playerName) {
			stats.setPlayerName(playerName);
		}
		manager.save(uuid, stats);
	}
}
