package team.unstudio.udpl._ui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class Slot {
    /**
     * Whether the slot can be changed (put or take ItemStack)
     */
    public boolean isEditable() {
        return false;
    }

    /**
     * Get the ItemStack display into Inventory ({@link UI#buildContent()})
     */
    public ItemStack getItemStackForDisplay() {
        return new ItemStack(Material.AIR);
    }

    public abstract void onClick();
    public abstract void onClose();
}
