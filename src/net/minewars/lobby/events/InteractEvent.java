package net.minewars.lobby.events;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.minewars.cloud.bukkit.main.CloudPlugin;
import net.minewars.cloud.bukkit.signs.Server;
import net.minewars.lobby.listeners.HideListener;
import net.minewars.lobby.main.Item;
import net.minewars.lobby.main.Lobby;
import net.minewars.lobby.scoreboard.ScoreboardClass;

public class InteractEvent implements Listener{

	private List<Player> players = new ArrayList<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			
			if(e.getClickedBlock() != null) {
				if(e.getClickedBlock().getType() == Material.ENDER_CHEST || e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST
						|| e.getClickedBlock().getType() == Material.DISPENSER || e.getClickedBlock().getType() == Material.WORKBENCH || e.getClickedBlock().getType() == Material.DROPPER 
							|| e.getClickedBlock().getType() == Material.FURNACE || e.getClickedBlock().getType() == Material.BURNING_FURNACE || e.getClickedBlock().getType() == Material.ANVIL 
								|| e.getClickedBlock().getType() == Material.HOPPER || e.getClickedBlock().getType() == Material.HOPPER_MINECART || e.getClickedBlock().getType() == Material.BREWING_STAND) {
					
					
					e.setCancelled(true);
				}
			}
			
			
			
			
			
