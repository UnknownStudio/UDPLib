package team.unstudio.udpl.area.function;

import org.bukkit.entity.Player;
import team.unstudio.udpl.area.Area;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface PlayerLeaveAreaCallback extends BiConsumer<Player, Area>{
}
