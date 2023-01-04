package team.emptyte.mercuryoptions.api.data;

import org.bukkit.inventory.ItemStack;
import team.emptyte.mercuryoptions.api.registry.Keyed;

public class SettingData implements Keyed {

    private final String id;

    private ItemStack item;
    private byte slot;

    private boolean enabledSecondItem;

    public SettingData(
            String id, ItemStack item, byte slot, boolean enabledSecondItem
    ) {
        this.id = id;
        this.item = item;
        this.slot = slot;
        this.enabledSecondItem = enabledSecondItem;
    }

    @Override
    public String getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public byte getSlot() {
        return slot;
    }

    public void setSlot(byte slot) {
        this.slot = slot;
    }

    public boolean isEnabledSecondItem() {
        return enabledSecondItem;
    }

    public void setEnabledSecondItem(boolean enabledSecondItem) {
        this.enabledSecondItem = enabledSecondItem;
    }
}
