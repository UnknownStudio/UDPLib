package team.unstudio.udpl.util;

import java.io.File;
import java.net.URL;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginUtils {

	private PluginUtils() {}
	
	public void saveDirectory(JavaPlugin plugin,String resourcePath,boolean replace){
		Validate.notEmpty(resourcePath);
		resourcePath = resourcePath.replace('\\', '/');
		URL url = plugin.getClass().getClassLoader().getResource(resourcePath);
		if(url == null)
			return;
		File path = new File(url.getPath());
		File outPath = new File(plugin.getDataFolder(),resourcePath);
		if(!outPath.exists())
			outPath.mkdirs();
		for (File file : path.listFiles()) {
			if(file.isFile())
				plugin.saveResource(resourcePath+"/"+file.getName(), replace);
			else
				saveDirectory(plugin, resourcePath+"/"+file.getName(), replace);
		}
	}
}
