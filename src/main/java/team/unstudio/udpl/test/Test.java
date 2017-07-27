package team.unstudio.udpl.test;

import team.unstudio.udpl.api.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.UDPLib;

public class Test {
	
	public static final Test INSTANCE = new Test();

	public static TestConfiguration config;
	public void onEnable(){
		testConfig();
		new AnnoCommandManager("test", UDPLib.getInstance()).addCommand(new AnnoCommand()).registerCommand();
	}
	
	private void testConfig(){
		UDPLib.getInstance().saveResource("test.yml", false);
		config = new TestConfiguration();
		config.reload();
		System.out.println(config.test);
		config.test = "Loaded plugin.";
		config.save();
		config.reload();
		System.out.println(config.test);
	}
}
