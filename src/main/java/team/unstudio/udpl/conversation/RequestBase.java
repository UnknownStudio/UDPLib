package team.unstudio.udpl.conversation;

import com.google.common.base.Strings;
import org.bukkit.Bukkit;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

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
	public Request<T> setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
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
	public Request<T> setOnStart(Consumer<Request<T>> onStart) {
		this.onStart = onStart;
		return this;
	}

	@Override
	public Consumer<Request<T>> getOnComplete() {
		return onComplete;
	}

	@Override
	public Request<T> setOnComplete(Consumer<Request<T>> onComplete) {
		this.onComplete = onComplete;
		return this;
	}

	@Override
	public BiPredicate<Request<T>, T> getValidator() {
		return validator;
	}

	@Override
	public Request<T> setValidator(BiPredicate<Request<T>, T> validator) {
		this.validator = validator;
		return this;
	}

	@Override
	public long getTimeout() {
		return timeout;
	}

	@Override
	public Request<T> setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}
	
	@Override
	public String getValidateFailedMessage() {
		return validateFailedMessage;
	}

	@Override
	public Request<T> setValidateFailedMessage(String validateFailedMessage) {
		this.validateFailedMessage = validateFailedMessage;
		return this;
	}

	@Override
	public String getTimeoutMessage() {
		return timeoutMessage;
	}
	
	@Override
	public Request<T> setTimeoutMessage(String timeoutMessage) {
		this.timeoutMessage = timeoutMessage;
		return this;
	}

	@Override
	public Consumer<Request<T>> getOnValidateFailed() {
		return onValidateFailed;
	}

	@Override
	public Request<T> setOnValidateFailed(Consumer<Request<T>> onValidateFailed) {
		this.onValidateFailed = onValidateFailed;
		return this;
	}

	@Override
	public Consumer<Request<T>> getOnTimeout() {
		return onTimeout;
	}

	@Override
	public Request<T> setOnTimeout(Consumer<Request<T>> onTimeout) {
		this.onTimeout = onTimeout;
		return this;
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
