package team.unstudio.udpl.area;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 区域，一个封装了两个坐标的类。
 * 通过 Area 你可以判断一个坐标是否在该区域，也可以判断两个 Area 之间的关系。
 * 相关使用方法请查看 <a href="https://github.com/UnknownStudio/UDPLib/wiki/%E5%8C%BA%E5%9F%9FAPI-(Area-API)">Github Wiki</a>
 */
public class Area implements ConfigurationSerializable {
    /**
     * 两坐标
     */
    private final Location min;
    private final Location max;

    /**
     * 用于存储该区域数据的 Map，你可以在该 Map 中放入任何你需要的内容。
     * 如：拥有者、价格等。
     */
    private AreaDataContainer data;

    /**
     * 两点必须在同一个世界，且两点坐标将会被重新计算。
     * 构造出来的 minLocation 和 maxLocation 和传入的 point 不一定相同，
     * 但其代表的区域一致。
     *
     * @param min 第一个点
     * @param max 第二个点
     * @throws IllegalArgumentException 坐标在不同世界时
     */
    public Area(@Nonnull Location min, @Nonnull Location max) {
        // 坐标在不同世界
        if (!min.getWorld().equals(max.getWorld()))
            throw new IllegalArgumentException("Area cannot be created between different worlds");

        //Location1 < Location2
        double x1 = min.getX(), x2 = max.getX(), y1 = min.getY(), y2 = max.getY(), z1 = min.getZ(), z2 = max.getZ(), t;
        if (x1 > x2) {
            t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y1 > y2) {
            t = y1;
            y1 = y2;
            y2 = t;
        }
        if (z1 > z2) {
            t = z1;
            z1 = z2;
            z2 = t;
        }
        this.min = new Location(min.getWorld(), x1, y1, z1);
        this.max = new Location(max.getWorld(), x2, y2, z2);
    }

    public final World getWorld() {
        return min.getWorld();
    }

    public final Location getMinLocation() {
        return min;
    }

    public final Location getMaxLocation() {
        return max;
    }

    /**
     * 获取区域数据 Map
     */
    public final AreaDataContainer getData() {
        if (data == null) {
            data = new AreaDataContainer();
        }
        return data;
    }

    /**
     * 判断坐标是否含于该区域
     *
     * @param location 坐标
     */
    public boolean contain(final Location location) {
        if (!getWorld().equals(location.getWorld()))
            return false;

        return location.getX() >= min.getX() && location.getY() >= min.getY() && location.getZ() >= min.getZ() &&
            location.getX() <= max.getX() && location.getY() <= max.getY() && location.getZ() <= max.getZ();
    }

    /**
     * 判断区域是否含于该区域
     *
     * @param other 区域
     */
    public boolean contain(final Area other) {
        if (!getWorld().equals(other.getWorld()))
            return false;

        return contain(other.getMinLocation()) && contain(other.getMaxLocation());
    }

    /**
     * 判断区域是否与该区域相交
     *
     * @param other 区域
     */
    public boolean intersect(final Area other) {
        return max.getX() >= other.min.getX() && max.getY() >= other.min.getY() && min.getZ() >= min.getZ() &&
            min.getX() <= other.max.getX() && min.getY() <= other.max.getY() && min.getZ() <= other.max.getZ();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("==", getClass().getName());
        map.put("point1", min);
        map.put("point2", max);
        if (data != null)
            map.put("data", data);
        return map;
    }

    public static Area deserialize(Map<String, Object> args) {
        Area area = new Area((Location) args.get("point1"), (Location) args.get("point2"));
        if (args.containsKey("data"))
            area.data = (AreaDataContainer) args.get("data");
        return area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return min.equals(area.min) &&
            max.equals(area.max) &&
            data.equals(area.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max, data);
    }

    @Override
    public String toString() {
        return "Area{" +
            "min=" + min +
            ", max=" + max +
            ", data=" + data +
            '}';
    }
}
