package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public interface Title {
	
	ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
	
	public static void title(Player player, String title){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(title));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void subTitle(Player player, String subTitle){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.SUBTITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(subTitle));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void setTimeAndDisplay(Player player, int fadeIn, int stay, int fadeOut){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TIMES);
		container.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void hide(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.CLEAR);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void reset(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.RESET);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
