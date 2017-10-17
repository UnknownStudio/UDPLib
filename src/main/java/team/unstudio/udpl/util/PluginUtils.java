package team.unstudio.udpl.util;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginUtils {

	private PluginUtils() {}
	
	public static void saveDirectory(@Nonnull JavaPlugin plugin,@Nonnull String resourcePath,boolean replace) throws IOException{
		Validate.notNull(plugin);
		Validate.notEmpty(resourcePath);
		resourcePath = resourcePath.replace('\\', '/');
		
		plugin.getLogger().info("Plugin save directory. Path: " + resourcePath);
		
		URL url = plugin.getClass().getClassLoader().getResource(resourcePath);
		if(url == null)
			throw new IllegalArgumentException("Directory isn't found. Path: "+resourcePath);
		
		JarURLConnection jarConn = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarConn.getJarFile();
		Enumeration<JarEntry> entrys = jarFile.entries();
		while(entrys.hasMoreElements()){
			JarEntry entry = entrys.nextElement();
			if(entry.getName().startsWith(resourcePath)&&!entry.isDirectory())
				plugin.saveResource(entry.getName(), replace);
		}
	}
}
