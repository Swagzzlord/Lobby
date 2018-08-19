package net.minewars.lobby.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import me.FriendsGui.main.FriendsStateChangeEvent;
import net.minewars.lobby.main.Lobby;
import net.minewars.lobby.scoreboard.ScoreboardClass;

public class ChatEvent implements Listener{

	private static List<Player> list = new ArrayList<>();
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {	
		if(Lobby.getInstance().getConfig().getBoolean("Ingame.Silentlobby.This")) {
			e.setCancelled(true);
			
		}else {
			e.setCancelled(true);
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(!Lobby.getInstance().getSilentPlayers().contains(p)) {
					p.sendMessage(e.getPlayer().getDisplayName() + " §8» §7" + e.getMessage());
				}
			}
		}
	}
	
	@EventHandler
	public void onFriend(FriendsStateChangeEvent e) {
		Player p = e.getPlayer();
		ScoreboardClass.setLobbyBoard(p);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		if(e.toWeatherState()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!list.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	public static List<Player> getList(){
		return list;
	}
}
