package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class AlloyingRecipeProvider extends RecipeProvider {
    // Get the parameters from GatherDataEvent.
    public AlloyingRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

//    new AlloyingRecipeBuilder(new ItemStack(ModItems.BRONZE_INGOT.get(),4))
//            .addInput(ModItems.TIN_INGOT.get(),1)
//            .addInput(Items.COPPER_INGOT,3)
//            .unlockedBy("has_tin_ingot",has(ModItems.TIN_INGOT))
//            .save(output,"bronze_ingot_alloying");

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Add your recipes here.
    }
}
