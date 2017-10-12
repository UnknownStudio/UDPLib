package team.unstudio.udpl.nms.nbt;

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
		return Long.toString(this.value);
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
}
