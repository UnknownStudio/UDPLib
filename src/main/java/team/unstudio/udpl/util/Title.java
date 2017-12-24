package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.base.Strings;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public interface Title {
	ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
	
	static Result title(Player player, @Nullable String title, @Nullable String subTitle, int fadeIn, int stay, int fadeOut){
		Result result = setTimeAndDisplay(player, fadeIn, stay, fadeOut);
		if(result.isFailure())
			return result;
		
		if(!Strings.isNullOrEmpty(title)){
			result = title(player, title);
			if(result.isFailure())
				return result;
		}
		
		if(!Strings.isNullOrEmpty(subTitle)){
			result = subTitle(player, subTitle);
			if(result.isFailure())
				return result;
		}
		
		return Result.success();
	}
	
	static Result title(Player player, String title){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(title));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
	}
	
	static Result subTitle(Player player, String subTitle){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.SUBTITLE);
		container.getChatComponents().write(0, WrappedChatComponent.fromText(subTitle));
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
	}
	
	static Result setTimeAndDisplay(Player player, int fadeIn, int stay, int fadeOut){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.TIMES);
		container.getIntegers().write(0, fadeIn).write(1, stay).write(2, fadeOut);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
	}
	
	static Result hide(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.CLEAR);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
	}
	
	static Result reset(Player player){
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.TITLE);
		container.getTitleActions().write(0, TitleAction.RESET);
		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
	}
}
