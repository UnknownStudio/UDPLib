package team.unstudio.udpl.example;

import java.io.File;

import team.unstudio.udpl.config.ConfigItem;
import team.unstudio.udpl.config.ConfigurationHandler;

public class ExampleConfig extends ConfigurationHandler{

	/**
	 * 你可以通过 @ConfigItem 来指定配置文件中对应的键名
	 */
	@ConfigItem("text")
	public String example;

	/**
	 * 该字段没有 @ConfigItem 注解，则将字段名作为配置文件的键
	 *
	 * 该字段的默认值为true
	 */
	public boolean enableExample = true;


	public ExampleConfig(File file) {
		super(file);
		// ConfigurationHandler::reload is used to attempt to set field
		// 在初始化后，不会自动加载，需要手动调用 ConfigurationHandler::reload
		reload();
	}

}
