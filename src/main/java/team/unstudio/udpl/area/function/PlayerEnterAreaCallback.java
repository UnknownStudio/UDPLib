package team.unstudio.udpl.area.function;

import org.bukkit.entity.Player;

import team.unstudio.udpl.area.Area;

import java.util.function.BiConsumer;
import java.util.function.Function;

@FunctionalInterface
public interface PlayerEnterAreaCallback extends BiConsumer<Player, Area> {
}
