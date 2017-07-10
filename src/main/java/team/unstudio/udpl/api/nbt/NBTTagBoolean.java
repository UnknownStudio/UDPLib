package team.unstudio.udpl.api.nbt;

public final class NBTTagBoolean extends NBTBase {
	private boolean value;

	public NBTTagBoolean(boolean value) {
		super(NBTBaseType.BOOLEAN);
		this.value = value;
	}

	public boolean getValue() {
		return this.value;
	}

	public String toString() {
		return Boolean.toString(this.value);
	}
}
