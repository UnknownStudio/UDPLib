package team.unstudio.udpl.conversation;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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

	public static Conversation newConversation(@Nonnull Plugin plugin, @Nonnull Player player) {
		return new Conversation(plugin, player);
	}

	private final Plugin plugin;
	private final Player player;

	private final List<Request<?>> requests = Lists.newLinkedList();

	private final Listener listener = new ConversationListener();

	private ConversationState state = ConversationState.UNSTARTED;

	private Request<?> currentRequest;
	private int currentRequestIndex = 0;

	private Consumer<Conversation> onCancel;
	private Consumer<Conversation> onComplete;

	public Conversation(@Nonnull Plugin plugin, @Nonnull Player player) {
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

	/**
	 * 添加请求
	 */
	public Conversation addRequest(Request<?> request) {
		requests.add(request);
		return this;
	}

	/**
	 * 获取请求
	 * 
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Request<?> getRequest(int index) {
		return requests.get(index);
	}

	/**
	 * 获取当前请求
	 */
	@Nullable
	public Request<?> getCurrentRequest() {
		return currentRequest;
	}

	/**
	 * 请求数量
	 */
	public int size() {
		return requests.size();
	}

	/**
	 * 请求队列是否为空
	 */
	public boolean isEmpty() {
		return requests.isEmpty();
	}

	/**
	 * 开始交流
	 */
	public Conversation start() {
		if (isStarted())
			return this;

		if (isEmpty())
			return this;

		if (!player.isOnline())
			return this;

		state = ConversationState.STARTED;

		PluginUtils.registerEvents(listener, getPlugin());

		currentRequestIndex = 0;
		currentRequest = requests.get(currentRequestIndex);
		currentRequest.setConversation(this);
		currentRequest.start();
		return this;
	}

	/**
	 * 取消交流
	 */
	public void cancel() {
		if (isStopped())
			return;

		state = ConversationState.CANCELLED;

		if (currentRequest != null && !currentRequest.isCompleted())
			currentRequest.dispose();

		dispose();
		if (onCancel != null)
			onCancel.accept(this);
	}

	/**
	 * 注：内部方法，不建议直接调用<br>
	 * inner method<br>
	 */
	public void next() {
		if (isStopped())
			return;

		if (!isStarted())
			return;

		if (!currentRequest.isCompleted())
			return;

		if (++currentRequestIndex >= requests.size()) {
			dispose();
			if (onComplete != null)
				onComplete.accept(this);
			return;
		}

		currentRequest = requests.get(currentRequestIndex);
		currentRequest.setConversation(this);
		currentRequest.start();
	}

	protected void dispose() {
		PlayerQuitEvent.getHandlerList().unregister(listener);
	}

	/**
	 * 交流是否已开始
	 */
	public boolean isStarted() {
		return state != ConversationState.UNSTARTED;
	}

	/**
	 * 交流是否已取消
	 */
	public boolean isCancelled() {
		return state == ConversationState.CANCELLED;
	}

	/**
	 * 交流是否已完成
	 */
	public boolean isCompleted() {
		return state == ConversationState.COMPLETED;
	}

	/**
	 * 交流是否已停止
	 */
	public boolean isStopped() {
		return isCompleted() || isCancelled();
	}

	/**
	 * 获取交流取消监听器
	 */
	@Nullable
	public Consumer<Conversation> getOnCancel() {
		return onCancel;
	}

	/**
	 * 设置交流取消监听器
	 */
	public Conversation setOnCancel(Consumer<Conversation> onCancel) {
		this.onCancel = onCancel;
		return this;
	}

	/**
	 * 获取交流完成监听器
	 */
	@Nullable
	public Consumer<Conversation> getOnComplete() {
		return onComplete;
	}

	/**
	 * 设置交流完成监听器
	 */
	public Conversation setOnComplete(Consumer<Conversation> onComplete) {
		this.onComplete = onComplete;
		return this;
	}

	private class ConversationListener implements Listener {
		@EventHandler(priority = EventPriority.LOWEST)
		public void onQuit(PlayerQuitEvent event) {
			if (!event.getPlayer().equals(getPlayer()))
				return;

			cancel();
		}
	}

	public Conversation requestString(String prompt) {
		addRequest(new RequestString().setPrompt(prompt));
		return this;
	}

	public Conversation requestString(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestString().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}

	public Conversation requestInteger(String prompt) {
		addRequest(new RequestInteger().setPrompt(prompt));
		return this;
	}

	public Conversation requestInteger(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestInteger().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}

	public Conversation requestDouble(String prompt) {
		addRequest(new RequestDouble().setPrompt(prompt));
		return this;
	}

	public Conversation requestDouble(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestDouble().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}

	public Conversation requestConfirm(String prompt) {
		addRequest(new RequestConfirm().setPrompt(prompt));
		return this;
	}

	public Conversation requestConfirm(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestConfirm().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}

	public Conversation requestBigInteger(String prompt) {
		addRequest(new RequestBigInteger().setPrompt(prompt));
		return this;
	}

	public Conversation requestBigInteger(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestBigInteger().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}

	public Conversation requestBigDecimal(String prompt) {
		addRequest(new RequestBigDecimal().setPrompt(prompt));
		return this;
	}

	public Conversation requestBigDecimal(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestBigDecimal().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}

	public Conversation requestEntity(String prompt) {
		addRequest(new RequestEntity().setPrompt(prompt));
		return this;
	}

	public Conversation requestEntity(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestEntity().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}

	public Conversation requestBlock(String prompt) {
		addRequest(new RequestBlock().setPrompt(prompt));
		return this;
	}

	public Conversation requestBlock(String prompt, long timeout, String timeoutMessage) {
		addRequest(new RequestBlock().setPrompt(prompt).setTimeout(timeout).setTimeoutMessage(timeoutMessage));
		return this;
	}
}
