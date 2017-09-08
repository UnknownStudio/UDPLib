package team.unstudio.udpl.core.nms.v1_11_R1;

import net.minecraft.server.v1_11_R1.NBTBase;
import net.minecraft.server.v1_11_R1.NBTTagByte;
import net.minecraft.server.v1_11_R1.NBTTagByteArray;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagDouble;
import net.minecraft.server.v1_11_R1.NBTTagFloat;
import net.minecraft.server.v1_11_R1.NBTTagInt;
import net.minecraft.server.v1_11_R1.NBTTagIntArray;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagLong;
import net.minecraft.server.v1_11_R1.NBTTagShort;
import net.minecraft.server.v1_11_R1.NBTTagString;

public class NMSNBT implements team.unstudio.udpl.nms.NMSNBT{
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagCompound toMap(Object nbt) throws Exception{
		NBTTagCompound oldNbt = (NBTTagCompound) nbt;
		team.unstudio.udpl.nbt.NBTTagCompound newNbt = new team.unstudio.udpl.nbt.NBTTagCompound();
		for (String key : oldNbt.c())
			newNbt.set(key, toNBTBase(oldNbt.get(key)));
		return newNbt;
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagList toList(Object nbt) throws Exception{
		NBTTagList oldNbt = (NBTTagList) nbt;
		team.unstudio.udpl.nbt.NBTTagList newNbt = new team.unstudio.udpl.nbt.NBTTagList();
		for(int i=0,size = oldNbt.size();i<size;i++)
			newNbt.add(toNBTBase(oldNbt.h(i)));
		return newNbt;
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagByte toByte(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagByte(((NBTTagByte) nbt).g());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagShort toShort(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagShort(((NBTTagShort) nbt).f());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagInt toInt(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagInt(((NBTTagInt) nbt).e());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagLong toLong(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagLong(((NBTTagLong) nbt).d());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagFloat toFloat(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagFloat(((NBTTagFloat) nbt).i());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagDouble toDouble(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagDouble(((NBTTagDouble) nbt).asDouble());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagString toString(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagString(((NBTTagString) nbt).c_());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagByteArray toByteArray(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagByteArray(((NBTTagByteArray) nbt).c());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagIntArray toIntArray(Object nbt){
		return new team.unstudio.udpl.nbt.NBTTagIntArray(((NBTTagIntArray) nbt).d());
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTBase toNBTBase(Object nbt) throws Exception{
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
	public NBTBase toNBT(team.unstudio.udpl.nbt.NBTBase nbt){
		switch (nbt.getType()) {
		case BYTE:
			return new NBTTagByte(((team.unstudio.udpl.nbt.NBTTagByte)nbt).getValue());
		case SHORT:
			return new NBTTagShort(((team.unstudio.udpl.nbt.NBTTagShort)nbt).getValue());
		case INTEGER:
			return new NBTTagInt(((team.unstudio.udpl.nbt.NBTTagInt)nbt).getValue());
		case LONG:
			return new NBTTagLong(((team.unstudio.udpl.nbt.NBTTagLong)nbt).getValue());
		case FLOAT:
			return new NBTTagFloat(((team.unstudio.udpl.nbt.NBTTagFloat)nbt).getValue());
		case DOUBLE:
			return new NBTTagDouble(((team.unstudio.udpl.nbt.NBTTagDouble)nbt).getValue());
		case BYTEARRAY:
			return new NBTTagByteArray(((team.unstudio.udpl.nbt.NBTTagByteArray)nbt).getValue());
		case INTARRAY:
			return new NBTTagIntArray(((team.unstudio.udpl.nbt.NBTTagIntArray)nbt).getValue());
		case STRING:
			return new NBTTagString(((team.unstudio.udpl.nbt.NBTTagString)nbt).getValue());
		case LIST:
			NBTTagList nmsList = new NBTTagList();
			for(team.unstudio.udpl.nbt.NBTBase nbtBase:((team.unstudio.udpl.nbt.NBTTagList)nbt).getList())
				nmsList.add(toNBT(nbtBase));
			return nmsList;
		case COMPOUND:
			team.unstudio.udpl.nbt.NBTTagCompound oldNbtMap = (team.unstudio.udpl.nbt.NBTTagCompound)nbt;
			NBTTagCompound nmsMap = new NBTTagCompound();
			for(String key:oldNbtMap.keySet())
				nmsMap.set(key, toNBT(oldNbtMap.get(key)));
			return nmsMap;
		case END:
			return null;
		}
		return null;
	}
}
