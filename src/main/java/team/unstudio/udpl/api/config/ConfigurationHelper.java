package team.unstudio.udpl.api.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public enum ConfigurationHelper {
	
	;
	
    /**
     * 载入配置文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static YamlConfiguration loadConfiguration(File file) throws IOException {
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

        if (!file.exists()) file.createNewFile();

        return YamlConfiguration.loadConfiguration(file);
    }
}
