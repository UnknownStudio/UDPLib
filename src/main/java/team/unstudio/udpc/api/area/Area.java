package team.unstudio.udpc.api.area;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Area implements ConfigurationSerializable{
	
    private Map<String, Object> properties = new HashMap<>();
	private final Location point1,point2;
	
	public Area(Map<String, Object> map) {
		this((Location)map.get("point1"),(Location)map.get("point2"));
	}
	
	public Area(Location point1,Location point2) {
		if(!point1.getWorld().equals(point2.getWorld())) throw new IllegalArgumentException("Different world");
		int x1=point1.getBlockX(),x2=point2.getBlockX(),y1=point1.getBlockY(),y2=point2.getBlockY(),z1=point1.getBlockZ(),z2=point2.getBlockZ(),t;
		if(x1<x2){t=x1;x1=x2;x2=t;}
		if(y1<y2){t=y1;y1=y2;y2=t;}
		if(z1<z2){t=z1;z1=z2;z2=t;}
		this.point1 = new Location(point1.getWorld(), x1, y1, z1);
		this.point2 = new Location(point2.getWorld(), x2, y2, z2);
	}

	public Location getPoint1() {
		return point1;
	}

	public Location getPoint2() {
		return point2;
	}
	
	/**
	 * 判断坐标是否含于该区域
	 * @param location
	 * @return
	 */
	public boolean contain(Location location){
		if(point1.getBlockX()>=location.getBlockX()&&location.getBlockX()>=point2.getBlockX()&&
				point1.getBlockY()>=location.getBlockY()&&location.getBlockY()>=point2.getBlockY()&&
				point1.getBlockZ()>=location.getBlockZ()&&location.getBlockZ()>=point2.getBlockZ()&&
				point1.getWorld().equals(location.getWorld())) return true;
		return false;
	}
	
	/**
	 * 判断区域是否含于该区域
	 * @param area
	 * @return
	 */
	public boolean contain(Area area){
		if(point1.getBlockX()>=area.getPoint1().getBlockX()&&point2.getBlockX()<=area.getPoint2().getBlockX()&&
				point1.getBlockY()>=area.getPoint1().getBlockY()&&point2.getBlockY()<=area.getPoint2().getBlockY()&&
				point1.getBlockZ()>=area.getPoint1().getBlockZ()&&point2.getBlockZ()<=area.getPoint2().getBlockZ()&&
				point1.getWorld().equals(area.getPoint1().getWorld())) return true;
		return false;
	}
	
	/**
	 * 判断区域是否与该区域相交
	 * @param area
	 * @return
	 */
	public boolean intersect(Area area){
		if(point1.getBlockX()>=area.getPoint2().getBlockX()&&point2.getBlockX()<=area.getPoint1().getBlockX()&&
				point1.getBlockY()>=area.getPoint2().getBlockY()&&point2.getBlockY()<=area.getPoint1().getBlockY()&&
				point1.getBlockZ()>=area.getPoint2().getBlockZ()&&point2.getBlockZ()<=area.getPoint1().getBlockZ()&&
				point1.getWorld().equals(area.getPoint1().getWorld())) return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(String key) {
		return (T) properties.get(key);
	}

	public void setProperty(String key,Object value) {
		properties.put(key, value);
	}

	public boolean hasProperty(String key) {
		return properties.containsKey(key);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("point1", point1);
		map.put("point2", point2);
		map.put("properties", properties);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static Area deserialize(Map<String, Object> map){
		Area area = new Area(map);
		area.properties.putAll((Map<? extends String, ? extends Object>) map.get("properties"));
		return area;
	}
	
	public static Area valueOf(Map<String, Object> map){
		return deserialize(map);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(getClass() != obj.getClass()) return false;
		
		Area other = (Area) obj;
		
		if(!point1.equals(other.point1)) return false;
		
		if(!point2.equals(other.point2)) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return point1.hashCode()*31+point2.hashCode();
	}
}
