package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public interface BlockUtils {
	ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
	
	/**
	 * 0–9 are the displayable destroy stages and each other number means that there is no animation on this coordinate.</br>
	 * Block break animations can still be applied on air; the animation will remain visible although there is no block being broken. However, if this is applied to a transparent block, odd graphical effects may happen, including water losing its transparency. (An effect similar to this can be seen in normal gameplay when breaking ice blocks)</br>
	 * If you need to display several break animations at the same time you have to give each of them a unique Entity ID.</br>
	 */
	static Result sendBlockBreakAnimation(Player player, Location location, byte state) {
		return sendBlockBreakAnimation(player, player.getEntityId(), location, state);
	}
	
	/**
	 * 0–9 are the displayable destroy stages and each other number means that there is no animation on this coordinate.</br>
	 * Block break animations can still be applied on air; the animation will remain visible although there is no block being broken. However, if this is applied to a transparent block, odd graphical effects may happen, including water losing its transparency. (An effect similar to this can be seen in normal gameplay when breaking ice blocks)</br>
	 * If you need to display several break animations at the same time you have to give each of them a unique Entity ID.</br>
	 */
	static Result sendBlockBreakAnimation(Player player, int entityId, Location location, byte state) {
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
		container.getIntegers().write(0, entityId);
		container.getBlockPositionModifier().write(0,
				new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		container.getIntegers().write(1, (int) state);

		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
	}
}
