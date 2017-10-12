package team.unstudio.udpl.nms.nbt;

public final class NBTTagIntArray extends NBTBase {
	private int[] value;

	public NBTTagIntArray(int[] value) {
		super(NBTBaseType.INTARRAY);
		this.value = value;
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
}
