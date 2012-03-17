package me.sirtyler.JunkyardCreek;

import java.io.File;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class JunkyardCreek extends JavaPlugin{
	public static JunkyardCreek plugin;
	public FileConfiguration config;
	public CommandEx myExecutor;
	public CommandExWG myExecutorWG;
	public Permission permission = null;
	public Economy economy = null;
	public WorldEditPlugin WorldEdit;
	public RegionManager rm;
	public File data;
	
	public JunkyardCreek() {
		super();
	}

	@Override
	public void onDisable() {
		if(WorldEdit != null) {
			rm.saveFile();
		}
	}

	@Override
	public void onEnable() {
		myExecutor = new CommandEx(this);
		setupPermissions();
		if(permission != null) this.getLogger().log(Level.INFO, "=====Permission Plugged into " + permission.getName() + "======");
		setupEconomy();
		if(economy != null) this.getLogger().log(Level.INFO, "=====Economy Plugged into " + economy.getName() + "==========");
		getCommand("junk").setExecutor(myExecutor);
		checkConfig();
		this.getLogger().log(Level.INFO, "=====Config Loaded===========================");
		Bukkit.getPluginManager().registerEvents(new FishListener(this), this);
		if(Bukkit.getPluginManager().getPlugin("WorldEdit") != null) {
			myExecutorWG = new CommandExWG(this);
			WorldEdit = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
			buildFile();
			rm = new RegionManager(this);
			this.getLogger().log(Level.INFO, "=====Plugged into WorldEdit==================");
			getCommand("junkyard").setExecutor(myExecutorWG);
		} else getCommand("junkyard").setExecutor(myExecutor);
		new VersionChecker(this,"http://dev.bukkit.org/server-mods/junkyardcreek/files.rss");
	}
	
	private void checkConfig() {
		String mainDirectory = ("plugins/" + this.getDescription().getName());
		File file = new File(mainDirectory + File.separator + "config.yml");
		try{
			config = this.getConfig();
			if(!file.exists()) { 
				config.options().copyDefaults(true);
				this.saveConfig();
			}
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
    
    private Boolean setupEconomy(){
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
    
	public void buildFile() {
		File folder = getDataFolder();
		data = new File(folder.getPath() + File.separatorChar + "region.txt");
		if(!(data.exists())) {
			folder.mkdir(); 
			try {
				data.createNewFile();
				this.getLogger().log(Level.FINE, "Successfully Created File");
			} catch (Exception e) {
				this.getLogger().log(Level.SEVERE, "File Error");
				e.printStackTrace();
			}
		}
	}
}
