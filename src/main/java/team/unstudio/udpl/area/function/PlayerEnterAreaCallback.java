package team.unstudio.udpl.area.function;

import org.bukkit.entity.Player;

import team.unstudio.udpl.area.Area;

@FunctionalInterface
public interface PlayerEnterAreaCallback {

	void apply(Player player,Area area);
}
