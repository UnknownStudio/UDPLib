package team.unstudio.udpl.conversation;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.bukkit.Bukkit;

import com.google.common.base.Strings;

public abstract class RequestBase<T> implements Request<T>{
	
	private Conversation conversation;
	private String prompt;
	private String validateFailedMessage;
	private String timeoutMessage;
	private long timeout = -1;
	
	private int timeoutTaskId = -1;
	
	private Consumer<Request<T>> onStart;
	private Consumer<Request<T>> onComplete;
	private BiPredicate<Request<T>, T> validator;
	private Consumer<Request<T>> onValidateFailed;
	private Consumer<Request<T>> onTimeout;
	
	private boolean started = false;
	private boolean completed = false;

	@Override
	public void start() {
		startTimeoutTask();
	}
	
	@Override
	public void dispose() {
		cancelTimeoutTask();
	}
	
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
	
	@Override
	public String getValidateFailedMessage() {
		return validateFailedMessage;
	}

	@Override
	public void setValidateFailedMessage(String validateFailedMessage) {
		this.validateFailedMessage = validateFailedMessage;
	}

	@Override
	public String getTimeoutMessage() {
		return timeoutMessage;
	}
	
	@Override
	public void setTimeoutMessage(String timeoutMessage) {
		this.timeoutMessage = timeoutMessage;
	}

	@Override
	public Consumer<Request<T>> getOnValidateFailed() {
		return onValidateFailed;
	}

	@Override
	public void setOnValidateFailed(Consumer<Request<T>> onValidateFailed) {
		this.onValidateFailed = onValidateFailed;
	}

	@Override
	public Consumer<Request<T>> getOnTimeout() {
		return onTimeout;
	}

	@Override
	public void setOnTimeout(Consumer<Request<T>> onTimeout) {
		this.onTimeout = onTimeout;
	}
	
	protected void startTimeoutTask(){
		if(timeout < 0)
			return;
		
		timeoutTaskId = Bukkit.getScheduler().runTaskLater(getConversation().getPlugin(), this::callTimeout, timeout*20).getTaskId();
	}
	
	protected void cancelTimeoutTask(){
		if(timeoutTaskId < 0)
			return;
		
		Bukkit.getScheduler().cancelTask(timeoutTaskId);
		timeoutTaskId = -1;
	}
	
	protected void setStarted(boolean started){
		this.started = started;
		if(started && onStart != null)
			onStart.accept(this);
	}
	
	protected void setCompleted(boolean completed){
		this.completed = completed;
		if(completed){
			dispose();
			if(onComplete != null)
				onComplete.accept(this);
			getConversation().next();
		}
	}
	
	protected void callTimeout(){
		getConversation().cancel();
		sendTimeoutMessage();
		if(onTimeout != null)
			onTimeout.accept(this);
	}
	
	protected boolean validate(T value){
		if(validator == null)
			return true;
		
		if(validator.test(this, value))
			return true;
		
		callValidateFailed();
		return false;
	}
	
	protected void callValidateFailed(){
		sendValidateFailedMessage();
		if(onValidateFailed != null)
			onValidateFailed.accept(this);
	}
	
	protected void sendPrompt(){
		if(Strings.isNullOrEmpty(prompt))
			return;
		
		getConversation().getPlayer().sendMessage(prompt);
	}
	
	protected void sendTimeoutMessage(){
		if(Strings.isNullOrEmpty(timeoutMessage))
			return;
		
		getConversation().getPlayer().sendMessage(timeoutMessage);
	}
	
	protected void sendValidateFailedMessage(){
		if(Strings.isNullOrEmpty(validateFailedMessage))
			return;
		
		getConversation().getPlayer().sendMessage(validateFailedMessage);
	}
}
