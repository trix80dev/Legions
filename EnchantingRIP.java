package me.zZolt.Legions;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cybermaxke.merchants.api.Merchant;
import me.cybermaxke.merchants.api.MerchantAPI;
import me.cybermaxke.merchants.api.MerchantOffer;
import me.cybermaxke.merchants.api.MerchantTradeListener;
import me.cybermaxke.merchants.api.Merchants;
import net.md_5.bungee.api.ChatColor;

public class Enchanting implements Listener {
	
	MerchantAPI api = Merchants.get();
	Merchant m = api.newMerchant("Enchantment Table");
	
	@EventHandler
	public void enchant(PlayerInteractEvent e) {
		
		ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = i.getItemMeta();
		ArrayList<String> il = new ArrayList<>();
		il.add(ChatColor.ITALIC + "Enchantment");
		im.setDisplayName(ChatColor.AQUA + "Enchanted Diamond Sword");
		im.setLore(il);
		i.setItemMeta(im);
		
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
			e.setCancelled(true);
			m.addOffer(api.newOffer(i, new ItemStack(Material.WOOD_SWORD), new ItemStack(Material.INK_SACK, 3, (short) 4)));
			m.addOffer(api.newOffer(i, new ItemStack(Material.STONE_SWORD), new ItemStack(Material.INK_SACK, 2, (short) 4)));
			m.addOffer(api.newOffer(i, new ItemStack(Material.IRON_SWORD), new ItemStack(Material.INK_SACK, 1, (short) 4)));
			m.addCustomer(p);
			
		}
		
	}
	
	@EventHandler
	public void Trading(InventoryClickEvent e) {
		HumanEntity p = e.getWhoClicked();
		
		ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = i.getItemMeta();
		ArrayList<String> il = new ArrayList<>();
		il.add(ChatColor.ITALIC + "Enchantment");
		im.setDisplayName(ChatColor.AQUA + "Enchanted Diamond Sword");
		//i.getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(il);
		i.setItemMeta(im);
		
		if(e.getClick() == ClickType.SHIFT_LEFT) {
			p.sendMessage("Shift Click");
			if(p.getInventory().contains(i)) {
				p.getInventory().remove(i);
				p.setItemOnCursor(i);
				
			}
		}
		
	m.addListener(new MerchantTradeListener() {
	
	@Override
	public void onTrade(Merchant merchant, MerchantOffer offer, Player customer) {
		//if(offer.getResultItem() == new ItemStack(Material.DIAMOND_SWORD)) {
			if(e.getClick() == ClickType.LEFT){
				p.sendMessage("Left Click");
				p.getItemOnCursor().addEnchantment(Enchantment.DAMAGE_ALL, 1);
			}
			
			
			//offer.getResultItem().addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 1);
			
		//}
	}
	
	});
	
	}

}
