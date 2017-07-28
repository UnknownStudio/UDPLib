package team.unstudio.udpl.test;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import team.unstudio.udpl.api.area.event.PlayerEnterAreaEvent;
import team.unstudio.udpl.api.area.event.PlayerLeaveAreaEvent;

public class AreaListener implements Listener{

	@EventHandler
	public void onEnterArea(PlayerEnterAreaEvent event){
		System.out.println(event.getPlayer().getName()+" | "+event.getArea().toString());
	}
	
	@EventHandler
	public void onLeaveArea(PlayerLeaveAreaEvent event){
		System.out.println(event.getPlayer().getName()+" | "+event.getArea().toString());
	}
}
