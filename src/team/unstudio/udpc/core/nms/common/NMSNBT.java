package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.unstudio.udpc.api.nms.NMSUtils;

public class NMSNBT implements team.unstudio.udpc.api.nms.NMSNBT{

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> toMap(Object nbt) throws Exception {
		Map<String, Object> map = new HashMap<>();
		if (!nbt.getClass().isAssignableFrom(
				NMSUtils.getNMSClass("NBTTagCompound"))) {
			throw new RuntimeException("Type isn't NBTTagCompound");
		}
		Field field = nbt.getClass().getDeclaredField("map");
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
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagList"))) {
			throw new RuntimeException("Type isn't NBTTagList");
		}
		Field field = nbt.getClass().getDeclaredField("list");
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
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagByte"))) {
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
		if (!nbt.getClass()
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagShort"))) {
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
		if (!nbt.getClass()
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagInt"))) {
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
		if (!nbt.getClass()
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagLong"))) {
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
		if (!nbt.getClass()
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagFloat"))) {
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
		if (!nbt.getClass()
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagDouble"))) {
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
		if (!nbt.getClass()
				.isAssignableFrom(NMSUtils.getNMSClass("NBTTagString"))) {
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
		if (!nbt.getClass().isAssignableFrom(
				NMSUtils.getNMSClass("NBTTagByteArray"))) {
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
		if (!nbt.getClass().isAssignableFrom(
				NMSUtils.getNMSClass("NBTTagIntArray"))) {
			throw new RuntimeException("Type isn't NBTTagIntArray");
		}
		Field field = nbt.getClass().getDeclaredField("data");
		field.setAccessible(true);
		value = (int[]) field.get(nbt);
		return value.clone();
	}

	@Override
	public Object toObject(Object nbt) throws Exception {
		if (!nbt.getClass()
				.isAssignableFrom(NMSUtils.getNMSClass("NBTBase"))) {
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
			Class<?> clist = NMSUtils.getNMSClass("NBTTagByte");
			Constructor<?> cons = clist.getDeclaredConstructor(byte.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Short){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagShort");
			Constructor<?> cons = clist.getDeclaredConstructor(short.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Integer){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagInt");
			Constructor<?> cons = clist.getDeclaredConstructor(int.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Long){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagLong");
			Constructor<?> cons = clist.getDeclaredConstructor(long.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Float){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagFloat");
			Constructor<?> cons = clist.getDeclaredConstructor(float.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Double){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagDouble");
			Constructor<?> cons = clist.getDeclaredConstructor(double.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof Byte[]){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagByteArray");
			Constructor<?> cons = clist.getDeclaredConstructor(byte[].class);
			cons.setAccessible(true);
			return cons.newInstance(((Object[]) obj).clone());
		}else if (obj instanceof Integer[]){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagIntArray");
			Constructor<?> cons = clist.getDeclaredConstructor(int[].class);
			cons.setAccessible(true);
			return cons.newInstance(((Object[]) obj).clone());
		}else if (obj instanceof String){
			Class<?> clist = NMSUtils.getNMSClass("NBTTagString");
			Constructor<?> cons = clist.getDeclaredConstructor(String.class);
			cons.setAccessible(true);
			return cons.newInstance(obj);
		}else if (obj instanceof List) {
			Class<?> cnbtbase = NMSUtils.getNMSClass("NBTBase");
			Class<?> clist = NMSUtils.getNMSClass("NBTTagList");
			Object o = clist.newInstance();
			Method add = clist.getDeclaredMethod("add", cnbtbase);
			add.setAccessible(true);
			for(Object e:(List<Object>)obj){
				add.invoke(o,toNBT(e));
			}
			return o;
		} else if (obj instanceof Map) {
			Class<?> cnbtbase = NMSUtils.getNMSClass("NBTBase");
			Class<?> cmap = NMSUtils.getNMSClass("NBTTagCompound");
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
