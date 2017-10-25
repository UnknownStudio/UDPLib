package team.unstudio.udpl.util;

import com.google.common.collect.Sets;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import team.unstudio.udpl.core.UDPLib;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface CacheUtils {
	
	Set<Map<Player,?>> PLAYER_MAP_CACHES = Sets.newHashSet();
	Set<Collection<Player>> PLAYER_COLLECTION_CACHES = Sets.newHashSet();
	
	static void initCacheUtils(){
		Bukkit.getPluginManager().registerEvents(new CacheListener(), UDPLib.getInstance());
	}
	
	static void registerPlayerCache(@Nonnull Map<Player, ?> cache){
		Validate.notNull(cache);
		PLAYER_MAP_CACHES.add(cache);
	}
	
	static void registerPlayerCache(@Nonnull Collection<Player> cache){
		Validate.notNull(cache);
		PLAYER_COLLECTION_CACHES.add(cache);
	}
	
	static void unregisterPlayerCache(@Nonnull Map<Player, ?> cache){
		Validate.notNull(cache);
		PLAYER_MAP_CACHES.remove(cache);
	}
	
	static void unregisterPlayerCache(@Nonnull Collection<Player> cache){
		Validate.notNull(cache);
		PLAYER_COLLECTION_CACHES.remove(cache);
	}
	
	class CacheListener implements Listener{
		
		@EventHandler(priority=EventPriority.MONITOR)
		public void onQuit(PlayerQuitEvent event){
			Player player = event.getPlayer();
			PLAYER_MAP_CACHES.forEach(cache->cache.remove(player));
			PLAYER_COLLECTION_CACHES.forEach(cache->cache.remove(player));
		}
	}
}
