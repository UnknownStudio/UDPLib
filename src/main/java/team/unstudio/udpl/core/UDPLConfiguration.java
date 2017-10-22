package team.unstudio.udpl.core;

import team.unstudio.udpl.config.ConfigurationHandler;

import java.io.File;

public final class UDPLConfiguration extends ConfigurationHandler{
	@ConfigItem("enableTest")
	public boolean enableTest = false;
	
	@ConfigItem("debug")
	public boolean debug = false;

	@ConfigItem("lang")
	public String lang = "";
	
	public UDPLConfiguration(File file) {
		super(file);
	}
}
