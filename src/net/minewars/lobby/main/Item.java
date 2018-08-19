package net.minewars.lobby.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {

	public static ItemStack cItem(Material mat, int amount, int shortid, String name, List<String> lore, Enchantment ench, int enchLevel) {
		ItemStack item = new ItemStack(mat, amount, (short) shortid);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		
		if(lore != null) {
			meta.setLore(lore);
		}
		
		if(ench != null) {
			item.addEnchantment(ench, enchLevel);
		}
		
		item.setItemMeta(meta);
		
		return item;
	}

	
	public static ItemStack item(Material mat, int amount, int shortid, String name, String lore, Enchantment ench, int enchLevel) {
		ItemStack item = new ItemStack(mat, amount, (short) shortid);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		
		if(lore != null) {
			List<String> list = new ArrayList<String>();
			list.add(lore);
			meta.setLore(list);
		}
		
		if(ench != null) {
			item.addEnchantment(ench, enchLevel);
		}
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
