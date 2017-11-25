package team.unstudio.udpl.area;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import team.unstudio.udpl.area.function.PlayerEnterAreaCallback;
import team.unstudio.udpl.area.function.PlayerLeaveAreaCallback;
import team.unstudio.udpl.core.UDPLI18n;
import team.unstudio.udpl.util.Chunk;
import team.unstudio.udpl.util.ZipUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 区域管理器，对 WorldAreaManager 的封装。
 * 通过该管理器，你可以监听玩家进出区域和更好的管理；
 * 相关使用方法请查看 <a href="https://github.com/UnknownStudio/UDPLib/wiki/%E5%8C%BA%E5%9F%9FAPI-(Area-API)">Github Wiki</a>
 */
public class AreaManager {
	/**
	 * 插件实例，用于注册监听器等
	 */
	private final JavaPlugin plugin;
	/**
	 * Bukkit 监听器，用于监听玩家位置
	 */
	private final AreaListener listener;
	/**
	 * 区域数据文件路径
	 */
	private final File areaPath;

	/**
	 * 对不同世界的区域管理器
	 */
	private final Map<World,WorldAreaManager> managers = Maps.newHashMap();
	/**
	 * 玩家进入区域的回调
	 */
	private final List<PlayerEnterAreaCallback> playerEnterAreaCallbacks = Lists.newArrayList();
	/**
	 * 玩家离开区域的回调
	 */
	private final List<PlayerLeaveAreaCallback> playerLeaveAreaCallbacks = Lists.newArrayList();
	
	/**
	 * 是否自动保存区域到配置文件
	 */
	private boolean autoSave = false;
	/**
	 * 自动保存区域的周期，单位为 ticks
	 */
	private long autoSavePeriod = 10*60*20;
	/**
	 * 自动保存区域的线程
	 */
	private BukkitRunnable autoSaveTask;

	/**
	 * 备份文件的路径
	 */
	private File backupPath;
	/**
	 * 是否自动备份区域
	 */
	private boolean autoBackup = false;
	/**
	 * 自动备份区域的周期，单位为 ticks
	 */
	private long autoBackupPeriod = 6*60*60*20;
	/**
	 * 自动备份区域的线程
	 */
	private BukkitRunnable autoBackupTask;

	/**
	 * 是否正在保存或者备份
	 */
	protected final AtomicBoolean isSavingOrBackuping = new AtomicBoolean(false);

	/**
	 * 构造区域管理器，文件为插件默认数据文件夹下的 "area" 文件夹
	 */
	public AreaManager(@Nonnull JavaPlugin plugin) {
		this(plugin,new File(plugin.getDataFolder(),"area"));
	}
	
	public AreaManager(@Nonnull JavaPlugin plugin,@Nonnull String areaPath) {
		this(plugin,new File(plugin.getDataFolder(),areaPath));
	}
	
	public AreaManager(@Nonnull JavaPlugin plugin,@Nonnull File areaPath) {
		Validate.notNull(plugin);
		Validate.notNull(areaPath);
		this.plugin = plugin;
		this.areaPath = areaPath;
		this.listener = new AreaListener(this);
		Bukkit.getPluginManager().registerEvents(listener, plugin);
		loadAll();
	}
	
	public JavaPlugin getPlugin() {
		return plugin;
	}
	
	public File getAreaPath() {
		return areaPath;
	}

	public WorldAreaManager getWorldAreaManager(World world){
		if(managers.containsKey(world)){
			return managers.get(world);
		}else{
			WorldAreaManager manager = new WorldAreaManager(world,areaPath);
			managers.put(world, manager);
			return manager;
		}
	}
	
	public WorldAreaManager getWorldAreaManager(String world){
		return getWorldAreaManager(Bukkit.getWorld(world));
	}
	
	public void addArea(Area area){
		getWorldAreaManager(area.getWorld()).addArea(area);
	}
	
	public void removeArea(Area area){
		getWorldAreaManager(area.getWorld()).removeArea(area);
	}
	
	public boolean containArea(Area area){
		return getWorldAreaManager(area.getWorld()).containArea(area);
	}
	
	public boolean hasArea(Location location){
		return getWorldAreaManager(location.getWorld()).hasArea(location);
	}
	
	public List<Area> getAreas(Location location){
		return getWorldAreaManager(location.getWorld()).getAreas(location);
	}
	
	public List<Area> getAreas(Chunk chunk){
		return getWorldAreaManager(chunk.getWorld()).getAreas(chunk);
	}
	
	public List<Area> getAreas(Area area){
		return getWorldAreaManager(area.getWorld()).getAreas(area);
	}
	
