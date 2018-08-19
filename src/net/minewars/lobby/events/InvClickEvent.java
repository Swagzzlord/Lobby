package net.minewars.lobby.events;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.minewars.lobby.listeners.Boot;
import net.minewars.lobby.listeners.HideListener;
import net.minewars.lobby.main.Lobby;
import net.minewars.lobby.scoreboard.ScoreboardClass;

public class InvClickEvent implements Listener{
	
	private static List<Player> fandt = new ArrayList<>();
	private static List<Player> f = new ArrayList<>();
	private static List<Player> t = new ArrayList<>();
	private static List<Player> no = new ArrayList<>();

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() != null) {
			if(e.getCurrentItem().hasItemMeta()) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§eLobby Switcher") || e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§aNavigator") || e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§5Schutzschild") || e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§cSilent Lobby") || e.getCurrentItem().getItemMeta().getDisplayName().contains("Automatischer Nickname") || e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§6Spieler verstecken") || e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§7Reports")) {
					e.setCancelled(true);
				}
			}
		}
		
		
		if(e.getClickedInventory() != null) {
			
			if(e.getClickedInventory().getName().equalsIgnoreCase("§9Boots")) {
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
						for(Boot b : Lobby.getInstance().getBoots()) {
							if(b.getIcon().equals(e.getCurrentItem())) {
								p.getInventory().setBoots(e.getCurrentItem());
								p.sendMessage(Lobby.getInstance().getPrefix() + "§7Du hast die " + b.getName() + " §7ausgewählt!");
								e.setCancelled(true);
								p.closeInventory();
							}
						}
					}
				}
				
				
			}else if(e.getClickedInventory().getName().equalsIgnoreCase("§aLobbys") || e.getClickedInventory().getName().startsWith("§bServers von§8:")) {
				if(e.getCurrentItem() != null) {
					if(e.getCurrentItem().getDurability() != 1) {
						if(e.getCurrentItem().hasItemMeta()) {
							String d = e.getCurrentItem().getItemMeta().getDisplayName();
							d = d.replace("§", "");
							d = d.replace(d.charAt(0), ' ');
							d = d.replace(" ", "");
							String server = d;
							
							ByteArrayOutputStream b = new ByteArrayOutputStream();
							DataOutputStream out = new DataOutputStream(b);
							try {
								out.writeUTF("Connect");
								out.writeUTF(server);
								
								p.sendPluginMessage(Lobby.getInstance(), "BungeeCord", b.toByteArray());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						
					}else {
						p.sendMessage(Lobby.getInstance().getPrefix() + "§cDieser Server ist momentan nicht erreichbar!");
					}
				}
				
				e.setCancelled(true);
			}else if(e.getClickedInventory().getName().equalsIgnoreCase("§eNavigator")) {
				if(e.getCurrentItem().getType() != Material.AIR) {
					int slot = 0;
					for(int i = 0; i < e.getClickedInventory().getSize(); i++) {
						if(e.getClickedInventory().getItem(i) != null) {
							if(e.getClickedInventory().getItem(i).isSimilar(e.getCurrentItem())) {
								slot = i;
								break;
							}
						}
					}
					
					Location loc = Lobby.getInstance().getNaviMap().get(slot);
					p.teleport(loc);
				}
				
				e.setCancelled(true);
				
			}else if(e.getClickedInventory().getName().equalsIgnoreCase("§6Spieler verstecken")) {
				if(e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
					if(e.getCurrentItem().getDurability() == 5) {
						//alle
						//  p.hidePlayer(target);
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(!p.canSee(all)) {
								p.showPlayer(all);
							}
						}
						
						if(fandt.contains(p)) {
							fandt.remove(p);
						}
						
						if(f.contains(p)) {
							f.remove(p);
						}
						
						if(t.contains(p)) {
							t.remove(p);
						}
						
						if(no.contains(p)) {
							no.remove(p);
						}
						
						HideListener.setState(p, 0);
						
						p.sendMessage(Lobby.getInstance().getPrefix() + "§7Dir werden jetzt §aalle §7Spieler angezeigt!");
						
					}else if(e.getCurrentItem().getDurability() == 1) {
						//team und freund
						
						Player[] friends = ScoreboardClass.getFriends(p.getUniqueId().toString());
						for(Player all : Bukkit.getOnlinePlayers()) {
							
							p.hidePlayer(all);
							
							if(friends != null) {
								if(friends.length > 0) {
									for(Player f : friends) {
										if(!p.canSee(f)) {
											p.showPlayer(f);
										}
									}
								}
							}
						}
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(!p.canSee(all)) {
								if(all.hasPermission("lobby.team")) {
									p.showPlayer(all);
								}
							}else {
								if(!all.hasPermission("lobby.team")) {
									p.hidePlayer(all);
								}
							}
						}
						
						if(!fandt.contains(p)) {
							fandt.add(p);
						}
						
						if(f.contains(p)) {
							f.remove(p);
						}
						
						if(t.contains(p)) {
							t.remove(p);
						}
						
						if(no.contains(p)) {
							no.remove(p);
						}
						
						HideListener.setState(p, 1);
						
						p.sendMessage(Lobby.getInstance().getPrefix() + "§7Dir werden jetzt nur §6Freunde und Teammitglieder §7angezeigt!");
						
					}else if(e.getCurrentItem().getDurability() == 4) {
						//Freunde
						Player[] friends = ScoreboardClass.getFriends(p.getUniqueId().toString());
						for(Player all : Bukkit.getOnlinePlayers()) {
							
							p.hidePlayer(all);
							
							if(friends != null) {
								if(friends.length > 0) {
									for(Player f : friends) {
										if(!p.canSee(f)) {
											p.showPlayer(f);
										}
									}
								}
							}
						}
						
						if(fandt.contains(p)) {
							fandt.remove(p);
						}
						
						if(!f.contains(p)) {
							f.add(p);
						}
						
						if(t.contains(p)) {
							t.remove(p);
						}
						
						if(no.contains(p)) {
							no.remove(p);
						}
						
						HideListener.setState(p, 2);
						
						p.sendMessage(Lobby.getInstance().getPrefix() + "§7Dir werden jetzt nur §eFreunde §7angezeigt!");
						
					}else if(e.getCurrentItem().getDurability() == 11) {
						//team
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(!p.canSee(all)) {
								if(all.hasPermission("lobby.team")) {
									p.showPlayer(all);
								}
							}else {
								if(!all.hasPermission("lobby.team")) {
									p.hidePlayer(all);
								}
							}
						}
						
						if(fandt.contains(p)) {
							fandt.remove(p);
						}
						
						if(f.contains(p)) {
							f.remove(p);
						}
						
						if(!t.contains(p)) {
							t.add(p);
						}
						
						if(no.contains(p)) {
							no.remove(p);
						}
						
						HideListener.setState(p, 3);
						
						p.sendMessage(Lobby.getInstance().getPrefix() + "§7Dir werden jetzt nur §9Teammitglieder §7angezeigt!");		
						
					}else if(e.getCurrentItem().getDurability() == 14) {
						//alle weg
						
						for(Player all : Bukkit.getOnlinePlayers()) {
							if(p.canSee(all)) {
								p.hidePlayer(all);
							}
						}
						
						if(fandt.contains(p)) {
							fandt.remove(p);
						}
						
						if(f.contains(p)) {
							f.remove(p);
						}
						
						if(t.contains(p)) {
							t.remove(p);
						}
						
						if(!no.contains(p)) {
							no.add(p);
						}
						
						HideListener.setState(p, 4);
						
						p.sendMessage(Lobby.getInstance().getPrefix() + "§7Dir wird jetzt §ckein §7Spieler mehr angezeigt!");
					}
					
					p.closeInventory();
				}
				
				e.setCancelled(true);
			}
		}
		
	}
	
	public static List<Player> getFriendsAndTeam(){
		return fandt;
	}
	
	public static List<Player> getFriends(){
		return f;
	}
	
	public static List<Player> getTeam(){
		return t;
	}
	
	public static List<Player> getHideAll(){
		return no;
	}

}
