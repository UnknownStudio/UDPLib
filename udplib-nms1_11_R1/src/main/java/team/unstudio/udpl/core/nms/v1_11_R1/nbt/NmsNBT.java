package team.unstudio.udpl.core.nms.v1_11_R1.nbt;

import net.minecraft.server.v1_11_R1.MojangsonParseException;
import net.minecraft.server.v1_11_R1.MojangsonParser;
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
import team.unstudio.udpl.nms.NmsException;

public class NmsNBT implements team.unstudio.udpl.nms.nbt.NmsNBT{
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagCompound toCompound(Object nbt){
		if(nbt == null)
			return null;
		NBTTagCompound oldNbt = (NBTTagCompound) nbt;
		team.unstudio.udpl.nms.nbt.NBTTagCompound newNbt = new team.unstudio.udpl.nms.nbt.NBTTagCompound();
		for (String key : oldNbt.c())
			newNbt.set(key, toNBTBase(oldNbt.get(key)));
		return newNbt;
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagList toList(Object nbt){
		if(nbt == null)
			return null;
		NBTTagList oldNbt = (NBTTagList) nbt;
		team.unstudio.udpl.nms.nbt.NBTTagList newNbt = new team.unstudio.udpl.nms.nbt.NBTTagList();
		for(int i=0,size = oldNbt.size();i<size;i++)
			newNbt.add(toNBTBase(oldNbt.h(i)));
		return newNbt;
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagByte toByte(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagByte(((NBTTagByte) nbt).g());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagShort toShort(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagShort(((NBTTagShort) nbt).f());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagInt toInt(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagInt(((NBTTagInt) nbt).e());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagLong toLong(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagLong(((NBTTagLong) nbt).d());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagFloat toFloat(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagFloat(((NBTTagFloat) nbt).i());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagDouble toDouble(Object nbt){		
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagDouble(((NBTTagDouble) nbt).asDouble());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagString toString(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagString(((NBTTagString) nbt).c_());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagByteArray toByteArray(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagByteArray(((NBTTagByteArray) nbt).c());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagIntArray toIntArray(Object nbt){
		if(nbt == null)
			return null;
		return new team.unstudio.udpl.nms.nbt.NBTTagIntArray(((NBTTagIntArray) nbt).d());
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTBase toNBTBase(Object nbt){
		if(nbt == null)
			return null;
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
			return toCompound(nbt);
		}
		return null;
	}
	
	@Override
	public NBTBase toNmsNBT(team.unstudio.udpl.nms.nbt.NBTBase nbt){
		if(nbt == null)
			return null;
		switch (nbt.getType()) {
		case BYTE:
			return new NBTTagByte(((team.unstudio.udpl.nms.nbt.NBTTagByte)nbt).getValue());
		case SHORT:
			return new NBTTagShort(((team.unstudio.udpl.nms.nbt.NBTTagShort)nbt).getValue());
		case INTEGER:
			return new NBTTagInt(((team.unstudio.udpl.nms.nbt.NBTTagInt)nbt).getValue());
		case LONG:
			return new NBTTagLong(((team.unstudio.udpl.nms.nbt.NBTTagLong)nbt).getValue());
		case FLOAT:
			return new NBTTagFloat(((team.unstudio.udpl.nms.nbt.NBTTagFloat)nbt).getValue());
		case DOUBLE:
			return new NBTTagDouble(((team.unstudio.udpl.nms.nbt.NBTTagDouble)nbt).getValue());
		case BYTEARRAY:
			return new NBTTagByteArray(((team.unstudio.udpl.nms.nbt.NBTTagByteArray)nbt).getValue());
		case INTARRAY:
			return new NBTTagIntArray(((team.unstudio.udpl.nms.nbt.NBTTagIntArray)nbt).getValue());
		case STRING:
			return new NBTTagString(((team.unstudio.udpl.nms.nbt.NBTTagString)nbt).getValue());
		case LIST:
			NBTTagList nmsList = new NBTTagList();
			for(team.unstudio.udpl.nms.nbt.NBTBase nbtBase:(team.unstudio.udpl.nms.nbt.NBTTagList)nbt)
				nmsList.add(toNmsNBT(nbtBase));
			return nmsList;
		case COMPOUND:
			team.unstudio.udpl.nms.nbt.NBTTagCompound oldNbtMap = (team.unstudio.udpl.nms.nbt.NBTTagCompound)nbt;
			NBTTagCompound nmsMap = new NBTTagCompound();
			for(String key:oldNbtMap.keySet())
				nmsMap.set(key, toNmsNBT(oldNbtMap.get(key)));
			return nmsMap;
		default:
			return null;
		}
	}

	@Override
	public String toNBTJson(team.unstudio.udpl.nms.nbt.NBTTagCompound nbt) {
		return toNmsNBT(nbt).toString();
	}

	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagCompound parseNBTJson(String json) {
		try {
			return toCompound(MojangsonParser.parse(json));
		} catch (MojangsonParseException e) {
			throw new NmsException(e);
		}
	}
}
