package team.unstudio.udpl.nms.nbt;


/**
 * NBT的转换接口
 */
public interface NmsNBT {
	
	/**
	 * 转换为Compound
	 */
	public NBTTagCompound toCompound(Object nbt);

	/**
	 * 转换为List
	 */
	public NBTTagList toList(Object nbt);

	/**
	 * 转换为Byte
	 */
	public NBTTagByte toByte(Object nbt);

	/**
	 * 转换为Short
	 */
	public NBTTagShort toShort(Object nbt);

	/**
	 * 转换为Int
	 */
	public NBTTagInt toInt(Object nbt);

	/**
	 * 转换为Long
	 */
	public NBTTagLong toLong(Object nbt);

	/**
	 * 转换为Float
	 */
	public NBTTagFloat toFloat(Object nbt);

	/**
	 * 转换为Double
	 */
	public NBTTagDouble toDouble(Object nbt);
	
	/**
	 * 转换为String
	 */
	public NBTTagString toString(Object nbt);
	
	/**
	 * 转换为ByteArray
	 */
	public NBTTagByteArray toByteArray(Object nbt);
	
	/**
	 * 转换为IntArray
	 */
	public NBTTagIntArray toIntArray(Object nbt);
	
	/**
	 * 转换为NBTBase
	 */
	public NBTBase toNBTBase(Object nbt);
	
	/**
	 * 转换为NMSNBT
	 */
	public Object toNmsNBT(NBTBase nbt);
}
