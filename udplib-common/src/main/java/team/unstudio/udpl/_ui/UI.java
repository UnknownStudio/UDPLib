package team.unstudio.udpl._ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * A inventory gui packaging
 */
public abstract class UI implements Cloneable {

    /**
     * Constructs an <code>UI</code> with size.
     */
    protected UI(int size) {
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
     * @return  if the index is valid
     */
    public boolean checkSlotID(int index){
        return index >= 0 && index < size;
    }
    /* ===================================
     *            <<< Slot End
     * ===================================
     */

    /* ===================================
     *           Title Start >>>
     * ===================================
     */
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String buildTitle(Player player) {
        return getTitle();
    }
    /* ===================================
     *            <<< Title End
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

        return inventory;
    }
    /**
     * create an inventory with certain players info
     */
    public Inventory buildInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, buildTitle(player));

        return inventory;
    }
}
