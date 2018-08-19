package net.minewars.lobby.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minewars.cloud.bukkit.main.CloudPlugin;
import net.minewars.cloud.bukkit.signs.Server;
import net.minewars.lobby.events.ChatEvent;
import net.minewars.lobby.events.InteractEvent;
import net.minewars.lobby.events.InvClickEvent;
import net.minewars.lobby.events.JoinEvent;
import net.minewars.lobby.events.MoveEvent;
import net.minewars.lobby.listeners.Boot;
import net.minewars.lobby.listeners.HideListener;
import net.minewars.lobby.scoreboard.GroupManager;
import net.minewars.lobby.scoreboard.ScoreboardClass;

public class Lobby extends JavaPlugin{

	private static Lobby main;
	private String prefix;
	private Inventory navi;
	private List<Player> shield = new ArrayList<>();
	private List<Player> silent = new ArrayList<>();
	private List<Boot> boots = new ArrayList<>();
	private HashMap<Integer, Location> map = new HashMap<>();
	private MySQL mysql;
	
	@Override
	public void onEnable() {
		main = this;
		
		
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		Bukkit.getPluginManager().registerEvents(new InvClickEvent(), this);
		Bukkit.getPluginManager().registerEvents(new InteractEvent(), this);
		Bukkit.getPluginManager().registerEvents(new MoveEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		this.initConfig();
		this.loadBoots();
		new ScoreboardClass();
		new GroupManager();
		
		prefix = this.getConfig().getString("Messages.Prefix");
		
		String host = this.getConfig().getString("MySQL.Host");
		String port = this.getConfig().getString("MySQL.Port");
		String database = this.getConfig().getString("MySQL.Database");
		String user = this.getConfig().getString("MySQL.Username");
		String pwd = this.getConfig().getString("MySQL.Password");
		
		MySQL sql = new MySQL(host, port, database, user, pwd);
		sql.connect();
		new HideListener(sql);
	
		mysql = sql;
		
		for(World w : Bukkit.getWorlds()) {
			w.setGameRuleValue("doDaylightCycle", "false");
		}
	}
	
	@Override
	public void onDisable() {
		ScoreboardClass.disconnect();
	}
	
	public static Lobby getInstance() {
		return main;
	}
	
	public HashMap<Integer, Location> getNaviMap(){
		return map;
	}
	
	public List<Player> getShieldPlayers(){
		return shield;
	}
	
	public MySQL getMySQL() {
		return mysql;
	}
	
	public List<Player> getSilentPlayers(){
		return silent;
	}

	public List<Boot> getBoots(){
		return boots;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public Inventory getNaviInventory() {
		return navi;
	}
	
	private void loadBoots() {
		
		/** Boots+
		 * 
		 * 1. SpeedBoots mit Effekt Speed 5
		 * 2. Boots mit herzen
		 * 3. Boots mit feuer
		 * 4. Boots mit rain
		 * 5. Boots mit endereffekten
		 * 6. boots mit noten
		 * 7. Explosionsboots
		 * 8. Smokeboots
		 * 9. Villagerangry boots
		 * 
		 */
		
		ItemStack boot1 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta1 = (LeatherArmorMeta) boot1.getItemMeta();
		meta1.setColor(Color.RED);
		meta1.setDisplayName("§4Love-Boots");
		boot1.setItemMeta(meta1);
		new Boot(boot1, EnumParticle.HEART, boot1.getItemMeta().getDisplayName());
		
		ItemStack boot2 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta2 = (LeatherArmorMeta) boot2.getItemMeta();
		meta2.setColor(Color.ORANGE);
		meta2.setDisplayName("§6Feuer-Boots");
		boot2.setItemMeta(meta2);
		new Boot(boot2, EnumParticle.LAVA, boot2.getItemMeta().getDisplayName());
		
		ItemStack boot3 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta3 = (LeatherArmorMeta) boot3.getItemMeta();
		meta3.setColor(Color.BLUE);
		meta3.setDisplayName("§9Rain-Boots");
		boot3.setItemMeta(meta3);
		new Boot(boot3, EnumParticle.DRIP_WATER, boot3.getItemMeta().getDisplayName());
		
		ItemStack boot4 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta4 = (LeatherArmorMeta) boot4.getItemMeta();
		meta4.setColor(Color.PURPLE);
		meta4.setDisplayName("§5Ender-Boots");
		boot4.setItemMeta(meta4);
		new Boot(boot4, EnumParticle.SPELL_WITCH, boot4.getItemMeta().getDisplayName());
		
		ItemStack boot5 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta5 = (LeatherArmorMeta) boot5.getItemMeta();
		meta5.setColor(Color.AQUA);
		meta5.setDisplayName("§3Musik-Boots");
		boot5.setItemMeta(meta5);
		new Boot(boot5, EnumParticle.NOTE, boot5.getItemMeta().getDisplayName());
		
		ItemStack boot6 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta6 = (LeatherArmorMeta) boot6.getItemMeta();
		meta6.setColor(Color.GRAY);
		meta6.setDisplayName("§8Smoke-Boots");
		boot6.setItemMeta(meta6);
		new Boot(boot6, EnumParticle.SPELL, boot6.getItemMeta().getDisplayName());
		
		ItemStack boot7 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta7 = (LeatherArmorMeta) boot7.getItemMeta();
		meta7.setColor(Color.GRAY);
		meta7.setDisplayName("§7Explosoins-Boots");
		boot7.setItemMeta(meta7);
		new Boot(boot7, EnumParticle.EXPLOSION_LARGE, boot7.getItemMeta().getDisplayName());
		
		ItemStack boot8 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta8 = (LeatherArmorMeta) boot8.getItemMeta();
		meta8.setColor(Color.FUCHSIA);
		meta8.setDisplayName("§cAngry-Boots");
		boot8.setItemMeta(meta8);
		new Boot(boot8, EnumParticle.VILLAGER_ANGRY, boot8.getItemMeta().getDisplayName());
		
		ItemStack boot9 = new ItemStack(Material.LEATHER_BOOTS, 1);
		LeatherArmorMeta meta9 = (LeatherArmorMeta) boot9.getItemMeta();
		meta9.setColor(Color.LIME);
		meta9.setDisplayName("§aSlime-Boots");
		boot9.setItemMeta(meta9);
		new Boot(boot9, EnumParticle.SLIME, boot9.getItemMeta().getDisplayName());
		
		
	}
	
	private void initConfig(){
		
		this.reloadConfig();
		
		this.getConfig().options().header("Lobby by Swagzzlord");
		this.getConfig().addDefault("MySQL.Host", "localhost");
		this.getConfig().addDefault("MySQL.Port", "3306");
		this.getConfig().addDefault("MySQL.Database", "db_skywars");
		this.getConfig().addDefault("MySQL.Username", "username");
		this.getConfig().addDefault("MySQL.Password", "password");
		
		this.getConfig().addDefault("Ingame.Join.TeleportToSpawn", true);
		this.getConfig().addDefault("Messages.Prefix", "§8[§aLobby§8] ");
		
		this.getConfig().addDefault("Ingame.Silentlobby.Type", "Silentlobby");
		this.getConfig().addDefault("Ingame.Silentlobby.This", false);
		
		this.getConfig().addDefault("Inventory.Navigator.Size", 27);
		this.getConfig().addDefault("Inventory.Navigator.Slot", 0);
		this.getConfig().addDefault("Inventory.Navigator.Material", Material.COMPASS);
		
		if(this.getConfig().getShortList("Inventory.Navigator.Games") == null) {
			List<String> games = new ArrayList<>();
			
			//Slot,Material,Name,Lore,shortid,TpLoc
			games.add("0,COMPASS,Test,Nur ein Test,0,World:world;x:0;y:0;z:0;yaw:0;pitch:0");
			
			this.getConfig().set("Inventory.Navigator.Games", games);
		}else {
			List<String> games = this.getConfig().getStringList("Inventory.Navigator.Games");
			navi = Bukkit.createInventory(null, this.getConfig().getInt("Inventory.Navigator.Size"), "§eNavigator");
			
			for(String e : games) {
				String[] args = e.split(",");
				int slot = Integer.parseInt(args[0]);
				Material mat = Material.getMaterial(args[1]);
				String name = args[2];
				List<String> l = new ArrayList<>();
				l.add(args[3]);
				int shortid = Integer.parseInt(args[4]);
				
				String[] req = args[5].split(";");
				World world = Bukkit.getWorld(req[0].split("World:")[1]);
				double x = Double.parseDouble(req[1].split("x:")[1]);
				double y = Double.parseDouble(req[2].split("y:")[1]);
				double z = Double.parseDouble(req[3].split("z:")[1]);
				float yaw = Float.valueOf(req[4].split("yaw:")[1]);
				float pitch = Float.valueOf(req[5].split("pitch:")[1]);
				
				Location loc = new Location(world, x, y, z, yaw, pitch);
				ItemStack item = Item.cItem(mat, 1, shortid, name, l, null, 0);
				map.put(slot, loc);
				
				navi.setItem(slot, item);
			}
		}
		
		
		//Prefix
		this.getConfig().addDefault("Tab.Admin.Prefix", "§4Admin §7| §4");
		this.getConfig().addDefault("Tab.SrDev.Prefix", "§3SrDev §7| §3");
		this.getConfig().addDefault("Tab.Dev.Prefix", "§bDev §7| §b");
		this.getConfig().addDefault("Tab.Content.Prefix", "§3Content §7| §3");
		this.getConfig().addDefault("Tab.SrMod.Prefix", "§cSrMod §7| §c");
		this.getConfig().addDefault("Tab.Mod.Prefix", "§cMod §7| §c");
		this.getConfig().addDefault("Tab.Sup.Prefix", "§aSup §7| §a");
		this.getConfig().addDefault("Tab.HeadBuilder.Prefix", "§aSrBuild §7| §a");
		this.getConfig().addDefault("Tab.Builder.Prefix", "§aBuilder §7| §a");
		this.getConfig().addDefault("Tab.YouTuber.Prefix", "§5");
		this.getConfig().addDefault("Tab.Premium+.Prefix", "§e");
		this.getConfig().addDefault("Tab.MWler.Prefix", "§9MWler §7| §9");
		this.getConfig().addDefault("Tab.Elite.Prefix", "§dElite §7| §d");
		this.getConfig().addDefault("Tab.Premium.Prefix", "§6");
		this.getConfig().addDefault("Tab.Player.Prefix", "§7");
		
		
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("navi")) {
				if(p.hasPermission("lobby.navi")) {
					if(args.length == 5) {
						int slot = Integer.parseInt(args[0]);
						Material mat = Material.getMaterial(Integer.parseInt(args[1]));
						String name = args[2];
						String lore = args[3];
						int shortid = Integer.parseInt(args[4]);
						Location loc = p.getLocation();
						
						name = name.replace("&", "§");
						lore = lore.replace("&", "§");
						
						String entry = slot + "," + mat + "," + name + "," + lore + "," + shortid + ",World:" + loc.getWorld().getName() + ";x:" + loc.getX() + ";y:" + loc.getY() + ";z:" + loc.getZ() + ";yaw:" + loc.getYaw() + ";pitch:" + loc.getPitch();
						
						List<String> games = this.getConfig().getStringList("Inventory.Navigator.Games");
						games.add(entry);
						
						this.getConfig().set("Inventory.Navigator.Games", games);
						this.saveConfig();
						
						p.sendMessage(prefix + "§7Du hast einen neuen Spawn gesetzt!");
						
					}else {
						p.sendMessage(prefix + "§c/navi <slot> <itemid> <name> <lore> <itemshortid>");
					}
				}else {
					p.sendMessage("§cDu hast keine Rechte dafür!");
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("build")) {
				if(p.hasPermission("Lobby.build")) {
					if(ChatEvent.getList().contains(p)) {
						ChatEvent.getList().remove(p);
						p.sendMessage(prefix + "§7Du kannst nun nicht mehr bauen!");
					}else {
						ChatEvent.getList().add(p);
						p.sendMessage(prefix + "§7Du kannst jetzt bauen!");
					}
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("setspawn")) {
				if(p.hasPermission("lobby.setspawn")) {
					if(args.length == 0) {
						this.getConfig().set("Ingame.Join.Spawn", p.getLocation());
						this.saveConfig();
						
						p.sendMessage(prefix + "§7Du hast den Spawn gesetzt!");
					}else {
						p.sendMessage(prefix + "§c/setspawn");
					}
				}else {
					p.sendMessage("§cDu hast keine Rechte dafür!");
				}
			}
			
			
			if(cmd.getName().equalsIgnoreCase("boots")) {
				if(p.hasPermission("lobby.boots")) {
					if(args.length == 0) {
						
						Inventory inv = Bukkit.createInventory(null, 27, "§9Boots");
						for(int i = 0; i < inv.getSize(); i++) {
							inv.setItem(i, Item.cItem(Material.STAINED_GLASS_PANE, 1, 9, " ", null, null, 0));
						}
						
						for(int i = 0; i < boots.size(); i++) {
							inv.setItem(9 + i, boots.get(i).getIcon());
						}
						
						p.openInventory(inv);
						
					}else {
						p.sendMessage(prefix + "§c/boots");
					}
				}else {
					p.sendMessage("§cDu hast keine Rechte dafür!");
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("gui")) {
				if(p.hasPermission("lobby.servergui")) {
					if(args.length == 1) {
						try {
							Server[] array = CloudPlugin.getServers(args[0]);
													
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
							
							Inventory inv = Bukkit.createInventory(null, size, "§bServers von§8: §a" + args[0]);
							
							for(Server s : array) {
								s.ping();
								
								if(s.isOnline()) {
									if(!s.inMaintenance()) {
										if(s.isFull()) {
											List<String> l = new ArrayList<>();
											l.add("§7" + s.getPlayerCount() + "§8/§7" + s.getMaxPlayers());
											l.add("§7Motd§8: §7" + s.getMotd());
											ItemStack item = Item.cItem(Material.GLOWSTONE_DUST, 1, 0, "§6" + s.getName(), l, null, 0);
											int slot = Integer.parseInt(s.getName().split("-")[1]);
											inv.setItem(slot, item);
											
										}else {
											List<String> l = new ArrayList<>();
											l.add("§7" + s.getPlayerCount() + "§8/§7" + s.getMaxPlayers());
											ItemStack item = Item.cItem(Material.SUGAR, 1, 0, "§6" + s.getName(), l, null, 0);
											int slot = Integer.parseInt(s.getName().split("-")[1]);
											inv.setItem(slot, item);
										}
									}else {
										List<String> l = new ArrayList<>();
										l.add("§4Maintenance");
										l.add("§7Motd§8: §7" + s.getMotd());
										ItemStack item = Item.cItem(Material.getMaterial(289), 1, 0, "§8" + s.getName(), l, null, 0);
										int slot = Integer.parseInt(s.getName().split("-")[1]);
										inv.setItem(slot, item);
									}
								}else {
									List<String> l = new ArrayList<>();
									l.add("§c------");
									ItemStack item = Item.cItem(Material.REDSTONE, 1, 0, "§c" + s.getName(), l, null, 0);
									int slot = Integer.parseInt(s.getName().split("-")[1]);
									inv.setItem(slot, item);
								}
							}
							
							for(int i = 0; i < inv.getSize(); i++) {
								if(inv.getItem(i) == null) {
									inv.setItem(i, Item.cItem(Material.STAINED_GLASS_PANE, 1, 15, " ", null, null, 0));
								}
							}
							
							p.openInventory(inv);
						} catch (IOException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					}else {
						p.sendMessage(prefix + "§c/gui <type>");
					}
				}else {
					p.sendMessage("§cDu hast keine Rechte dafür!");
				}
			}
		}else {
			sender.sendMessage("§cDu musst ein Spieler sein!");
		}
		
		return true;
	}

}
