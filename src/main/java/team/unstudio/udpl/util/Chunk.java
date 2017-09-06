package team.unstudio.udpl.util;

import org.bukkit.Location;
import org.bukkit.World;

public final class Chunk {
	private final World world;
	private final int chunkX,chunkZ;
	
	public Chunk(Location location){
		world = location.getWorld();
		chunkX = location.getBlockX()>>4;
		chunkZ = location.getBlockZ()>>4;
	}
	
	public Chunk(World world,int chunkX,int chunkZ) {
		this.world = world;
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}
	
	public World getWorld(){
		return world;
	}
	
	public int getChunkX(){
		return chunkX;
	}
	
	public int getChunkZ(){
		return chunkZ;
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
		return world.hashCode()*37*37+chunkX*37+chunkZ;
	}
	
	@Override
	public String toString() {
		return String.format("{world:%s,chunkx:%s,chunkz:%s}", getWorld().getName(),getChunkX(),getChunkZ());
	}
}
