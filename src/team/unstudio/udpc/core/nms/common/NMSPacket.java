package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import team.unstudio.udpc.api.nms.ReflectionUtils;

public class NMSPacket implements team.unstudio.udpc.api.nms.NMSPacket{

	@Override
	public Object createPacketPlayOutChat(String message, byte type) throws Exception {
		Constructor<?> c = ReflectionUtils.getNMSClass("PacketPlayOutChat").getDeclaredConstructor(ReflectionUtils.getNMSClass("IChatBaseComponent"),byte.class);
		c.setAccessible(true);
		Method a = ReflectionUtils.getNMSClass("IChatBaseComponent.ChatSerializer").getDeclaredMethod("a", String.class);
		a.setAccessible(true);
		return c.newInstance(a.invoke(null, message),type);
	}

	@Override
	public Object createPacketPlayOutChat(String message) throws Exception {
		return createPacketPlayOutChat(message, (byte) 1);
	}
}
