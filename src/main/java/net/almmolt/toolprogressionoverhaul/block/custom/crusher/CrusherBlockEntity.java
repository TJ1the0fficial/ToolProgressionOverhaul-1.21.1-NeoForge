package net.almmolt.toolprogressionoverhaul.block.custom.crusher;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;
import java.util.Optional;

public class CrusherBlockEntity extends BlockEntity {
    public CrusherBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CRUSHER_BLOCK_ENTITY.get(), pos, blockState);
    }

    int progress;
    int maxProgress;
    boolean lastActiveState;

    // 1. A simple inventory that doesn't AMblock the machine's own code
    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    // 2. These "Sided Wrappers" tell Hoppers what they are allowed to touch
    private final IItemHandler modifiableInput = new net.neoforged.neoforge.items.wrapper.RangedWrapper(inventory, 1, 2); // Slot 1 only
    private final IItemHandler modifiableOutput = new net.neoforged.neoforge.items.wrapper.RangedWrapper(inventory, 2, 3); // Slot 2 only

    // 3. This method returns the correct handler based on the side
    public IItemHandler getItemHandlerCapability(Direction side) {
        if (side == Direction.DOWN) return modifiableOutput; // Bottom = Output
        return modifiableInput; // Any other side = Input
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        // Use a unique key like "crusher_inv"
        tag.put("crusher_inv", this.inventory.serializeNBT(registries));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("crusher_inv")) {
            this.inventory.deserializeNBT(registries, tag.getCompound("crusher_inv"));
        }

        // --- LOUD DEBUG ---
//        if (this.level != null && this.level.isClientSide) {
//            ItemStack wheel = this.inventory.getStackInSlot(0);
//            System.out.println("CLIENT DATA RECEIVED! Slot 0 now contains: " + wheel.getItem());
//        }
    }

    // Ensure these three are exactly like this:
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider registries) {
        // Manually trigger the load logic when the packet arrives
        this.loadAdditional(pkt.getTag(), registries);
    }

    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2; // Number of integers we are syncing
        }
    };

    static void tick(Level level, BlockPos pos, BlockState state, CrusherBlockEntity blockEntity) {
        if (level.isClientSide) return;

        ItemStack wheel = blockEntity.inventory.getStackInSlot(0);
        ItemStack inputSlot = blockEntity.inventory.getStackInSlot(1);
        CrushingRecipeInput recipeInput = new CrushingRecipeInput(wheel, inputSlot);

        // 1. Sync Working State
        boolean isActuallyWorking = blockEntity.progress > 0;
        if (state.getValue(CrusherEntityBlock.WORKING) != isActuallyWorking) {
            level.setBlock(pos, state.setValue(CrusherEntityBlock.WORKING, isActuallyWorking), 3);
        }

        // 2. Item Suction (Top side only)
        AABB searchBox = new AABB(pos.above());
        List<ItemEntity> itemsAbove = level.getEntitiesOfClass(ItemEntity.class, searchBox);
        for (ItemEntity itemEntity : itemsAbove) {
            if (!itemEntity.isAlive()) continue;
            if (itemEntity.getItem().is(ModTags.INCORRECT_FOR_CRUSHER)) return;
            if (itemEntity.hasPickUpDelay()) continue;
            // Move item to input slot
            ItemStack remaining = blockEntity.inventory.insertItem(1, itemEntity.getItem().copy(), false);
            if (remaining.getCount() < itemEntity.getItem().getCount()) {
                if (remaining.isEmpty()) itemEntity.discard();
                else itemEntity.setItem(remaining);
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }

        // 3. Recipe Logic
        Optional<RecipeHolder<CrushingRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.CRUSHING.get(), recipeInput, level);

        if (recipe.isPresent()) {
            CrushingRecipe match = recipe.get().value();
            blockEntity.maxProgress = match.getDuration();
            ItemStack result = match.assemble(recipeInput, level.registryAccess());

            // Check if Slot 2 (Output) can fit the result
            // We use simulate=true to check space
            if (!result.isEmpty() && blockEntity.inventory.insertItem(2, result, true).isEmpty()) {
                if (blockEntity.progress < blockEntity.maxProgress) {
                    blockEntity.progress++;
                    if (level.getGameTime() % 2 == 0) {
                        level.playSound(null, pos, SoundEvents.BASALT_HIT, SoundSource.BLOCKS, 0.5f, 1.5f);
                    }
                } else {
                    // SUCCESS: Add to output slot
                    blockEntity.inventory.insertItem(2, result.copy(), false);
                    // CONSUME: Take 1 from input (Directly from slot to avoid sidedness blocks)
                    blockEntity.inventory.getStackInSlot(1).shrink(1);
                    blockEntity.progress = 0;
                }
            }
        } else {
            if (blockEntity.progress > 0) blockEntity.progress--;
        }

        // --- UPDATED PASSIVE OUTPUT LOGIC ---
        ItemStack outputBuffer = blockEntity.inventory.getStackInSlot(2);

        if (!outputBuffer.isEmpty()) {
            BlockPos below = pos.below();

            // Check if the AMblock below is AIR or something you can't put items into
            // We only "spit" if the bottom is open.
            // If it's a Chest, we stay quiet and wait for a Hopper to pull it out.
            if (!level.getBlockState(below).is(Blocks.HOPPER)) {
                ItemEntity entity = new ItemEntity(level,
                        pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                        outputBuffer.copy());

                entity.setPickUpDelay(30);
                entity.setDeltaMovement(0, 0.2, 0);
                level.addFreshEntity(entity);

                // Clear the buffer because we threw it in the world
                blockEntity.inventory.setStackInSlot(2, ItemStack.EMPTY);
            }
            // If it's NOT air, we do NOTHING.
            // Slot 2 acts as a storage. If a Hopper is placed below,
            // the Hopper's own code will pull the item out.
        }
        setChanged(level, pos, state);
    }
}
