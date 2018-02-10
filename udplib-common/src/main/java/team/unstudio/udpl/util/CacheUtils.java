package team.unstudio.udpl.util;

import com.google.common.collect.Sets;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.UDPLib;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

public final class CacheUtils {

	private static final Set<MapCacheWrapper<Player, ?>> PLAYER_MAP_CACHES = Sets.newHashSet();
	private static final Set<CollectionCacheWrapper<Player>> PLAYER_COLLECTION_CACHES = Sets.newHashSet();

	@Init
	public static void initCacheUtils() {
		Bukkit.getPluginManager().registerEvents(new CacheListener(), UDPLib.getPlugin());
	}

	public static <V> void registerPlayerCache(@Nonnull Map<Player, V> cache) {
		PLAYER_MAP_CACHES.add(new MapCacheWrapper<>(cache, null));
	}

	public static void registerPlayerCache(@Nonnull Collection<Player> cache) {
		PLAYER_COLLECTION_CACHES.add(new CollectionCacheWrapper<>(cache, null));
	}
	
	public static <V> void registerPlayerCache(@Nonnull Map<Player, V> cache, MapRemoveListener<Player, V> listener) {
		PLAYER_MAP_CACHES.add(new MapCacheWrapper<>(cache, listener));
	}

	public static void registerPlayerCache(@Nonnull Collection<Player> cache, CollectionRemoveListener<Player> listener) {
		PLAYER_COLLECTION_CACHES.add(new CollectionCacheWrapper<>(cache, listener));
	}

	public static void unregisterPlayerCache(Map<Player, ?> cache) {
		PLAYER_MAP_CACHES.removeIf(wrapper->cache == wrapper.getCache());
	}

	public static void unregisterPlayerCache(Collection<Player> cache) {
		PLAYER_COLLECTION_CACHES.removeIf(wrapper->cache == wrapper.getCache());
	}

	@FunctionalInterface
	public static interface MapRemoveListener<K, V> {

		void onRemove(K key, V value);
	}
	
	@FunctionalInterface
	public static interface CollectionRemoveListener<E> {

		void onRemove(E element);
	}

	private static class CacheListener implements Listener {

		@EventHandler(priority = EventPriority.MONITOR)
		public void onQuit(PlayerQuitEvent event) {
			remove(event.getPlayer());
		}
		
		@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
		public void onKick(PlayerKickEvent event) {
			remove(event.getPlayer());
		}
		
		private void remove(Player player) {
			PLAYER_MAP_CACHES.forEach(cache -> {
				if (!cache.remove(player))
					PLAYER_MAP_CACHES.remove(cache);
			});
			PLAYER_COLLECTION_CACHES.forEach(cache -> {
				if (!cache.remove(player))
					PLAYER_COLLECTION_CACHES.remove(cache);
			});
		}
	}
	
	private static class MapCacheWrapper<K, V> {

		private final WeakReference<Map<K, V>> cache;
		private final MapRemoveListener<K, V> listener;

		public MapCacheWrapper(Map<K ,V> cache, MapRemoveListener<K, V> listener) {
			Validate.notNull(cache);
			this.cache = new WeakReference<>(cache);
			this.listener = listener;
		}

		public boolean remove(K key) {
			Map<K, V> cache = this.cache.get();
			if (cache == null)
				return false;
			if (!cache.containsKey(key))
				return true;
			V value = cache.get(key);
			cache.remove(key);
			if (listener != null)
				listener.onRemove(key, value);
			return true;
		}
		
		public Map<K, V> getCache() {
			return cache.get();
		}
	}
	
	private static class CollectionCacheWrapper<E> {
		
		private final WeakReference<Collection<E>> cache;
		private final CollectionRemoveListener<E> listener;

		public CollectionCacheWrapper(Collection<E> cache, CollectionRemoveListener<E> listener) {
			Validate.notNull(cache);
			this.cache = new WeakReference<>(cache);
			this.listener = listener;
		}

		public boolean remove(E element) {
			Collection<E> cache = this.cache.get();
			if (cache == null)
				return false;
			if (!cache.remove(element))
				return true;
			if (listener != null)
				listener.onRemove(element);
			return true;
		}
		
		public Collection<E> getCache() {
			return cache.get();
		}
	}
}
