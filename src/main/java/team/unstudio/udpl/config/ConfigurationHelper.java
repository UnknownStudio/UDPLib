package team.unstudio.udpl.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.Nullable;

import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigurationHelper {
	
    /**
     * 载入配置文件
     */
	@Nullable
    static YamlConfiguration loadConfiguration(File file){
        if (!file.getAbsoluteFile().getParentFile().exists()) 
        	file.getAbsoluteFile().getParentFile().mkdirs();

        if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {}
        
		return AutoCharsetYamlConfiguration.loadConfiguration(file);
    }

    /**
     * 创建空的配配置文件
     */
    static YamlConfiguration newConfiguration(){
		return new DecodedYamlConfiguration();
    }
    
    /**
     * 创建空的配配置文件
     */
    static YamlConfiguration newConfiguration(Charset charset){
		return new DecodedYamlConfiguration(charset);
    }
}
