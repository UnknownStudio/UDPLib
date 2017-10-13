package team.unstudio.udpl.nms.nbt;

import java.util.Map;

import com.google.common.collect.Maps;

public final class NBTTagString extends NBTBase {
	private String value;

	public NBTTagString(String value) {
		super(NBTBaseType.STRING);
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String toString() {
		return "\""+this.value+"\"";
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		map.put("value", getValue());
		return map;
	}
	
	public static NBTTagString deserialize(Map<String, Object> map){
		return new NBTTagString((String) map.get("value"));
	}
}