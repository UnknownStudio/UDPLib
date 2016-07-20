package team.unstudio.udpc.api.nms;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class NMSManager {
	
	public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	private static final NMSNBT COMMON_NMSNBT = new team.unstudio.udpc.core.nms.common.NMSNBT();
	private static final NMSNBT INSTANCE_NMSNBT = loadNMSNBT();
	
	private static NMSNBT loadNMSNBT(){
		switch (NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSNBT();
		default:
			return getNMSNBTCommon();
		}
	}
	
	public static NMSNBT getNMSNBTCommon(){
		return COMMON_NMSNBT;
	}
	
	public static NMSNBT getNMSNBT(){
		return INSTANCE_NMSNBT;
	}
	
	public static NMSTileEntity createNMSTileEntity(BlockState blockState){
		switch (NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSTileEntity(blockState);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSTileEntity(blockState);
		}
	}
	
	public static NMSItemStack createNMSItemStack(ItemStack itemStack){
		switch (NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSItemStack();
		default:
			return new team.unstudio.udpc.core.nms.common.NMSItemStack();
		}
	}
	
	public static NMSEntity createNMSEntity(Entity entity){
		switch (NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSEntity(entity);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSEntity(entity);
		}
	}
}
