package team.unstudio.udpc.api.area.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import team.unstudio.udpc.api.area.Area;

public class PlayerLeaveAreaEvent extends AreaPlayerEvent{

	public PlayerLeaveAreaEvent(Area area, Player player) {
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
