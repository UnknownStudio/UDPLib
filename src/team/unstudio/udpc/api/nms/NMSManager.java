package team.unstudio.udpc.api.nms;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NMSManager {

	private static final NMSNBT COMMON_NMSNBT = new team.unstudio.udpc.core.nms.common.NMSNBT();
	private static final NMSNBT INSTANCE_NMSNBT = loadNMSNBT();
	private static final NMSPacket COMMON_NMSPACKET = new team.unstudio.udpc.core.nms.common.NMSPacket();
	private static final NMSPacket INSTANCE_NMSPACKET = loadNMSPacket();
	
	private static NMSNBT loadNMSNBT(){
		switch (ReflectionUtils.NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSNBT();
		default:
			return COMMON_NMSNBT;
		}
	}
	
	private static NMSPacket loadNMSPacket(){
		switch (ReflectionUtils.NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSPacket();
		default:
			return COMMON_NMSPACKET;
		}
	}
	
	public static NMSNBT getNMSNBTCommon(){
		return COMMON_NMSNBT;
	}
	
	public static NMSNBT getNMSNBT(){
		return INSTANCE_NMSNBT;
	}
	
	public static NMSPacket getNMSPacketCommon(){
		return COMMON_NMSPACKET;
	}
	
	public static NMSPacket getNMSPacket(){
		return INSTANCE_NMSPACKET;
	}
	
	public static NMSTileEntity createNMSTileEntity(BlockState blockState){
		switch (ReflectionUtils.NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSTileEntity(blockState);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSTileEntity(blockState);
		}
	}
	
	public static NMSItemStack getNMSItemStack(){
		switch (ReflectionUtils.NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSItemStack();
		default:
			return new team.unstudio.udpc.core.nms.common.NMSItemStack();
		}
	}
	
	public static NMSEntity createNMSEntity(Entity entity){
		switch (ReflectionUtils.NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSEntity(entity);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSEntity(entity);
		}
	}
	
	public static NMSPlayer createNMSPlayer(Player player){
		switch (ReflectionUtils.NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSPlayer(player);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSPlayer(player);
		}
	}
}
