package team.unstudio.udpl.api.area;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import team.unstudio.udpl.api.area.event.AreaCreateEvent;
import team.unstudio.udpl.api.area.event.AreaRemoveEvent;
import team.unstudio.udpl.api.util.Chunk;
import team.unstudio.udpl.api.util.Utils;
import team.unstudio.udpl.core.UDPLib;

public final class AreaManager {
	
	private static final File AREA_PATH = new File(UDPLib.getInstance().getDataFolder(), "area");

	private final World world;
	private final List<Area> areas = new ArrayList<>();
	private final Map<Chunk,List<Area>> chunks = new HashMap<>();
	
	public AreaManager(World world) {
		this.world = world;
	}
	
	public void addArea(Area area){
		if(!area.getMinLocation().getWorld().equals(world)) 
			throw new IllegalArgumentException("Different world");
		
		Bukkit.getPluginManager().callEvent(new AreaCreateEvent(area));
		areas.add(area);
		World world = area.getWorld();
		Chunk chunk1 = new Chunk(area.getMinLocation()), chunk2 = new Chunk(area.getMaxLocation());
		for(int x = chunk1.chunkX;x<=chunk2.chunkX;x++)
			for(int z = chunk1.chunkZ;z<=chunk2.chunkZ;z++)
				getAreas(new Chunk(world, x, z)).add(area);
	}
	
	public boolean containArea(Area area){
		return areas.contains(area);
	}
	
	public void removeArea(Area area){
		if(!area.getMinLocation().getWorld().equals(world)) 
			return;
		Bukkit.getPluginManager().callEvent(new AreaRemoveEvent(area));
		areas.remove(area);
		World world = area.getWorld();
		Chunk chunk1 = new Chunk(area.getMinLocation()), chunk2 = new Chunk(area.getMaxLocation());
		for(int x = chunk1.chunkX;x<=chunk2.chunkX;x++)
			for(int z = chunk1.chunkZ;z<=chunk2.chunkZ;z++)
				getAreas(new Chunk(world, x, z)).remove(area);
	}
	
	@Deprecated
	public Area getArea(Location location){
		if(!location.getWorld().equals(world)) 
			return null;
		
		for(Area area:getAreas(new Chunk(location))) 
			if(area.contain(location)) 
				return area;
		
		return null;
	}
	
	public List<Area> getAreas(Location location){
		List<Area> areas = new ArrayList<>();
		
		if(!location.getWorld().equals(world)) 
			return areas;
		
		for(Area area:getAreas(new Chunk(location))) 
			if(area.contain(location)) 
				areas.add(area);
		
		return areas;
	}
	
	public List<Area> getAreas(){
		return areas;
	}
	
	public List<Area> getAreas(Chunk chunk){
		if(chunks.containsKey(chunk)) return chunks.get(chunk);
		else {
			List<Area> areas = new ArrayList<>();
			chunks.put(chunk, areas);
			return areas;
		}
	}
	
	public List<Area> getAreas(Area area){
		List<Area> areas = new ArrayList<>();
		if(!area.getMinLocation().getWorld().equals(world)) 
			return areas;
		
		World world = area.getWorld();
		Chunk chunk1 = new Chunk(area.getMinLocation()), chunk2 = new Chunk(area.getMaxLocation());
		for(int x = chunk1.chunkX;x<=chunk2.chunkX;x++)
			for(int z = chunk1.chunkZ;z<=chunk2.chunkZ;z++)
				for(Area a:getAreas(new Chunk(world, x, z)))
					if(area.intersect(a)) areas.add(a);
		return areas;
	}
	
	public void save(){
		try {
			File configPath = new File(AREA_PATH, world.getName()+".yml");
			FileConfiguration config = Utils.loadConfiguration(configPath);
			config.set(world.getName(), areas);
			config.save(configPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(){
		areas.clear();
		chunks.clear();
		
		try {
			FileConfiguration config = Utils.loadConfiguration(new File(AREA_PATH, world.getName()+".yml"));
			for(Area area:(List<Area>) config.getList(world.getName(), new ArrayList<>())){
				areas.add(area);
				World world = area.getWorld();
				Chunk chunk1 = new Chunk(area.getMinLocation()), chunk2 = new Chunk(area.getMaxLocation());
				for(int x = chunk1.chunkX;x<=chunk2.chunkX;x++)
					for(int z = chunk1.chunkZ;z<=chunk2.chunkZ;z++)
						getAreas(new Chunk(world, x, z)).add(area);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final Map<World,AreaManager> managers = new HashMap<>();
	
	public static AreaManager getAreaManager(World world){
		if(managers.containsKey(world)){
			return managers.get(world);
		}else{
			AreaManager manager = new AreaManager(world);
			managers.put(world, manager);
			return manager;
		}
	}
	
	public static AreaManager getAreaManager(String world){
		return getAreaManager(Bukkit.getWorld(world));
	}
	
	public static void addArea$(Area area){
		getAreaManager(area.getWorld()).addArea(area);
	}
	
	public static void removeArea$(Area area){
		getAreaManager(area.getWorld()).removeArea(area);
	}
	
	public static void loadAll(){
		for(World world:Bukkit.getWorlds()){
			AreaManager a = new AreaManager(world);
			a.load();
			managers.put(world, a);
		}
	}
	
	public static void saveAll(){
		for(AreaManager a:managers.values()) 
			a.save();
	}
}
