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

import team.unstudio.udpl.conversation.request.RequestBigDecimal;
import team.unstudio.udpl.conversation.request.RequestBigInteger;
import team.unstudio.udpl.conversation.request.RequestBlock;
import team.unstudio.udpl.conversation.request.RequestConfirm;
import team.unstudio.udpl.conversation.request.RequestDouble;
import team.unstudio.udpl.conversation.request.RequestEntity;
import team.unstudio.udpl.conversation.request.RequestInteger;
import team.unstudio.udpl.conversation.request.RequestString;
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
	
	public Conversation addRequest(Request<?> request){
		request.setConversation(this);
		requests.add(request);
		return this;
	}
	
	public Request<?> getRequest(int index){
		return requests.get(index);
	}
	
	public Request<?> getCurrentRequest(){
		return currentRequest;
	}
	
	public int size(){
		return requests.size();
	}
	
	public boolean isEmpty(){
		return requests.isEmpty();
	}
	
	public Conversation start(){
		if(isStarted())
			return this;
		
		if(isEmpty())
			return this;
		
		if(!player.isOnline())
			return this;
		
		state = ConversationState.STARTED;
		
		PluginUtils.registerEvents(listener, getPlugin());
		
		currentRequestIndex = 0;
		currentRequest = requests.get(currentRequestIndex);
		currentRequest.start();
		return this;
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

	public Conversation setOnCancel(Consumer<Conversation> onCancel) {
		this.onCancel = onCancel;
		return this;
	}

	public Consumer<Conversation> getOnComplete() {
		return onComplete;
	}

	public Conversation setOnComplete(Consumer<Conversation> onComplete) {
		this.onComplete = onComplete;
		return this;
	}
	
	private class ConversationListener implements Listener{
		@EventHandler(priority = EventPriority.LOWEST)
		public void onQuit(PlayerQuitEvent event){
			if(!event.getPlayer().equals(getPlayer()))
				return;
			
			cancel();
		}
	}
	
	public Conversation requestString(String prompt){
		addRequest(new RequestString().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestString(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestString().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
	
	public Conversation requestInteger(String prompt){
		addRequest(new RequestInteger().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestInteger(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestInteger().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
	
	public Conversation requestDouble(String prompt){
		addRequest(new RequestDouble().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestDouble(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestDouble().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
	
	public Conversation requestConfirm(String prompt){
		addRequest(new RequestConfirm().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestConfirm(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestConfirm().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
	
	public Conversation requestBigInteger(String prompt){
		addRequest(new RequestBigInteger().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestBigInteger(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestBigInteger().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
	
	public Conversation requestBigDecimal(String prompt){
		addRequest(new RequestBigDecimal().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestBigDecimal(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestBigDecimal().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
	
	public Conversation requestEntity(String prompt){
		addRequest(new RequestEntity().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestEntity(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestEntity().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
	
	public Conversation requestBlock(String prompt){
		addRequest(new RequestBlock().setPrompt(prompt));
		return this;
	}
	
	public Conversation requestBlock(String prompt,long timeout,String timeoutMessage){
		addRequest(new RequestBlock().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
}
