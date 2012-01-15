package me.sirtyler.JunkyardCreek;

import java.util.Random;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerFish extends PlayerListener{
	private static JunkyardCreek plugin;
	private FileConfiguration config;
	public Permission perm = null;
	Random rand = new Random();
	public PlayerFish(JunkyardCreek instance) {
		plugin = instance;
	}
	public void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		String state = event.getState().name();
		config = plugin.getConfig();
		perm = plugin.permission;
		if(state.equalsIgnoreCase("CAUGHT_FISH")) {
			int itemNum = 349;
			ItemStack item = new ItemStack(itemNum,1);
			String itemName = item.getType().name();
			if(config.contains("Junk.Items")) {
				try{
				String con = config.get("Junk.Items").toString();
				String[] items = con.split(",");
				int max = items.length;
				int pickedNumber = rand.nextInt(max);
				if(items[pickedNumber].contains(">")) {
					String[] test = items[pickedNumber].split(">");
					itemNum = Integer.parseInt(test[0]);
					item = new ItemStack(itemNum, 1);
					item.setDurability(Short.valueOf(test[1]));
				} else {
					itemNum = Integer.parseInt(items[pickedNumber]);
					item = new ItemStack(itemNum, 1);
				}
				itemName = item.getType().name();
				} catch (Exception e) {
					Exception p = new Exception("Could not understand Config");
					e.printStackTrace();
					p.printStackTrace();
				}
				event.setCancelled(true);
				Location oLoc = event.getCaught().getLocation();
				Location pLoc = player.getLocation();
				Entity ent = player.getWorld().dropItem(oLoc, item);
				//Velocity from Minecraft Source + MCP Decompiler. Thank you Notch and MCP :3
				double d1 = pLoc.getX() - oLoc.getX();
				double d3 = pLoc.getY() - oLoc.getY();
				double d5 = pLoc.getZ() - oLoc.getZ();
				double d7 = ((float)Math.sqrt((d1 * d1 + d3 * d3 + d5 * d5)));
				double d9 = 0.10000000000000001D;
				double motionX = d1 * d9;
				double motionY = d3 * d9 + (double)((float)Math.sqrt(d7)) * 0.080000000000000002D;
				double motionZ = d5 * d9;
				ent.setVelocity(new Vector(motionX, motionY, motionZ));
				player.sendMessage(ChatColor.GOLD + "You got a " + itemName);
			} else {
				
			}
		} else if(state.equalsIgnoreCase("FAILED_ATTEMPT")) {
			player.sendMessage(ChatColor.GRAY + "Awww, you didn't get anything");
		}
	}
}
