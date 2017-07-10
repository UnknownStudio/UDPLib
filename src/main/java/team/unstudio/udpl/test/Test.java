package team.unstudio.udpl.test;

import team.unstudio.udpl.api.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.UDPLib;

public class Test {
	
	public static final Test INSTANCE = new Test();

	public void onEnable(){
		new AnnoCommandManager("test", UDPLib.getInstance()).addCommand(new AnnoCommand()).registerCommand();
	}
}
