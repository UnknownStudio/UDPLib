package team.unstudio.udpl.conversation;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Lists;

import team.unstudio.udpl.util.PluginUtils;

public class Conversation {

	private final Plugin plugin;
	private final Player player;
	
	private final List<Request<?>> requests = Lists.newLinkedList();
	
	private final Listener listener = new ConversationListener();
	
	private ConversationState state = ConversationState.UNSTARTED;
	
	private Request<?> currentRequest;
	private int currentRequestIndex = 0;
	
	private Consumer<Conversation> onCancel;
	private Consumer<Conversation> onComplete;
	
	public Conversation(@Nonnull Plugin plugin,@Nonnull Player player) {
		Validate.notNull(plugin);
		Validate.notNull(player);
		this.plugin = plugin;
		this.player = player;
	}
	
	@Nonnull
	public final Plugin getPlugin() {
		return plugin;
	}

	@Nonnull
	public final Player getPlayer() {
		return player;
	}
	
	public void addRequest(Request<?> request){
		request.setConversation(this);
		requests.add(request);
	}
	
	public Request<?> getRequest(int index){
		return requests.get(index);
	}
	
	public Request<?> getCurrentRequest(){
		return currentRequest;
	}
	
	public void start(){
		if(isStarted())
			return;
		
		if(requests.isEmpty())
			return;
		
		if(!player.isOnline())
			return;
		
		state = ConversationState.STARTED;
		
		PluginUtils.registerEvents(listener, getPlugin());
		
		currentRequestIndex = 0;
		currentRequest = requests.get(currentRequestIndex);
		currentRequest.start();
	}
	
	public void cancel(){
		if(isStopped())
			return;
		
		state = ConversationState.CANCELLED;
		
		if(currentRequest != null && !currentRequest.isCompleted())
			currentRequest.dispose();
		
		dispose();
		getOnCancel().accept(this);
	}
	
	public void next(){
		if(isStopped())
			return;
		
		if(!isStarted())
			return;
		
		if(!currentRequest.isCompleted())
			return;
		
		if(++currentRequestIndex >= requests.size()){
			dispose();
			getOnComplete().accept(this);
			return;
		}
		
		currentRequest = requests.get(currentRequestIndex);
		currentRequest.start();
	}
	
	protected void dispose(){
		PlayerQuitEvent.getHandlerList().unregister(listener);
	}
	
	public boolean isStarted() {
		return state != ConversationState.UNSTARTED;
	}

	public boolean isCancelled() {
		return state == ConversationState.CANCELLED;
	}

	public boolean isCompleted() {
		return state == ConversationState.COMPLETED;
	}
	
	public boolean isStopped(){
		return isCompleted() || isCancelled();
	}

	public Consumer<Conversation> getOnCancel() {
		return onCancel;
	}

	public void setOnCancel(Consumer<Conversation> onCancel) {
		this.onCancel = onCancel;
	}

	public Consumer<Conversation> getOnComplete() {
		return onComplete;
	}

	public void setOnComplete(Consumer<Conversation> onComplete) {
		this.onComplete = onComplete;
	}
	
	private class ConversationListener implements Listener{
		@EventHandler(priority = EventPriority.LOWEST)
		public void onQuit(PlayerQuitEvent event){
			if(!event.getPlayer().equals(getPlayer()))
				return;
			
			cancel();
		}
	}
}
