package team.unstudio.udpl.nms.nbt;

import java.util.regex.Pattern;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public final class NBTUtils {
	
	private NBTUtils(){}
	
	private static final JsonParser JSON_PARSER = new JsonParser();
	private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
	private static final Pattern UNIDENTIFIED_DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
	private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
	private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?[0-9]+[l|L]");
	private static final Pattern INT_PATTERN = Pattern.compile("[-+]?[0-9]+");
	private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?[0-9]+[s|S]");
	private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?[0-9]+[b|B]");
	
	public static NBTBase parseFromJson(String value){
		return parseFromJson(JSON_PARSER.parse(value));
	}
	
	public static NBTBase parseFromJson(JsonElement element){
		if(element.isJsonObject()){
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			element.getAsJsonObject().entrySet().forEach(entry->nbtTagCompound.set(entry.getKey(),parseFromJson(entry.getValue())));
			return nbtTagCompound;
		}else if(element.isJsonArray()){
			JsonArray jsonArray = element.getAsJsonArray();
			boolean isByteArray = jsonArray.size() != 0;
			boolean isIntArray = jsonArray.size() != 0;
			NBTBase[] nbtBases = new NBTBase[jsonArray.size()];
			for (int i = 0, size = jsonArray.size(); i < size; i++) {
				nbtBases[i] = parse(jsonArray.get(i).toString());
				if(nbtBases[i].getType() != NBTBaseType.BYTE)
					isByteArray = false;
				if(nbtBases[i].getType() != NBTBaseType.INTEGER)
					isIntArray = false;
			}
			
			if(isByteArray){
				byte[] bytes = new byte[nbtBases.length];
				for(int i = 0, size = nbtBases.length;i<size;i++)
					bytes[i] = ((NBTTagByte)nbtBases[i]).getValue();
				return new NBTTagByteArray(bytes);
			}else if(isIntArray){
				int[] ints = new int[nbtBases.length];
				for(int i = 0, size = nbtBases.length;i<size;i++)
					ints[i] = ((NBTTagInt)nbtBases[i]).getValue();
				return new NBTTagIntArray(ints);
			}else{
				return new NBTTagList(nbtBases);
			}
		}else{
			return parse(element.toString());
		}
	}
	
	private static NBTBase parse(String value){
		if(DOUBLE_PATTERN.matcher(value).matches()){
			return new NBTTagDouble(Double.parseDouble(value.substring(0, value.length()-1)));
		}else if(FLOAT_PATTERN.matcher(value).matches()){
			return new NBTTagFloat(Float.parseFloat(value.substring(0, value.length()-1)));
		}else if(LONG_PATTERN.matcher(value).matches()){
			return new NBTTagLong(Long.parseLong(value.substring(0, value.length()-1)));
		}else if(SHORT_PATTERN.matcher(value).matches()){
			return new NBTTagShort(Short.parseShort(value.substring(0, value.length()-1)));
		}else if(BYTE_PATTERN.matcher(value).matches()){
			return new NBTTagByte(Byte.parseByte(value.substring(0, value.length()-1)));
		}else if(value.toLowerCase().equals("true")){
			return new NBTTagByte((byte)1);
		}else if(value.toLowerCase().equals("false")){
			return new NBTTagByte((byte)0);
		}else if(INT_PATTERN.matcher(value).matches()){
			return new NBTTagInt(Integer.parseInt(value));
		}else if(UNIDENTIFIED_DOUBLE_PATTERN.matcher(value).matches()){
			return new NBTTagDouble(Double.parseDouble(value));
		}else{
			return new NBTTagString(value);
		}
	}
	
	public static void registerAllNBTSerilizable(){
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
		ConfigurationSerialization.registerClass(NBTTagLongArray.class);
		ConfigurationSerialization.registerClass(NBTTagShort.class);
		ConfigurationSerialization.registerClass(NBTTagString.class);
	}
}
