package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AlloyingSmelterBlockEntity extends BlockEntity{
    public AlloyingSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOYING_SMELTER_BLOCK_ENTITY.get(), pos, state);
    }

    // Magic Numbers
    public static final int MIN_FUEL_PROGRESS = 14;
    public static final int MAX_FUEL = 1000;

    public final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };
    int progress = 0;
    int fuelCapacity = 0;
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AlloyingSmelterBlockEntity.this.progress;
                case 1 -> AlloyingSmelterBlockEntity.this.fuelCapacity;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> AlloyingSmelterBlockEntity.this.progress = value;
                case 1 -> AlloyingSmelterBlockEntity.this.fuelCapacity = value;
            }
        }

        @Override
        public int getCount() {
            return 2; // Number of integers we are syncing
        }
    };

    // Create an update tag here, like above.
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.fuelCapacity = tag.getInt("fuelCapacity");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.inventory.serializeNBT(registries));
        tag.putInt("progress", this.progress);
        tag.putInt("fuelCapacity", this.fuelCapacity);
    }

    public ContainerData getData() {
        return this.data;
    }

    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Optionally: Run some custom logic when the packet is received.
    // The super/default implementation forwards to #loadAdditional.
    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
        // Do whatever you need to do here.
    }

    boolean recipeStarted = false;
    public static void tick(Level level, BlockPos pos, BlockState state, AlloyingSmelterBlockEntity blockEntity) {
        // Whatever you want to do during ticking.
        // For example, you could change a crafting progress value or consume power here.
        if (!level.isClientSide) {
            if (
                blockEntity.inventory.getStackInSlot(3).is(Items.COAL) &&
                blockEntity.fuelCapacity == 0 &&
                blockEntity.inventory.getStackInSlot(0).is(Items.COPPER_INGOT) &&
                blockEntity.inventory.getStackInSlot(0).getCount() >= 3 &&
                blockEntity.inventory.getStackInSlot(1).is(ModItems.TIN_INGOT)
            ) {
                blockEntity.inventory.extractItem(3,1,false);
                blockEntity.fuelCapacity = AlloyingSmelterBlockEntity.MAX_FUEL;
                blockEntity.recipeStarted = true;
            }
            if (
                blockEntity.progress >= 200 &&
                blockEntity.inventory.getStackInSlot(0).is(Items.COPPER_INGOT) &&
                blockEntity.inventory.getStackInSlot(0).getCount() >= 3 &&
                blockEntity.inventory.getStackInSlot(1).is(ModItems.TIN_INGOT)
            ) {
                blockEntity.inventory.insertItem(4,new ItemStack(ModItems.BRONZE_INGOT.get(),4),false);
                blockEntity.inventory.extractItem(0,3,false);
                blockEntity.inventory.extractItem(1,1,false);
                blockEntity.progress = 0;
                blockEntity.recipeStarted = false;
            }
            if (
                    blockEntity.inventory.getStackInSlot(0).is(Items.COPPER_INGOT) &&
                    blockEntity.inventory.getStackInSlot(0).getCount() >= 3 &&
                    blockEntity.inventory.getStackInSlot(1).is(ModItems.TIN_INGOT) &&
                    blockEntity.fuelCapacity > 0
            ) {
                blockEntity.progress++;
            } else if (blockEntity.fuelCapacity == 0 && blockEntity.progress > 0) {
                blockEntity.progress--;
            } else if (
                !blockEntity.inventory.getStackInSlot(0).is(Items.COPPER_INGOT) &&
                blockEntity.inventory.getStackInSlot(0).getCount() < 3 &&
                !blockEntity.inventory.getStackInSlot(1).is(ModItems.TIN_INGOT) &&
                blockEntity.progress > 0
            ) {
                blockEntity.progress--;
            }
            if (blockEntity.progress < 0) blockEntity.progress = 0;
            else if (blockEntity.fuelCapacity == 0) level.setBlock(pos, state.setValue(AlloyingSmelterEntityBlock.LIT, false), 3);
            else level.setBlock(pos, state.setValue(AlloyingSmelterEntityBlock.LIT, true), 3);
            if (blockEntity.fuelCapacity > 0) blockEntity.fuelCapacity--;
            blockEntity.setChanged();
        }
    }
}
