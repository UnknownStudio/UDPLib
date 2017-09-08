package team.unstudio.udpl.test;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import team.unstudio.udpl.area.Area;

public class CustomArea extends Area{
	
	private OfflinePlayer owner;

	public CustomArea(Map<String, Object> map) {
		super(map);
		owner = Bukkit.getPlayer(UUID.fromString((String) map.get("owner")));
	}

	public CustomArea(Location point1, Location point2, OfflinePlayer player) {
		super(point1, point2);
		this.owner = player;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = super.serialize();
		data.put("owner", owner.getUniqueId().toString());
		return data;
	}

}
