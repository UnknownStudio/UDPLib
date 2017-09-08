package team.unstudio.udpl.nbt;

public final class NBTTagByteArray extends NBTBase {
	private byte[] value;

	public NBTTagByteArray(byte[] value) {
		super(NBTBaseType.BYTEARRAY);
		this.value = value;
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

}
