package team.unstudio.udpl.nms.nbt;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public abstract class NBTBase implements ConfigurationSerializable{
	private NBTBaseType type;

	protected NBTBase(NBTBaseType type) {
		this.type = type;
	}

	public final NBTBaseType getType() {
		return this.type;
	}
}
