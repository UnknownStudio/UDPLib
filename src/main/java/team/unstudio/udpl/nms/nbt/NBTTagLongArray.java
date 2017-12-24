package team.unstudio.udpl.nms.nbt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public final class NBTTagLongArray extends NBTBase {
	private long[] value;

	public NBTTagLongArray(long[] value) {
		super(NBTBaseType.LONGARRAY);
		this.value = value;
	}
	
	public NBTTagLongArray(Long[] value) {
		super(NBTBaseType.LONGARRAY);
		this.value = new long[value.length];
		for (int i = 0, size = value.length; i < size; i++)
			this.value[i] = value[i];
	}

	public long[] getValue() {
		return this.value;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("[L;");
        for (long aValue : value) builder.append(aValue).append(',');
		
		if(builder.charAt(builder.length()-1)==',')
			builder.deleteCharAt(builder.length()-1);
		
		return builder.append(']').toString();
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		List<Long> values = Lists.newLinkedList();
		for(long value:getValue()) values.add(value);
		map.put("value", values);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static NBTTagLongArray deserialize(Map<String, Object> map){
		return new NBTTagLongArray(((List<Long>)map.get("value")).toArray(new Long[0]));
	}
}
