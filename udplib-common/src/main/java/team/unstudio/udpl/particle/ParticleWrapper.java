package team.unstudio.udpl.particle;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ParticleWrapper implements Cloneable {

	private Particle particle;
	private double x;
	private double y;
	private double z;
	private int count;
	private double offsetX = 0;
	private double offsetY = 0;
	private double offsetZ = 0;
	private double extra = 1;
	private Object data = null;

	public ParticleWrapper(Particle particle, Location location, int count) {
		this(particle, location.getX(), location.getY(), location.getZ(), count);
	}

	public ParticleWrapper(Particle particle, double x, double y, double z, int count) {
		this(particle, x, y, z, count, 0, 0, 0, null);
	}

	public ParticleWrapper(Particle particle, Location location, int count, Object data) {
		this(particle, location.getX(), location.getY(), location.getZ(), count, data);
	}

	public ParticleWrapper(Particle particle, double x, double y, double z, int count, Object data) {
		this(particle, x, y, z, count, 0, 0, 0, data);
	}

	public ParticleWrapper(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ) {
		this(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
	}

	public ParticleWrapper(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY,
			double offsetZ) {
		this(particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
	}

	public ParticleWrapper(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, Object data) {
		this(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);
	}

	public ParticleWrapper(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY,
			double offsetZ, Object data) {
		this(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1, data);
	}

	public ParticleWrapper(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra) {
		this(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
	}

	public ParticleWrapper(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY,
			double offsetZ, double extra) {
		this(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
	}

	public ParticleWrapper(Particle particle, Location location, int count, double offsetX, double offsetY,
			double offsetZ, double extra, Object data) {
		this(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra,
				data);
	}

	public ParticleWrapper(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY,
			double offsetZ, double extra, Object data) {
		setParticle(particle);
		setX(x);
		setY(y);
		setZ(z);
		setCount(count);
		setOffsetX(offsetX);
		setOffsetY(offsetY);
		setOffsetZ(offsetZ);
		setExtra(extra);
		setData(data);
	}

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = Objects.requireNonNull(particle);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setLocation(Location location) {
		setX(location.getX());
		setY(location.getY());
		setZ(location.getZ());
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(double offsetX) {
		this.offsetX = offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(double offsetY) {
		this.offsetY = offsetY;
	}

	public double getOffsetZ() {
		return offsetZ;
	}

	public void setOffsetZ(double offsetZ) {
		this.offsetZ = offsetZ;
	}

	public double getExtra() {
		return extra;
	}

	public void setExtra(double extra) {
		this.extra = extra;
	}

	public Object getData() {
		return data;
	}

	public <T> void setData(T data) {
		if (data != null && !getParticle().getDataType().isInstance(data)) {
			throw new IllegalArgumentException("data should be " + particle.getDataType() + " got " + data.getClass());
		}
		this.data = data;
	}

	public void spawnParticle(Player player) {
		player.spawnParticle(getParticle(), getX(), getY(), getZ(), getCount(), getOffsetX(), getOffsetY(),
				getOffsetZ(), getExtra(), getData());
	}

	public void spawnParticle(World world) {
		world.spawnParticle(getParticle(), getX(), getY(), getZ(), getCount(), getOffsetX(), getOffsetY(), getOffsetZ(),
				getExtra(), getData());
	}
	
	@Override
	public ParticleWrapper clone() {
		ParticleWrapper particle = null;
		try {
			particle = (ParticleWrapper) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
		return particle;
	}
}
