package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;

import team.unstudio.udpl.core.UDPLib;

import com.comphenix.protocol.wrappers.WrappedChatComponent;

public final class Title {
	
	private Title(){}
	
	private static final boolean debug = UDPLib.isDebug();
	private static final ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
	
	public static boolean title(Player player, String title){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(title));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return true;
		} catch (InvocationTargetException e) {
			if(debug)
				e.printStackTrace();
		}
		return false;
	}
	
	public static boolean subTitle(Player player, String subTitle){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.SUBTITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(subTitle));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return true;
		} catch (InvocationTargetException e) {
			if(debug)
				e.printStackTrace();
		}
		return false;
	}
	
	public static boolean setTimeAndDisplay(Player player, int fadeIn, int stay, int fadeOut){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TIMES);
		container.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return true;
		} catch (InvocationTargetException e) {
			if(debug)
				e.printStackTrace();
		}
		return false;
	}
	
	public static boolean hide(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.CLEAR);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return true;
		} catch (InvocationTargetException e) {
			if(debug)
				e.printStackTrace();
		}
		return false;
	}
	
	public static boolean reset(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.RESET);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return true;
		} catch (InvocationTargetException e) {
			if(debug)
				e.printStackTrace();
		}
		return false;
	}
}
