package team.unstudio.udpl.nbt;

public final class NBTTagFloat extends NBTNumber {
	private float value;

	public NBTTagFloat(float value) {
		super(NBTBaseType.FLOAT);
		this.value = value;
	}

	public float getValue() {
		return this.value;
	}

	public String toString() {
		return Float.toString(this.value);
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
		return value;
	}

	@Override
	public double getDouble() {
		return value;
	}
}
