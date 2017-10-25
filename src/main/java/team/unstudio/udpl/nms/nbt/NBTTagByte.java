package team.unstudio.udpl.nms.nbt;

import java.util.Map;

import com.google.common.collect.Maps;

public final class NBTTagByte extends NBTNumber {
	private byte value;

	public NBTTagByte(byte value) {
		super(NBTBaseType.BYTE);
		this.value = value;
	}

	public byte getValue() {
		return this.value;
	}

	public String toString() {
		return Byte.toString(this.value)+"B";
	}

	@Override
	public byte getByte() {
		return value;
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
	
	public static NBTTagByte deserialize(Map<String, Object> map){
		return new NBTTagByte(((Number)map.get("value")).byteValue());
	}
}
