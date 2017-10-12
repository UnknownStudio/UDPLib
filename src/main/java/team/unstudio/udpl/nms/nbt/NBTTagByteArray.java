package team.unstudio.udpl.nms.nbt;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public final class NBTTagByteArray extends NBTBase {
	private byte[] value;

	public NBTTagByteArray(byte[] value) {
		super(NBTBaseType.BYTEARRAY);
		this.value = value;
	}
	
	public NBTTagByteArray(Byte[] value) {
		super(NBTBaseType.BYTEARRAY);
		this.value = new byte[value.length];
		for (int i = 0, size = value.length; i < size; i++)
			this.value[i] = value[i];
	}

	public byte[] getValue() {
		return this.value;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0; i < value.length; ++i) 
			builder.append(value[i]).append(',');
		
		if(builder.charAt(builder.length()-1)==',')
			builder.deleteCharAt(builder.length()-1);
		
		return builder.append(']').toString();
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("==", getClass().getName());
		map.put("value", Arrays.asList(getValue()));
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static NBTTagByteArray deserialize(Map<String, Object> map){
		return new NBTTagByteArray(((List<Byte>)map.get("value")).toArray(new Byte[0]));
	}
}
