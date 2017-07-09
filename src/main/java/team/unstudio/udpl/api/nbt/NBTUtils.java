package team.unstudio.udpl.api.nbt;

public interface NBTUtils {
	
	public static NBTTagCompound loadNBTTagCompoundFromJson(String json){
		return null;
	}
	
	public static NBTTagList loadNBTTagListFromJson(String json){
		return null;
	}
	
	/**
	 * @throws NumberFormatException
	 */
	public static NBTNumber loadNBTNumber(String value){
		return new NBTTagDouble(Double.valueOf(value));
	}
}
