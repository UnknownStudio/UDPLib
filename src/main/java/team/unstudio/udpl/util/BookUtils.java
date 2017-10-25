package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.util.ReflectionUtils.PackageType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface BookUtils {
	
	ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();
	
	static Result open(Player player, ItemStack book){
		ItemStack held = player.getInventory().getItemInMainHand();
		player.getInventory().setItemInMainHand(book);
		
		PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);
		container.getStrings().write(0, "MC|BOpen");
		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeByte(0);
		Object serializer = MinecraftReflection.getPacketDataSerializer(byteBuf);
		container.getModifier().withType(ByteBuf.class).write(0, serializer);

		try {
			PROTOCOL_MANAGER.sendServerPacket(player, container);
			return Result.success();
		} catch (InvocationTargetException e) {
			if (UDPLib.isDebug())
				e.printStackTrace();
			return Result.failure(e);
		} finally {
			player.getInventory().setItemInMainHand(held);
		}
	}
	
	static Result setPages(BookMeta book, BaseComponent... pages){
		return setPages(book, Arrays.stream(pages).map(ComponentSerializer::toString).toArray(String[]::new));
	}
	
	@SuppressWarnings("unchecked")
	static Result setPages(BookMeta book, String... pages){
		try {
			List<Object> listPages = (List<Object>) ReflectionUtils.getField(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"),true,"pages").get(book);
			Object ChatSerializer = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer").newInstance();
			Method ChatSerializer_a = ReflectionUtils.getMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER
						.getClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
			for (String page : pages)
				listPages.add(ChatSerializer_a.invoke(ChatSerializer, page));
			return Result.success();
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			if (UDPLib.isDebug())
				e.printStackTrace();
			return Result.failure(e);
		}
	}
	

	static Optional<BaseComponent[]> getPagesReturnBaseComponent(BookMeta book){
		Optional<String[]> pages = getPages(book);
		if(!pages.isPresent())
			return Optional.empty();
		
		return Optional.of(Arrays.stream(pages.get()).map(ComponentSerializer::parse).toArray(BaseComponent[]::new));
	}
	
	@SuppressWarnings("unchecked")
	static Optional<String[]> getPages(BookMeta book){
		try {
			List<Object> listPages = (List<Object>) ReflectionUtils.getField(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"),true,"pages").get(book);
			return Optional.of(listPages.stream().map(Object::toString).toArray(String[]::new));
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			if (UDPLib.isDebug())
				e.printStackTrace();
		}
		return Optional.empty();
	}
}
