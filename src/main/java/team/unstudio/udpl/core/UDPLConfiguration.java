package team.unstudio.udpl.core;

import java.io.File;

import team.unstudio.udpl.config.ConfigurationHandler;

public final class UDPLConfiguration extends ConfigurationHandler{
	
	@ConfigItem("enableAreaAPI")
	public boolean enableAreaAPI = false;
	
	@ConfigItem("enableTest")
	public boolean enableTest = false;
	
	public UDPLConfiguration(File file) {
		super(file);
	}
}
