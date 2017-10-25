package team.unstudio.udpl.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class FakeSignUpdateEvent extends PlayerEvent{
	
	private final String[] lines;
	
	public FakeSignUpdateEvent(Player who,String[] lines) {
		super(who);
		this.lines = lines;
	}
	
	public String[] getLines() {
		return lines;
	}

	private static final HandlerList HANDLER_LIST = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
