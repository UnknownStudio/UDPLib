package team.unstudio.udpl.nms.nbt;

import java.util.Map;

import com.google.common.collect.Maps;

public final class NBTTagDouble extends NBTNumber {
	private double value;

	public NBTTagDouble(double value) {
		super(NBTBaseType.DOUBLE);
		this.value = value;
	}

	public double getValue() {
		return this.value;
	}

	public String toString() {
		return Double.toString(this.value)+"D";
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
		return (int) value;
	}

	@Override
	public long getLong() {
		return (long) value;
	}

	@Override
	public float getFloat() {
		return (float) value;
	}

	@Override
	public double getDouble() {
		return value;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		map.put("value", getValue());
		return map;
	}
	
	public static NBTTagDouble deserialize(Map<String, Object> map){
		return new NBTTagDouble(((Number)map.get("value")).doubleValue());
	}
}
