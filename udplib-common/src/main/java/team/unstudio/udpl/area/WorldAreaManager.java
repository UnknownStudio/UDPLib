package team.unstudio.udpl.area;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.util.extra.Chunk;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WorldAreaManager{

	private final File worldFile;
	private final World world;
	private final List<Area> areas = new ArrayList<>();
	private final Map<Chunk,List<Area>> chunks = new HashMap<>();
	
	public WorldAreaManager(@Nonnull World world,@Nonnull File path) {
		this.world = world;
		this.worldFile = new File(path, world.getName()+".yml");
	}
	
	public void addArea(Area area){
		if(!area.getMinLocation().getWorld().equals(world)) 
			throw new IllegalArgumentException("Different world");

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

		areas.remove(area);
		World world = area.getWorld();
		Chunk chunk1 = new Chunk(area.getMinLocation()), chunk2 = new Chunk(area.getMaxLocation());
		for(int x = chunk1.getChunkX();x<=chunk2.getChunkX();x++)
			for(int z = chunk1.getChunkZ();z<=chunk2.getChunkZ();z++)
				getAreas(new Chunk(world, x, z)).remove(area);
	}
	
	public boolean hasArea(Location location){	
		if(!location.getWorld().equals(world)) 
			return false;
		
		for(Area area:getAreas(new Chunk(location))) 
			if(area.contain(location)) 
				return true;
		
		return false;
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
	
	public final World getWorld() {
		return world;
	}

	public void save(){
		try {
			FileConfiguration config = ConfigurationHelper.loadConfiguration(worldFile);
			config.set(world.getName(), areas);
			config.save(worldFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(){
		areas.clear();
		chunks.clear();
		
		FileConfiguration config = ConfigurationHelper.loadConfiguration(worldFile);
		if(config == null)
			return;
		
		for (Area area : (List<Area>) config.getList(world.getName(), new ArrayList<>())) {
			areas.add(area);
			World world = area.getWorld();
			Chunk chunk1 = new Chunk(area.getMinLocation()), chunk2 = new Chunk(area.getMaxLocation());
			for (int x = chunk1.getChunkX(); x <= chunk2.getChunkX(); x++)
				for (int z = chunk1.getChunkZ(); z <= chunk2.getChunkZ(); z++)
					getAreas(new Chunk(world, x, z)).add(area);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 22;
		int result = 1;
		result = prime * result + ((areas == null) ? 0 : areas.hashCode());
		result = prime * result + ((chunks == null) ? 0 : chunks.hashCode());
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (!(obj instanceof WorldAreaManager))
			return false;
		
		WorldAreaManager other = (WorldAreaManager) obj;
		return getWorld().equals(other.getWorld());
	}
}
