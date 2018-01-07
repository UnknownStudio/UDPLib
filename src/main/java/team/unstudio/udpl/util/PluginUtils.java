package team.unstudio.udpl.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public interface PluginUtils {
	
	static void saveDirectory(@Nonnull JavaPlugin plugin, @Nonnull String resourcePath, boolean replace){
		Validate.notNull(plugin);
		Validate.notEmpty(resourcePath);
		resourcePath = resourcePath.replace('\\', '/');
		
		plugin.getLogger().info("Plugin save directory. Path: " + resourcePath);
		
		URL url = plugin.getClass().getClassLoader().getResource(resourcePath);
		if(url == null)
			throw new IllegalArgumentException("Directory isn't found. Path: "+resourcePath);
		
		JarURLConnection jarConn;
		try {
			jarConn = (JarURLConnection) url.openConnection();
			JarFile jarFile = jarConn.getJarFile();
			Enumeration<JarEntry> entrys = jarFile.entries();
			while(entrys.hasMoreElements()){
				JarEntry entry = entrys.nextElement();
				if(entry.getName().startsWith(resourcePath)&&!entry.isDirectory())
					plugin.saveResource(entry.getName(), replace);
			}
		} catch (IOException e) {
			plugin.getLogger().warning("Plugin save directory failed. Path: " + resourcePath);
			e.printStackTrace();
		}
	}
	
	static void registerEvents(Listener listener, Plugin plugin){
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}
	
	/**
	 * Call event.
	 */
	static <T extends Event> T callEvent(T event) {
		Bukkit.getPluginManager().callEvent(event);
		return event;
	}

	/**
	 * Call event.
	 * 
	 * @param event
	 * @return <code>event.isCancelled()</code> or <code>false</code>
	 */
	static boolean callEventIsCancelled(Event event) {
		Bukkit.getPluginManager().callEvent(event);
		return event instanceof Cancellable ? ((Cancellable) event).isCancelled() : false;
	}
}
