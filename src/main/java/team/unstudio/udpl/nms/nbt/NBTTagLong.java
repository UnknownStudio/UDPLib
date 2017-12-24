package team.unstudio.udpl.nms.nbt;

import com.google.common.collect.Maps;

import java.util.Map;

public final class NBTTagLong extends NBTNumber {
	private long value;

	public NBTTagLong(long value) {
		super(NBTBaseType.LONG);
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

	public String toString() {
		return Long.toString(this.value)+"L";
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
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		map.put("value", getValue());
		return map;
	}
	
	public static NBTTagLong deserialize(Map<String, Object> map){
		return new NBTTagLong(((Number)map.get("value")).longValue());
	}
}
