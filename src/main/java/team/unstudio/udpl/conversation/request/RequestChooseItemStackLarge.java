package team.unstudio.udpl.conversation.request;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import team.unstudio.udpl.conversation.RequestBase;
import team.unstudio.udpl.ui.Slot;
import team.unstudio.udpl.ui.UI;
import team.unstudio.udpl.util.IndexedItemStack;

public class RequestChooseItemStackLarge extends RequestBase<IndexedItemStack>{
	
	private final List<ItemStack> itemStacks = Lists.newLinkedList();
	private final Map<Integer, UI> pageToUi = Maps.newHashMap();
	
	private String title;
	private ItemStack nextPageItem;
	private ItemStack lastPageItem;
	
	private IndexedItemStack result;
	
	public RequestChooseItemStackLarge addItem(@Nonnull ItemStack itemStack) {
		Validate.notNull(itemStack);
		itemStacks.add(itemStack);
		return this;
	}

	public RequestChooseItemStackLarge addItem(@Nonnull ItemStack... itemStacks) {
		Validate.noNullElements(itemStacks);
		for (ItemStack itemStack : itemStacks)
			addItem(itemStack);
		return this;
	}
	
	public RequestChooseItemStackLarge addItem(Collection<ItemStack> itemStacks) {
		Validate.noNullElements(itemStacks);
		itemStacks.forEach(this::addItem);
		return this;
	}
	
	public ItemStack getNextPageItem() {
		return nextPageItem;
	}

	public RequestChooseItemStackLarge setNextPageItem(ItemStack nextPageItem) {
		this.nextPageItem = nextPageItem;
		return this;
	}

	public ItemStack getLastPageItem() {
		return lastPageItem;
	}

	public RequestChooseItemStackLarge setLastPageItem(ItemStack lastPageItem) {
		this.lastPageItem = lastPageItem;
		return this;
	}
	
	public String getTitle() {
		return title;
	}

	public RequestChooseItemStackLarge setTitle(String title) {
		this.title = title;
		return this;
	}

	private boolean changingPage = false;
	
	protected void openChooserUi(int page){
		changingPage = true;
		getConversation().getPlayer().closeInventory();
		getUi(page).open(getConversation().getPlayer());
		changingPage = false;
	}
	
	protected UI getUi(int page){
		int normalizedPage = getNormalizedPage(page);
		if(pageToUi.containsKey(normalizedPage))
			return pageToUi.get(normalizedPage);
		
		UI ui = new UI(54,Strings.nullToEmpty(getTitle())+" - " + normalizedPage);
		Slot lastPageSlot = new Slot(getLastPageItem(), 45);
		lastPageSlot.setOnClick(event -> openChooserUi(normalizedPage - 1));
		Slot nextPageSlot = new Slot(getNextPageItem(), 53);
		nextPageSlot.setOnClick(event -> openChooserUi(normalizedPage + 1));
		ui.addSlot(lastPageSlot, nextPageSlot);
		int firstIndex = getPageFirstItemIndex(normalizedPage);
		for (int i = firstIndex, lastIndex = getPageLastItemIndex(
				normalizedPage); i <= lastIndex; i++) {
			ItemStack itemStack = itemStacks.get(i);
			Slot slot = new Slot(itemStack, i - firstIndex);
			slot.setOnClick(event->{
				IndexedItemStack invalidate = new IndexedItemStack(slot.getItemStack(), slot.getSlot());
				if (!validate(invalidate))
					return;

				result = invalidate;
				setCompleted(true);
			});
			ui.addSlot(slot);
		}
		ui.setOnClose((u, p) -> {
			if (!isCompleted()&&!changingPage)
				getConversation().cancel();
		});
		pageToUi.put(normalizedPage, ui);
		return ui;
	}
	
	protected int getMaxPageAmount() {
		return (int) Math.ceil(itemStacks.size() / 45D);
	}

	protected int getNormalizedPage(int invalidatePage) {
		int maxPageAmount = getMaxPageAmount();
		int i = invalidatePage % maxPageAmount;
		return i > 0 ? i : maxPageAmount - i;
	}
	
	protected int getPageFirstItemIndex(int page){
		return (page - 1) * 45;
	}
	
	protected int getPageLastItemIndex(int page){
		return Math.min(page * 45 - 1, itemStacks.size() - 1);
	}

	@Override
	public void start() {
		super.start();
		setStarted(true);
		sendPrompt();
		openChooserUi(0);
	}

	@Override
	public void dispose() {
		super.dispose();
		getConversation().getPlayer().closeInventory();
	}

	@Override
	public Optional<IndexedItemStack> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
}
