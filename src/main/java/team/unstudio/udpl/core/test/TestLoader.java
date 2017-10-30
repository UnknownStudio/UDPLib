package team.unstudio.udpl.core.test;

import team.unstudio.udpl.area.AreaManager;
import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.i18n.I18n;
import team.unstudio.udpl.i18n.SimpleI18n;
import team.unstudio.udpl.util.PluginUtils;

import java.io.File;

public enum  TestLoader {
	INSTANCE;

	public static AreaManager areaManager;
	public static I18n i18n;
	public static TestConfiguration config;
	
	public void onLoad(){
		testConfig();
		
		PluginUtils.saveDirectory(UDPLib.getInstance(), "test/lang", false);
		i18n = new SimpleI18n(new File(UDPLib.getInstance().getDataFolder(),"test/lang"));
		UDPLib.getLog().info("Loaded test.");
	}
	
	public void onEnable(){
		areaManager = new AreaManager(UDPLib.getInstance());
		areaManager.addPlayerEnterAreaCallback((player,area)->player.sendMessage("Entered Area."));
		areaManager.addPlayerLeaveAreaCallback((player,area)->player.sendMessage("Leaved Area."));
		areaManager.setAutoSavePeriod(20*60);
		areaManager.setAutoSave(true);
		areaManager.setAutoBackupPeriod(20*120);
		areaManager.setAutoBackup(true);
		
		new AnnoCommandManager("test", UDPLib.getInstance()).addHandler(new TestCommand()).unsafeRegisterCommand();
		UDPLib.getLog().info("Enabled test.");
	}
	
	private void testConfig(){
		UDPLib.getLog().info("[Test]Config");
		UDPLib.getInstance().saveResource("test.yml", false);
		config = new TestConfiguration();
		config.reload();
		UDPLib.getLog().info("[Test]"+config.test);
		config.test = "Loaded plugin.";
		config.save();
		config.reload();
		UDPLib.getLog().info("[Test]"+config.test);
	}
}
