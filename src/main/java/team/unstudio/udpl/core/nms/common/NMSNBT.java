package team.unstudio.udpl.core.nms.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.unstudio.udpl.api.nms.ReflectionUtils;

public class NMSNBT implements team.unstudio.udpl.api.nms.NMSNBT{

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> toMap(Object nbt) throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagCompound");
		}
		Field field = nbt.getClass().getDeclaredField("map");
		field.setAccessible(true);
		Map<String, Object> nmap = (Map<String, Object>) field.get(nbt);
		for (String key : nmap.keySet()) {
			map.put(key, toNBTBase(nmap.get(key)));
		}
		return map;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> toList(Object nbt) throws Exception {
		List<Object> list = new ArrayList<>();
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagList").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagList");
		}
		Field field = nbt.getClass().getDeclaredField("list");
		field.setAccessible(true);
		List<Object> nlist = (List<Object>) field.get(nbt);
		for (Object base : nlist) {
			list.add(toNBTBase(base));
		}
		return list;
	}

	@Override
	public byte toByte(Object nbt) throws Exception {
		byte value = 0;
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagByte").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagByte");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (byte) field.get(nbt);
		return value;
	}

	@Override
	public short toShort(Object nbt) throws Exception {
		short value = 0;
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagShort").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagShort");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (short) field.get(nbt);
		return value;
	}

	@Override
	public int toInt(Object nbt) throws Exception {
		int value = 0;
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagInt").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagInt");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (int) field.get(nbt);
		return value;
	}

	@Override
	public long toLong(Object nbt) throws Exception {
		long value = 0;
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagLong").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagLong");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (long) field.get(nbt);
		return value;
	}

	@Override
	public float toFloat(Object nbt) throws Exception {
		float value = 0;
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagFloat").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagFloat");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (float) field.get(nbt);
		return value;
	}

	@Override
	public double toDouble(Object nbt) throws Exception {
		double value = 0;
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagDouble").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagDouble");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (double) field.get(nbt);
		return value;
	}

	@Override
	public String toString(Object nbt) throws Exception {
		String value = null;
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagString").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagString");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (String) field.get(nbt);
		return value;
	}

	@Override
	public byte[] toByteArray(Object nbt) throws Exception {
		byte[] value = new byte[0];
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagByteArray").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagByteArray");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (byte[]) field.get(nbt);
		return value.clone();
	}

	@Override
	public int[] toIntArray(Object nbt) throws Exception {
		int[] value = new int[0];
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagIntArray").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTTagIntArray");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (int[]) field.get(nbt);
		return value.clone();
	}

	@Override
	public Object toNBTBase(Object nbt) throws Exception {
		if (!ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTBase").equals(nbt.getClass())) {
			throw new RuntimeException("Type isn't NBTBase");
		}
		Method method = nbt.getClass().getDeclaredMethod("getTypeId");
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
	public Object toNBT(Object obj) throws Exception{
		if (obj instanceof Byte){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagByte");
			Constructor<?> cons = clist.getDeclaredConstructor(byte.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Short){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagShort");
			Constructor<?> cons = clist.getDeclaredConstructor(short.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Integer){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagInt");
			Constructor<?> cons = clist.getDeclaredConstructor(int.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Long){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagLong");
			Constructor<?> cons = clist.getDeclaredConstructor(long.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Float){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagFloat");
			Constructor<?> cons = clist.getDeclaredConstructor(float.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Double){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagDouble");
			Constructor<?> cons = clist.getDeclaredConstructor(double.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Byte[]){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagByteArray");
			Constructor<?> cons = clist.getDeclaredConstructor(byte[].class);
			cons.setAccessible(true);
			return cons.newInstance(((Object[]) obj).clone());
		}else if (obj instanceof Integer[]){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagIntArray");
			Constructor<?> cons = clist.getDeclaredConstructor(int[].class);
			cons.setAccessible(true);
			return cons.newInstance(((Object[]) obj).clone());
		}else if (obj instanceof String){
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagString");
			Constructor<?> cons = clist.getDeclaredConstructor(String.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof List) {
			Class<?> cnbtbase = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTBase");
			Class<?> clist = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagList");
			Object o = clist.newInstance();
			Method add = clist.getDeclaredMethod("add", cnbtbase);
			add.setAccessible(true);
			for(Object e:(List<Object>)obj){
				add.invoke(o,toNBT(e));
			}
			return o;
		} else if (obj instanceof Map) {
			Class<?> cnbtbase = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTBase");
			Class<?> cmap = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
			Object o = cmap.newInstance();
			Method set = cmap.getDeclaredMethod("set", String.class, cnbtbase);
			set.setAccessible(true);
			for(String key:((Map<String,Object>) obj).keySet()){
				set.invoke(o, key,toNBT(((Map<String,Object>) obj).get(key)));
			}
			return o;
		} else
			return null;
	}
}
