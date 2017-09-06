package team.unstudio.udpl.nbt;

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
		return Double.toString(this.value);
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
}
