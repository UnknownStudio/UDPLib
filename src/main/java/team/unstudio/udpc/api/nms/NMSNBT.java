package team.unstudio.udpc.api.nms;

import java.util.List;
import java.util.Map;

public interface NMSNBT {
	
	public Map<String, Object> toMap(Object nbt) throws Exception;

	public List<Object> toList(Object nbt) throws Exception;

	public byte toByte(Object nbt) throws Exception;

	public short toShort(Object nbt) throws Exception;

	public int toInt(Object nbt) throws Exception;

	public long toLong(Object nbt) throws Exception;

	public float toFloat(Object nbt) throws Exception;

	public double toDouble(Object nbt) throws Exception;
	
	public String toString(Object nbt) throws Exception;
	
	public byte[] toByteArray(Object nbt) throws Exception;
	
	public int[] toIntArray(Object nbt) throws Exception;
	
	public Object toObject(Object nbt) throws Exception;
	
	public Object toNBT(Object obj) throws Exception;
}
