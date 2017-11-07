package team.unstudio.udpl.core.test;

import team.unstudio.udpl.area.AreaManager;
import team.unstudio.udpl.command.anno.AnnoCommandManager;
import team.unstudio.udpl.core.UDPLib;

public enum TestLoader {
	INSTANCE;

	public static AreaManager areaManager;
	
	public void onLoad(){
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
}
