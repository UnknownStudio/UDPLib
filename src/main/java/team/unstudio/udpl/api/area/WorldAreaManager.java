
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

public final class WorldAreaManager {
	private static final File AREA_PATH = new File(UDPLib.getInstance().getDataFolder(), "area");

	private final World world;
	private final List<Area> areas = new ArrayList<>();
	private final Map<Chunk,List<Area>> chunks = new HashMap<>();
	
	public WorldAreaManager(World world) {
		this.world = world;
	}
	
	public void addArea(Area area){
		if(!area.getMinLocation().getWorld().equals(world)) 
			throw new IllegalArgumentException("Different world");
		
		Bukkit.getPluginManager().callEvent(new AreaCreateEvent(area));
		areas.add(area);
		World world = area.getWorld();
		Chunk chunk1 = new Chunk(area.getMinLocation()), chunk2 = new Chunk(area.getMaxLocation());
		for(int x = chunk1.getChunkX();x<=chunk2.getChunkX();x++)
			for(int z = chunk1.getChunkZ();z<=chunk2.getChunkZ();z++)
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
		for(int x = chunk1.getChunkX();x<=chunk2.getChunkX();x++)
			for(int z = chunk1.getChunkZ();z<=chunk2.getChunkZ();z++)
				getAreas(new Chunk(world, x, z)).remove(area);
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
		for(int x = chunk1.getChunkX();x<=chunk2.getChunkX();x++)
			for(int z = chunk1.getChunkZ();z<=chunk2.getChunkZ();z++)
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
				for(int x = chunk1.getChunkX();x<=chunk2.getChunkX();x++)
					for(int z = chunk1.getChunkZ();z<=chunk2.getChunkZ();z++)
						getAreas(new Chunk(world, x, z)).add(area);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
