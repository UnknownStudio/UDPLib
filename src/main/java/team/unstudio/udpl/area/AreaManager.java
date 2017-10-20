package team.unstudio.udpl.area;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import team.unstudio.udpl.area.function.PlayerEnterAreaCallback;
import team.unstudio.udpl.area.function.PlayerLeaveAreaCallback;
import team.unstudio.udpl.util.Chunk;
import team.unstudio.udpl.util.ZipUtils;

public class AreaManager {
	
	private final JavaPlugin plugin;
	private final AreaListener listener;
	private final File areaPath;

	private final Map<World,WorldAreaManager> managers = Maps.newHashMap();
	private final List<PlayerEnterAreaCallback> playerEnterAreaCallbacks = Lists.newArrayList();
	private final List<PlayerLeaveAreaCallback> playerLeaveAreaCallbacks = Lists.newArrayList();
	
	private boolean autoSave = false;
	private long autoSavePeriod = 10*60*20;
	private BukkitRunnable autoSaveTask;
	
	private File backupPath;
	private boolean autoBackup = false;
	private long autoBackupPeriod = 6*60*60*20;
	private BukkitRunnable autoBackupTask;
	
	protected final AtomicBoolean isSavingOrBackuping = new AtomicBoolean(false);
	
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
		plugin.getLogger().info(String.format("[%s]Loading areas...", plugin.getName()));
		managers.clear();
		for(World world:Bukkit.getWorlds()){
			WorldAreaManager a = new WorldAreaManager(world,areaPath);
			a.load();
			managers.put(world, a);
		}
		plugin.getLogger().info(String.format("[%s]Loaded areas.", plugin.getName()));
	}
	
	public void saveAll(){
		plugin.getLogger().info(String.format("[%s]Saving areas...", plugin.getName()));
		for(WorldAreaManager a:managers.values()) 
			a.save();
		plugin.getLogger().info(String.format("[%s]Saved areas.", plugin.getName()));
	}
	
	public synchronized void backupAll(){
		File backupPath = this.backupPath != null ? this.backupPath : new File(areaPath, "backup");
		if(!backupPath.exists())
			backupPath.mkdirs();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String date = dateFormat.format(new Date());
		File backupFile = new File(backupPath,"backup-"+date+".zip");
		File[] files = areaPath.listFiles((dir,name)->name.endsWith(".yml"));
		ZipUtils.zip(backupFile, files);
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
		playerEnterAreaCallbacks.forEach(callback->callback.apply(player, area));
	}
	
	public void callPlayerLeaveArea(Player player,Area area){
		playerLeaveAreaCallbacks.forEach(callback->callback.apply(player, area));
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
