package team.unstudio.udpl.api.nbt;

public final class NBTTagIntArray extends NBTBase {
	private int[] value;

	public NBTTagIntArray(int[] value) {
		super(NBTBaseType.INTARRAY);
		this.value = value;
	}

	public int[] getValue() {
		return this.value;
	}

	public String toString() {
		return this.value.toString();
	}
}