			if(e.getItem() != null) {
				if(e.getItem().getItemMeta().getDisplayName().startsWith("§eLobby Switcher")) {
					try {
						Server[] array = CloudPlugin.getServers("Lobby");
						
						int length = array.length;
						int dif = 54 - length;
						int size = 9;
						for(int i = 0; i <= dif; i++) {
							int l = length + i;
							if(l % 9 == 0) {
								size = l;
								break;
							}
						}
						
						Inventory inv = Bukkit.createInventory(null, size, "§aLobbys");
						
						for(Server s : array) {
							s.ping();
							
							if(s.isOnline()) {
								if(!s.inMaintenance()) {
									if(s.isFull()) {
										List<String> l = new ArrayList<>();
										l.add("§7" + s.getPlayerCount() + "§8/§7" + s.getMaxPlayers());
										ItemStack item = Item.cItem(Material.INK_SACK, 1, 11, "§6" + s.getName(), l, null, 0);
										//Item.cItem(mat, amount, shortid, name, lore, ench, enchLevel)
										int slot = Integer.parseInt(s.getName().split("-")[1]);
										inv.setItem(slot, item);
										
									}else {
										List<String> l = new ArrayList<>();
										l.add("§7" + s.getPlayerCount() + "§8/§7" + s.getMaxPlayers());
										ItemStack item = Item.cItem(Material.INK_SACK, 1, 10, "§6" + s.getName(), l, null, 0);
										int slot = Integer.parseInt(s.getName().split("-")[1]);
										inv.setItem(slot, item);
									}
								}else {
									List<String> l = new ArrayList<>();
									l.add("§4Maintenance");
									ItemStack item = Item.cItem(Material.INK_SACK, 1, 8, "§8" + s.getName(), l, null, 0);
									int slot = Integer.parseInt(s.getName().split("-")[1]);
									inv.setItem(slot, item);
								}
							}else {
								List<String> l = new ArrayList<>();
								l.add("§c------");
								ItemStack item = Item.cItem(Material.INK_SACK, 1, 1, "§c" + s.getName(), l, null, 0);
								
								int slot = Integer.parseInt(s.getName().split("-")[1]);
								inv.setItem(slot, item);
							}
						}
						
						for(int i = 0; i < inv.getSize(); i++) {
							if(inv.getItem(i) == null) {
								inv.setItem(i, Item.cItem(Material.STAINED_GLASS_PANE, 1, 15, " ", null, null, 0));
							}
						}
						
						e.getPlayer().openInventory(inv);
					} catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}else if(e.getItem().getItemMeta().getDisplayName().startsWith("§aNavigator")){
					e.getPlayer().openInventory(Lobby.getInstance().getNaviInventory());
					
				}else if(e.getItem().getItemMeta().getDisplayName().contains("Automatischer Nickname")){
					List<String> lore4 = new ArrayList<>();
					lore4.add("§7Aktiviere den AutoNick!");
					
					if(!players.contains(e.getPlayer())) {
						if(ScoreboardClass.isNicked(e.getPlayer().getUniqueId().toString())) {
							ScoreboardClass.nick(e.getPlayer().getUniqueId().toString(), false);
							e.getPlayer().getInventory().setItem(5, null);
							e.getPlayer().getInventory().setItem(5, Item.cItem(Material.NAME_TAG, 1, 0, "§cAutomatischer Nickname §7[Rechtsklick]", lore4, null, 0));
							
							e.getPlayer().sendMessage(Lobby.getInstance().getPrefix() + "§7Automatischer Nickname §cdeaktiviert!");
						}else {
							ScoreboardClass.nick(e.getPlayer().getUniqueId().toString(), true);
							e.getPlayer().getInventory().setItem(5, null);
							e.getPlayer().getInventory().setItem(5, Item.cItem(Material.NAME_TAG, 1, 0, "§aAutomatischer Nickname §7[Rechtsklick]", lore4, null, 0));
							
							e.getPlayer().sendMessage(Lobby.getInstance().getPrefix() + "§7Automatischer Nickname §aaktiviert!");
						}
						
						players.add(e.getPlayer());
						
						Bukkit.getScheduler().runTaskLater(Lobby.getInstance(), new Runnable() {
							
							@Override
							public void run() {
								players.remove(e.getPlayer());
								
							}
						}, 20*2);
					}else {
						e.getPlayer().sendMessage(Lobby.getInstance().getPrefix() + "§cSpamschutz§7: Bitte warte einen Moment...");
					}
					
				}else if(e.getItem().getItemMeta().getDisplayName().startsWith("§6Spieler verstecken")){
					Inventory inv = Bukkit.createInventory(null, 27, "§6Spieler verstecken");
					for(int i = 0; i < inv.getSize(); i++) {
						inv.setItem(i, Item.cItem(Material.STAINED_GLASS_PANE, 1, 9, " ", null, null, 0));
					}
					
					/**
					 * 2,243e-18 Ws = 13,999 eV
					 * 0,000000000000000002243
					 * 
					 * We = U * I * t
					 * 0,000000000000000002243 = 36 * I * 60
					 * 0,000000000000000002243 = 2160 * I
					 * 0,000000000000000000001038426 = I
					 */
					
					ItemStack all = Item.item(Material.STAINED_CLAY, 1, 5, "§aAlle anzeigen §7[Rechtsklick]", "§7Alle Spieler anzeigen", null, 0);
					ItemStack fandt = Item.item(Material.STAINED_CLAY, 1, 1, "§6Freunde und Teammitglieder anzeigen §7[Rechtsklick]", "§7Alle Freunde und Teammitglieder anzeigen", null, 0);
					ItemStack f = Item.item(Material.STAINED_CLAY, 1, 4, "§eAlle Freunde anzeigen §7[Rechtsklick]", "§7Alle Freunde anzeigen", null, 0);
					ItemStack t = Item.item(Material.STAINED_CLAY, 1, 11, "§9Alle Teammitglieder anzeigen §7[Rechtsklick]", "§7Alle Teammitglieder anzeigen", null, 0);
					ItemStack no = Item.item(Material.STAINED_CLAY, 1, 14, "§cAlle verstecken §7[Rechtsklick]", "§7Alle Spieler verstecken", null, 0);
					
					if(HideListener.getState(e.getPlayer()) == 0) {
						all.getItemMeta().addEnchant(Enchantment.ARROW_INFINITE, 1, true);
						
					}else if(HideListener.getState(e.getPlayer()) == 1) {
						fandt.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
						
					}else if(HideListener.getState(e.getPlayer()) == 2) {
						f.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
						
					}else if(HideListener.getState(e.getPlayer()) == 3) {
						t.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
						
					}else if(HideListener.getState(e.getPlayer()) == 4) {
						no.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
						
					}
					
					
					inv.setItem(9, all);
					inv.setItem(11, fandt);
					inv.setItem(13, f);
					inv.setItem(15, t);
					inv.setItem(17, no);
					
					e.getPlayer().openInventory(inv);
					
				}else if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5Schutzschild")){
					if(!Lobby.getInstance().getShieldPlayers().contains(e.getPlayer())) {
						Lobby.getInstance().getShieldPlayers().add(e.getPlayer());
						e.getPlayer().sendMessage(Lobby.getInstance().getPrefix() + "§7Schutzschild §aaktiviert!");
						
					}else {
						Lobby.getInstance().getShieldPlayers().remove(e.getPlayer());
						e.getPlayer().sendMessage(Lobby.getInstance().getPrefix() + "§7Schutzschild §cdeaktiviert!");
					}
					
					e.setCancelled(true);
					
				}else if(e.getItem().getItemMeta().getDisplayName().startsWith("§cSilent Lobby")){
					
					if(Lobby.getInstance().getConfig().getBoolean("Ingame.Silentlobby.This")){
						Server[] array = null;
						try {
							array = CloudPlugin.getServers("Lobby");
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						e.getPlayer().sendMessage(Lobby.getInstance().getPrefix() + "§7Du bist nun §anicht mehr §7in der Silentlobby!");
						
						ByteArrayOutputStream b = new ByteArrayOutputStream();
						DataOutputStream out = new DataOutputStream(b);
						try {
							
							int id = 0;
							try {
								Random rdm = new Random();
								int i = rdm.nextInt(array.length);
								id = i;
							}catch(Exception wee) {
								
							}
							
							Server server = CloudPlugin.getServer("Lobby-" + id);
							if(server.isOnline() && !server.isFull()) {
								
							}else {
								for(Server s : array) {
									if(s.isOnline() && !s.isFull()) {
										id = Integer.parseInt(s.getName().split("-")[1]);
									}
								}
							}
							
							out.writeUTF("Connect");
							out.writeUTF("Lobby" + "-" + id);
							
							e.getPlayer().sendPluginMessage(Lobby.getInstance(), "BungeeCord", b.toByteArray());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						HideListener.setSilentLobby(e.getPlayer(), false);
						e.setCancelled(true);
						
					}else {
						Server[] array = null;
						String type = Lobby.getInstance().getConfig().getString("Ingame.Silentlobby.Type");
						try {
							array = CloudPlugin.getServers(type);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						ByteArrayOutputStream b = new ByteArrayOutputStream();
						DataOutputStream out = new DataOutputStream(b);
						try {
							int id = 0;
							try {
								Random rdm = new Random();
								int i = rdm.nextInt(array.length);
								id = i;
							}catch(Exception wee) {
								
							}
							
							Server server = CloudPlugin.getServer(type + "-" + id);
							if(server.isOnline() && !server.isFull()) {
								
							}else {
								for(Server s : array) {
									if(s.isOnline() && !s.isFull()) {
										id = Integer.parseInt(s.getName().split("-")[1]);
									}
								}
							}
							
							e.getPlayer().sendMessage(Lobby.getInstance().getPrefix() + "§7Du bist nun in der §cSilentlobby!");
							
							out.writeUTF("Connect");
							out.writeUTF(type + "-" + id);
							
							e.getPlayer().sendPluginMessage(Lobby.getInstance(), "BungeeCord", b.toByteArray());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						HideListener.setSilentLobby(e.getPlayer(), true);
						e.setCancelled(true);
					}
					
				}
			}
		}else if(e.getAction() == Action.PHYSICAL) {
			e.setCancelled(true);
		}
	}

}
