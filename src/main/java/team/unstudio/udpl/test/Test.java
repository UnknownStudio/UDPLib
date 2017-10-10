package team.unstudio.udpl.test;

import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.UDPLib;

public class Test {
	
	public static final Test INSTANCE = new Test();

	public static TestConfiguration config;
	
	public void onLoad(){
	}
	
	public void onEnable(){
		testConfig();
		new AnnoCommandManager("test", UDPLib.getInstance()).addCommand(new TestCommand()).unsafeRegisterCommand();
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
}
