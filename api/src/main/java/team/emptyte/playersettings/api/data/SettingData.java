package team.emptyte.playersettings.api.data;

import org.bukkit.inventory.ItemStack;
import team.emptyte.playersettings.api.storage.model.Model;

import java.util.Map;

public class SettingData implements Model {

    private final String id;
    private final ItemStack item;
    private final int slot;

    private final String permission;

    private final boolean secondItem;

    public SettingData(
            String id,
            ItemStack item, int slot,
            String permission,
            boolean secondItem) {
        this.id = id;
        this.item = item;
        this.slot = slot;
        this.permission = permission;
        this.secondItem = secondItem;
    }

    @Override
    public String getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isSecondItem() {
        return secondItem;
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static class Builder {
        private String id;
        private ItemStack item;
        private int slot;
        private String permission;
        private boolean secondItem;

        public Builder(String id) {
            this.id = id;
        }

        public Builder setItem(ItemStack item) {
            this.item = item;

            return this;
        }

        public Builder setSlot(int slot) {
            this.slot = slot;

            return this;
        }

        public Builder setPermission(String permission) {
            this.permission = permission;

            return this;
        }

        public Builder setSecondItem(boolean secondItem) {
            this.secondItem = secondItem;

            return this;
        }

        public SettingData build() {
            return new SettingData(id, item, slot, permission, secondItem);
        }

    }

}
