package team.unstudio.udpc.api.area.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import team.unstudio.udpc.api.area.Area;

public class AreaEvent extends Event{
	
	private final Area area;
	
	public AreaEvent(Area area) {
		this.area = area;
	}

	public Area getArea() {
		return area;
	}
	
	private static final HandlerList handler = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handler;
	}
	
	public static HandlerList getHandlerList(){
		return handler;
	}
}
