package team.unstudio.udpl.particle;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ParticleWrapper implements Cloneable {

    public static Builder builder() {
        return new Builder();
    }

	private Particle particle;
	private int count;
	private double offsetX = 0;
	private double offsetY = 0;
	private double offsetZ = 0;
	private double extra = 1;
	private Object data = null;

	public ParticleWrapper(Particle particle, int count) {
		this(particle, count, 0, 0, 0, null);
	}

	public ParticleWrapper(Particle particle, int count, Object data) {
		this(particle, count, 0, 0, 0, data);
	}

	public ParticleWrapper(Particle particle, int count, double offsetX, double offsetY, double offsetZ) {
		this(particle, count, offsetX, offsetY, offsetZ, null);
	}

	public ParticleWrapper(Particle particle, int count, double offsetX, double offsetY, double offsetZ, Object data) {
		this(particle, count, offsetX, offsetY, offsetZ, 1, data);
	}

	public ParticleWrapper(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra) {
		this(particle, count, offsetX, offsetY, offsetZ, extra, null);
	}

	public ParticleWrapper(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra,
			Object data) {
		setParticle(particle);
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

	public void spawnParticle(Player player, Location location) {
		spawnParticle(player, location.getX(), location.getY(), location.getZ());
	}

	public void spawnParticle(Player player, double x, double y, double z) {
		player.spawnParticle(getParticle(), x, y, z, getCount(), getOffsetX(), getOffsetY(), getOffsetZ(), getExtra(),
				getData());
	}

	public void spawnParticle(Location location) {
		spawnParticle(location.getWorld(), location.getX(), location.getY(), location.getZ());
	}

	public void spawnParticle(World world, double x, double y, double z) {
		world.spawnParticle(getParticle(), x, y, z, getCount(), getOffsetX(), getOffsetY(), getOffsetZ(), getExtra(),
				getData());
	}

	@Override
	public ParticleWrapper clone() {
		ParticleWrapper particle;
		try {
			particle = (ParticleWrapper) super.clone();
			particle.setParticle(getParticle());
			particle.setCount(getCount());
			particle.setOffsetX(getOffsetX());
			particle.setOffsetY(getOffsetY());
			particle.setOffsetZ(getOffsetZ());
			particle.setExtra(getExtra());
			particle.setData(getData());
            return particle;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	public static class Builder {
        private Particle particle;
        private int count;
        private double offsetX = 0;
        private double offsetY = 0;
        private double offsetZ = 0;
        private double extra = 1;
        private Object data = null;

        public ParticleWrapper build() {
            return new ParticleWrapper(particle, count, offsetX, offsetY, offsetZ, extra, data);
        }

        public Builder particle(Particle particle) {
            this.particle = particle;
            return this;
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder offestX(double offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder offestY(double offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public Builder offestZ(double offsetZ) {
            this.offsetZ = offsetZ;
            return this;
        }

        public Builder offest(double offsetX, double offsetY, double offsetZ) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            return this;
        }

        public Builder extra(double extra) {
            this.extra = extra;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }
    }
}
