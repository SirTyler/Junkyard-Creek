package me.sirtyler.JunkyardCreek;

import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class JunkyardCreek extends JavaPlugin{
	public static JunkyardCreek plugin;
	private FileConfiguration config;
	public final Logger logger = Logger.getLogger("Minecraft");
	private final PlayerFish interact = new PlayerFish(this);
	public CommandEx myExecutor;
	public Permission permission = null;
	
	public JunkyardCreek() {
		super();
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info("[" + pdfFile.getName() + "]" + " version-" + pdfFile.getVersion() + " is now Disabled.");	
	}

	@Override
	public void onEnable() {
		myExecutor = new CommandEx(this);
		PluginManager pm = getServer().getPluginManager();
		PluginDescriptionFile pdfFile = this.getDescription();
		setupPermissions();
		pm.registerEvent(Event.Type.PLAYER_FISH, this.interact, Event.Priority.Highest, this);
		getCommand("junk").setExecutor(myExecutor);
		getCommand("jc").setExecutor(myExecutor);
		checkConfig();
		this.logger.info("[" + pdfFile.getName() + "]" + " version-" + pdfFile.getVersion() + " is now Enabled.");	
	}
	
	private void checkConfig() {
		try{
			config = this.getConfig();
			config.options().copyDefaults(true);
			this.saveConfig();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    private Boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

}
