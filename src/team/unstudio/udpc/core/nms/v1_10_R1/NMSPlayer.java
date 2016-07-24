package team.unstudio.udpc.core.nms.v1_10_R1;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_10_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.block.CraftSign;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_10_R1.map.CraftMapView;
import org.bukkit.craftbukkit.v1_10_R1.map.RenderData;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.MapIcon;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_10_R1.PacketPlayOutMap;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_10_R1.TileEntitySign;

public class NMSPlayer extends NMSEntity implements team.unstudio.udpc.api.nms.NMSPlayer{
	
	private final Player player;

	public NMSPlayer(Player player) {
		super(player);
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void sendPacket(Object packet){
		if(!(packet instanceof Packet))return;
		((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
	}
	
	@SuppressWarnings("deprecation")
	public void sendBlockChange(Location loc, Material material, byte data) {
		sendBlockChange(loc, material.getId(), data);
	}

	@SuppressWarnings("deprecation")
	public void sendBlockChange(Location loc, int material, byte data) {
		if (((CraftPlayer) player).getHandle().playerConnection == null)
			return;

		PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) loc.getWorld()).getHandle(),
				new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));

		packet.block = CraftMagicNumbers.getBlock(material).fromLegacyData(data);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public void sendSignChange(Location loc, String[] lines) {
		if (((CraftPlayer) player).getHandle().playerConnection == null) {
			return;
		}

		if (lines == null) {
			lines = new String[4];
		}

		Validate.notNull(loc, "Location can not be null");
		if (lines.length < 4) {
			throw new IllegalArgumentException("Must have at least 4 lines");
		}

		IChatBaseComponent[] components = CraftSign.sanitizeLines(lines);
		TileEntitySign sign = new TileEntitySign();
		sign.setPosition(new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
		System.arraycopy(components, 0, sign.lines, 0, sign.lines.length);

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(sign.getUpdatePacket());
	}

	@SuppressWarnings("deprecation")
	public void sendMap(MapView map) {
		if (((CraftPlayer) player).getHandle().playerConnection == null)
			return;

		RenderData data = ((CraftMapView) map).render(((CraftPlayer) player));
		Collection<MapIcon> icons = new ArrayList<>();
		for (MapCursor cursor : data.cursors) {
			if (cursor.isVisible()) {
				icons.add(new MapIcon(cursor.getRawType(), cursor.getX(), cursor.getY(), cursor.getDirection()));
			}
		}

		PacketPlayOutMap packet = new PacketPlayOutMap(map.getId(), map.getScale().getValue(), true, icons, data.buffer,
				0, 0, 0, 0);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public void sendTitle(String title, String subtitle) {
		if (title != null) {
			PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
					CraftChatMessage.fromString(title)[0]);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetTitle);
		}

		if (subtitle != null) {
			PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
					CraftChatMessage.fromString(subtitle)[0]);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetSubtitle);
		}
	}

	public void resetTitle() {
		PacketPlayOutTitle packetReset = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, null);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetReset);
	}
	
	public void spawnParticle(Particle particle, Location location, int count) {
		spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count);
	}

	public void spawnParticle(Particle particle, double x, double y, double z, int count) {
		spawnParticle(particle, x, y, z, count, null);
	}

	public <T> void spawnParticle(Particle particle, Location location, int count, T data) {
		spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, data);
	}

	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data) {
		spawnParticle(particle, x, y, z, count, 0.0D, 0.0D, 0.0D, data);
	}

	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ) {
		spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
	}

	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ) {
		spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
	}

	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, T data) {
		spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ,
				data);
	}

	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, T data) {
		spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1.0D, data);
	}

	public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra) {
		spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ,
				extra);
	}

	public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra) {
		spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
	}

	public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra, T data) {
		spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ,
				extra, data);
	}

	public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX,
			double offsetY, double offsetZ, double extra, T data) {
		if ((data != null) && (!particle.getDataType().isInstance(data))) {
			throw new IllegalArgumentException("data should be " + particle.getDataType() + " got " + data.getClass());
		}
		PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(
				CraftParticle.toNMS(particle), true, (float) x, (float) y, (float) z, (float) offsetX, (float) offsetY,
				(float) offsetZ, (float) extra, count, CraftParticle.toData(particle, data));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetplayoutworldparticles);
	}
}
