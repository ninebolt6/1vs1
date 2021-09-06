package net.ninebolt.onevsone.match;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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

import net.ninebolt.onevsone.OneVsOne;
import net.ninebolt.onevsone.event.MatchEndEvent;
import net.ninebolt.onevsone.event.RoundStartEvent;
import net.ninebolt.onevsone.stats.Stats;
import net.ninebolt.onevsone.stats.StatsManager;

public class MatchListener implements Listener {

	/**
	 * プレイヤーがMatch参加中にブロックを壊した場合、キャンセルするメソッドです。
	 * @param event ブロックが壊された際に呼び出されるイベント
	 */
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		MatchManager manager = MatchManager.getInstance();

		if(!manager.isPlaying(player)) {
			return;
		}

		// 火を消した場合、何もしない
		if(block.getType().equals(Material.FIRE)) {
			return;
		}

		event.setCancelled(true);
	}

	/**
	 * プレイヤーがMatch参加中にブロックを置いた場合、キャンセルするメソッドです。
	 * @param event ブロックが置かれた際に呼び出されるイベント
	 */
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

	/**
	 * プレイヤーが死亡した際に、Matchを終了するメソッドです。
	 * @param event プレイヤーが死亡した際に呼び出されるイベント
	 */
	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		MatchManager manager = MatchManager.getInstance();

		if(!manager.isPlaying(player)) {
			return;
		}

		// An unexpected behavior happened
		// プレイヤーが死亡することはありえないのでMatchを停止する
		Match match = manager.getMatch(player);
		event.getDrops().clear();
		for(Player p : match.getPlayers()) {
			manager.leave(p);
		}
	}

	/**
	 * プレイヤーがMatch中に死亡するほどのダメージを受けた際に呼び出されるメソッドです。
	 * @param event エンティティがダメージを受けた際に呼び出されるイベント
	 */
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
					Player opponent = match.getOpponent(defender);
					match.getMatchData().setDeath(defender, match.getMatchData().getDeath(defender)+1);
					match.getMatchData().setKill(opponent, match.getMatchData().getKill(opponent)+1);
					RoundStartEvent startEvent = new RoundStartEvent(match);
					Bukkit.getPluginManager().callEvent(startEvent);
				}
			}
		}
	}

	/**
	 * プレイヤーがサーバーから退出する際に、Matchから離脱するメソッドです。
	 * @param event プレイヤーがサーバーから退出する際に呼び出されるイベント
	 */
	@EventHandler
	public void onQuitWhileMatch(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		MatchManager manager = MatchManager.getInstance();

		if(manager.isPlaying(player)) {
			manager.leave(player);
		}
	}

	/**
	 * ラウンド開始時に呼び出されるメソッドです。最後のラウンドが終わり呼び出された場合には、
	 * {@link #stop()}が呼び出されマッチが終了します。
	 */
	@EventHandler
	public void onRoundStart(RoundStartEvent event) {
		Match match = event.getMatch();

		// 終了判定
		if(match.getMatchData().getKill(match.getPlayers()[0]) >= MatchManager.FIRST_TO) {
			match.getMatchData().setWinner(match.getPlayers()[0]);

			MatchEndEvent endEvent = new MatchEndEvent(match, MatchEndCause.FINISHED);
			Bukkit.getPluginManager().callEvent(endEvent);
			return;
		} else if(match.getMatchData().getKill(match.getPlayers()[1]) >= MatchManager.FIRST_TO) {
			match.getMatchData().setWinner(match.getPlayers()[1]);

			MatchEndEvent endEvent = new MatchEndEvent(match, MatchEndCause.FINISHED);
			Bukkit.getPluginManager().callEvent(endEvent);
			return;
		}

		for(Player player : match.getPlayers()) {
			player.setGameMode(GameMode.SURVIVAL);
			player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			player.setFoodLevel(20);
			player.getInventory().clear();
			player.getInventory().setContents(match.getArena().getInventory().getContents());
			player.getInventory().setArmorContents(match.getArena().getInventory().getArmorContents());
			player.getInventory().setExtraContents(match.getArena().getInventory().getExtraContents());
			player.teleport(match.getArena().getArenaSpawn().getLocation(match.getPlayerNumber(player)));
		}

		MatchTimer timer = new MatchTimer(match);
		match.getMatchData().setRound(match.getMatchData().getRound() + 1);
		timer.getCountdownTimer(3).runTaskTimerAsynchronously(OneVsOne.getInstance(), 0, 20);
	}

	@EventHandler
	public void onMatchEnd(MatchEndEvent event) {
		MatchManager matchManager = MatchManager.getInstance();

		if(event.getCause() == MatchEndCause.INTERRUPTED) {
			// statsを保存しない
		} else {
			event.getMatch().sendMessage("マッチ終了！ " + event.getMatch().getMatchData().getWinner().getDisplayName() + "の勝利！");
			for(Player player : event.getMatch().getPlayers()) {
				if(player != null) {
					// save stats
					StatsManager statsManager = OneVsOne.getStatsManager();
					Stats stats = statsManager.getStats(player.getUniqueId().toString());
					stats.addKills(event.getMatch().getMatchData().getKill(player));
					stats.addDeaths(event.getMatch().getMatchData().getDeath(player));
					if(event.getMatch().getMatchData().getWinner().equals(player)) {
						stats.addWins(1);
					} else {
						stats.addDefeats(1);
					}
					statsManager.save(player.getUniqueId().toString(), stats);

					matchManager.leave(player);
				}
			}
		}

		event.getMatch().initMatch();
	}
}
