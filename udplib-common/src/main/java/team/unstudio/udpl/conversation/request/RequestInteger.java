package team.unstudio.udpl.conversation.request;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import team.unstudio.udpl.util.PluginUtils;

import java.util.Optional;

public class RequestInteger extends RequestBase<Integer>{
	
	public static RequestInteger newRequestInteger(){
		return new RequestInteger();
	}
	
	public static RequestInteger create(){
		return new RequestInteger();
	}
	
	private final Listener listener = new RequestListener();
	
	private Integer result;

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
	public void reset() {
		result = null;
	}

	@Override
	public Optional<Integer> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
	
	private class RequestListener implements Listener{
		@EventHandler(priority = EventPriority.LOWEST)
		public void onChat(AsyncPlayerChatEvent event) {
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			try{
				Integer invalidate = Integer.valueOf(event.getMessage());
				
				if(!validate(invalidate))
					return;
				
				result = invalidate;
				Bukkit.getScheduler().runTask(getConversation().getPlugin(), () -> setCompleted(true));
			}catch(NumberFormatException ignored){
			}
		}
		
		@EventHandler(priority = EventPriority.LOWEST)
		public void onCommand(PlayerCommandPreprocessEvent event) {
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			try{
				Integer invalidate = Integer.valueOf(event.getMessage());
				
				if(!validate(invalidate))
					return;
				
				result = invalidate;
				setCompleted(true);
			}catch(NumberFormatException ignored){
			}
		}
	}
}
