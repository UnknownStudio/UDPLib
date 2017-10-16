package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

public final class BlockUtils {
	
	private BlockUtils() {}
	
	private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	
	/**
	 * 0–9 are the displayable destroy stages and each other number means that there is no animation on this coordinate.</br>
	 * Block break animations can still be applied on air; the animation will remain visible although there is no block being broken. However, if this is applied to a transparent block, odd graphical effects may happen, including water losing its transparency. (An effect similar to this can be seen in normal gameplay when breaking ice blocks)</br>
	 * If you need to display several break animations at the same time you have to give each of them a unique Entity ID.</br>
	 * @param player
	 * @param location
	 * @param state
	 * @return
	 */
	public static Result sendBlockBreakAnimation(Player player, Location location, byte state) {
		PacketContainer container = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
		container.getIntegers().write(0, player.getEntityId());
		container.getBlockPositionModifier().write(0,
				new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		container.getIntegers().write(1, (int) state);

		try {
			protocolManager.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
	}
}
