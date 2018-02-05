package team.unstudio.udpl.conversation.request;

import java.util.Optional;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import team.unstudio.udpl.util.PluginUtils;

public class RequestEntity extends RequestBase<Entity>{
	
	public static RequestEntity newRequestEntity(){
		return new RequestEntity();
	}
	
	private final Listener listener = new RequestListener();
	
	private Entity result;

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
		EntityDamageByEntityEvent.getHandlerList().unregister(listener);
		PlayerInteractEntityEvent.getHandlerList().unregister(listener);
	}

	@Override
	public Optional<Entity> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
	
	private class RequestListener implements Listener{
		
		@EventHandler(priority = EventPriority.LOWEST)
		public void onClick(EntityDamageByEntityEvent event){
			if(!event.getDamager().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			Entity invalidate = event.getEntity();

			if (!validate(invalidate))
				return;

			result = invalidate;
			setCompleted(true);
		}
		
		@EventHandler(priority = EventPriority.LOWEST)
		public void onInteractEntity(PlayerInteractEntityEvent event){
			if(!event.getPlayer().equals(getConversation().getPlayer()))
				return;
			
			event.setCancelled(true);
			
			Entity invalidate = event.getRightClicked();

			if (!validate(invalidate))
				return;

			result = invalidate;
			setCompleted(true);
		}
	}
}
