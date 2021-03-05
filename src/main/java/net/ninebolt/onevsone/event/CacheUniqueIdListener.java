package net.ninebolt.onevsone.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.player.Stats;

public class CacheUniqueIdListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// create data for 1vs1 stats
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		String uuid = player.getUniqueId().toString();

		if(OneVsOne.getUUIDCache().getUUIDByName(playerName) == null) {
			OneVsOne.getUUIDCache().set(playerName, uuid);
			OneVsOne.getUUIDCache().save();
		}

		if(!Stats.exists(uuid)) {
			Stats stats = new Stats(uuid);
			stats.setPlayerName(playerName);
			stats.save();
		} else {
			Stats stats = Stats.getStats(uuid);
			if(stats.getPlayerName() != playerName) {
				stats.setPlayerName(playerName);
				stats.save();
				OneVsOne.getUUIDCache().set(playerName, uuid);
				OneVsOne.getUUIDCache().save();
			}
		}
	}
}
