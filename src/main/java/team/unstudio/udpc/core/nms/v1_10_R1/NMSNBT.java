package team.unstudio.udpc.core.nms.v1_10_R1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

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
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> toMap(Object nbt){
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
	public List<Object> toList(Object nbt){
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
	
	public byte toByte(Object nbt){
		return ((NBTTagByte) nbt).g();
	}
	
	public short toShort(Object nbt){
		return ((NBTTagShort) nbt).f();
	}
	
	public int toInt(Object nbt){
		return ((NBTTagInt) nbt).e();
	}
	
	public long toLong(Object nbt){
		return ((NBTTagLong) nbt).d();
	}
	
	public float toFloat(Object nbt){
		return ((NBTTagFloat) nbt).i();
	}
	
	public double toDouble(Object nbt){
		return ((NBTTagDouble) nbt).h();
	}
	
	public String toString(Object nbt){
		return ((NBTTagString) nbt).c_();
	}
	
	public byte[] toByteArray(Object nbt){
		return ((NBTTagByteArray) nbt).c().clone();
	}
	
	public int[] toIntArray(Object nbt){
		return ((NBTTagIntArray) nbt).d().clone();
	}
	
	public Object toObject(Object nbt){
		switch (((NBTBase) nbt).getTypeId()) {
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
	
	@SuppressWarnings("unchecked")
	public NBTBase toNBTBase(Object obj){
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
			for(Object o:(List<Object>)obj) nlist.add(toNBTBase(o));
			return nlist;
		}
		else if(obj instanceof Map) {
			NBTTagCompound nmap = new NBTTagCompound();
			for(String key:((Map<String,Object>) obj).keySet()) nmap.set(key, toNBTBase(((Map<String,Object>) obj).get(key)));
			return nmap;
		}
		else return null;
	}
	
	public Map<String, Object> getNBTFromItemStack(ItemStack itemStack) {
		return toMap(CraftItemStack.asNMSCopy(itemStack).getTag());
	}

	public ItemStack setNBTToItemStack(ItemStack itemStack,Map<String,Object> nbt) {
		net.minecraft.server.v1_10_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		nitem.setTag((NBTTagCompound)toNBTBase(nbt));
		return CraftItemStack.asBukkitCopy(nitem);
	}
}
