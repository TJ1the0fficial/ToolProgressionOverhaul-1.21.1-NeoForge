package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import java.util.Optional;

public class AlloyingSmelterRecipe implements Recipe<AlloyingSmelterRecipeInput> {
    // An in-code representation of our recipe data. This can be basically anything you want.
    // Common things to have here is a processing time integer of some kind, or an experience reward.
    // Note that we now use an ingredient instead of an item stack for the input.
    private final BlockState inputState;
    private final Ingredient inputItem;
    private final ItemStack result;

    // Add a constructor that sets all properties.
    public AlloyingSmelterRecipe(BlockState inputState, Ingredient inputItem, ItemStack result) {
        this.inputState = inputState;
        this.inputItem = inputItem;
        this.result = result;
    }

    // A list of our ingredients. Does not need to be overridden if you have no ingredients
    // (the default implementation returns an empty list here). It makes sense to cache larger lists in a field.
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AlloyingSmelterRecipeSerializer.ALLOYING_SMELTER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALLOYING_SMELTER_RECIPE.get();
    }

    // Grid-based recipes should return whether their recipe can fit in the given dimensions.
    // We don't have a grid, so we just return if any item can be placed in there.
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    // Check whether the given input matches this recipe. The first parameter matches the generic.
    // We check our blockstate and our item stack, and only return true if both match.
    @Override
    public boolean matches(AlloyingSmelterRecipeInput input, Level level) {
        return this.inputState == input.state() && this.inputItem.test(input.stack());
    }

    // Return an UNMODIFIABLE version of your result here. The result of this method is mainly intended
    // for the recipe book, and commonly used by JEI and other recipe viewers as well.
    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    // Return the result of the recipe here, based on the given input. The first parameter matches the generic.
    // IMPORTANT: Always call .copy() if you use an existing result! If you don't, things can and will break,
    // as the result exists once per recipe, but the assembled stack is created each time the recipe is crafted.
    @Override
    public ItemStack assemble(AlloyingSmelterRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    public ItemStack getResult() {
        return result;
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public BlockState getInputState() {
        return inputState;
    }

    // This example outlines the most important methods. There is a number of other methods to override.
    // Check the class definition of Recipe to view them all.

    @SubscribeEvent // on the game event bus
    public static void useItemOnBlock(UseItemOnBlockEvent event) {
        // Skip if we are not in the block-dictated phase of the event. See the event's javadocs for details.
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.BLOCK) return;
        // Get the parameters we need.
        UseOnContext context = event.getUseOnContext();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        ItemStack itemStack = context.getItemInHand();
        RecipeManager recipes = level.getRecipeManager();
        // Create an input and query the recipe.
        AlloyingSmelterRecipeInput input = new AlloyingSmelterRecipeInput(blockState, itemStack);
        Optional<RecipeHolder<AlloyingSmelterRecipe>> optional = recipes.getRecipeFor(
                // The recipe type.
                ModRecipes.ALLOYING_SMELTER_RECIPE.get(),
                input,
                level
        );
        ItemStack result = optional
                .map(RecipeHolder::value)
                .map(e -> e.assemble(input, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
        // If there is a result, break the block and drop the result in the world.
        if (!result.isEmpty()) {
            level.removeBlock(pos, false);
            // If the level is not a server level, don't spawn the entity.
            if (!level.isClientSide()) {
                ItemEntity entity = new ItemEntity(level,
                        // Center of pos.
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        result);
                level.addFreshEntity(entity);
            }
            // Cancel the event to stop the interaction pipeline.
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
