package team.unstudio.udpl.test;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.api.command.anno.Command;
import team.unstudio.udpl.api.command.anno.Optional;
import team.unstudio.udpl.api.command.anno.Required;
import team.unstudio.udpl.api.ui.UI;
import team.unstudio.udpl.api.ui.UIFactory;

public final class AnnoCommand {

	UI ui;
	Player player;
	
	@Command(value = "opengui", 
			senders = Player.class)
	public void openGUI(Player sender) {
		ui = UIFactory.createUI(9, "TestUI");
		ui.open(sender);
		player = sender;
	}
	
	@Command(value = "closegui", 
			senders = ConsoleCommandSender.class)
	public void closeGUI(ConsoleCommandSender sender) {
		ui.close(player);
	}
	
	@Command(value = "give",senders = Player.class)
	public boolean giveItemToPlayer(CommandSender sender,@Required Player target,@Required Material itemType,@Optional("1") int amount,@Optional("0") short durability){
		if(target==null)
			return false;
		
		if(itemType==null)
			return false;
		
		ItemStack item = new ItemStack(itemType);
		item.setAmount(amount);
		item.setDurability(durability);
		
		target.getInventory().addItem(item);
		return true;
	}

	@Command(value = "getnbt", senders = Player.class)
    public void getNbt(CommandSender sender) {
        Player player = (Player)sender;
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setBoolean("Boolean", false);
        tagCompound.setByte("Byte", (byte)3);
        byte a[] = {(byte)1, (byte)2, (byte)5};
        tagCompound.setByteArray("ByteArray", a);
        tagCompound.setString("String", "测试");
        tagCompound.setDouble("Double", 1.01);
        tagCompound.setFloat("Float", (float) 2.01);
        tagCompound.setInt("Int", 1);
        tagCompound.setLong("Long", 11);
        tagCompound.setShort("Short", (short) 13);
        player.sendRawMessage(ChatColor.GREEN + tagCompound.toString());
    }
}
