package team.unstudio.udpl.api.nbt;

public final class NBTTagString extends NBTBase {
	private String value;

	public NBTTagString(String value) {
		super(NBTBaseType.STRING);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String toString() {
		return "\""+this.value+"\"";
	}
}