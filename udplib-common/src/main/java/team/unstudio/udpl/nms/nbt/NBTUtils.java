package team.unstudio.udpl.nms.nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.util.regex.Pattern;

public final class NBTUtils {

	private NBTUtils() {
	}

	private static final JsonParser JSON_PARSER = new JsonParser();
	private static final Pattern INT_ARRAY_PATTERN = Pattern.compile("\\[[I|i];([-+]?[0-9]+)?(,[-+]?[0-9]+)*]");
	private static final Pattern BYTE_ARRAY_PATTERN = Pattern.compile("\\[[B|b];([-+]?[0-9]+)?(,[-+]?[0-9]+)*]");
	private static final Pattern LONG_ARRAY_PATTERN = Pattern.compile("\\[[L|l];([-+]?[0-9]+)?(,[-+]?[0-9]+)*]");
	private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([E|e][0-9]+)?[d|D]"); //TODO: Fix double pattern
	private static final Pattern UNIDENTIFIED_DOUBLE_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([E|e][0-9]+)?");
	private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+([E|e][0-9]+)?[f|F]");
	private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?[0-9]+[l|L]");
	private static final Pattern INT_PATTERN = Pattern.compile("[-+]?[0-9]+");
	private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?[0-9]+[s|S]");
	private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?[0-9]+[b|B]");

	public static NBTBase parseFromJson(String value) {
		return parseFromJson(JSON_PARSER.parse(value));
	}

	public static NBTBase parseFromJson(JsonElement element) {
		if (element.isJsonObject()) {
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			element.getAsJsonObject().entrySet()
					.forEach(entry -> nbtTagCompound.set(entry.getKey(), parseFromJson(entry.getValue())));
			return nbtTagCompound;
		} else if (element.isJsonArray()) {
			JsonArray jsonArray = element.getAsJsonArray();
			if (jsonArray.size() == 0)
				return new NBTTagList();

			JsonElement first = jsonArray.get(0);
			if (first.getAsCharacter() == 'B' || first.getAsCharacter() == 'b') {
				byte[] bytes = new byte[jsonArray.size() - 1];
				for (int i = 0, size = jsonArray.size() - 1; i < size; i++)
					bytes[i] = ((NBTNumber) parseFromJson(jsonArray.get(i + 1))).getByte();
				return new NBTTagByteArray(bytes);
			} else if (first.getAsCharacter() == 'I' || first.getAsCharacter() == 'i') {
				int[] bytes = new int[jsonArray.size() - 1];
				for (int i = 0, size = jsonArray.size() - 1; i < size; i++)
					bytes[i] = ((NBTNumber) parseFromJson(jsonArray.get(i + 1))).getInt();
				return new NBTTagIntArray(bytes);
			} else if (first.getAsCharacter() == 'L' || first.getAsCharacter() == 'l') {
				long[] longs = new long[jsonArray.size() - 1];
				for (int i = 0, size = jsonArray.size() - 1; i < size; i++)
					longs[i] = ((NBTNumber) parseFromJson(jsonArray.get(i + 1))).getLong();
				return new NBTTagLongArray(longs);
			} else {
				NBTBase[] nbtBases = new NBTBase[jsonArray.size()];
				for (int i = 0, size = jsonArray.size(); i < size; i++)
					nbtBases[i] = parseFromJson(jsonArray.get(i));
				return new NBTTagList(nbtBases);
			}
		} else {
			return parseType(element.getAsString());
		}
	}

	public static NBTBase parseType(String value) {
		try {
			if (DOUBLE_PATTERN.matcher(value).matches()) {
				return new NBTTagDouble(Double.parseDouble(value.substring(0, value.length() - 1)));
			} else if (FLOAT_PATTERN.matcher(value).matches()) {
				return new NBTTagFloat(Float.parseFloat(value.substring(0, value.length() - 1)));
			} else if (LONG_PATTERN.matcher(value).matches()) {
				return new NBTTagLong(Long.parseLong(value.substring(0, value.length() - 1)));
			} else if (SHORT_PATTERN.matcher(value).matches()) {
				return new NBTTagShort(Short.parseShort(value.substring(0, value.length() - 1)));
			} else if (BYTE_PATTERN.matcher(value).matches()) {
				return new NBTTagByte(Byte.parseByte(value.substring(0, value.length() - 1)));
			} else if (value.toLowerCase().equals("true")) {
				return new NBTTagByte((byte) 1);
			} else if (value.toLowerCase().equals("false")) {
				return new NBTTagByte((byte) 0);
			} else if (INT_PATTERN.matcher(value).matches()) {
				return new NBTTagInt(Integer.parseInt(value));
			} else if (UNIDENTIFIED_DOUBLE_PATTERN.matcher(value).matches()) {
				return new NBTTagDouble(Double.parseDouble(value));
			} else if (INT_ARRAY_PATTERN.matcher(value).matches()) {
				String[] numbers = value.substring(3, value.length()).split(",");
				int[] ints = new int[numbers.length];
				for (int i = 0, size = numbers.length; i < size; i++)
					ints[i] = Integer.parseInt(numbers[i]);
				return new NBTTagIntArray(ints);
			} else if (BYTE_ARRAY_PATTERN.matcher(value).matches()) {
				String[] numbers = value.substring(3, value.length()).split(",");
				byte[] bytes = new byte[numbers.length];
				for (int i = 0, size = numbers.length; i < size; i++)
					bytes[i] = Byte.parseByte(numbers[i]);
				return new NBTTagByteArray(bytes);
			} else if (LONG_ARRAY_PATTERN.matcher(value).matches()) {
				String[] numbers = value.substring(3, value.length()).split(",");
				long[] longs = new long[numbers.length];
				for (int i = 0, size = numbers.length; i < size; i++)
					longs[i] = Long.parseLong(numbers[i]);
				return new NBTTagLongArray(longs);
			}
		} catch (NumberFormatException ignored) {
		}
		return new NBTTagString(value);
	}

	/**
	 * inner method.
	 */
	public static void registerAllNBTSerilizable() {
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
