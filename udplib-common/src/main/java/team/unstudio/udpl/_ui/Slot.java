package team.unstudio.udpl._ui;

import org.bukkit.inventory.ItemStack;

public class Slot {
    private ItemStack itemStackForDisplay;
    private boolean editable = false;

    public boolean isEditable() {
        return editable;
    }

    public ItemStack getItemStackForDisplay() {
        return itemStackForDisplay;
    }
}
