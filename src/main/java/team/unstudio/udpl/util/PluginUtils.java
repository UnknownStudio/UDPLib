package team.unstudio.udpl.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginUtils {

	private PluginUtils() {}
	
	public static void saveDirectory(@Nonnull JavaPlugin plugin,@Nonnull String resourcePath,boolean replace){
		Validate.notNull(plugin);
		Validate.notEmpty(resourcePath);
		
		plugin.getLogger().info("Plugin save directory. " + resourcePath);
		
		resourcePath = resourcePath.replace('\\', '/');
		URL url = plugin.getClass().getClassLoader().getResource(resourcePath);
		if(url == null)
			throw new IllegalArgumentException("File isn't found. "+resourcePath);
		
		File path = new File(url.getPath());
		if(!path.exists())
			throw new IllegalArgumentException("File isn't found. "+path.getAbsolutePath());
		if(path.isFile()){
			plugin.saveResource(resourcePath, replace);
			return;
		}
		
		File outPath = new File(plugin.getDataFolder(),resourcePath);
		if(!outPath.exists())
			outPath.mkdirs();
		
		File[] files = path.listFiles();
		if(files == null)
			return;
		
		for (File file : files) {
			if(file.isFile())
				plugin.saveResource(resourcePath+"/"+file.getName(), replace);
			else
				saveDirectory(plugin, resourcePath+"/"+file.getName(), replace);
		}
	}
}
