package team.unstudio.udpl.area.function;

import org.bukkit.entity.Player;

import team.unstudio.udpl.area.Area;

@FunctionalInterface
public interface PlayerLeaveAreaCallback {

	void apply(Player player,Area area);
}
