package team.unstudio.udpl.area.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import team.unstudio.udpl.area.Area;

public class AreaPlayerEvent extends AreaEvent{
	
	private final Player player;

	public AreaPlayerEvent(Area area,Player player) {
		super(area);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
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
