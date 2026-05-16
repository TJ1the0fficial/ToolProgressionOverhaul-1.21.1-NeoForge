package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.openjdk.nashorn.internal.ir.annotations.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlloyingSmelterBlockEntity extends BlockEntity{
    public AlloyingSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALLOYING_SMELTER_BLOCK_ENTITY.get(), pos, state);
    }

    // Magic Numbers
    public static final int MIN_FUEL_PROGRESS = 14;
    int progress = 0;
    int maxProgress = 200;
    int fuelCapacity = 0;
    int maxFuelCapacity = 1000;
    int experience = 10;

    public final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> AlloyingSmelterBlockEntity.this.progress;
                case 1 -> AlloyingSmelterBlockEntity.this.maxProgress;
                case 2 -> AlloyingSmelterBlockEntity.this.fuelCapacity;
                case 3 -> AlloyingSmelterBlockEntity.this.maxFuelCapacity;
                case 4 -> AlloyingSmelterBlockEntity.this.experience;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> AlloyingSmelterBlockEntity.this.progress = value;
                case 1 -> AlloyingSmelterBlockEntity.this.maxProgress = value;
                case 2 -> AlloyingSmelterBlockEntity.this.fuelCapacity = value;
                case 3 -> AlloyingSmelterBlockEntity.this.maxFuelCapacity = value;
                case 4 -> AlloyingSmelterBlockEntity.this.experience = value;
            }
        }

        @Override
        public int getCount() {
            return 5; // Number of integers we are syncing
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
        this.maxProgress = tag.getInt("maxProgress");
        this.fuelCapacity = tag.getInt("fuelCapacity");
        this.maxFuelCapacity = tag.getInt("maxFuelCapacity");
        this.experience = tag.getInt("experience");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.inventory.serializeNBT(registries));
        tag.putInt("progress", this.progress);
        tag.putInt("maxProgress", this.maxProgress);
        tag.putInt("fuelCapacity", this.fuelCapacity);
        tag.putInt("maxFuelCapacity", this.maxFuelCapacity);
        tag.putInt("experience", this.experience);
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

    public int getBurnTimeIfFuel(ItemStack itemStack) {
//        int burnTime = this.level.getFuelValues().getBurnTime(itemStack);
        return Math.max(itemStack.getBurnTime(RecipeType.SMELTING), 0);
    }

    boolean recipeStarted = false;
    public static void tick(Level level, BlockPos pos, BlockState state, AlloyingSmelterBlockEntity blockEntity) {
        // Whatever you want to do during ticking.
        // For example, you could change a crafting progress value or consume power here.
        if (level.isClientSide) return;

//        System.out.println(
//                "########################################\n"+
//                "progress : "+blockEntity.progress+"\n"+
//                "maxProgress : "+blockEntity.maxProgress+"\n"+
//                "fuelCapacity : "+blockEntity.fuelCapacity+"\n"+
//                "maxFuelCapacity : "+blockEntity.maxFuelCapacity+"\n"+
//                "experience : "+blockEntity.experience+"\n"+
//                "------------------------------------\n"+
//                "progress : "+blockEntity.data.get(0)+"\n"+
//                "maxProgress : "+blockEntity.data.get(1)+"\n"+
//                "fuelCapacity : "+blockEntity.data.get(2)+"\n"+
//                "maxFuelCapacity : "+blockEntity.data.get(3)+"\n"+
//                "experience : "+blockEntity.data.get(4)+"\n"+
//                "########################################\n"
//        );

        // Variables
        ItemStack slotInput1 = blockEntity.inventory.getStackInSlot(0);
        ItemStack slotInput2 = blockEntity.inventory.getStackInSlot(1);
        ItemStack slotInput3 = blockEntity.inventory.getStackInSlot(2);
        ItemStack fuelSlot = blockEntity.inventory.getStackInSlot(3);

        AlloyingRecipeInput input = new AlloyingRecipeInput(
                slotInput1,
                slotInput2,
                slotInput3
        );

        Optional<RecipeHolder<AlloyingRecipe>> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.ALLOYING.get(), input, level);

        boolean lit = blockEntity.fuelCapacity > 0;
        recipe.ifPresent(alloyingRecipeRecipeHolder -> blockEntity.maxProgress = alloyingRecipeRecipeHolder.value().getDuration());
        if (blockEntity.data.get(1) != blockEntity.maxProgress) blockEntity.data.set(1,blockEntity.maxProgress);
        blockEntity.experience = blockEntity.data.get(4);
        //

        // Main
        if (blockEntity.fuelCapacity > 0) blockEntity.fuelCapacity--;

        if (slotInput1.is(Blocks.AIR.asItem()) && !blockEntity.recipeStarted && blockEntity.progress > 0) blockEntity.progress--;

        blockEntity.level.setBlock(pos, state.setValue(AlloyingSmelterEntityBlock.LIT, lit), 3);

        if (recipe.isPresent()) {
            AlloyingRecipe match = recipe.get().value();
            // The slots match! You can now check if you can fit the output
            // or increment your progress bar.
            ItemStack result = match.assemble(input, level.registryAccess());

            if (match.matches(new AlloyingRecipeInput(slotInput1,slotInput2,slotInput3),level)) {
                if ( // fuel consumption logic
                        blockEntity.getBurnTimeIfFuel(fuelSlot) != 0 &&
                        blockEntity.fuelCapacity == 0 &&
                        blockEntity.inventory.getStackInSlot(4).getCount() < blockEntity.inventory.getStackInSlot(4).getMaxStackSize()
                ) {
                    blockEntity.fuelCapacity = blockEntity.getBurnTimeIfFuel(fuelSlot);
                    blockEntity.maxFuelCapacity = blockEntity.getBurnTimeIfFuel(fuelSlot);
                    if (!fuelSlot.is(Tags.Items.BUCKETS)) blockEntity.inventory.extractItem(3,1,false);
                    else {
                        blockEntity.inventory.extractItem(3,1,false);
                        blockEntity.inventory.insertItem(3,new ItemStack(Items.BUCKET),false);
                    }
                }

                if (blockEntity.fuelCapacity > 0 && blockEntity.progress < blockEntity.maxProgress) {
                    if (    blockEntity.inventory.getStackInSlot(4).isEmpty() ||
                            blockEntity.inventory.getStackInSlot(4).getCount()+result.getCount() <= blockEntity.inventory.getStackInSlot(4).getMaxStackSize()
                    ) {
                        blockEntity.recipeStarted = true;
                        blockEntity.progress++;
                    }
                }
                else if (blockEntity.progress > 0) blockEntity.progress--;

                if (
                    slotInput1.is(Items.AIR) &&
                    slotInput2.is(Items.AIR) &&
                    slotInput3.is(Items.AIR) &&
                    blockEntity.progress > 0
                ) blockEntity.progress--;

                if (
                    blockEntity.progress == blockEntity.maxProgress
                ) { // this if part was rewritten by AI, so the player don't have to put the ingredients in a set sequence
                    blockEntity.progress = 0;

                    // --- NEW EXTRACTION LOGIC ---
                    // Keep track of slots we already "used" so we don't extract from the same slot twice
                    List<Integer> availableSlots = new ArrayList<>(List.of(0, 1, 2));

                    // Iterate through the ingredients defined in the recipe
                    // (Assuming getInputItems() returns your list of SizedIngredients/Ingredients)
                    for (var recipeIng : recipe.get().value().getInputItems()) {
                        for (int i = 0; i < availableSlots.size(); i++) {
                            int slotIndex = availableSlots.get(i);
                            ItemStack stackInSlot = blockEntity.inventory.getStackInSlot(slotIndex);

                            // If this slot matches the ingredient, consume from it
                            if (recipeIng.ingredient().test(stackInSlot)) {
                                blockEntity.inventory.extractItem(slotIndex, recipeIng.count(), false);
                                availableSlots.remove(i); // Don't use this slot for the next ingredient
                                break;
                            }
                        }
                    }
                    // --- END NEW EXTRACTION LOGIC ---

                    blockEntity.inventory.insertItem(4, result, false);
                    if (level instanceof ServerLevel serverLevel) {
                        ExperienceOrb.award(serverLevel, pos.getCenter(), blockEntity.experience);
                    }
                    blockEntity.recipeStarted = false;
                }
            }
        } else if (blockEntity.progress > 0) blockEntity.progress--;
        setChanged(level,pos,state);
    }
}
