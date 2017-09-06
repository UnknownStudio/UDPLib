package team.unstudio.udpl.nbt;

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
		return Byte.toString(this.value);
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
}
