package team.unstudio.udpl.core.nms.v1_11_R1.entity;

import java.util.Locale;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_11_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class NmsPlayer extends NmsEntity implements team.unstudio.udpl.nms.entity.NmsPlayer{
	
	private final Player player;

	public NmsPlayer(Player player) {
		super(player);
		this.player = player;
	}

	@Override
	public Player getBukkitEntity() {
		return player;
	}
	
	@Override
	public Locale getLocale() {
		return Locale.forLanguageTag(normalizeLanguageTag(((CraftPlayer)player).getHandle().locale));
	}
	
	private String normalizeLanguageTag(String languageTag){
		languageTag = languageTag.replaceAll("_", "-");
		int first = languageTag.indexOf("-"), second = languageTag.indexOf("-", first+1);
		if(first == -1)
			return languageTag;
		else if(second == -1){
			return languageTag.substring(0, first+1) + languageTag.substring(first+1).toUpperCase();
		}else{
			return languageTag.substring(0, first+1) + languageTag.substring(first+1,second).toUpperCase() + languageTag.substring(second);
		}
	}
	
	@Override
	public void sendPacket(Object packet){
		if(!(packet instanceof Packet))
			throw new IllegalArgumentException("Parameter packet isn't net.minecraft.server.Packet");
		((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
	}
	
	@Override
	public void sendTitle(String title, String subtitle) {
		if (title != null) {
			PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
					CraftChatMessage.fromString(title)[0]);
			sendPacket(packetTitle);
		}

		if (subtitle != null) {
			PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
					CraftChatMessage.fromString(subtitle)[0]);
			sendPacket(packetSubtitle);
		}
	}

	@Override
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
		sendPacket(packetplayoutworldparticles);
	}
	
	@Override
	public void sendActionBar(String message){
	    sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte)2));
	}
}
