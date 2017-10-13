package team.unstudio.udpl.nms.nbt;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public final class NBTTagList extends NBTBase implements Iterable<NBTBase>{
	private final List<NBTBase> list = Lists.newLinkedList();

	public NBTTagList() {
		super(NBTBaseType.LIST);
	}
	
	public NBTTagList(Collection<NBTBase> list) {
		this();
		this.list.addAll(list);
	}
	
	public NBTTagList(NBTBase... bases){
		this();
		Collections.addAll(list, bases);
	}

	public NBTBase get(int index) {
		return this.list.get(index);
	}

	public NBTTagList add(NBTBase value) {
		if(value.getType() != NBTBaseType.END)
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
	
	public Iterator<NBTBase> iterator(){
		return list.iterator();
	}
	
	public Stream<NBTBase> stream(){
		return list.stream();
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0,size = list.size(); i < size; ++i) 
			builder.append(list.get(i)).append(',');
		
		if(builder.charAt(builder.length()-1)==',')
			builder.deleteCharAt(builder.length()-1);
		
		return builder.append(']').toString();
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("==", getClass().getName());
		map.put("value", this.list);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static NBTTagList deserialize(Map<String, Object> map){
		return new NBTTagList((List<NBTBase>) map.get("value"));
	}
}
