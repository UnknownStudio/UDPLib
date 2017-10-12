package team.unstudio.udpl.nms.nbt;

import java.util.Map;

import com.google.common.collect.Maps;

public final class NBTTagInt extends NBTNumber {
	private int value;

	public NBTTagInt(int value) {
		super(NBTBaseType.INTEGER);
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String toString() {
		return Integer.toString(this.value);
	}
	
	@Override
	public byte getByte() {
		return (byte) value;
	}

	@Override
	public short getShort() {
		return (short) value;
	}

	@Override
	public int getInt() {
		return value;
	}

	@Override
	public long getLong() {
		return value;
	}

	@Override
	public float getFloat() {
		return value;
	}

	@Override
	public double getDouble() {
		return value;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("==", getClass().getName());
		map.put("value", getValue());
		return map;
	}
	
	public static NBTTagInt deserialize(Map<String, Object> map){
		return new NBTTagInt((int) map.get("value"));
	}
}
