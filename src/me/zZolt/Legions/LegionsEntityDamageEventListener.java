package me.zZolt.Legions;


import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_13_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_13_R2.PacketPlayOutTitle.EnumTitleAction;


public class LegionsEntityDamageEventListener 
implements Listener
{

	//TODO: cancel the explosion of the end crystal and make the xp bar turn yellow and count down from 8 seconds when u activate ability

	static int maxEnergy = 18;
	
	int increment = 6;
	
	
	public static HashMap<String, Integer> playerEnergy = new HashMap<String, Integer>();
	
	public static HashMap<Player, EnderCrystal> endCrystals = new HashMap<Player, EnderCrystal>();

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event)
	{
		
		Entity damager = event.getDamager();
		
		Entity damagee = event.getEntity();
		
		if(damager instanceof Player /*&& damagee instanceof Player*/) //checks if they're both players
		{
			
				if(playerEnergy.get(((Player) damager).getDisplayName()) < maxEnergy) 
				{
					
				playerEnergy.put(((Player) damager).getDisplayName(), playerEnergy.get(((Player) damager).getDisplayName()) + increment);
				
				}
				
			sendActionBar((Player)damager);
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		
		Player player = event.getPlayer();
		
		Vector dir = player.getLocation().getDirection();
		
		if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getItemInHand().getType() == Material.DIAMOND_SWORD) {
			
			if (endCrystals.containsKey(player)) {
	            EnderCrystal crystal = endCrystals.get(player);

	            player.teleport(crystal);
	            crystal.remove();
	            endCrystals.remove(player);
	            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 2F);
	            player.getLocation().setDirection(dir);
	            return;
	        }
			
			if(playerEnergy.get(player.getDisplayName()) == maxEnergy) {
			
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 2F);
			
			EnderCrystal eCrystal = (EnderCrystal) player.getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_CRYSTAL);
			
			endCrystals.put(player, eCrystal);
			
			eCrystal.setShowingBottom(false);
			
			Main.autoRemove(eCrystal, player);
			
			playerEnergy.put(player.getDisplayName(), 0);
			
			sendActionBar(player);
			
			}
			
		}
		
	}
	
	public static void sendActionBar(Player player) 
	{
		
		Integer loop = LegionsEntityDamageEventListener.playerEnergy.get(player.getDisplayName());
		
		StringBuilder energyBuilder = new StringBuilder();
		
		StringBuilder missingBuilder = new StringBuilder();
		if(loop == maxEnergy) {
		energyBuilder.append("&a");
		} else {
			
		energyBuilder.append("&b");
			
		}
		missingBuilder.append("&8");
		
		for(int i = 0; i < loop; i++) 
		{
			
			energyBuilder.append("█");
			
		}
		
		String energy = energyBuilder.toString();
		
		for(int i = 0; i < maxEnergy - loop; i++) 
		{
			
			missingBuilder.append("█");
			
		}
		
		String missing = missingBuilder.toString();
		
    	PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + energy + missing + "\"}")), 0, 20, 0);
        
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
		
	}
	
	/*@EventHandler
	public void onDeath(EntityDeathEvent event) {
		
		EnderCrystal entity = (EnderCrystal) event.getEntity();
		
		if(endCrystals.containsValue(entity)) {
			
			entity.remove();
			
			
		}
		
		
	}*/
	
	
}
