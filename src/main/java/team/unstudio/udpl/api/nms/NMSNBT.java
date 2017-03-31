package team.unstudio.udpl.api.nms;

import java.util.List;
import java.util.Map;

/**
 * NBT的转换接口
 */
public interface NMSNBT {
	
	/**
	 * 转换为Map
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> toMap(Object nbt) throws Exception;

	/**
	 * 转换为List
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public List<Object> toList(Object nbt) throws Exception;

	/**
	 * 转换为Byte
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public byte toByte(Object nbt) throws Exception;

	/**
	 * 转换为Short
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public short toShort(Object nbt) throws Exception;

	/**
	 * 转换为Int
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public int toInt(Object nbt) throws Exception;

	/**
	 * 转换为Long
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public long toLong(Object nbt) throws Exception;

	/**
	 * 转换为Float
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public float toFloat(Object nbt) throws Exception;

	/**
	 * 转换为Double
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public double toDouble(Object nbt) throws Exception;
	
	/**
	 * 转换为String
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public String toString(Object nbt) throws Exception;
	
	/**
	 * 转换为ByteArray
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public byte[] toByteArray(Object nbt) throws Exception;
	
	/**
	 * 转换为IntArray
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public int[] toIntArray(Object nbt) throws Exception;
	
	/**
	 * 转换为Object
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public Object toObject(Object nbt) throws Exception;
	
	/**
	 * 转换为NBT
	 * @param nbt
	 * @return
	 * @throws Exception
	 */
	public Object toNBT(Object obj) throws Exception;
}
