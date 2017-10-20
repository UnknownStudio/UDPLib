package team.unstudio.udpl.core.test;

import java.io.File;

import team.unstudio.udpl.area.AreaManager;
import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.i18n.I18n;
import team.unstudio.udpl.i18n.SimpleI18n;
import team.unstudio.udpl.util.PluginUtils;

public class TestLoader {
	
	public static final TestLoader INSTANCE = new TestLoader();
	
	public static AreaManager areaManager = new AreaManager(UDPLib.getInstance());
	public static I18n i18n;
	public static TestConfiguration config;
	
	public void onLoad(){
		testConfig();
		UDPLib.getInstance().getLogger().info("Loaded test.");
		
		areaManager.addPlayerEnterAreaCallback((player,area)->player.sendMessage("Entered Area."));
		areaManager.addPlayerLeaveAreaCallback((player,area)->player.sendMessage("Leaved Area."));
	}
	
	public void onEnable(){
		PluginUtils.saveDirectory(UDPLib.getInstance(), "test/lang", false);
		i18n = new SimpleI18n(new File(UDPLib.getInstance().getDataFolder(),"test/lang"));
		new AnnoCommandManager("test", UDPLib.getInstance()).addCommand(new TestCommand()).unsafeRegisterCommand();
		UDPLib.getInstance().getLogger().info("Enable test.");
	}
	
	private void testConfig(){
		UDPLib.getInstance().getLogger().info("[Test]Config");
		UDPLib.getInstance().saveResource("test.yml", false);
		config = new TestConfiguration();
		config.reload();
		UDPLib.getInstance().getLogger().info("[Test]"+config.test);
		config.test = "Loaded plugin.";
		config.save();
		config.reload();
		UDPLib.getInstance().getLogger().info("[Test]"+config.test);
	}
}
