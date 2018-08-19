package net.minewars.lobby.listeners;

import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minewars.lobby.main.Lobby;

public class Boot {

	private ItemStack icon;
	private EnumParticle part;
	private String name;
	
	
	public Boot(ItemStack icon, EnumParticle particle, String name) {
		this.icon = icon;
		this.part = particle;
		this.name = name;
		
		Lobby.getInstance().getBoots().add(this);
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public EnumParticle getParticle() {
		return part;
	}
	
	public String getName() {
		return name;
	}
	
	public void remove() {
		Lobby.getInstance().getBoots().remove(this);
	}

}
