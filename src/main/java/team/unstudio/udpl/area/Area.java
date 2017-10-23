package team.unstudio.udpl.area;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Area implements ConfigurationSerializable{
	
	private final Location minLocation,maxLocation;
	private AreaDataContainer data;
	
	public Area(@Nonnull Location point1,@Nonnull Location point2) {
		if(!point1.getWorld().equals(point2.getWorld())) 
			throw new IllegalArgumentException("Area cannot be created between different worlds");
		
		//Location1 < Location2
		double x1=point1.getX(),x2=point2.getX(),y1=point1.getY(),y2=point2.getY(),z1=point1.getZ(),z2=point2.getZ(),t;
		if(x1>x2){t=x1;x1=x2;x2=t;}
		if(y1>y2){t=y1;y1=y2;y2=t;}
		if(z1>z2){t=z1;z1=z2;z2=t;}
		this.minLocation = new Location(point1.getWorld(), x1, y1, z1);
		this.maxLocation = new Location(point2.getWorld(), x2, y2, z2);
	}
	
	public final World getWorld(){
		return minLocation.getWorld();
	}

	public final Location getMinLocation() {
		return minLocation;
	}

	public final Location getMaxLocation() {
		return maxLocation;
	}
	
	public final AreaDataContainer getData() {
		if(data == null)
			data = new AreaDataContainer();
		return data;
	}
	
	/**
	 * 判断坐标是否含于该区域
	 * @param location 坐标
	 */
	public boolean contain(final Location location){
		if(location==null)
			return false;

		if(!getMinLocation().getWorld().equals(location.getWorld()))
			return false;

		if(getMinLocation().getX()>location.getX())
			return false;
		if(getMinLocation().getY()>location.getY())
			return false;
		if(getMinLocation().getZ()>location.getZ())
			return false;

		if(getMaxLocation().getX()<location.getX())
			return false;
		if(getMaxLocation().getY()<location.getY())
			return false;
		if(getMaxLocation().getZ()<location.getZ())
			return false;

		return true;
	}
	
	/**
	 * 判断区域是否含于该区域
	 * @param area 区域
	 */
	public boolean contain(final Area area){
		if(area==null)
			return false;
		
		if(!getMinLocation().getWorld().equals(area.getMinLocation().getWorld()))
			return false;
		
		if(getMinLocation().getX()>area.getMinLocation().getX())
			return false;
		if(getMinLocation().getY()>area.getMinLocation().getY())
			return false;
		if(getMinLocation().getZ()>area.getMinLocation().getZ())
			return false;
		
		if(getMaxLocation().getX()<area.getMaxLocation().getX())
			return false;
		if(getMaxLocation().getY()<area.getMaxLocation().getY())
			return false;
		if(getMaxLocation().getZ()<area.getMaxLocation().getZ())
			return false;
		
		return true;
	}
	
	/**
	 * 判断区域是否与该区域相交
	 * @param area 区域
	 */
	public boolean intersect(final Area area){
		if(area==null)
			return false;
		
		if(contain(area.getMinLocation()))
			return true;
		
		if(contain(area.getMaxLocation()))
			return true;
		
		if(area.contain(getMinLocation()))
			return true;
		
		if(area.contain(getMaxLocation()))
			return true;
		
		return false;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("==", getClass().getName());
		map.put("point1", getMinLocation());
		map.put("point2", getMaxLocation());
		if(data != null)
			map.put("data", data);
		return map;
	}
	
	public static Area deserialize(Map<String, Object> args) {
		Area area = new Area((Location)args.get("point1"),(Location)args.get("point2"));
		if(args.containsKey("data"))
			area.data = (AreaDataContainer) args.get("data");
		return area;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if(!(obj instanceof Area)) return false;
		
		Area other = (Area) obj;
		
		if(!getMinLocation().equals(other.getMinLocation())) return false;
		
		if(!getMaxLocation().equals(other.getMaxLocation())) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return getMinLocation().hashCode()*37+getMaxLocation().hashCode();
	}
	
	@Override
	public String toString() {
		return "Area: {MaxLocation: "+getMinLocation().toString()+", MinLocation: "+getMaxLocation().toString()+", Data: "+data.toString()+" }";
	}
}
