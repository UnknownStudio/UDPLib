package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpc.core.nms.NMSManager;

public class NMSNBT implements team.unstudio.udpc.api.nms.NMSNBT{

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> toMap(Object nbt) throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (!nbt.getClass().isAssignableFrom(
				Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagCompound"))) {
			throw new RuntimeException("Type isn't NBTTagCompound");
		}
		Field field = nbt.getClass().getField("map");
		field.setAccessible(true);
		Map<String, Object> nmap = (Map<String, Object>) field.get(nbt);
		for (String key : nmap.keySet()) {
			map.put(key, toObject(nmap.get(key)));
		}
		return map;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> toList(Object nbt) throws Exception {
		List<Object> list = new ArrayList<>();
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagList"))) {
			throw new RuntimeException("Type isn't NBTTagList");
		}
		Field field = nbt.getClass().getField("list");
		field.setAccessible(true);
		List<Object> nlist = (List<Object>) field.get(nbt);
		for (Object base : nlist) {
			list.add(toObject(base));
		}
		return list;
	}

	@Override
	public byte toByte(Object nbt) throws Exception {
		byte value = 0;
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagByte"))) {
			throw new RuntimeException("Type isn't NBTTagByte");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (byte) field.get(nbt);
		return value;
	}

	@Override
	public short toShort(Object nbt) throws Exception {
		short value = 0;
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagShort"))) {
			throw new RuntimeException("Type isn't NBTTagShort");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (short) field.get(nbt);
		return value;
	}

	@Override
	public int toInt(Object nbt) throws Exception {
		int value = 0;
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagInt"))) {
			throw new RuntimeException("Type isn't NBTTagInt");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (int) field.get(nbt);
		return value;
	}

	@Override
	public long toLong(Object nbt) throws Exception {
		long value = 0;
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagLong"))) {
			throw new RuntimeException("Type isn't NBTTagLong");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (long) field.get(nbt);
		return value;
	}

	@Override
	public float toFloat(Object nbt) throws Exception {
		float value = 0;
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagFloat"))) {
			throw new RuntimeException("Type isn't NBTTagFloat");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (float) field.get(nbt);
		return value;
	}

	@Override
	public double toDouble(Object nbt) throws Exception {
		double value = 0;
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagDouble"))) {
			throw new RuntimeException("Type isn't NBTTagDouble");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (double) field.get(nbt);
		return value;
	}

	@Override
	public String toString(Object nbt) throws Exception {
		String value = null;
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagString"))) {
			throw new RuntimeException("Type isn't NBTTagString");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (String) field.get(nbt);
		return value;
	}

