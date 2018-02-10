package team.unstudio.udpl.bungeecord;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局坐标，因为 server, world 都不安全，所以使用时需要确保可用性
 */
public class ServerLocation implements ConfigurationSerializable {
    /**
     * the name of the server
     */
    private String server;

    /**
     * world name
     */
    private String world;

    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public ServerLocation(String server, String world, double x, double y, double z, float pitch, float yaw) {
        this.server = server;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public ServerLocation(String server, String world, double x, double y, double z) {
        this(server, world, x, y, z, 0.0f, 0.0f);
    }

    public String getServer() {
        return server;
    }
    public String getWorld() {
        return world;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public float getPitch() {
        return pitch;
    }
    public float getYaw() {
        return yaw;
    }

    public void setServer(String server) {
        this.server = server;
    }
    public void setWorld(String world) {
        this.world = world;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setZ(double z) {
        this.z = z;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public ServerLocation add(ServerLocation vec) {
        if (vec != null && vec.getServer().equals(this.getServer()) && vec.getWorld().equals(this.getWorld())) {
            this.x += vec.x;
            this.y += vec.y;
            this.z += vec.z;
            return this;
        } else {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
    }
    public ServerLocation add(Vector vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        return this;
    }
    public ServerLocation add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public ServerLocation subtract(ServerLocation vec) {
        if (vec != null && vec.getServer().equals(this.getServer()) && vec.getWorld().equals(this.getWorld())) {
            this.x -= vec.x;
            this.y -= vec.y;
            this.z -= vec.z;
            return this;
        } else {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
    }
    public ServerLocation subtract(Vector vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        return this;
    }
    public ServerLocation subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            ServerLocation other = (ServerLocation) obj;
            return this.server != null && this.server.equals(other.server) && (this.world != null && this.world.equals(other.world) && (Double.doubleToLongBits(this.x) == Double.doubleToLongBits(other.x) && (Double.doubleToLongBits(this.y) == Double.doubleToLongBits(other.y) && (Double.doubleToLongBits(this.z) == Double.doubleToLongBits(other.z) && (Float.floatToIntBits(this.pitch) == Float.floatToIntBits(other.pitch) && Float.floatToIntBits(this.yaw) == Float.floatToIntBits(other.yaw))))));
        }
    }

    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.server != null ? this.server.hashCode() : 0);
        hash = 19 * hash + (this.world != null ? this.world.hashCode() : 0);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
        hash = 19 * hash + Float.floatToIntBits(this.pitch);
        hash = 19 * hash + Float.floatToIntBits(this.yaw);
        return hash;
    }

    public String toString() {
        return "ServerLocation{server=" + this.server + ",world=" + this.world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",pitch=" + this.pitch + ",yaw=" + this.yaw + '}';
    }

    /**
     * convert to bukkit location with {@link Bukkit#getWorld(String)}
     * @throws IllegalArgumentException if couldn't find world
     */
    public Location toLocation() {
        World world = Bukkit.getWorld(getWorld());
        if (world == null) {
            throw new IllegalArgumentException("unknown world");
        } else {
            return new Location(world, x, y, z, yaw, pitch);
        }
    }

    /**
     * convert to bukkit location with provided world
     */
    public Location toLocation(World world) {
        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * serializer
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("==", getClass().getName());
        data.put("server", this.server);
        data.put("world", this.world);
        data.put("x", this.x);
        data.put("y", this.y);
        data.put("z", this.z);
        data.put("yaw", this.yaw);
        data.put("pitch", this.pitch);
        return data;
    }

    /**
     * deserializer
     */
    public static ServerLocation deserialize(Map<String, Object> args) {
        return new ServerLocation((String) args.get("server"), (String) args.get("world"), NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")), NumberConversions.toFloat(args.get("yaw")), NumberConversions.toFloat(args.get("pitch")));
    }

    public static final Pattern deserializePattern = Pattern.compile("ServerLocation\\{server=(?<server>[a-zA-Z0-9-_]+),world=(?<world>[a-zA-Z0-9/-_]+),x=(?<x>[0-9.-]+),y=(?<y>[0-9.-]+),z=(?<z>[0-9.-]+),pitch=(?<pitch>[0-9.-]+),yaw=(?<yaw>[0-9.-]+)}");

    /**
     * deserializer
     */
    public static ServerLocation deserialize(String str) {
        Matcher matcher = deserializePattern.matcher(str);
        if (matcher.find()) {
            String server = matcher.group("server");
            String world = matcher.group("world");
            double x = Double.parseDouble(matcher.group("x"));
            double y = Double.parseDouble(matcher.group("y"));
            double z = Double.parseDouble(matcher.group("z"));
            float pitch = Float.parseFloat(matcher.group("pitch"));
            float yaw = Float.parseFloat(matcher.group("yaw"));

            return new ServerLocation(server, world, x, y, z, pitch, yaw);
        }
        throw new IllegalArgumentException("Cannot deserialize " + str + " as an ServerLocation");
    }
}
