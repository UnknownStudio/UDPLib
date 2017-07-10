package team.unstudio.udpl.api.area;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Area implements ConfigurationSerializable{
	
	private final Location point1,point2;
	
	public Area(Map<String, Object> map) {
		this((Location)map.get("point1"),(Location)map.get("point2"));
	}
	
	public Area(@Nonnull Location point1,@Nonnull Location point2) {
		if(!point1.getWorld().equals(point2.getWorld())) 
			throw new IllegalArgumentException("Different world.");
		
		//Location1 < Location2
		double x1=point1.getX(),x2=point2.getX(),y1=point1.getY(),y2=point2.getY(),z1=point1.getZ(),z2=point2.getZ(),t;
		if(x1>x2){t=x1;x1=x2;x2=t;}
		if(y1>y2){t=y1;y1=y2;y2=t;}
		if(z1>z2){t=z1;z1=z2;z2=t;}
		this.point1 = new Location(point1.getWorld(), x1, y1, z1);
		this.point2 = new Location(point2.getWorld(), x2, y2, z2);
	}

	public final Location getPoint1() {
		return point1;
	}

	public final Location getPoint2() {
		return point2;
	}
	
	/**
	 * 判断坐标是否含于该区域
	 * @param location
	 * @return
	 */
	public boolean contain(@Nonnull final Location location){
		if(!point1.getWorld().equals(location.getWorld()))
			return false;
		
		if(point1.getX()>location.getX())
			return false;
		if(point1.getY()>location.getY())
			return false;
		if(point1.getZ()>location.getZ())
			return false;
		
		if(point2.getX()<location.getX())
			return false;
		if(point2.getY()<location.getY())
			return false;
		if(point2.getZ()<location.getZ())
			return false;
		
		return true;
	}
	
	/**
	 * 判断区域是否含于该区域
	 * @param area
	 * @return
	 */
	public boolean contain(@Nonnull final Area area){
		if(!point1.getWorld().equals(area.point1.getWorld()))
			return false;
		
		if(point1.getX()>area.point1.getX())
			return false;
		if(point1.getY()>area.point1.getY())
			return false;
		if(point1.getZ()>area.point1.getZ())
			return false;
		
		if(point2.getX()<area.point2.getX())
			return false;
		if(point2.getY()<area.point2.getY())
			return false;
		if(point2.getZ()<area.point2.getZ())
			return false;
		
		return true;
	}
	
	/**
	 * 判断区域是否与该区域相交
	 * @param area
	 * @return
	 */
	public boolean intersect(Area area){
		if(contain(area.point1))
			return true;
		
		if(contain(area.point2))
			return true;
		
		return false;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("==", getClass().getName());
		map.put("point1", point1);
		map.put("point2", point2);
		return map;
	}
	
	public static Area deserialize(Map<String, Object> map){
		return new Area(map);
	}
	
	public static Area valueOf(Map<String, Object> map){
		return new Area(map);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Area)) return false;
		
		Area other = (Area) obj;
		
		if(!point1.equals(other.point1)) return false;
		
		if(!point2.equals(other.point2)) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return point1.hashCode()*37+point2.hashCode();
	}
}