	@Override
	public byte[] toByteArray(Object nbt) throws Exception {
		byte[] value = new byte[0];
		if (!nbt.getClass().isAssignableFrom(
				Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagByteArray"))) {
			throw new RuntimeException("Type isn't NBTTagByteArray");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (byte[]) field.get(nbt);
		return value.clone();
	}

	@Override
	public int[] toIntArray(Object nbt) throws Exception {
		int[] value = new int[0];
		if (!nbt.getClass().isAssignableFrom(
				Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagIntArray"))) {
			throw new RuntimeException("Type isn't NBTTagIntArray");
		}
		Field field = nbt.getClass().getField("data");
		field.setAccessible(true);
		value = (int[]) field.get(nbt);
		return value.clone();
	}

	@Override
	public Object toObject(Object nbt) throws Exception {
		if (!nbt.getClass()
				.isAssignableFrom(Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTBase"))) {
			throw new RuntimeException("Type isn't NBTBase");
		}
		Method method = nbt.getClass().getMethod("getTypeId");
		method.setAccessible(true);
		switch ((byte) method.invoke(nbt)) {
		case 1:
			return toByte(nbt);
		case 2:
			return toShort(nbt);
		case 3:
			return toInt(nbt);
		case 4:
			return toLong(nbt);
		case 5:
			return toFloat(nbt);
		case 6:
			return toDouble(nbt);
		case 7:
			return toByteArray(nbt);
		case 11:
			return toIntArray(nbt);
		case 8:
			return toString(nbt);
		case 9:
			return toList(nbt);
		case 10:
			return toMap(nbt);
		default:
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object toNBTBase(Object obj) throws Exception{
		if (obj instanceof Byte){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagByte");
			Constructor<?> cons = clist.getDeclaredConstructor(byte.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Short){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagShort");
			Constructor<?> cons = clist.getDeclaredConstructor(short.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Integer){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagInt");
			Constructor<?> cons = clist.getDeclaredConstructor(int.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Long){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagLong");
			Constructor<?> cons = clist.getDeclaredConstructor(long.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Float){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagFloat");
			Constructor<?> cons = clist.getDeclaredConstructor(float.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Double){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagDouble");
			Constructor<?> cons = clist.getDeclaredConstructor(double.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Byte[]){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagByteArray");
			Constructor<?> cons = clist.getDeclaredConstructor(byte[].class);
			cons.setAccessible(true);
			return cons.newInstance(((Object[]) obj).clone());
		}else if (obj instanceof Integer[]){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagIntArray");
			Constructor<?> cons = clist.getDeclaredConstructor(int[].class);
			cons.setAccessible(true);
			return cons.newInstance(((Object[]) obj).clone());
		}else if (obj instanceof String){
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagString");
			Constructor<?> cons = clist.getDeclaredConstructor(String.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof List) {
			Class<?> cnbtbase = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTBase");
			Class<?> clist = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagList");
			Object o = clist.newInstance();
			Method add = clist.getMethod("add", cnbtbase);
			add.setAccessible(true);
			for(Object e:(List<Object>)obj){
				add.invoke(o,toNBTBase(e));
			}
			return o;
		} else if (obj instanceof Map) {
			Class<?> cnbtbase = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTBase");
			Class<?> cmap = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagCompound");
			Object o = cmap.newInstance();
			Method set = cmap.getMethod("set", String.class, cnbtbase);
			set.setAccessible(true);
			for(String key:((Map<String,Object>) obj).keySet()){
				set.invoke(o, key,toNBTBase(((Map<String,Object>) obj).get(key)));
			}
			return o;
		} else
			return null;
	}

	@Override
	public Map<String, Object> getNBTFromItemStack(ItemStack itemStack) throws Exception{
		Class<?> ccitemstack = Class.forName("org.bukkit.craftbukkit." + NMSManager.NMS_VERSION + ".inventory.CraftItemStack");
		Class<?> citemstack = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".ItemStack");
		Method asnmscopy = ccitemstack.getMethod("asNMSCopy", ItemStack.class);
		asnmscopy.setAccessible(true);
		Method gettag = citemstack.getMethod("getTag");
		gettag.setAccessible(true);
		return toMap(gettag.invoke(asnmscopy.invoke(null, itemStack)));
	}

	@Override
	public ItemStack setNBTToItemStack(ItemStack itemStack, Map<String,Object> nbt) throws Exception{
		Class<?> ccitemstack = Class.forName("org.bukkit.craftbukkit." + NMSManager.NMS_VERSION + ".inventory.CraftItemStack");
		Class<?> citemstack = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".ItemStack");
		Method asnmscopy = ccitemstack.getMethod("asNMSCopy", ItemStack.class);
		asnmscopy.setAccessible(true);
		Method settag = citemstack.getMethod("setTag");
		settag.setAccessible(true);
		Method asbukkitcopy = ccitemstack.getMethod("asBukkitCopy", citemstack);
		asbukkitcopy.setAccessible(true);
		Object nitem = asnmscopy.invoke(null, itemStack);
		settag.invoke(nitem,toNBTBase(nbt));
		return (ItemStack) asbukkitcopy.invoke(null, nitem);
	}
}
