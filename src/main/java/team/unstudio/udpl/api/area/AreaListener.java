package team.unstudio.udpl.api.area;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import team.unstudio.udpl.api.area.event.PlayerEnterAreaEvent;
import team.unstudio.udpl.api.area.event.PlayerLeaveAreaEvent;

public final class AreaListener implements Listener{
	
	private static final Map<Player, Area> playerArea = new HashMap<>();
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent event){
		Player player =event.getPlayer();
		Location to = event.getTo();
		Location from = event.getFrom();
		if(!isMove(from,to))return;
		Area oldArea = playerArea.get(player);
		Area newArea = AreaManager.getAreaManager(to.getWorld()).getArea(to);
		if((oldArea==null&&newArea!=null)||!oldArea.equals(newArea)) {
			if(oldArea!=null)Bukkit.getPluginManager().callEvent(new PlayerLeaveAreaEvent(oldArea,player));
			playerArea.put(player, newArea);
			if(newArea!=null)Bukkit.getPluginManager().callEvent(new PlayerEnterAreaEvent(newArea,player));
		}
	}
	private boolean isMove(Location from,Location to) {
		if(to.getX()==from.getX()&&to.getY()==from.getY()&&to.getZ()==from.getZ())
			return false;
		return true;
	}
}
