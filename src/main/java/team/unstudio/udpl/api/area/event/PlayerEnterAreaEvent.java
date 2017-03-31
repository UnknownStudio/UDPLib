package team.unstudio.udpl.api.area.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import team.unstudio.udpl.api.area.Area;

public class PlayerEnterAreaEvent extends AreaPlayerEvent{

	public PlayerEnterAreaEvent(Area area, Player player) {
		super(area, player);
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
