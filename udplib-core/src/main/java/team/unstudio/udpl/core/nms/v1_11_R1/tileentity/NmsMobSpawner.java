package team.unstudio.udpl.core.nms.v1_11_R1.tileentity;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.block.CreatureSpawner;

import net.minecraft.server.v1_11_R1.MobSpawnerAbstract;
import net.minecraft.server.v1_11_R1.MobSpawnerData;
import net.minecraft.server.v1_11_R1.TileEntityMobSpawner;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.NmsException;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;
import team.unstudio.udpl.nms.nbt.NBTTagList;
import team.unstudio.udpl.nms.nbt.NmsNBT;

public class NmsMobSpawner extends NmsTileEntity implements team.unstudio.udpl.nms.tileentity.NmsMobSpawner{
	
	private static Field mobs;
	private static Field spawnData;
	private static Field minSpawnDelay;
	private static Field maxSpawnDelay;
	private static Field spawnCount;
	private static Field maxNearbyEntities;
	private static Field requiredPlayerRange;
	private static Field spawnRange;
	
	static{
		try {
			mobs = MobSpawnerAbstract.class.getDeclaredField("mobs");
			mobs.setAccessible(true);
			spawnData = MobSpawnerAbstract.class.getDeclaredField("spawnData");
			spawnData.setAccessible(true);
			minSpawnDelay = MobSpawnerAbstract.class.getDeclaredField("minSpawnDelay");
			minSpawnDelay.setAccessible(true);
			maxSpawnDelay = MobSpawnerAbstract.class.getDeclaredField("maxSpawnDelay");
			maxSpawnDelay.setAccessible(true);
			spawnCount = MobSpawnerAbstract.class.getDeclaredField("spawnCount");
			spawnCount.setAccessible(true);
			maxNearbyEntities = MobSpawnerAbstract.class.getDeclaredField("maxNearbyEntities");
			maxNearbyEntities.setAccessible(true);
			requiredPlayerRange = MobSpawnerAbstract.class.getDeclaredField("requiredPlayerRange");
			requiredPlayerRange.setAccessible(true);
			spawnRange = MobSpawnerAbstract.class.getDeclaredField("spawnRange");
			spawnRange.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			UDPLib.debug(e);
		}
	}

	public NmsMobSpawner(CreatureSpawner spawner) {
		super(spawner);
	}
	
	@Override
	public CreatureSpawner getBukkitBlockState() {
		return (CreatureSpawner) super.getBukkitBlockState();
	}

	@Override
	protected TileEntityMobSpawner getTileEntity() {
		return (TileEntityMobSpawner) super.getTileEntity();
	}

	@SuppressWarnings("unchecked")
	@Override
	public NBTTagList getSpawnEntities() {
		NBTTagList spawnEntitiesNbt = new NBTTagList();
		try {
			((List<MobSpawnerData>) mobs.get(getTileEntity().getSpawner())).stream()
			.map(data->NmsNBT.getInstance().toCompound(data.a()))
			.forEach(spawnEntitiesNbt::add);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
		return spawnEntitiesNbt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addSpawnEntities(NBTTagCompound nbt, int weight) {
		try {
			((List<MobSpawnerData>) mobs.get(getTileEntity().getSpawner())).add(new MobSpawnerData(weight, (net.minecraft.server.v1_11_R1.NBTTagCompound) NmsNBT.getInstance().toNmsNBT(nbt)));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public NBTTagCompound getSpawnEntity() {
		try {
			return NmsNBT.getInstance().toCompound(((MobSpawnerData) spawnData.get(getTileEntity().getSpawner())).b());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public void setSpawnEntity(NBTTagCompound nbt) {
		getTileEntity().getSpawner().a(new MobSpawnerData((net.minecraft.server.v1_11_R1.NBTTagCompound) NmsNBT.getInstance().toNmsNBT(nbt)));
	}

	@Override
	public short getSpawnCount() {
		try {
			return (short) spawnCount.getInt(getTileEntity().getSpawner());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public void setSpawnCount(short count) {
		try {
			spawnCount.set(getTileEntity().getSpawner(), count);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public short getSpawnRange() {
		try {
			return (short) spawnRange.getInt(getTileEntity().getSpawner());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public void setSpawnRange(short range) {
		try {
			spawnRange.set(getTileEntity().getSpawner(), range);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public short getDelay() {
		return (short) getTileEntity().getSpawner().spawnDelay;
	}

	@Override
	public void setDelay(short delay) {
		getTileEntity().getSpawner().spawnDelay = delay;
	}

	@Override
	public short getMinSpawnDelay() {
		try {
			return (short) minSpawnDelay.getInt(getTileEntity().getSpawner());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public void setMinSpawnDelay(short minDelay) {
		try {
			maxSpawnDelay.set(getTileEntity().getSpawner(), minDelay);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public short getMaxSpawnDelay() {
		try {
			return (short) maxSpawnDelay.getInt(getTileEntity().getSpawner());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public void setMaxSpawnDelay(short maxDelay) {
		try {
			maxSpawnDelay.set(getTileEntity().getSpawner(), maxDelay);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public short getMaxNearbyEntities() {
		try {
			return (short) maxNearbyEntities.getInt(getTileEntity().getSpawner());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public void setMaxNearbyEntities(short value) {
		try {
			maxNearbyEntities.set(getTileEntity().getSpawner(), value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public short getRequiredPlayerRange() {
		try {
			return (short) requiredPlayerRange.getInt(getTileEntity().getSpawner());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}

	@Override
	public void setRequiredPlayerRange(short range) {
		try {
			requiredPlayerRange.set(getTileEntity().getSpawner(), range);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new NmsException(e);
		}
	}
}
