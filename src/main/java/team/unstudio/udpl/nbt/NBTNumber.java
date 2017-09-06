package team.unstudio.udpl.nbt;

public abstract class NBTNumber extends NBTBase{
	
	public NBTNumber(NBTBaseType type) {
		super(type);
	}

	public abstract byte getByte();
	
	public abstract short getShort();
	
	public abstract int getInt();
	
	public abstract long getLong();
	
	public abstract float getFloat();
	
	public abstract double getDouble();
}
