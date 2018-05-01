package team.unstudio.udpl.core.command;

import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import team.unstudio.udpl.command.anno.Command;
import team.unstudio.udpl.command.anno.Optional;
import team.unstudio.udpl.command.anno.Required;
import team.unstudio.udpl.conversation.Conversation;
import team.unstudio.udpl.conversation.request.RequestBlock;
import team.unstudio.udpl.conversation.request.RequestEntity;
import team.unstudio.udpl.core.UDPLI18n;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.entity.NmsEntity;
import team.unstudio.udpl.nms.inventory.NmsItemStack;
import team.unstudio.udpl.nms.nbt.NBTBase;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;
import team.unstudio.udpl.nms.nbt.NBTTagList;
import team.unstudio.udpl.nms.tileentity.NmsTileEntity;
import team.unstudio.udpl.util.ChatUtils;

public class NBTEditor {
	
	private Map<Player, Object> editingObject = new WeakHashMap<>();
	private Map<Player, NBTTagCompound> editingNbt = new WeakHashMap<>();
	
	@Command(value = "me", 
			permission = "udpl.nbteditor.me", 
			senders = Player.class)
	public void editMe(Player player) {
		editingObject.put(player, player);
		editingNbt.put(player, NmsHelper.createNmsEntity(player).save());
		show(player, "");
	}
	
	@Command(value = "entity", 
			permission = "udpl.nbteditor.entity", 
			senders = Player.class)
	public void editEntity(Player player) {
		clickEntity.start(player);
	}
	
	@SuppressWarnings("unchecked")
	private final Conversation clickEntity = Conversation.newConversation(UDPLib.getInstance())
			.addRequest(RequestEntity.newRequestEntity().setPrompt(UDPLI18n.format("udpl.nbt.click.entity")))
			.setOnComplete(conv->{
				java.util.Optional<Entity> result = (java.util.Optional<Entity>) conv.getRequest(0).getResult();
				if(result.isPresent()) {
					NmsEntity entity = NmsHelper.createNmsEntity(result.get());
					editingObject.put(conv.getPlayer(), entity);
					editingNbt.put(conv.getPlayer(), entity.save());
					show(conv.getPlayer(), "");
				}
			});
	
	@Command(value = "block", 
			permission = "udpl.nbteditor.block", 
			senders = Player.class)
	public void editBlock(Player player) {
		clickBlock.start(player);
	}
	
	@SuppressWarnings("unchecked")
	private final Conversation clickBlock = Conversation.newConversation(UDPLib.getInstance())
			.addRequest(RequestBlock.newRequestBlock().setPrompt(UDPLI18n.format("udpl.nbt.click.block")))
			.setOnComplete(conv->{
				java.util.Optional<Location> result = (java.util.Optional<Location>) conv.getRequest(0).getResult();
				if(result.isPresent()) {
					NmsTileEntity tileEntity = NmsHelper.createNmsTileEntity(result.get().getBlock().getState());
					editingObject.put(conv.getPlayer(), tileEntity);
					editingNbt.put(conv.getPlayer(), tileEntity.save());
					show(conv.getPlayer(), "");
				}
			});
	
	@Command(value = "item", 
			permission = "udpl.nbteditor.item", 
			senders = Player.class)
	public void editItem(Player player) {
		ItemStack itemStack = player.getInventory().getItemInMainHand();
		if(itemStack == null)
			return;
		
		NmsItemStack nmsItemStack = NmsHelper.createNmsItemStack(itemStack);
		editingObject.put(player, nmsItemStack);
		editingNbt.put(player, nmsItemStack.save());
		show(player, "");
	}
	
	private static final Pattern LIST_INDEX = Pattern.compile("\\[\\d+\\]");
	
	@Command(value = "set", 
			permission = "udpl.nbteditor.set", 
			senders = Player.class)
	public void set(Player player, 
			@Required(name="nbteditor.command.path") String path, 
			@Required(name="nbteditor.command.value") String value) {
		NBTTagCompound compound = editingNbt.get(player);
		if(compound == null)
			return;
		
		String[] splitedPath = path.split("\\.");
		for (int i = 0; i < splitedPath.length - 1; i++) {
			String subPath = splitedPath[i];
			int index = parseListIndex(subPath);
			if(index < 0) {
				
			} else {
				
			}
		}
	}
	
	@Command(value = "setc", 
			permission = "udpl.nbteditor.set", 
			senders = Player.class,
			hide = true)
	public void setc(Player player, @Required String path) {
		
	}
	
	@Command(value = "addc", 
			permission = "udpl.nbteditor.set", 
			senders = Player.class,
			hide = true)
	public void addc(Player player, @Required String path) {
		
	}
	
	@Command(value = "remove", 
			permission = "udpl.nbteditor.remove", 
			senders = Player.class)
	public void remove(Player player, @Required(name="nbteditor.command.path") String path) {

	}
	
	@Command(value = "save", 
			permission = "udpl.nbteditor.save", 
			senders = Player.class)
	public void save(Player player) {

	}
	
	@Command(value = "show", 
			permission = "udpl.nbteditor.show", 
			senders = Player.class)
	public void show(Player player, @Optional(value="", name="nbteditor.command.path") String path) {
		NBTTagCompound compound = editingNbt.get(player);
		if(compound == null)
			return;
		
	}
	
	private int parseListIndex(String path) {
		try {
			if (LIST_INDEX.matcher(path).matches()) {
				int index = Integer.parseInt(path.substring(1, path.length() - 1));
				return index < 0 ? 0 : index;
			}
		} catch (NumberFormatException ignored) {}
		return -1;
	}
	
	private void showCompound(Player player, String path, NBTTagCompound compound) {
		ComponentBuilder builder = new ComponentBuilder(ChatUtils.SPLITTER).bold(true).strikethrough(true).append("\n") // SPLITTER
		.append(UDPLI18n.format(player, "nbteditor.message.path", path))
		.append(" [+]").bold(true).event(new ClickEvent(Action.RUN_COMMAND, "/nbteditor addc"))
		.append(" [<]\n").bold(true).event(new ClickEvent(Action.RUN_COMMAND, "/nbteditor show " + path.substring(0, path.lastIndexOf('.'))));
		
		for (Entry<String, NBTBase> entry : compound.getEntry()) {

		}
		
		builder.append(ChatUtils.SPLITTER).bold(true).strikethrough(true);
	}
	
	private void showList(Player player, String path, NBTTagList list) {
		
	}
}
