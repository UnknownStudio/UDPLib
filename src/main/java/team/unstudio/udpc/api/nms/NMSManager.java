package team.unstudio.udpc.api.nms;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * NMS的工厂类
 * @author AAA
 *
 */
public class NMSManager {

	private static final NMSNBT COMMON_NMSNBT = new team.unstudio.udpc.core.nms.common.NMSNBT();
	private static final NMSNBT INSTANCE_NMSNBT = loadNMSNBT();
	private static final NMSPacket COMMON_NMSPACKET = new team.unstudio.udpc.core.nms.common.NMSPacket();
	private static final NMSPacket INSTANCE_NMSPACKET = loadNMSPacket();
	
	private static NMSNBT loadNMSNBT(){
		switch (ReflectionUtils.PackageType.getServerVersion()) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSNBT();
		default:
			return COMMON_NMSNBT;
		}
	}
	
	private static NMSPacket loadNMSPacket(){
		switch (ReflectionUtils.PackageType.getServerVersion()) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSPacket();
		default:
			return COMMON_NMSPACKET;
		}
	}
	
	public static NMSNBT getNMSNBTCommon(){
		return COMMON_NMSNBT;
	}
	
	/**
	 * 获取NBT的操作类
	 * @return
	 */
	public static NMSNBT getNMSNBT(){
		return INSTANCE_NMSNBT;
	}
	
	/**
	 * 获取NBT的反射操作类
	 * @return
	 */
	public static NMSPacket getNMSPacketCommon(){
		return COMMON_NMSPACKET;
	}
	
	/**
	 * 获取通信包的操作类
	 * @return
	 */
	public static NMSPacket getNMSPacket(){
		return INSTANCE_NMSPACKET;
	}
	
	/**
	 * 创建一个对TileEntity操作的包装对象
	 * @param blockState
	 * @return
	 */
	public static NMSTileEntity createNMSTileEntity(BlockState blockState){
		switch (ReflectionUtils.PackageType.getServerVersion()) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSTileEntity(blockState);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSTileEntity(blockState);
		}
	}
	
	/**
	 * 创建一个对ItemStack操作的包装对象
	 * @param itemstack
	 * @return
	 */
	public static NMSItemStack getNMSItemStack(ItemStack itemstack){
		switch (ReflectionUtils.PackageType.getServerVersion()) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSItemStack();
		default:
			return new team.unstudio.udpc.core.nms.common.NMSItemStack();
		}
	}
	
	/**
	 * 创建一个对Entity操作的包装对象
	 * @param entity
	 * @return
	 */
	public static NMSEntity createNMSEntity(Entity entity){
		switch (ReflectionUtils.PackageType.getServerVersion()) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSEntity(entity);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSEntity(entity);
		}
	}
	
	/**
	 * 创建一个对Player操作的包装对象
	 * @param player
	 * @return
	 */
	public static NMSPlayer createNMSPlayer(Player player){
		switch (ReflectionUtils.PackageType.getServerVersion()) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSPlayer(player);
		default:
			return new team.unstudio.udpc.core.nms.common.NMSPlayer(player);
		}
	}
}
