package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AlloyingSmelterBlockEntity extends BlockEntity implements MenuProvider {
    // This is the actual inventory (3 inputs + 1 output = 4 slots)
    public static final int SIZE = 4;

    public final ItemStackHandler inventory = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged(); // Tells Minecraft to save the block to disk
        }
    };

    public AlloyingSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOYING_SMELTER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Alloyer");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        // We pass 'this' (the BlockEntity) and its inventory to the menu
        return new AlloyingSmelterMenu(containerId, playerInventory, this);
    }

    // IMPORTANT: You must override these so items aren't deleted when you leave the game
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", inventory.serializeNBT(registries));
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }
}
