package team.unstudio.udpl.core;

import java.io.File;

import team.unstudio.udpl.api.util.ConfigurationHandler;

public final class UDPLConfiguration extends ConfigurationHandler{
	
	@ConfigItem("enableAreaAPI")
	public boolean enableAreaAPI = false;
	
	public UDPLConfiguration(File file) {
		super(file);
	}
}
