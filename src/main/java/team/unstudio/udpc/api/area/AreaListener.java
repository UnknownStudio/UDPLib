package team.unstudio.udpc.api.area;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import team.unstudio.udpc.api.area.event.PlayerEnterAreaEvent;
import team.unstudio.udpc.api.area.event.PlayerLeaveAreaEvent;

public class AreaListener implements Listener{
	
	private static final Map<Player, Area> playerArea = new HashMap<>();
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent event){
		Area oldArea = playerArea.get(event.getPlayer());
		Area newArea = AreaManager.getAreaManager(event.getTo().getWorld()).getArea(event.getTo());
		if(oldArea==null&&newArea!=null||!oldArea.equals(newArea)) {
			if(oldArea!=null)Bukkit.getPluginManager().callEvent(new PlayerLeaveAreaEvent(oldArea,event.getPlayer()));
			playerArea.put(event.getPlayer(), newArea);
			if(newArea!=null)Bukkit.getPluginManager().callEvent(new PlayerEnterAreaEvent(newArea, event.getPlayer()));
		}
	}

}
