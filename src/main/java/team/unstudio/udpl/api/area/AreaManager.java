package team.unstudio.udpl.api.area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import team.unstudio.udpl.api.util.Chunk;

public enum AreaManager {
	
	;
	
	private static final Map<World,WorldAreaManager> managers = new HashMap<>();
	
	public static WorldAreaManager getAreaManager(World world){
		if(managers.containsKey(world)){
			return managers.get(world);
		}else{
			WorldAreaManager manager = new WorldAreaManager(world);
			managers.put(world, manager);
			return manager;
		}
	}
	
	public static WorldAreaManager getAreaManager(String world){
		return getAreaManager(Bukkit.getWorld(world));
	}
	
	public static void addArea(Area area){
		getAreaManager(area.getWorld()).addArea(area);
	}
	
	public static void removeArea(Area area){
		getAreaManager(area.getWorld()).removeArea(area);
	}
	
	public static boolean containArea(Area area){
		return getAreaManager(area.getWorld()).containArea(area);
	}
	
	public static List<Area> getAreas(Location location){
		return getAreaManager(location.getWorld()).getAreas(location);
	}
	
	public static List<Area> getAreas(Chunk chunk){
		return getAreaManager(chunk.getWorld()).getAreas(chunk);
	}
	
	public static List<Area> getAreas(Area area){
		return getAreaManager(area.getWorld()).getAreas(area);
	}
	
	public static void loadAll(){
		for(World world:Bukkit.getWorlds()){
			WorldAreaManager a = new WorldAreaManager(world);
			a.load();
			managers.put(world, a);
		}
	}
	
	public static void saveAll(){
		for(WorldAreaManager a:managers.values()) 
			a.save();
	}
}
