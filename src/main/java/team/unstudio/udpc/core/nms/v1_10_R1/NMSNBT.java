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

public class NMSNBT {
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> toMap(NBTTagCompound nbt){
		Map<String,Object> map = new HashMap<>();
		try {
			Field field = nbt.getClass().getField("map");
			field.setAccessible(true);
			Map<String, NBTBase> nmap = (Map<String, NBTBase>) field.get(nbt);
			for(String key:nmap.keySet()){
				map.put(key, toObject(nmap.get(key)));
			}
		} catch (Exception e) {}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> toList(NBTTagList nbt){
		List<Object> list = new ArrayList<>();
		try {
			Field field = nbt.getClass().getField("list");
			field.setAccessible(true);
			List<NBTBase> nlist = (List<NBTBase>) field.get(nbt);
			for(NBTBase base:nlist){
				list.add(toObject(base));
			}
		} catch (Exception e) {}
		return list;
	}
	
	public byte toByte(NBTTagByte nbt){
		return nbt.g();
	}
	
	public short toShort(NBTTagShort nbt){
		return nbt.f();
	}
	
	public int toInt(NBTTagInt nbt){
		return nbt.e();
	}
	
	public long toLong(NBTTagLong nbt){
		return nbt.d();
	}
	
	public float toFloat(NBTTagFloat nbt){
		return nbt.i();
	}
	
	public double toDouble(NBTTagDouble nbt){
		return nbt.h();
	}
	
	public String toString(NBTTagString nbt){
		return nbt.c_();
	}
	
	public byte[] toByteArray(NBTTagByteArray nbt){
		return nbt.c().clone();
	}
	
	public int[] toIntArray(NBTTagIntArray nbt){
		return nbt.d().clone();
	}
	
	public Object toObject(NBTBase nbt){
		switch (nbt.getTypeId()) {
		case 1:
			return toByte((NBTTagByte) nbt);
		case 2:
			return toShort((NBTTagShort) nbt);
		case 3:
			return toInt((NBTTagInt) nbt);
		case 4:
			return toLong((NBTTagLong) nbt);
		case 5:
			return toFloat((NBTTagFloat) nbt);
		case 6:
			return toDouble((NBTTagDouble) nbt);
		case 7:
			return toByteArray((NBTTagByteArray) nbt);
		case 11:
			return toIntArray((NBTTagIntArray) nbt);
		case 8:
			return toString((NBTTagString) nbt);
		case 9:
			return toList((NBTTagList) nbt);
		case 10:
			return toMap((NBTTagCompound) nbt);
		}
		return null;
	}
	
	public NBTBase toNBTBase(Object obj){
		if(obj instanceof Map){
			
		}
	}
}
