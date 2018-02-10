package team.unstudio.udpl.conversation.request;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import team.unstudio.udpl.util.PluginUtils;

public class RequestPlayer extends RequestBase<Player>{
	
	public static RequestPlayer newRequestPlayer(){
		return new RequestPlayer();
	}
	
	private final Listener listener = new RequestListener();
	
	private Player result;

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
	}

	@Override
	public Optional<Player> getResult() {
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
			
			Player result = Bukkit.getPlayerExact(event.getMessage());
			if(result == null)
				callValidateFailed();
			
			if(!validate(result))
				return;
				
			RequestPlayer.this.result = result;
			Bukkit.getScheduler().runTask(getConversation().getPlugin(), ()->setCompleted(true));
		}
	}
}
