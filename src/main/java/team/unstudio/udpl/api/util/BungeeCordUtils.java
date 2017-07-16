package team.unstudio.udpl.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import team.unstudio.udpl.core.UDPLib;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class BungeeCordUtils {
    private static BungeeCordUtils instance;
    private static String nowServer;

    public static BungeeCordUtils getInstance() {
        return instance;
    }

    private BungeeCordUtils() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(UDPLib.getInstance(), "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(UDPLib.getInstance(), "BungeeCord", new InfoInComeListener());
        Bukkit.getMessenger().registerIncomingPluginChannel(UDPLib.getInstance(), "UDPLib", new ForwardListener());
    }

    private static void init() {
        instance = new BungeeCordUtils();
    }

    /**
     * 将玩家连接到服务器
     *
     * @param player
     * @param server
     */
    public void sendPlayerToServer(Player player, String server) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(UDPLib.getInstance(), "BungeeCord", bytes.toByteArray());
    }

    /**
     * 发送自定义的数据
     *
     * @param player 发送者
     * @param server 服务器
     */
    public void sendCustomData(Player player, String server, byte[] data) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);

        try {
            out.writeUTF("Forward");
            out.writeUTF(server);
            out.writeUTF("UDPLib");

            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(UDPLib.getInstance(), "BungeeCord", bytes.toByteArray());
    }

    public byte[] stringArrayByteArray(String... strings) {
        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            for (String string : strings) {
                msgout.writeUTF(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msgbytes.toByteArray();
    }

    class ForwardListener implements PluginMessageListener {
        @Override
        public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

        }
    }

    class InfoInComeListener implements PluginMessageListener {
        @Override
        public void onPluginMessageReceived(String s, Player player, byte[] bytes) {

        }
    }

    /**
     * 全局坐标，因为 server, world 都不安全，所以使用时需要确保可用性
     */
    static class ServerLocation implements ConfigurationSerializable {
        private String server;
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
                return this.server == null || !this.server.equals(other.server) ? false : this.world == null || !this.world.equals(other.world) ? false : (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x) ? false : (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y) ? false : (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z) ? false : (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch) ? false : Float.floatToIntBits(this.yaw) == Float.floatToIntBits(other.yaw)))));
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

        public Location toLocation() {
            World world = Bukkit.getWorld(getWorld());
            if (world == null) {
                throw new IllegalArgumentException("unknown world");
            } else {
                return new Location(world, x, y, z, yaw, pitch);
            }
        }

        @Override
        public Map<String, Object> serialize() {
            Map<String, Object> data = new HashMap();
            data.put("server", this.server);
            data.put("world", this.world);
            data.put("x", Double.valueOf(this.x));
            data.put("y", Double.valueOf(this.y));
            data.put("z", Double.valueOf(this.z));
            data.put("yaw", Float.valueOf(this.yaw));
            data.put("pitch", Float.valueOf(this.pitch));
            return data;
        }

        public static ServerLocation deserialize(Map<String, Object> args) {
            return new ServerLocation((String) args.get("server"), (String) args.get("world"), NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")), NumberConversions.toFloat(args.get("yaw")), NumberConversions.toFloat(args.get("pitch")));
        }
    }
}
