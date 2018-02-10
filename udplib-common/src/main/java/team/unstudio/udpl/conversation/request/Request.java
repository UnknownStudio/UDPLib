package team.unstudio.udpl.conversation.request;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import team.unstudio.udpl.conversation.Conversation;

/**
 * 请求接口<br>
 * 另请见{@link team.unstudio.udpl.conversation.Conversation}类
 * @param <T> 请求返回值类型
 */
public interface Request<T> {
	
	/**
	 * 开始请求<br>
	 * 注：内部方法，不建议直接调用<br>
	 */
	void start();
	
	/**
	 * 卸载请求<br>
	 * 注：内部方法，不建议直接调用<br>
	 */
	void dispose();
	
	/**
	 * 请求是否开始
	 */
	boolean isStarted();
	
	/**
	 * 请求是否完成
	 */
	boolean isCompleted();
	
	/**
	 * 获取请求提示
	 */
	String getPrompt();
	
	/**
	 * 设置请求提示
	 */
	Request<T> setPrompt(String prompt);
	
	/**
	 * 获取结果
	 */
	Optional<T> getResult();
	
	/**
	 * 获取请求开始监听器
	 */
	Consumer<Request<T>> getOnStart();
	
	/**
	 * 设置请求开始监听器
	 */
	Request<T> setOnStart(Consumer<Request<T>> onStart);
	
	/**
	 * 获取请求完成监听器
	 */
	Consumer<Request<T>> getOnComplete();
	
	/**
	 * 设置请求完成监听器
	 */
	Request<T> setOnComplete(Consumer<Request<T>> onComplete);
	
	/**
	 * 获取请求结果验证器
	 */
	BiPredicate<Request<T>,T> getValidator();
	
	/**
	 * 设置请求结果验证器
	 */
	Request<T> setValidator(BiPredicate<Request<T>,T> validator);
	
	/**
	 * 获取验证失败消息
	 */
	String getValidateFailedMessage();
	
	/**
	 * 设置验证失败消息
	 */
	Request<T> setValidateFailedMessage(String message);
	
	/**
	 * 获取验证失败监听器
	 */
	Consumer<Request<T>> getOnValidateFailed();
	
	/**
	 * 设置验证失败监听器
	 */
	Request<T> setOnValidateFailed(Consumer<Request<T>> onValidateFailed);
	
	/**
	 * 获取请求超时时间，单位秒(s)
	 */
	long getTimeout();
	
	/**
	 * 设置请求超时时间，单位秒(s)
	 */
	Request<T> setTimeout(long timeout);
	
	/**
	 * 获取请求超时消息
	 */
	String getTimeoutMessage();
	
	/**
	 * 设置请求超时消息
	 */
	Request<T> setTimeoutMessage(String message);
	
	/**
	 * 获取请求超时监听器
	 */
	Consumer<Request<T>> getOnTimeout();
	
	/**
	 * 设置请求超时监听器
	 */
	Request<T> setOnTimeout(Consumer<Request<T>> onTimeout);
	
	/**
	 * 获取请求的交流
	 */
	Conversation getConversation();
	
	/**
	 * 设置请求的交流<br>
	 * 注：内部方法，不建议直接调用<br>
	 */
	void setConversation(Conversation conversation);
	
}
