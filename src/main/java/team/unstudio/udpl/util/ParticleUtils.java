package team.unstudio.udpl.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public interface ParticleUtils {
	
	double DOUBLE_PI = Math.PI * 2;
	
	static void sendCircle(Player player, Location location, Particle particle, int count, double radius, int particleCount) {
		sendCircle(player, location.getX(), location.getY(), location.getZ(), particle, count, radius, particleCount);
	}
	
	static void sendCircle(Player player, double x, double y, double z, Particle particle, int count, double radius, int particleCount) {
		double preAngle = DOUBLE_PI / particleCount;
		for (double angle = 0; angle < DOUBLE_PI; angle += preAngle) {
			
		}
		player.spawnParticle(particle, x, y, z, count, 0, 0, 0);
	}

}
