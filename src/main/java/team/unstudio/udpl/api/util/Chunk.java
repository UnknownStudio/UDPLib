package team.unstudio.udpl.api.util;

import org.bukkit.Location;
import org.bukkit.World;

public final class Chunk {
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
		
		if(!world.equals(other.world)) return false;
		
		if(chunkX != other.chunkX) return false;
		
		if(chunkZ != other.chunkZ) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return chunkX*37+chunkZ;
	}
}
