package team.unstudio.udpl.nms.nbt;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public final class NBTTagIntArray extends NBTBase {
	private int[] value;

	public NBTTagIntArray(int[] value) {
		super(NBTBaseType.INTARRAY);
		this.value = value;
	}
	
	public NBTTagIntArray(Integer[] value) {
		super(NBTBaseType.INTARRAY);
		this.value = new int[value.length];
		for (int i = 0, size = value.length; i < size; i++)
			this.value[i] = value[i];
	}

	public int[] getValue() {
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
	public static NBTTagIntArray deserialize(Map<String, Object> map){
		return new NBTTagIntArray(((List<Byte>)map.get("value")).toArray(new Integer[0]));
	}
}
