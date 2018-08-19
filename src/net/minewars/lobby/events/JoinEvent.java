package net.minewars.lobby.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.minewars.lobby.listeners.HideListener;
import net.minewars.lobby.main.Item;
import net.minewars.lobby.main.Lobby;
import net.minewars.lobby.scoreboard.ScoreboardClass;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().getInventory().clear();
		
		if(!HideListener.exists(e.getPlayer())) {
			HideListener.addPlayer(e.getPlayer());
		}
		
		Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				f(e.getPlayer());
			}
		}, 20);
		
		e.setJoinMessage("");
	}
	
	private void f(Player p) {
		
		if(!Lobby.getInstance().getConfig().getBoolean("Ingame.Silentlobby.This")) {
			p.setLevel(0);
			
			List<String> lore = new ArrayList<>();
			lore.add("§7Wechsel hiermit zu einer anderen Lobby!");
			p.getInventory().setItem(1, Item.cItem(Material.NETHER_STAR, 1, 0, "§eLobby Switcher §7[Rechtsklick]", lore, null, 0));
			
			List<String> lore2 = new ArrayList<>();
			lore2.add("§7Teleportiert dich zum gewünschten Spielmodus!");
			p.getInventory().setItem(0, Item.cItem(Material.COMPASS, 1, 0, "§aNavigator §7[Rechtsklick]", lore2, null, 0));
			
			List<String> lore3 = new ArrayList<>();
			lore3.add("§7Spieler verstecken leicht gemacht!");
			p.getInventory().setItem(7, Item.cItem(Material.BLAZE_ROD, 1, 0, "§6Spieler verstecken §7[Rechtsklick]", lore3, null, 0));
			
			if(p.hasPermission("lobby.schild")) {
				//e.getPlayer().getInventory().setItem(1, Item.cItem(Material.EYE_OF_ENDER, 1, 0, "§5Schutzschild", lore, null, 0));
			}
			
			if(p.hasPermission("lobby.nick")) {
				List<String> lore4 = new ArrayList<>();
				lore4.add("§7Aktiviere den AutoNick!");
				if(ScoreboardClass.isNicked(p.getUniqueId().toString())) {
					p.getInventory().setItem(5, Item.cItem(Material.NAME_TAG, 1, 0, "§aAutomatischer Nickname §7[Rechtsklick]", lore4, null, 0));
				}else {
					p.getInventory().setItem(5, Item.cItem(Material.NAME_TAG, 1, 0, "§cAutomatischer Nickname §7[Rechtsklick]", lore4, null, 0));
				}
			}
			
			if(p.hasPermission("lobby.bangui")) {
				List<String> lore4 = new ArrayList<>();
				lore4.add("§7");
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta meta = (SkullMeta) item.getItemMeta();
				meta.setDisplayName("§7Reports §7[Rechtsklick]");
				meta.setOwner("MHF_Exclamation");
				meta.setLore(lore4);
				item.setItemMeta(meta);
				
				p.getInventory().setItem(4, item);
				
			}
			
			if(p.hasPermission("lobby.silentlobby")) {
				List<String> lore4 = new ArrayList<>();
				lore4.add("§7Teleportiert dich in die Silent-Lobby!");
				p.getInventory().setItem(3, Item.cItem(Material.TNT, 1, 0, "§cSilent Lobby §7[Rechtsklick]", lore4, null, 0));
			}
			
			if(Lobby.getInstance().getConfig().getBoolean("Ingame.Join.TeleportToSpawn")) {
				Location loc = (Location) Lobby.getInstance().getConfig().get("Ingame.Join.Spawn");
				p.teleport(loc);
			}
			
			
			for(Player no : HideListener.byState(4)) {
				no.hidePlayer(p);
			}
			
			for(Player fandt : HideListener.byState(1)) {
				if(!p.hasPermission("lobby.team")) {
					fandt.hidePlayer(p);
				}
				
				Player[] friends = ScoreboardClass.getFriends(fandt.getUniqueId().toString());
				boolean hide = true;
				for(Player g : friends) {
					if(g.getName().equals(p.getName())) {
						hide = false;
					}
				}
				
				if(hide) {
					if(!p.hasPermission("lobby.team")) {
						fandt.hidePlayer(p);
					}
				}else {
					
				}
			}
			
			for(Player t : HideListener.byState(3)) {
				if(!p.hasPermission("lobby.team")) {
					t.hidePlayer(p);
				}
			}
			
			for(Player f : HideListener.byState(2)) {
				Player[] friends = ScoreboardClass.getFriends(f.getUniqueId().toString());
				boolean hide = true;
				for(Player g : friends) {
					if(g.getName().equals(p.getName())) {
						hide = false;
					}
				}
				
				if(hide) {
					f.hidePlayer(p);
				}else {
					
				}
			}
			
			if(HideListener.inSilentLobby(p)) {
				Lobby.getInstance().getSilentPlayers().add(p);
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.hidePlayer(p);
					p.hidePlayer(all);
				}
				
			}else {
				Lobby.getInstance().getSilentPlayers().remove(p);
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.showPlayer(p);
					p.showPlayer(all);
				}
			}
		
			ScoreboardClass.setLobbyBoard(p);
			
			p.setGameMode(GameMode.SURVIVAL);
			
		}else {
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.hidePlayer(p);
				p.hidePlayer(all);
			}
			
			p.setLevel(0);
			
			List<String> lore = new ArrayList<>();
			lore.add("§7Wechsel hiermit zu einer anderen Lobby!");
			p.getInventory().setItem(1, Item.cItem(Material.NETHER_STAR, 1, 0, "§eLobby Switcher §7[Rechtsklick]", lore, null, 0));
			
			List<String> lore2 = new ArrayList<>();
			lore2.add("§7Teleportiert dich zum gewünschten Spielmodus!");
			p.getInventory().setItem(0, Item.cItem(Material.COMPASS, 1, 0, "§aNavigator §7[Rechtsklick]", lore2, null, 0));
			
			List<String> lore3 = new ArrayList<>();
			lore3.add("§7Spieler verstecken leicht gemacht!");
			p.getInventory().setItem(7, Item.cItem(Material.BLAZE_ROD, 1, 0, "§6Spieler verstecken §7[Rechtsklick]", lore3, null, 0));
			
			if(p.hasPermission("lobby.schild")) {
				//e.getPlayer().getInventory().setItem(1, Item.cItem(Material.EYE_OF_ENDER, 1, 0, "§5Schutzschild", lore, null, 0));
			}
			
			if(p.hasPermission("lobby.nick")) {
				List<String> lore4 = new ArrayList<>();
				lore4.add("§7Aktiviere den AutoNick!");
				if(ScoreboardClass.isNicked(p.getUniqueId().toString())) {
					p.getInventory().setItem(5, Item.cItem(Material.NAME_TAG, 1, 0, "§aAutomatischer Nickname §7[Rechtsklick]", lore4, null, 0));
				}else {
					p.getInventory().setItem(5, Item.cItem(Material.NAME_TAG, 1, 0, "§cAutomatischer Nickname §7[Rechtsklick]", lore4, null, 0));
				}
			}
			
			if(p.hasPermission("lobby.bangui")) {
				List<String> lore4 = new ArrayList<>();
				lore4.add("§7");
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				SkullMeta meta = (SkullMeta) item.getItemMeta();
				meta.setDisplayName("§7Reports §7[Rechtsklick]");
				meta.setOwner("MHF_Exclamation");
				meta.setLore(lore4);
				item.setItemMeta(meta);
				
				p.getInventory().setItem(4, item);
				
			}
			
			if(p.hasPermission("lobby.silentlobby")) {
				List<String> lore4 = new ArrayList<>();
				lore4.add("§7Teleportiert dich in die Silent-Lobby!");
				p.getInventory().setItem(3, Item.cItem(Material.TNT, 1, 0, "§cSilent Lobby §7[Rechtsklick]", lore4, null, 0));
			}
			
			if(Lobby.getInstance().getConfig().getBoolean("Ingame.Join.TeleportToSpawn")) {
				Location loc = (Location) Lobby.getInstance().getConfig().get("Ingame.Join.Spawn");
				p.teleport(loc);
			}
			
			
			for(Player no : HideListener.byState(4)) {
				no.hidePlayer(p);
			}
			
			for(Player fandt : HideListener.byState(1)) {
				if(!p.hasPermission("lobby.team")) {
					fandt.hidePlayer(p);
				}
				
				Player[] friends = ScoreboardClass.getFriends(fandt.getUniqueId().toString());
				boolean hide = true;
				for(Player g : friends) {
					if(g.getName().equals(p.getName())) {
						hide = false;
					}
				}
				
				if(hide) {
					if(!p.hasPermission("lobby.team")) {
						fandt.hidePlayer(p);
					}
				}else {
					
				}
			}
			
			for(Player t : HideListener.byState(3)) {
				if(!p.hasPermission("lobby.team")) {
					t.hidePlayer(p);
				}
			}
			
			for(Player f : HideListener.byState(2)) {
				Player[] friends = ScoreboardClass.getFriends(f.getUniqueId().toString());
				boolean hide = true;
				for(Player g : friends) {
					if(g.getName().equals(p.getName())) {
						hide = false;
					}
				}
				
				if(hide) {
					f.hidePlayer(p);
				}else {
					
				}
			}
			
			if(HideListener.inSilentLobby(p)) {
				Lobby.getInstance().getSilentPlayers().add(p);
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.hidePlayer(p);
					p.hidePlayer(all);
				}
				
			}else {
				Lobby.getInstance().getSilentPlayers().remove(p);
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.showPlayer(p);
					p.showPlayer(all);
				}
			}
		
			ScoreboardClass.setLobbyBoard(p);
			
			p.setGameMode(GameMode.SURVIVAL);
		}
		
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		ScoreboardClass.getMap().remove(e.getPlayer());
		e.setQuitMessage("");
	}
	
}
