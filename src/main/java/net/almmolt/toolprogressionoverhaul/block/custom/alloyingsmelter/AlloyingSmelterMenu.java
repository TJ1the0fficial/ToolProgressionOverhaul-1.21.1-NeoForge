package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.gui.ModMenus;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.function.Supplier;

public class AlloyingSmelterMenu extends AbstractContainerMenu {
    private final AlloyingSmelterBlockEntity blockEntity;

    // Client-side constructor (NeoForge calls this when opening the screen)
    public AlloyingSmelterMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, (AlloyingSmelterBlockEntity) null);
    }

    // Server-side constructor
    public AlloyingSmelterMenu(int containerId, Inventory playerInv, AlloyingSmelterBlockEntity entity) {
        super(ModMenus.ALLOYING_SMELTER_MENU.get(), containerId);
        this.blockEntity = entity;

        // Use the BlockEntity's inventory, or a dummy if on client
        IItemHandler inv = entity != null ? entity.inventory : new ItemStackHandler(4);

        // 1. ADD MACHINE SLOTS (Example positions)
        this.addSlot(new SlotItemHandler(inv, 0, 44, 12)); // Input 1
        this.addSlot(new SlotItemHandler(inv, 1, 80, 12)); // Input 2
        this.addSlot(new SlotItemHandler(inv, 2, 116, 12)); // Input 3
        this.addSlot(new SlotItemHandler(inv, 3, 80, 58)); // Output

        // 2. ADD PLAYER INVENTORY (Standard MC layout)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // 3. ADD PLAYER HOTBAR
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            // IF THE ITEM IS IN THE MACHINE SLOTS (0-3)
            if (index < 4) {
                // Try moving it to the player inventory (slots 4-40)
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
            }
            // IF THE ITEM IS IN THE PLAYER INVENTORY (4-39)
            else {
                // 1. Try to move to the INPUT SLOTS (0, 1, 2)
                // Note: We stop at index 3 because index 3 is the Output slot.
                // We don't want players shift-clicking items into the output!
                if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {

                    // 2. If it couldn't go into the machine, move between Hotbar and Main Inventory
                    if (index < 31) { // From Main Inventory to Hotbar
                        if (!this.moveItemStackTo(itemstack1, 31, 40, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else { // From Hotbar to Main Inventory
                        if (!this.moveItemStackTo(itemstack1, 4, 31, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.blockEntity == null ? true : AbstractContainerMenu.stillValid(ContainerLevelAccess.create(this.blockEntity.getLevel(), this.blockEntity.getBlockPos()), player, ModBlocks.ALLOYING_SMELTER.get());
    }

    // Your quickMoveStack logic looks mostly okay, but ensure the indexes
    // match the 4 machine slots + 36 player slots!
}
