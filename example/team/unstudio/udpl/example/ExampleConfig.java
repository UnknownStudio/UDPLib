package team.unstudio.udpl.example;

import java.io.File;

import team.unstudio.udpl.config.ConfigurationHandler;
import team.unstudio.udpl.config.serialization.Setting;

public class ExampleConfig extends ConfigurationHandler{
	
	@Setting("text") // 配置文件的键
	public String example;
	
	// 如果没有@Setting注解，就将字段名作为配置文件的键
	public boolean enableExample = true; // 设置字段的默认值为true

	public ExampleConfig(File file) {
		super(file);
	}

}
