package team.unstudio.udpl.nms.nbt;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

public interface NBTUtils {
	
	static NBTBase parseFromJson(String json){
		if(json.startsWith("{")&&json.endsWith("}")){
			
		}else if(json.startsWith("[")&&json.endsWith("]")){
			
		}else{
			
		}
		return null;
	}
	
	static NBTNumber parseNBTNumber(String value){
		return new NBTTagDouble(Double.valueOf(value));
	}
	
	static void registerAllNBTSerilizable(){
		ConfigurationSerialization.registerClass(NBTTagByte.class);
		ConfigurationSerialization.registerClass(NBTTagByteArray.class);
		ConfigurationSerialization.registerClass(NBTTagCompound.class);
		ConfigurationSerialization.registerClass(NBTTagDouble.class);
		ConfigurationSerialization.registerClass(NBTTagEnd.class);
		ConfigurationSerialization.registerClass(NBTTagFloat.class);
		ConfigurationSerialization.registerClass(NBTTagInt.class);
		ConfigurationSerialization.registerClass(NBTTagIntArray.class);
		ConfigurationSerialization.registerClass(NBTTagList.class);
		ConfigurationSerialization.registerClass(NBTTagLong.class);
		ConfigurationSerialization.registerClass(NBTTagShort.class);
		ConfigurationSerialization.registerClass(NBTTagString.class);
	}
}
