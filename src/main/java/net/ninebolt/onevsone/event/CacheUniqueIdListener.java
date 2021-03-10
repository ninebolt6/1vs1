package net.ninebolt.onevsone.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.util.UUIDCache;

public class CacheUniqueIdListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getDisplayName();
		String uuid = player.getUniqueId().toString();
		UUIDCache cache = OneVsOne.getUUIDCache();

		if(cache.getUUIDByName(playerName) == null) {
			cache.set(playerName, uuid);
			cache.save();
		}
	}
}
