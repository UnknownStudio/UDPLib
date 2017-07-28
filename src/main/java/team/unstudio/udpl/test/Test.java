package team.unstudio.udpl.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import team.unstudio.udpl.api.area.Area;
import team.unstudio.udpl.api.area.AreaManager;
import team.unstudio.udpl.api.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.UDPLib;

public class Test {
	
	public static final Test INSTANCE = new Test();

	public static TestConfiguration config;
	public void onEnable(){
		testConfig();
		testArea();
		new AnnoCommandManager("test", UDPLib.getInstance()).addCommand(new AnnoCommand()).registerCommand();
	}
	
	private void testConfig(){
		UDPLib.getInstance().getLogger().info("-----Test Config-----");
		UDPLib.getInstance().saveResource("test.yml", false);
		config = new TestConfiguration();
		config.reload();
		UDPLib.getInstance().getLogger().info(config.test);
		config.test = "Loaded plugin.";
		config.save();
		config.reload();
		UDPLib.getInstance().getLogger().info(config.test);
	}
	
	private void testArea(){
		if(!UDPLib.getUDPLConfig().enableAreaAPI)
			return;
		
		UDPLib.getInstance().getLogger().info("-----Test Area-----");
		Bukkit.getServer().getPluginManager().registerEvents(new AreaListener(), UDPLib.getInstance());
		Area area = new Area(new Location(Bukkit.getWorld("world"), 0, 0, 0), new Location(Bukkit.getWorld("world"), 100, 100, 100));
		if(AreaManager.getAreaManager(area.getWorld()).getAreas(area).size()==0)
			AreaManager.addArea$(area);
	}
}
