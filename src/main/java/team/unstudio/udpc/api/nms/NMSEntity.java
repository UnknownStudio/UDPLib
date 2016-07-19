package team.unstudio.udpc.api.nms;

import java.util.Map;

import org.bukkit.entity.Entity;

public interface NMSEntity {

	public Entity getEntity();
	
	public Map<String, Object> getNBT() throws Exception;

	public NMSEntity setNBT(Map<String, Object> map) throws Exception;
}
