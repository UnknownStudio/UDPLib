package team.unstudio.udpc.api.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 配置文件处理器
 */
public class ConfigurationHandler{

	private final File file;
	
	public ConfigurationHandler(File file) {
		this.file = file;
	}
	
	/**
	 * 重载
	 * @return
	 */
	public boolean reload(){
		try{
			if(!file.getParentFile().exists())file.getParentFile().mkdirs();
			
			if(!file.exists()) file.createNewFile();
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	/**
	 * 保存
	 * @return
	 */
	public boolean save(){
		try{
			if(!file.getParentFile().exists())file.getParentFile().mkdirs();
			
			if(!file.exists()) file.createNewFile();
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	public @interface Load{
		String value();
	}
}
