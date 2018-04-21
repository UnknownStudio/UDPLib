package team.unstudio.udpl.conversation.request;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import team.unstudio.udpl.util.PluginUtils;

public class RequestConfirm extends RequestBase<Boolean>{
	
	public static RequestConfirm newRequestConfirm(){
		return new RequestConfirm();
	}
	
	private final Listener listener = new RequestListener();
	
	private boolean result;

	@Override
	public void start() {
		super.start();
		PluginUtils.registerEvents(listener, getConversation().getPlugin());
		setStarted(true);
		sendPrompt();
	}

	@Override
	public void dispose() {
		super.dispose();
		AsyncPlayerChatEvent.getHandlerList().unregister(listener);
		PlayerCommandPreprocessEvent.getHandlerList().unregister(listener);
	}

	@Override
	public Optional<Boolean> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
	
	@Override
	public void reset() {
		result = false;
	}
	
	private class RequestListener implements Listener{
		@EventHandler(priority = EventPriority.LOWEST)
		public void onChat(AsyncPlayerChatEvent event) {
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			result = "confirm".equals(event.getMessage().toLowerCase());
			Bukkit.getScheduler().runTask(getConversation().getPlugin(), ()->setCompleted(true));
		}
		
		@EventHandler(priority = EventPriority.LOWEST)
		public void onCommand(PlayerCommandPreprocessEvent event) {
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			result = "/confirm".equals(event.getMessage().toLowerCase());
			setCompleted(true);
		}
	}
}
