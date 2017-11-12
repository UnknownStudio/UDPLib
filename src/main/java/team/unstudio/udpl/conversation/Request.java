package team.unstudio.udpl.conversation;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public interface Request<T> {
	
	void start();
	
	void dispose();
	
	boolean isStarted();
	
	boolean isCompleted();
	
	String getPrompt();
	
	Request<T> setPrompt(String prompt);
	
	Optional<T> getResult();
	
	Consumer<Request<T>> getOnStart();
	
	Request<T> setOnStart(Consumer<Request<T>> onStart);
	
	Consumer<Request<T>> getOnComplete();
	
	Request<T> setOnComplete(Consumer<Request<T>> onComplete);
	
	BiPredicate<Request<T>,T> getValidator();
	
	Request<T> setValidator(BiPredicate<Request<T>,T> validator);
	
	String getValidateFailedMessage();
	
	Request<T> setValidateFailedMessage(String message);
	
	Consumer<Request<T>> getOnValidateFailed();
	
	Request<T> setOnValidateFailed(Consumer<Request<T>> onValidateFailed);
	
	long getTimeout();
	
	Request<T> setTimeout(long timeout);
	
	String getTimeoutMessage();
	
	Request<T> setTimeoutMessage(String message);
	
	Consumer<Request<T>> getOnTimeout();
	
	Request<T> setOnTimeout(Consumer<Request<T>> onTimeout);
	
	Conversation getConversation();
	
	void setConversation(Conversation conversation);
}
