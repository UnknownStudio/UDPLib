package team.unstudio.udpl.nms.nbt;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
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
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		List<Byte> values = Lists.newLinkedList();
		for(byte value:getValue()) values.add(value);
		map.put("value", values);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static NBTTagByteArray deserialize(Map<String, Object> map){
		List<Number> list = (List<Number>) map.get("value");
		byte[] bytes = new byte[list.size()];
		for (int i = 0, size = list.size(); i < size; i++)
			bytes[i] = list.get(i).byteValue();
		return new NBTTagByteArray(bytes);
	}
}
