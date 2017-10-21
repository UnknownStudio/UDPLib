package team.unstudio.udpl.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.Sets;

import team.unstudio.udpl.core.UDPLib;

public final class CacheUtils {
	
	private CacheUtils(){}
	
	private static Set<Map<Player,?>> PLAYER_MAP_CACHES = Sets.newHashSet();
	private static Set<Collection<Player>> PLAYER_COLLECTION_CACHES = Sets.newHashSet();
	
	public static void initCacheUtils(){
		Bukkit.getPluginManager().registerEvents(new CacheListener(), UDPLib.getInstance());
	}
	
	public static void registerPlayerCache(@Nonnull Map<Player,?> cache){
		Validate.notNull(cache);
		PLAYER_MAP_CACHES.add(cache);
	}
	
	public static void registerPlayerCache(@Nonnull Collection<Player> cache){
		Validate.notNull(cache);
		PLAYER_COLLECTION_CACHES.add(cache);
	}
	
	public static void unregisterPlayerCache(@Nonnull Map<Player,?> cache){
		Validate.notNull(cache);
		PLAYER_MAP_CACHES.remove(cache);
	}
	
	public static void unregisterPlayerCache(@Nonnull Collection<Player> cache){
		Validate.notNull(cache);
		PLAYER_COLLECTION_CACHES.remove(cache);
	}
	
	private static class CacheListener implements Listener{
		
		@EventHandler(priority=EventPriority.MONITOR)
		public void onQuit(PlayerQuitEvent event){
			Player player = event.getPlayer();
			PLAYER_MAP_CACHES.forEach(cache->cache.remove(player));
			PLAYER_COLLECTION_CACHES.forEach(cache->cache.remove(player));
		}
	}
}
