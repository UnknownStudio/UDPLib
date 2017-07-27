package team.unstudio.udpl.api.nbt;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

public final class NBTTagCompound extends NBTBase {
	private final Map<String, NBTBase> map = Maps.newHashMap();

	public NBTTagCompound(Map<String, NBTBase> map) {
		this();
		map.putAll(map);
	}

	public NBTTagCompound() {
		super(NBTBaseType.COMPOUND);
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public NBTTagCompound set(String path, NBTBase value) {
		this.map.put(path, value);
		return this;
	}

	public NBTTagCompound setByte(String path, byte value) {
		this.map.put(path, new NBTTagByte(value));
		return this;
	}

	public NBTTagCompound setShort(String path, short value) {
		this.map.put(path, new NBTTagShort(value));
		return this;
	}

	public NBTTagCompound setInt(String path, int value) {
		this.map.put(path, new NBTTagInt(value));
		return this;
	}

	public NBTTagCompound setLong(String path, long value) {
		this.map.put(path, new NBTTagLong(value));
		return this;
	}

	public NBTTagCompound setFloat(String path, float value) {
		this.map.put(path, new NBTTagFloat(value));
		return this;
	}

	public NBTTagCompound setDouble(String path, double value) {
		this.map.put(path, new NBTTagDouble(value));
		return this;
	}

	public NBTTagCompound setString(String path, String value) {
		this.map.put(path, new NBTTagString(value));
		return this;
	}

	public NBTTagCompound setByteArray(String path, byte[] value) {
		this.map.put(path, new NBTTagByteArray(value));
		return this;
	}

	public NBTTagCompound setIntArray(String path, int[] value) {
		this.map.put(path, new NBTTagIntArray(value));
		return this;
	}

	public NBTBase get(String path) {
		return this.map.get(path);
	}

	public byte getByte(String path) {
		try {

			return ((NBTNumber) this.map.get(path)).getByte();
		} catch (Exception e) {

		}
		return 0;
	}

	public short getShort(String path) {
		try {
			return ((NBTNumber) this.map.get(path)).getShort();
		} catch (Exception e) {

		}
		return 0;
	}

	public int getInt(String path) {
		try {
			return ((NBTNumber) this.map.get(path)).getInt();
		} catch (Exception e) {

		}
		return 0;
	}

	public long getLong(String path) {
		try {
			return ((NBTNumber) this.map.get(path)).getLong();
		} catch (Exception e) {

		}
		return 0;
	}

	public float getFloat(String path) {
		try {
			return ((NBTNumber) this.map.get(path)).getFloat();
		} catch (Exception e) {

		}
		return 0.0F;
	}

	public double getDouble(String path) {
		try {
			return ((NBTNumber) this.map.get(path)).getDouble();
		} catch (Exception e) {

		}

		return 0.0D;
	}

	public String getString(String path) {
		try {
			return ((NBTTagString) this.map.get(path)).getValue();
		} catch (Exception e) {

		}
		return "";
	}

	public byte[] getByteArray(String path) {
		try {
			return ((NBTTagByteArray) this.map.get(path)).getValue();
		} catch (Exception e) {

		}
		return new byte[0];
	}

	public int[] getIntArray(String path) {
		try {
			return ((NBTTagIntArray) this.map.get(path)).getValue();
		} catch (Exception e) {

		}
		return new int[0];
	}

	public NBTTagCompound getCompound(String path) {
		try {
			return ((NBTTagCompound) this.map.get(path)).getValue();
		} catch (Exception e) {

		}
		return new NBTTagCompound();
	}

	public NBTTagCompound getValue() {
		return this;
	}

	public NBTTagList getList(String path) {
		try {
			return ((NBTTagList) this.map.get(path));
		} catch (Exception e) {
		}
		return new NBTTagList();
	}

	public NBTTagCompound remove(String path) {
		this.map.remove(path);
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
}
