package team.unstudio.udpc.core.nms.v1_10_R1;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_10_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;

public class NMSPacket {

	public Object createPacketPlayOutBlockChange(Location loc, int material, byte data){
		return new PacketPlayOutBlockChange(((CraftWorld) loc.getWorld()).getHandle(),new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
	}
	
	public Object createPacketPlayOutWorldParticles(Particle particle, Location location, int count) {
		return createPacketPlayOutWorldParticles(particle, location.getX(), location.getY(), location.getZ(), count);
	}

	public Object createPacketPlayOutWorldParticles(Particle particle, double x, double y, double z, int count) {
		return createPacketPlayOutWorldParticles(particle, x, y, z, count, null);
	}

	public <T> Object createPacketPlayOutWorldParticles(Particle particle, Location location, int count, T data) {
		return createPacketPlayOutWorldParticles(particle, location.getX(), location.getY(), location.getZ(), count, data);
	}

	public <T> Object createPacketPlayOutWorldParticles(Particle particle, double x, double y, double z, int count, T data) {
		return createPacketPlayOutWorldParticles(particle, x, y, z, count, 0.0D, 0.0D, 0.0D, data);
	}

	public Object createPacketPlayOutWorldParticles(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ) {
		return createPacketPlayOutWorldParticles(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
	}

	public Object createPacketPlayOutWorldParticles(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ) {
		return createPacketPlayOutWorldParticles(particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
	}

	public <T> Object createPacketPlayOutWorldParticles(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, T data) {
		return createPacketPlayOutWorldParticles(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ,
				data);
	}

	public <T> Object createPacketPlayOutWorldParticles(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, T data) {
		return createPacketPlayOutWorldParticles(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1.0D, data);
	}

	public Object createPacketPlayOutWorldParticles(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra) {
		return createPacketPlayOutWorldParticles(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ,
				extra);
	}

	public Object createPacketPlayOutWorldParticles(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra) {
		return createPacketPlayOutWorldParticles(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
	}

	public <T> Object createPacketPlayOutWorldParticles(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra, T data) {
		return createPacketPlayOutWorldParticles(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ,
				extra, data);
	}
	
	public <T> Object createPacketPlayOutWorldParticles(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
		if ((data != null) && (!particle.getDataType().isInstance(data))) {
			throw new IllegalArgumentException("data should be " + particle.getDataType() + " got " + data.getClass());
		}
		return new PacketPlayOutWorldParticles(CraftParticle.toNMS(particle), true, (float) x, (float) y, (float) z, (float) offsetX, (float) offsetY,(float) offsetZ, (float) extra, count, CraftParticle.toData(particle, data));
	}
}
