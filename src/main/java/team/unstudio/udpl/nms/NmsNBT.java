package team.unstudio.udpl.nms;

import javax.annotation.Nullable;

import team.unstudio.udpl.nbt.NBTBase;
import team.unstudio.udpl.nbt.NBTTagByte;
import team.unstudio.udpl.nbt.NBTTagByteArray;
import team.unstudio.udpl.nbt.NBTTagCompound;
import team.unstudio.udpl.nbt.NBTTagDouble;
import team.unstudio.udpl.nbt.NBTTagFloat;
import team.unstudio.udpl.nbt.NBTTagInt;
import team.unstudio.udpl.nbt.NBTTagIntArray;
import team.unstudio.udpl.nbt.NBTTagList;
import team.unstudio.udpl.nbt.NBTTagLong;
import team.unstudio.udpl.nbt.NBTTagShort;
import team.unstudio.udpl.nbt.NBTTagString;

/**
 * NBT的转换接口
 */
public interface NmsNBT {
	
	/**
	 * 转换为Compound
	 */
	@Nullable
	public NBTTagCompound toCompound(Object nbt);

	/**
	 * 转换为List
	 */
	@Nullable
	public NBTTagList toList(Object nbt);

	/**
	 * 转换为Byte
	 */
	@Nullable
	public NBTTagByte toByte(Object nbt);

	/**
	 * 转换为Short
	 */
	@Nullable
	public NBTTagShort toShort(Object nbt);

	/**
	 * 转换为Int
	 */
	@Nullable
	public NBTTagInt toInt(Object nbt);

	/**
	 * 转换为Long
	 */
	@Nullable
	public NBTTagLong toLong(Object nbt);

	/**
	 * 转换为Float
	 */
	@Nullable
	public NBTTagFloat toFloat(Object nbt);

	/**
	 * 转换为Double
	 */
	@Nullable
	public NBTTagDouble toDouble(Object nbt);
	
	/**
	 * 转换为String
	 */
	@Nullable
	public NBTTagString toString(Object nbt);
	
	/**
	 * 转换为ByteArray
	 */
	@Nullable
	public NBTTagByteArray toByteArray(Object nbt);
	
	/**
	 * 转换为IntArray
	 */
	@Nullable
	public NBTTagIntArray toIntArray(Object nbt);
	
	/**
	 * 转换为NBTBase
	 */
	@Nullable
	public NBTBase toNBTBase(Object nbt);
	
	/**
	 * 转换为NMSNBT
	 */
	@Nullable
	public Object toNBT(NBTBase nbt);
}