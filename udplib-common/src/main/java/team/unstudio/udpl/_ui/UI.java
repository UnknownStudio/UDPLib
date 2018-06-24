package team.unstudio.udpl._ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A inventory gui packaging
 */
public abstract class UI implements Cloneable {

    /**
     * Constructs an <code>UI</code> with size.
     */
    protected UI(int size) {
        if (size > 56 || size <= 0) throw new IllegalArgumentException("Inventory size couldn't out of range (0,56]");
        this.size = size;
        this.slots = new Slot[size];
    }

    /* ===================================
     *           Slot Start >>>
     * ===================================
     */
    private final Slot[] slots;
    /**
     * the length of this ui
     */
    private final int size;

    public int getSize() {
        return size;
    }

    /**
     * get slot by index
     *
     * @param index the index of slot
     * @return slot,
     *
     * @throws IndexOutOfBoundsException
     */
    public Slot getSlot(int index) {
        if (!checkSlotID(index)) throw new IndexOutOfBoundsException("the id must be in [0, size)");

        return slots[index];
    }

    /**
     * @return all slots (some slot maybe null)
     */
    public Slot[] getSlots() {
        return slots;
    }

    public void setSlot(int index, Slot slot) {
        if (!checkSlotID(index)) throw new IndexOutOfBoundsException("the id must be in [0, size)");

        slots[index] = slot;
    }

    /**
     * set the slot whose index is in [start, end] into {@code slot}
     */
    public void fillSlots(int start, int end, Slot slot) {
        for (int i = start; i <= end; i++) setSlot(i, slot);
    }

    /**
     * set the empty slot into {@code slot}
     */
    public void fillEmptySlots(Slot slot) {
        for (int i = 0; i < getSlots().length; i++)
            if (!hasSlot(i)) setSlot(i, slot);
    }

    /**
     * @return  if the index is valid
     */
    public boolean checkSlotID(int index){
        return index >= 0 && index < size;
    }


    /**
     * Performs an action for each slot of this stream.
     * @param action a <a href="package-summary.html#NonInterference">
     *               non-interfering</a> action to perform on the elements
     */
    public void forEach(@Nonnull Consumer<? super Slot> action) {
        Arrays.stream(getSlots()).forEach(action);
    }

    public boolean hasSlot(int index) {
        return getSlot(index) == null;
    }

    /* ===================================
     *            <<< Slot End
     * ===================================
     */

    /* ===================================
     *           TitleUtils Start >>>
     * ===================================
     */
    public String getTitle(Player player) {
        return getTitle();
    }

    public String getTitle() {
        return "Inventory";
    }
    /* ===================================
     *            <<< TitleUtils End
     * ===================================
     */

    /**
     * open a new inventory for player
     */
    public void open(Player player) {
        player.openInventory(buildInventory());
    }

    /**
     * create an inventory
     */
    public Inventory buildInventory() {
        Inventory inventory = Bukkit.createInventory(null, size, getTitle());
        inventory.setContents(buildContent());
        return inventory;
    }

    /**
     * create an inventory with certain players info
     */
    public Inventory buildInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, getTitle(player));
        inventory.setContents(buildContent());
        return inventory;
    }

    public ItemStack[] buildContent() {
        return Arrays.stream(getSlots()).map(Slot::getItemStackForDisplay).collect(Collectors.toList()).toArray(new ItemStack[0]);
    }
}
