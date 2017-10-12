package team.unstudio.udpl.nms.nbt;

import java.util.Map;

import com.google.common.collect.Maps;

public class NBTTagEnd extends NBTBase {

	public NBTTagEnd() {
		super(NBTBaseType.END);
	}

	public String toString() {
		return "End";
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("==", getClass().getName());
		return map;
	}
	
	public static NBTTagEnd deserialize(Map<String, Object> map){
		return new NBTTagEnd();
	}
}
