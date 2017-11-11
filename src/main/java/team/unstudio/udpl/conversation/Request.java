package team.unstudio.udpl.conversation;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public interface Request<T> {
	
	void start();
	
	void dispose();
	
	boolean isStarted();
	
	boolean isCompleted();
	
	String getPrompt();
	
	void setPrompt(String prompt);
	
	Optional<T> getResult();
	
	Conversation getConversation();
	
	void setConversation(Conversation conversation);
	
	Consumer<Request<T>> getOnStart();
	
	void setOnStart(Consumer<Request<T>> onStart);
	
	Consumer<Request<T>> getOnComplete();
	
	void setOnComplete(Consumer<Request<T>> onComplete);
	
	BiPredicate<Request<T>,T> getValidator();
	
	void setValidator(BiPredicate<Request<T>,T> validator);
	
	String getValidateFailedMessage();
	
	void setValidateFailedMessage(String message);
	
	BiConsumer<Request<T>,T> getOnValidateFailed();
	
	void setOnValidateFailed(BiConsumer<Request<T>,T> onValidateFailed);
	
	long getTimeout();
	
	void setTimeout(long timeout);
	
	String getTimeoutMessage();
	
	void setTimeoutMessage(String message);
	
	Consumer<Request<T>> getOnTimeout();
	
	void setOnTimeout(Consumer<Request<T>> onTimeout);
}
