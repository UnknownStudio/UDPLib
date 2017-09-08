package team.unstudio.udpl.area.event;

import org.bukkit.event.HandlerList;

import team.unstudio.udpl.area.Area;

public class AreaCreateEvent extends AreaEvent{

	public AreaCreateEvent(Area area) {
		super(area);
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
