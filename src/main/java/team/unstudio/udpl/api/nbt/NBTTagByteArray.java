package team.unstudio.udpl.api.nbt;

public final class NBTTagByteArray extends NBTBase {
	private byte[] value;

	public NBTTagByteArray(byte[] value) {
		super(NBTBaseType.BYTEARRAY);
		this.value = value;
	}

	public byte[] getValue() {
		return this.value;
	}

	public String toString() {
		return this.value.toString();
	}

}
