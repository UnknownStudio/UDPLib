package team.unstudio.udpl.nms.nbt;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public final class NBTTagCompound extends NBTBase {
	private final Map<String, NBTBase> map = Maps.newLinkedHashMap();

	public NBTTagCompound(Map<String, NBTBase> map) {
		this();
		this.map.putAll(map);
	}

	public NBTTagCompound() {
		super(NBTBaseType.COMPOUND);
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public NBTTagCompound set(String key, NBTBase value) {
		if(value.getType() != NBTBaseType.END)
			this.map.put(key, value);
		return this;
	}

	public NBTTagCompound setByte(String key, byte value) {
		this.map.put(key, new NBTTagByte(value));
		return this;
	}

	public NBTTagCompound setShort(String key, short value) {
		this.map.put(key, new NBTTagShort(value));
		return this;
	}

	public NBTTagCompound setInt(String key, int value) {
		this.map.put(key, new NBTTagInt(value));
		return this;
	}

	public NBTTagCompound setLong(String key, long value) {
		this.map.put(key, new NBTTagLong(value));
		return this;
	}

	public NBTTagCompound setFloat(String key, float value) {
		this.map.put(key, new NBTTagFloat(value));
		return this;
	}

	public NBTTagCompound setDouble(String key, double value) {
		this.map.put(key, new NBTTagDouble(value));
		return this;
	}

	public NBTTagCompound setString(String key, @Nonnull String value) {
		Validate.notNull(value);
		this.map.put(key, new NBTTagString(value));
		return this;
	}

	public NBTTagCompound setByteArray(String key, @Nonnull byte[] value) {
		Validate.notNull(value);
		this.map.put(key, new NBTTagByteArray(value));
		return this;
	}

	public NBTTagCompound setIntArray(String key, @Nonnull int[] value) {
		Validate.notNull(value);
		this.map.put(key, new NBTTagIntArray(value));
		return this;
	}
	
	public NBTTagCompound setLongArray(String key, @Nonnull long[] value){
		Validate.notNull(value);
		this.map.put(key, new NBTTagLongArray(value));
		return this;
	}

	public NBTBase get(String key) {
		return this.map.get(key);
	}

	public byte getByte(String key) {
		try {
			return ((NBTNumber) this.map.get(key)).getByte();
		} catch (ClassCastException ignored) {}
		return 0;
	}

	public short getShort(String key) {
		try {
			return ((NBTNumber) this.map.get(key)).getShort();
		} catch (ClassCastException ignored) {}
		return 0;
	}

	public int getInt(String key) {
		try {
			return ((NBTNumber) this.map.get(key)).getInt();
		} catch (ClassCastException ignored) {}
		return 0;
	}

	public long getLong(String key) {
		try {
			return ((NBTNumber) this.map.get(key)).getLong();
		} catch (ClassCastException ignored) {}
		return 0;
	}

	public float getFloat(String key) {
		try {
			return ((NBTNumber) this.map.get(key)).getFloat();
		} catch (ClassCastException ignored) {}
		return 0.0F;
	}

	public double getDouble(String key) {
		try {
			return ((NBTNumber) this.map.get(key)).getDouble();
		} catch (ClassCastException ignored) {}
		return 0.0D;
	}

	@Nullable
	public String getString(String key) {
		return containOfType(key, NBTBaseType.STRING)?((NBTTagString) this.map.get(key)).getValue():null;
	}

	@Nullable
	public byte[] getByteArray(String key) {
		return containOfType(key, NBTBaseType.BYTEARRAY)?((NBTTagByteArray) this.map.get(key)).getValue():null;
	}

	@Nullable
	public int[] getIntArray(String key) {
		return containOfType(key, NBTBaseType.INTARRAY)?((NBTTagIntArray) this.map.get(key)).getValue():null;
	}
	
	@Nullable
	public long[] getLongArray(String key){
		return containOfType(key, NBTBaseType.LONGARRAY)?((NBTTagLongArray)this.map.get(key)).getValue():null;
	}

	@Nonnull
	public NBTTagCompound getCompound(String key) {
		return containOfType(key, NBTBaseType.COMPOUND)?(NBTTagCompound) this.map.get(key):new NBTTagCompound();
	}

	@Nonnull
	public NBTTagList getList(String key) {
		return containOfType(key,NBTBaseType.LIST)?(NBTTagList) this.map.get(key):new NBTTagList();
	}
	
	public NBTTagCompound getValue() {
		return this;
	}
	
	public boolean contain(String key){
		return this.map.containsKey(key);
	}
	
	public boolean containOfType(String key, NBTBaseType type){
		if(!this.map.containsKey(key))
			return false;
		
		return this.map.get(key).getType() == type;
	}

	public NBTTagCompound remove(String key) {
		this.map.remove(key);
		return this;
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	public NBTTagCompound clear() {
		this.map.clear();
		return this;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		for (Map.Entry<String, NBTBase> localEntry : this.map.entrySet())
			builder.append("\"").append(localEntry.getKey()).append("\":").append(localEntry.getValue()).append(",");
		
		if(builder.charAt(builder.length()-1)==',')
			builder.deleteCharAt(builder.length()-1);
		
		return builder.append('}').toString();
	}

	public Set<Map.Entry<String, NBTBase>> getEntry() {
		return this.map.entrySet();
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		map.put("value", this.map);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static NBTTagCompound deserialize(Map<String, Object> map){
		return new NBTTagCompound((Map<String,NBTBase>) map.get("value"));
	}
}
