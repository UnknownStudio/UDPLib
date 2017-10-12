package team.unstudio.udpl.nms.nbt;

import java.util.List;

import com.google.common.collect.Lists;

public final class NBTTagList extends NBTBase {
	private final List<NBTBase> list = Lists.newArrayList();

	public NBTTagList() {
		super(NBTBaseType.LIST);
	}

	public List<NBTBase> getList() {
		return this.list;
	}

	public NBTBase get(int index) {
		return this.list.get(index);
	}

	public NBTTagList add(NBTBase value) {
		this.list.add(value);
		return this;
	}

	public int size() {
		return this.list.size();
	}

	public NBTTagList remove(int index) {
		this.list.remove(index);
		return this;
	}

	public NBTTagList set(int index, NBTBase value) {
		this.list.set(index, value);
		return this;
	}

	public NBTTagList clear() {
		this.list.clear();
		return this;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0,size = list.size(); i < size; ++i) 
			builder.append(list.get(i)).append(',');
		
		if(builder.charAt(builder.length()-1)==',')
			builder.deleteCharAt(builder.length()-1);
		
		return builder.append(']').toString();
	}

}
