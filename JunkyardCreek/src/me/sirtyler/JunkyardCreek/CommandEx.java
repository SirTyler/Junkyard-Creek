package me.sirtyler.JunkyardCreek;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEx implements CommandExecutor{
	private JunkyardCreek plugin;
	private static Permission perm;
	
	public CommandEx(JunkyardCreek instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		perm = plugin.permission;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 1){
                String cmd = args[0].toString();
                if(cmd.equalsIgnoreCase("help")) {
                	player.sendMessage(ChatColor.DARK_GREEN + "[JunkyardCreek Help Guide] #Page 1#");
					player.sendMessage(ChatColor.BLUE + "Who knew that Waste Dumping was so usefull?!");
					player.sendMessage(ChatColor.GREEN + "/junk or /jc");
					player.sendMessage(ChatColor.GREEN + "/junk help - Displays this and other help pages.");
					player.sendMessage(ChatColor.GREEN + "/junk test - Check if JunkyardCreek is working.");
					return true;
                } else if(cmd.equalsIgnoreCase("test")){
                	if(perm.playerHas(player, "junkyardcreek.test")) {
                		sender.sendMessage(ChatColor.GOLD + "[JunkyardCreek] is Working!");
                		return true;
                	}else {
            			sender.sendMessage(ChatColor.RED + "[JunkyardCreek]: You Do Not Have Permission for That.");
            			return true;
            		}
                }
			}
		}
		return false;
	}

}
