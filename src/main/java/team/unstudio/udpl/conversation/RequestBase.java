package team.unstudio.udpl.conversation;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.bukkit.Bukkit;

public abstract class RequestBase<T> implements Request<T>{
	
	private Conversation conversation;
	private String prompt;
	private long timeout = -1;
	
	private int timeoutTaskId = -1;
	
	private Consumer<Request<T>> onStart;
	private Consumer<Request<T>> onComplete;
	private BiPredicate<Request<T>, T> validator;
	
	private boolean started = false;
	private boolean completed = false;

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}

	@Override
	public String getPrompt() {
		return prompt;
	}

	@Override
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public Conversation getConversation() {
		return conversation;
	}

	@Override
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	@Override
	public Consumer<Request<T>> getOnStart() {
		return onStart;
	}

	@Override
	public void setOnStart(Consumer<Request<T>> onStart) {
		this.onStart = onStart;
	}

	@Override
	public Consumer<Request<T>> getOnComplete() {
		return onComplete;
	}

	@Override
	public void setOnComplete(Consumer<Request<T>> onComplete) {
		this.onComplete = onComplete;
	}

	@Override
	public BiPredicate<Request<T>, T> getValidator() {
		return validator;
	}

	@Override
	public void setValidator(BiPredicate<Request<T>, T> validator) {
		this.validator = validator;
	}

	@Override
	public long getTimeout() {
		return timeout;
	}

	@Override
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	protected void startTimeoutTask(){
		if(timeout < 0)
			return;
		
		timeoutTaskId = Bukkit.getScheduler().runTaskLater(getConversation().getPlugin(), getConversation()::cancel, timeout*20).getTaskId();
	}
	
	protected void cancelTimeoutTask(){
		if(timeoutTaskId < 0)
			return;
		
		Bukkit.getScheduler().cancelTask(timeoutTaskId);
		timeoutTaskId = -1;
	}
	
	protected void setStarted(boolean started){
		this.started = started;
		if(started)
			callStart();
	}
	
	protected void setCompleted(boolean completed){
		this.completed = completed;
		if(completed)
			callComplete();
	}
	
	protected void callStart(){
		getOnStart().accept(this);
	}
	
	protected void callComplete(){
		dispose();
		getOnComplete().accept(this);
		getConversation().next();
	}
	
	protected boolean validate(T value){
		if(validator == null)
			return true;
		
		return validator.test(this, value);
	}
}
