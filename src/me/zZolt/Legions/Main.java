package me.zZolt.Legions;

import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	@Override
	public void onEnable()
	{
		this.getLogger().info("Legions has been enabled.!");
		this.getServer().getPluginManager().registerEvents(new LegionsEntityDamageEventListener(), this);
		
		
		for (Player player: getServer().getOnlinePlayers()) {
			
		LegionsEntityDamageEventListener.playerEnergy.put(player.getDisplayName(), 0);
		
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
		    public void run(){
		    	
		    	for (Player player: getServer().getOnlinePlayers()) {
		    	
		    	LegionsEntityDamageEventListener.sendActionBar(player);
		    	
		    	
		    }
		    	
		    }
		}, 0, 10);
		
		
	}
	
	@Override
	public void onDisable()
	{
		
		this.getLogger().info("Legions has been disabled.");
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if(LegionsEntityDamageEventListener.playerEnergy.containsKey(player.getDisplayName())){
			
			return;
			
		} else {
		
		LegionsEntityDamageEventListener.playerEnergy.put(player.getDisplayName(), 0);
		
		}
	}
	
	public static void autoRemove(EnderCrystal end, Player player) {
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Legions"), new Runnable(){
		    public void run(){
		    	
		    	
		    	if(!end.isDead()) {
		    	
		    	end.remove();
		    	
		    	}
		    	
		    	LegionsEntityDamageEventListener.endCrystals.remove(player);
		    	
		    }
		}, 160);
		
	}
		
}
	
