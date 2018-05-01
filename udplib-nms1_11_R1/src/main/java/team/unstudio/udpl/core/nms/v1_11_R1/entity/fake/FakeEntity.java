package team.unstudio.udpl.core.nms.v1_11_R1.entity.fake;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

import team.unstudio.udpl.util.reflect.ReflectionUtils;

public abstract class FakeEntity {
	
	private static int nextEntityId = Integer.MAX_VALUE;
	
	private int entityId;
	private UUID uuid;
	
	private final Set<Player> observers;
	private final Set<Player> unmodifiableOvservers;
	
	public FakeEntity() {
		observers = Sets.newHashSet();
		unmodifiableOvservers = Collections.unmodifiableSet(observers);
		
		entityId = getNewEntityId();
		uuid = UUID.randomUUID();
	}
	
	private int getNewEntityId(){
		try {
			int entityId = (int) ReflectionUtils.getValue(null, ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Entity"),true, "entityCount");
			ReflectionUtils.setValue(null, ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Entity"),true, "entityCount", entityId-1);
			return entityId;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
				| ClassNotFoundException e) {
			return nextEntityId--;
		}
	}
	
	public int getEntityId() {
		return entityId;
	}
	
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Set<Player> getObservers() {
		return unmodifiableOvservers;
	}
}
