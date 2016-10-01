package team.unstudio.udpc.api.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;

public class AreaManager {

	private final World world;
	private final List<Area> areas = new ArrayList<>();
	private final Map<Chunk,List<Area>> chunks = new HashMap<>();
	
	public AreaManager(World world) {
		this.world = world;
	}
	
	public void addArea(Area area){
		if(!area.getPoint1().getWorld().equals(world)) throw new IllegalArgumentException("Different world");
		areas.add(area);
		World world = area.getPoint1().getWorld();
		Chunk chunk1 = new Chunk(area.getPoint1()), chunk2 = new Chunk(area.getPoint2());
		for(int x = chunk2.chunkX;x<=chunk1.chunkX;x++)
			for(int z = chunk2.chunkZ;z<=chunk1.chunkZ;z++)
				getAreas(new Chunk(world, x, z)).add(area);
	}
	
	public void removeArea(Area area){
		if(!area.getPoint1().getWorld().equals(world)) return;
		areas.remove(area);
		World world = area.getPoint1().getWorld();
		Chunk chunk1 = new Chunk(area.getPoint1()), chunk2 = new Chunk(area.getPoint2());
		for(int x = chunk2.chunkX;x<=chunk1.chunkX;x++)
			for(int z = chunk2.chunkZ;z<=chunk1.chunkZ;z++)
				getAreas(new Chunk(world, x, z)).remove(area);
	}
	
	public Area getArea(Location location){
		if(!location.getWorld().equals(world)) return null;
		for(Area area:getAreas(new Chunk(location))) if(area.contain(location)) return area;
		return null;
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
	
	public void save(){
		
	}
	
	public void load(){
		areas.clear();
		chunks.clear();
	}
	
	public class Chunk{
		public final World world;
		public final int chunkX,chunkZ;
		
		public Chunk(Location location){
			world = location.getWorld();
			chunkX = (location.getBlockX()>=0?location.getBlockX()/16:location.getBlockX()-16/16);
			chunkZ = (location.getBlockZ()>=0?location.getBlockZ()/16:location.getBlockZ()-16/16);
		}
		
		public Chunk(World world,int chunkX,int chunkZ) {
			this.world = world;
			this.chunkX = chunkX;
			this.chunkZ = chunkZ;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj==null) return false;
			
			if(!(obj instanceof Chunk)) return false;
			
			Chunk other = (Chunk) obj;
			
			if(!other.world.equals(world)) return false;
			
			if(other.chunkX != chunkX) return false;
			
			if(other.chunkZ != chunkZ) return false;
			
			return true;
		}
		
		@Override
		public int hashCode() {
			return chunkX*31+chunkZ;
		}
	}
}