	public void loadAll(){
		plugin.getLogger().info(UDPLI18n.format("debug.area.load"));
		managers.clear();
		for(World world:Bukkit.getWorlds()){
			WorldAreaManager a = new WorldAreaManager(world,areaPath);
			a.load();
			managers.put(world, a);
		}
		plugin.getLogger().info(UDPLI18n.format("debug.area.loaded", managers.size()));
	}
	
	public void saveAll(){
		plugin.getLogger().info(UDPLI18n.format("debug.area.save"));
		for(WorldAreaManager a:managers.values()) 
			a.save();
		plugin.getLogger().info(UDPLI18n.format("debug.area.saved", managers.size()));
	}
	
	public synchronized void backupAll(){
		plugin.getLogger().info(UDPLI18n.format("debug.area.backup"));
		File backupPath = this.backupPath != null ? this.backupPath : new File(areaPath, "backup");
		if(!backupPath.exists())
			backupPath.mkdirs();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String date = dateFormat.format(new Date());
		File backupFile = new File(backupPath,"backup-"+date+".zip");
		File[] files = (File[]) ArrayUtils.nullToEmpty(areaPath.listFiles((dir, name)->name.endsWith(".yml")));
		ZipUtils.zip(backupFile, files);
		plugin.getLogger().info(UDPLI18n.format("debug.area.backuped", files.length));
	}
	
	public void addPlayerEnterAreaCallback(PlayerEnterAreaCallback callback){
		playerEnterAreaCallbacks.add(callback);
	}
	
	public void removePlayerEnterAreaCallback(PlayerEnterAreaCallback callback){
		playerEnterAreaCallbacks.remove(callback);
	}
	
	public void addPlayerLeaveAreaCallback(PlayerLeaveAreaCallback callback){
		playerLeaveAreaCallbacks.add(callback);
	}
	
	public void removePlayerLeaveAreaCallback(PlayerLeaveAreaCallback callback){
		playerLeaveAreaCallbacks.remove(callback);
	}
	
	public void callPlayerEnterArea(Player player,Area area){
		playerEnterAreaCallbacks.forEach(callback->callback.accept(player, area));
	}
	
	public void callPlayerLeaveArea(Player player,Area area){
		playerLeaveAreaCallbacks.forEach(callback->callback.accept(player, area));
	}
	
	public boolean isAutoSave() {
		return autoSave;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
		updateAutoSaveTask();
	}
	
	public long getAutoSavePeriod() {
		return autoSavePeriod;
	}

	/**
	 * 设置自动保存周期
	 * @param autoSavePeriod 单位为tick，1s=20tick.
	 */
	public void setAutoSavePeriod(long autoSavePeriod) {
		this.autoSavePeriod = autoSavePeriod;
		updateAutoSaveTask();
	}
	
	private void updateAutoSaveTask(){
		if(autoSave)
			startNewAutoSaveTask();
		else
			stopAutoSaveTask();
	}
	
	private void startNewAutoSaveTask(){
		stopAutoSaveTask();
		autoSaveTask = new AutoSave();
		autoSaveTask.runTaskTimer(getPlugin(), getAutoSavePeriod(), getAutoSavePeriod());
	}
	
	private void stopAutoSaveTask(){
		if(autoSaveTask!=null)
			autoSaveTask.cancel();
	}

	public boolean isAutoBackup() {
		return autoBackup;
	}

	public void setAutoBackup(boolean autoBackup) {
		this.autoBackup = autoBackup;
		updateAutoBackupTask();
	}

	public long getAutoBackupPeriod() {
		return autoBackupPeriod;
	}

	/**
	 * 设置自动备份周期
	 * @param autoBackupPeriod 单位为tick，1s=20tick.
	 */
	public void setAutoBackupPeriod(long autoBackupPeriod) {
		this.autoBackupPeriod = autoBackupPeriod;
		updateAutoBackupTask();
	}
	
	private void updateAutoBackupTask(){
		if(autoBackup)
			startNewAutoBackupTask();
		else
			stopAutoBackupTask();
	}
	
	private void startNewAutoBackupTask(){
		stopAutoBackupTask();
		autoBackupTask = new AutoBackup();
		autoBackupTask.runTaskTimerAsynchronously(getPlugin(), getAutoBackupPeriod(), getAutoBackupPeriod());
	}
	
	private void stopAutoBackupTask(){
		if(autoBackupTask!=null)
			autoBackupTask.cancel();
	}

	public File getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(File backupPath) {
		this.backupPath = backupPath;
	}

	private class AutoSave extends BukkitRunnable{

		@Override
		public void run() {
			if(isSavingOrBackuping.compareAndSet(false, true)){
				saveAll();
				isSavingOrBackuping.set(false);
			}
		}
	}
	
	private class AutoBackup extends BukkitRunnable{

		@Override
		public void run() {
			while(!isSavingOrBackuping.compareAndSet(false, true)){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
			}
			backupAll();
			isSavingOrBackuping.set(false);
		}
	}
}
