package net.minewars.lobby.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.minewars.lobby.listeners.Boot;
import net.minewars.lobby.listeners.Particle;
import net.minewars.lobby.main.Lobby;

public class MoveEvent implements Listener{

	private int cubic = 3;
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.getPlayer().getLocation().getY() <= -100) {
			Location loc = (Location) Lobby.getInstance().getConfig().get("Ingame.Join.Spawn");
			e.getPlayer().teleport(loc);
		}else {
			Player player = e.getPlayer();
			
			if(player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SLIME_BLOCK && player.getLocation().subtract(0, 2, 0).getBlock().getType() == Material.EMERALD_BLOCK) {
				Location loc = player.getLocation();
				loc.setPitch(-30);
				Vector v = loc.getDirection().multiply(3D);
				
				player.setVelocity(v);
			}
			
			if(player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.SLIME_BLOCK && player.getLocation().getBlock().getType() == Material.CARPET) {
				Vector v = new Vector(0, 5, 0).normalize();
				
				player.setVelocity(v);
			}
			
			
			if(e.getPlayer().getInventory().getBoots() != null) {
				ItemStack boots = e.getPlayer().getInventory().getBoots();
				for(Boot b : Lobby.getInstance().getBoots()) {
					if(b.getIcon().equals(boots)) {
						Particle p = new Particle(b.getParticle(), e.getPlayer().getLocation(), true, 0, 0, 0, 1, 1);
						p.sendAll();
					}
				}
			}
			
			
			
			if(Lobby.getInstance().getShieldPlayers().contains(e.getPlayer())) {
				
				for(Entity entity : e.getPlayer().getNearbyEntities(cubic, cubic, cubic)) {
					if(entity instanceof Player) {
						Player p = (Player) entity;
						
						double ax = e.getPlayer().getLocation().getX();
						double ay = e.getPlayer().getLocation().getY();
						double az = e.getPlayer().getLocation().getZ();
						
						double bx = p.getLocation().getX();
						double by = p.getLocation().getY();
						double bz = p.getLocation().getZ();
						
						double x = ax - bx;
						double y = ay - by;
						double z = az - bz;
						
						Vector v = new Vector(x, y, z).normalize().multiply(1D).setY(0.3D);
						p.setVelocity(v);
						
					}
				}
				
			}else {
				for(Entity entity : e.getPlayer().getNearbyEntities(cubic, cubic, cubic)) {
					if(entity instanceof Player) {
						Player p = (Player) entity;
						if(Lobby.getInstance().getShieldPlayers().contains(p)) {
							double ax = e.getPlayer().getLocation().getX();
							double ay = e.getPlayer().getLocation().getY();
							double az = e.getPlayer().getLocation().getZ();
							
							double bx = p.getLocation().getX();
							double by = p.getLocation().getY();
							double bz = p.getLocation().getZ();
							
							double x = bx - ax;
							double y = by - ay;
							double z = bz - az;
							
							Vector v = new Vector(x, y, z).normalize().multiply(1D).setY(0.3D);
							e.getPlayer().setVelocity(v);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if(e.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onD(EntityDamageByEntityEvent e) {
		e.setCancelled(true);
	}

}
