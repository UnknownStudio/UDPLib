package team.unstudio.udpl.api.nbt;

public abstract class NBTBase {
	private NBTBaseType type;

	public NBTBase(NBTBaseType type) {
		this.type = type;

	}

	public final NBTBaseType getType() {
		return this.type;
	}

}
