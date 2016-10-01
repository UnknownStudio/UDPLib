package team.unstudio.udpc.api.area;

import org.bukkit.Location;

public class Area {
	private final Location point1,point2;
	
	public Area(Location point1,Location point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	public Location getPoint1() {
		return point1;
	}

	public Location getPoint2() {
		return point2;
	}
}
