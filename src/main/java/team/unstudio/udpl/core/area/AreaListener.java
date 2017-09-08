package team.unstudio.udpl.core.area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.google.common.collect.Lists;

import team.unstudio.udpl.area.Area;
import team.unstudio.udpl.area.AreaManager;
import team.unstudio.udpl.area.event.PlayerEnterAreaEvent;
import team.unstudio.udpl.area.event.PlayerLeaveAreaEvent;

public final class AreaListener implements Listener{
	
	private static final Map<Player, List<Area>> playerArea = new HashMap<>();
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Location to = event.getTo();
		Location from = event.getFrom();
		if(!isMove(from,to))return;
		updateArea(player, to);
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerTeleport(PlayerTeleportEvent event){
		Player player = event.getPlayer();
		Location to = event.getTo();
		Location from = event.getFrom();
		if(!isMove(from,to))return;
		updateArea(player, to);
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event){
		updateArea(event.getPlayer(), event.getPlayer().getLocation());
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerJoin(PlayerJoinEvent event){
		updateArea(event.getPlayer(), event.getPlayer().getLocation());
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onPlayerQuit(PlayerQuitEvent event){
		playerArea.remove(event.getPlayer());
	}
	
	private void updateArea(Player player,Location to){
		List<Area> oldAreas = playerArea.containsKey(player)?playerArea.get(player):Lists.newArrayList();
		List<Area> nowAreas = AreaManager.getWorldAreaManager(to.getWorld()).getAreas(to);
		
		for(Area area:oldAreas)
			if(!nowAreas.contains(area))
				callPlayerLeaveAreaEvent(player, area);
		
		for(Area area:nowAreas)
			if(!oldAreas.contains(area))
				callPlayerEnterAreaEvent(player, area);
		
		playerArea.put(player, nowAreas);
	}
	
	private void callPlayerLeaveAreaEvent(Player player,Area area){
		if(area==null)
			return;
		Bukkit.getPluginManager().callEvent(new PlayerLeaveAreaEvent(area,player));
	}
	
	private void callPlayerEnterAreaEvent(Player player,Area area){
		if(area==null)
			return;
		Bukkit.getPluginManager().callEvent(new PlayerEnterAreaEvent(area,player));
	}
	
	private boolean isMove(Location from,Location to) {
		if(to.getX()==from.getX()&&to.getY()==from.getY()&&to.getZ()==from.getZ())
			return false;
		return true;
	}
}
