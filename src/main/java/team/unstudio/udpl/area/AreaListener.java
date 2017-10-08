package team.unstudio.udpl.area;

import java.util.List;
import java.util.Map;
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
import com.google.common.collect.Maps;

public final class AreaListener implements Listener{
	
	private final AreaManager areaManager;
	private final Map<Player, List<Area>> playerArea = Maps.newHashMap();
	
	public AreaListener(AreaManager areaManager) {
		this.areaManager = areaManager;
	}
	
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
		List<Area> nowAreas = areaManager.getWorldAreaManager(to.getWorld()).getAreas(to);
		
		for(Area area:oldAreas)
			if(!nowAreas.contains(area))
				areaManager.callPlayerLeaveArea(player, area);
		
		for(Area area:nowAreas)
			if(!oldAreas.contains(area))
				areaManager.callPlayerEnterArea(player, area);
		
		playerArea.put(player, nowAreas);
	}
	
	private boolean isMove(Location from,Location to) {
		if(to.getX()!=from.getX())
			return true;
		if(to.getY()!=from.getY())
			return true;
		if(to.getZ()!=from.getZ())
			return true;
		return false;
	}
}
