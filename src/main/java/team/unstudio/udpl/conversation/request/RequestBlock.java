package team.unstudio.udpl.conversation.request;

import java.util.Optional;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import team.unstudio.udpl.conversation.RequestBase;
import team.unstudio.udpl.util.PluginUtils;

public class RequestBlock extends RequestBase<Block>{
	
	private final Listener listener = new RequestListener();
	
	private Block result;

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
		PlayerInteractEvent.getHandlerList().unregister(listener);
	}

	@Override
	public Optional<Block> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
	
	private class RequestListener implements Listener{

		@EventHandler(priority = EventPriority.LOWEST)
		public void onInteract(PlayerInteractEvent event){
			if(event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			Block invalidate = event.getClickedBlock();

			if (!validate(invalidate))
				return;

			result = invalidate;
			setCompleted(true);
		}
	}
}
