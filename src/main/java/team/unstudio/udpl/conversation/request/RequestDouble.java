package team.unstudio.udpl.conversation.request;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import team.unstudio.udpl.conversation.RequestBase;
import team.unstudio.udpl.util.PluginUtils;

public class RequestDouble extends RequestBase<Double>{
	
	private final Listener listener = new RequestListener();
	
	private Double result;

	@Override
	public void start() {
		PluginUtils.registerEvents(listener, getConversation().getPlugin());
		getConversation().getPlayer().sendMessage(getPrompt());
		setStarted(true);
	}

	@Override
	public void dispose() {
		AsyncPlayerChatEvent.getHandlerList().unregister(listener);
	}

	@Override
	public Optional<Double> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
	
	private class RequestListener implements Listener{
		@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
		public void onChat(AsyncPlayerChatEvent event) {
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			try{
				Double invalidate = Double.valueOf(event.getMessage());
				
				if(!validate(invalidate))
					return;
				
				result = invalidate;
				Bukkit.getScheduler().runTask(getConversation().getPlugin(), () -> setCompleted(true));
			}catch(NumberFormatException e){
				return;
			}
		}
	}

}
