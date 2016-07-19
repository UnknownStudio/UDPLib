package team.unstudio.udpc.core.nms.v1_10_R1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_10_R1.NBTBase;
import net.minecraft.server.v1_10_R1.NBTTagByte;
import net.minecraft.server.v1_10_R1.NBTTagByteArray;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagDouble;
import net.minecraft.server.v1_10_R1.NBTTagFloat;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import net.minecraft.server.v1_10_R1.NBTTagIntArray;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.NBTTagLong;
import net.minecraft.server.v1_10_R1.NBTTagShort;
import net.minecraft.server.v1_10_R1.NBTTagString;

public class NMSNBT implements team.unstudio.udpc.api.nms.NMSNBT{
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> toMap(Object nbt) throws Exception{
		Map<String,Object> map = new HashMap<>();
			Field field = nbt.getClass().getField("map");
			field.setAccessible(true);
			Map<String, NBTBase> nmap = (Map<String, NBTBase>) field.get(nbt);
			for(String key:nmap.keySet()){
				map.put(key, toObject(nmap.get(key)));
			}
		return map;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> toList(Object nbt) throws Exception{
		List<Object> list = new ArrayList<>();
			Field field = nbt.getClass().getField("list");
			field.setAccessible(true);
			List<NBTBase> nlist = (List<NBTBase>) field.get(nbt);
			for(NBTBase base:nlist){
				list.add(toObject(base));
			}
		return list;
	}
	
	@Override
	public byte toByte(Object nbt){
		return ((NBTTagByte) nbt).g();
	}
	
	@Override
	public short toShort(Object nbt){
		return ((NBTTagShort) nbt).f();
	}
	
	@Override
	public int toInt(Object nbt){
		return ((NBTTagInt) nbt).e();
	}
	
	@Override
	public long toLong(Object nbt){
		return ((NBTTagLong) nbt).d();
	}
	
	@Override
	public float toFloat(Object nbt){
		return ((NBTTagFloat) nbt).i();
	}
	
	@Override
	public double toDouble(Object nbt){
		return ((NBTTagDouble) nbt).h();
	}
	
	@Override
	public String toString(Object nbt){
		return ((NBTTagString) nbt).c_();
	}
	
	@Override
	public byte[] toByteArray(Object nbt){
		return ((NBTTagByteArray) nbt).c().clone();
	}
	
	@Override
	public int[] toIntArray(Object nbt){
		return ((NBTTagIntArray) nbt).d().clone();
	}
	
	@Override
	public Object toObject(Object nbt) throws Exception{
		switch (((NBTBase) nbt).getTypeId()) {
		case 1:
			return toByte(nbt);
		case 2:
			return toShort(nbt);
		case 3:
			return toInt(nbt);
		case 4:
			return toLong(nbt);
		case 5:
			return toFloat(nbt);
		case 6:
			return toDouble(nbt);
		case 7:
			return toByteArray(nbt);
		case 11:
			return toIntArray(nbt);
		case 8:
			return toString(nbt);
		case 9:
			return toList(nbt);
		case 10:
			return toMap(nbt);
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public NBTBase toNBT(Object obj){
		if(obj instanceof Byte)return new NBTTagByte((byte) obj);
		else if (obj instanceof Short)return new NBTTagShort((short) obj);
		else if(obj instanceof Integer)return new NBTTagInt((int) obj);
		else if(obj instanceof Long)return new NBTTagLong((long) obj);
		else if(obj instanceof Float)return new NBTTagFloat((float) obj);
		else if(obj instanceof Double)return new NBTTagDouble((double) obj);
		else if(obj instanceof Byte[])return new NBTTagByteArray((byte[]) obj);
		else if(obj instanceof Integer[])return new NBTTagIntArray((int[]) obj);
		else if(obj instanceof String)return new NBTTagString((String) obj);
		else if(obj instanceof List){
			NBTTagList nlist = new NBTTagList();
			for(Object o:(List<Object>)obj) nlist.add(toNBT(o));
			return nlist;
		}
		else if(obj instanceof Map) {
			NBTTagCompound nmap = new NBTTagCompound();
			for(String key:((Map<String,Object>) obj).keySet()) nmap.set(key, toNBT(((Map<String,Object>) obj).get(key)));
			return nmap;
		}
		else return null;
	}
}
