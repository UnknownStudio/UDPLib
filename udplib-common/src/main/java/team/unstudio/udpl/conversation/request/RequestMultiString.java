package team.unstudio.udpl.conversation.request;

import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.google.common.collect.Sets;

import team.unstudio.udpl.util.PluginUtils;

public class RequestMultiString extends RequestBase<Set<String>>{
	
	public static RequestMultiString newRequestMultiString(@Nonnull String endCommand){
		return new RequestMultiString(endCommand);
	}
	
	private final Listener listener = new RequestListener();
	
	private final Set<String> result = Sets.newLinkedHashSet();
	private final String endCommand;
	
	public RequestMultiString(@Nonnull String endCommand) {
		Validate.notEmpty(endCommand);
		this.endCommand = endCommand;
	}
	
	public String getEndCommand() {
		return endCommand;
	}

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
	public Optional<Set<String>> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
	
	@Override
	public void reset() {
		result.clear();
	}

	private class RequestListener implements Listener{
		@EventHandler(priority = EventPriority.LOWEST)
		public void onChat(AsyncPlayerChatEvent event) {
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			String invalidate = event.getMessage();
			if(invalidate.equals(getEndCommand())){
				if(!validate(result)){
					result.clear();
					return;
				}
				Bukkit.getScheduler().runTask(getConversation().getPlugin(), ()->setCompleted(true));
			}else{
				result.add(invalidate);
			}
		}
		
		@EventHandler(priority = EventPriority.LOWEST)
		public void onCommand(PlayerCommandPreprocessEvent event) {
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			String invalidate = event.getMessage();
			if(invalidate.equals(getEndCommand())){
				if(!validate(result)){
					result.clear();
					return;
				}
				setCompleted(true);
			}else{
				result.add(invalidate);
			}
		}
	}
}
