package team.unstudio.udpc.api.nms;

import java.util.Map;

import org.bukkit.block.BlockState;

public interface NMSTileEntity {

	public BlockState getBlockState();
	
	public Map<String, Object> getNBT() throws Exception;

	public NMSTileEntity setNBT(Map<String, Object> map) throws Exception;
}
