package team.unstudio.udpl.area;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import team.unstudio.udpl.area.function.PlayerEnterAreaCallback;
import team.unstudio.udpl.area.function.PlayerLeaveAreaCallback;
import team.unstudio.udpl.util.Chunk;

public class AreaManager {
	
	private final JavaPlugin plugin;
	private final AreaListener listener;
	private final File areaPath;
	private final Map<World,WorldAreaManager> managers = Maps.newHashMap();
	private final List<PlayerEnterAreaCallback> playerEnterAreaCallbacks = Lists.newArrayList();
	private final List<PlayerLeaveAreaCallback> playerLeaveAreaCallbacks = Lists.newArrayList();
	
	public AreaManager(JavaPlugin plugin) {
		this(plugin,new File(plugin.getDataFolder(),"area"));
	}
	
	public AreaManager(JavaPlugin plugin,String areaPath) {
		this(plugin,new File(plugin.getDataFolder(),areaPath));
	}
	
	public AreaManager(JavaPlugin plugin,File areaPath) {
		this.plugin = plugin;
		this.areaPath = areaPath;
		this.listener = new AreaListener(this);
		Bukkit.getPluginManager().registerEvents(listener, plugin);
		loadAll();
	}
	
	public JavaPlugin getPlugin() {
		return plugin;
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
		managers.clear();
		for(World world:Bukkit.getWorlds()){
			WorldAreaManager a = new WorldAreaManager(world,areaPath);
			a.load();
			managers.put(world, a);
		}
	}
	
	public void saveAll(){
		for(WorldAreaManager a:managers.values()) 
			a.save();
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
}
