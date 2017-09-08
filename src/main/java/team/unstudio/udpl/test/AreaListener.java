package team.unstudio.udpl.test;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import team.unstudio.udpl.area.event.PlayerEnterAreaEvent;
import team.unstudio.udpl.area.event.PlayerLeaveAreaEvent;

public class AreaListener implements Listener{

	@EventHandler
	public void onEnterArea(PlayerEnterAreaEvent event){
		System.out.println("Enter area: "+event.getPlayer().getName()+" | "+event.getArea().toString());
	}
	
	@EventHandler
	public void onLeaveArea(PlayerLeaveAreaEvent event){
		System.out.println("Leave area: "+event.getPlayer().getName()+" | "+event.getArea().toString());
	}
}
