package me.sirtyler.JunkyardCreek;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

public class RegionManager {
	private JunkyardCreek plugin;
	private List<Region> list = new ArrayList<Region>();
	private File data;
	
	public RegionManager(JunkyardCreek instance) {
		plugin = instance;
		data = plugin.data;
		loadFile();
		plugin.getLogger().log(Level.INFO, "=====Region Manager Loaded===================");
	}
	
	public Region addRegion(Region reg) {
		if(list.contains(reg)) {
			return null;
		} else {
			list.add(reg);
			return reg;
		}
	}
	public Region getRegion(Location loc) {
		Iterator<Region> it = list.iterator();
		while(it.hasNext()) {
			Region reg = it.next();
			if(reg.contains(new Vector(loc.getX(), loc.getY(), loc.getZ()))) {
				return reg;
			}
		}
		return null;
	}
	private Region buildRegion(String data) {
		Util u = new Util();
		String[] point1 = data.split("\\) - \\(")[0].split(",");
		String[] point2 = data.split("\\) - \\(")[1].split(",");
		for(int i=0; i < point1.length; i++) {
			point1[i] = u.replace(point1[i]);
		}
		for(int i=0; i < point2.length; i++) {
			point2[i] = u.replace(point2[i]);
		}
		CuboidRegion reg = new CuboidRegion(
				new Vector(Float.parseFloat(point1[0]),Float.parseFloat(point1[1]),Float.parseFloat(point1[2])), 
				new Vector(Float.parseFloat(point2[0]),Float.parseFloat(point2[1]),Float.parseFloat(point2[2])));
		return reg;
	}
	
	public void saveFile() {
		try {
			if(list == null) {
				BufferedWriter out = new BufferedWriter(new FileWriter(data));
				out.write("");
				out.close();
				data.delete();
				plugin.getLogger().log(Level.FINE, "Removed File, is Empty");
			} else {
				BufferedWriter out = new BufferedWriter(new FileWriter(data,true));
				Iterator<Region> it = list.iterator();
				while(it.hasNext()) {
					out.append(it.next().toString());
					out.newLine();
				}
				out.close();
				plugin.getLogger().log(Level.FINE, "Successfully ReWrote File");
			}
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error Writing File");
			e.printStackTrace();
		}
	}
	
	public void loadFile() {
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(data));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data;
			while((data = br.readLine()) != null) {
				list.add(buildRegion(data));
			}
			in.close();
			plugin.getLogger().log(Level.FINE, "Successfully Loaded");
		} catch (Exception e) {
			plugin.getLogger().log(Level.WARNING, "Error Loading File");
			e.printStackTrace();
		}
	}
}
