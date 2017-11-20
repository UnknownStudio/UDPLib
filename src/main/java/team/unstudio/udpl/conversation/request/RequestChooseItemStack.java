package team.unstudio.udpl.conversation.request;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.conversation.RequestBase;
import team.unstudio.udpl.ui.Slot;
import team.unstudio.udpl.ui.UI;
import team.unstudio.udpl.util.IndexedItemStack;

public class RequestChooseItemStack extends RequestBase<IndexedItemStack>{
	
	public static RequestChooseItemStack newRequestChooseItemStack(InventoryType type){
		return new RequestChooseItemStack(type);
	}
	
	public static RequestChooseItemStack newRequestChooseItemStack(int size){
		return new RequestChooseItemStack(size);
	}
	
	public static RequestChooseItemStack newRequestChooseItemStack(InventoryType type, String title){
		return new RequestChooseItemStack(type, title);
	}
	
	public static RequestChooseItemStack newRequestChooseItemStack(int size, String title){
		return new RequestChooseItemStack(size, title);
	}
	
	private final UI ui;
	
	public RequestChooseItemStack(InventoryType type) {
		ui = new UI(type);
		initUi();
	}
	
	public RequestChooseItemStack(int size) {
		ui = new UI(size);
		initUi();
	}
	
	public RequestChooseItemStack(InventoryType type, String title) {
		ui = new UI(type, title);
		initUi();
	}
	
	public RequestChooseItemStack(int size, String title) {
		ui = new UI(size, title);
		initUi();
	}
	
	private void initUi(){
		ui.setOnClose((ui, player) -> {
			if (!isCompleted())
				getConversation().cancel();
		});
	}
	
	public RequestChooseItemStack addItem(@Nonnull ItemStack itemStack) {
		Validate.notNull(itemStack);
		int firstEmpty = ui.getInventory().firstEmpty();
		if(firstEmpty < 0)
			throw new IndexOutOfBoundsException("Can't add more item.");
		Slot slot = new Slot(itemStack, firstEmpty);
		slot.setOnClick(event -> {
			IndexedItemStack invalidate = new IndexedItemStack(slot.getItemStack(), slot.getSlot());
			if (!validate(invalidate))
				return;

			result = invalidate;
			setCompleted(true);
		});
		ui.addSlot(slot);
		return this;
	}

	public RequestChooseItemStack addItem(@Nonnull ItemStack... itemStacks) {
		Validate.noNullElements(itemStacks);
		for (ItemStack itemStack : itemStacks)
			addItem(itemStack);
		return this;
	}
	
	public RequestChooseItemStack addItem(Collection<ItemStack> itemStacks) {
		Validate.noNullElements(itemStacks);
		itemStacks.forEach(this::addItem);
		return this;
	}
	
	private IndexedItemStack result;

	@Override
	public void start() {
		super.start();
		setStarted(true);
		sendPrompt();
		ui.open(getConversation().getPlayer());
	}

	@Override
	public void dispose() {
		super.dispose();
		ui.close(getConversation().getPlayer());
	}

	@Override
	public Optional<IndexedItemStack> getResult() {
		if(!isCompleted())
			return Optional.empty();
		return Optional.of(result);
	}
}
