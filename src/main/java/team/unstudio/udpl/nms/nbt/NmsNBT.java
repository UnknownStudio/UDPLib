package team.unstudio.udpl.nms.nbt;

import team.unstudio.udpl.nms.NmsHelper;

/**
 * NBT的转换接口
 */
public interface NmsNBT {
	
	/**
	 * 转换为Compound
	 */
	NBTTagCompound toCompound(Object nbt);

	/**
	 * 转换为List
	 */
	NBTTagList toList(Object nbt);

	/**
	 * 转换为Byte
	 */
	NBTTagByte toByte(Object nbt);

	/**
	 * 转换为Short
	 */
	NBTTagShort toShort(Object nbt);

	/**
	 * 转换为Int
	 */
	NBTTagInt toInt(Object nbt);

	/**
	 * 转换为Long
	 */
	NBTTagLong toLong(Object nbt);

	/**
	 * 转换为Float
	 */
	NBTTagFloat toFloat(Object nbt);

	/**
	 * 转换为Double
	 */
	NBTTagDouble toDouble(Object nbt);
	
	/**
	 * 转换为String
	 */
	NBTTagString toString(Object nbt);
	
	/**
	 * 转换为ByteArray
	 */
	NBTTagByteArray toByteArray(Object nbt);
	
	/**
	 * 转换为IntArray
	 */
	NBTTagIntArray toIntArray(Object nbt);
	
	/**
	 * 转换为NBTBase
	 */
	NBTBase toNBTBase(Object nbt);
	
	/**
	 * 转换为 {@link net.minecraft.server} 的NBT类
	 */
	Object toNmsNBT(NBTBase nbt);
	
	static NmsNBT getInstance(){
		return NmsHelper.getNmsNBT();
	}
}
