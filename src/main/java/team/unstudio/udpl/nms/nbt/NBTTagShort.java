package team.unstudio.udpl.nms.nbt;

import java.util.Map;

import com.google.common.collect.Maps;

public final class NBTTagShort extends NBTNumber {
	private short value;

	public NBTTagShort(short value) {
		super(NBTBaseType.DOUBLE);
		this.value = value;
	}

	public short getValue() {
		return this.value;
	}

	public String toString() {
		return Short.toString(this.value);
	}
	
	@Override
	public byte getByte() {
		return (byte) value;
	}

	@Override
	public short getShort() {
		return value;
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
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		map.put("value", getValue());
		return map;
	}
	
	public static NBTTagShort deserialize(Map<String, Object> map){
		return new NBTTagShort(((Number)map.get("value")).shortValue());
	}
}
