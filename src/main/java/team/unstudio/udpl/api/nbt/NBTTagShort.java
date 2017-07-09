package team.unstudio.udpl.api.nbt;

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
}
