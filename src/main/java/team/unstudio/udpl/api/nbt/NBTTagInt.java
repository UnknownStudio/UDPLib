package team.unstudio.udpl.api.nbt;

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
}
