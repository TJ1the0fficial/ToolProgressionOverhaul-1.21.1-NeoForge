package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    // Get the parameters from GatherDataEvent.
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_INGOT, 3)
                .pattern("CC ")
                .pattern("CT ")
                .pattern("   ")
                .define('C', Items.COPPER_INGOT)
                .define('T', ModItems.TIN_INGOT)
                .unlockedBy("tin_ingot", has(ModItems.TIN_INGOT))
                .save(output);

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModItems.RAW_TIN),
                        RecipeCategory.MISC,
                        ModItems.TIN_INGOT,
                        0.5f,
                        200
                )
                .unlockedBy("has_raw_tin", has(ModItems.RAW_TIN))
                .save(output, "raw_tin_smelting");

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.RAW_TIN),
                        RecipeCategory.MISC,
                        ModItems.TIN_INGOT,
                        1.5f,
                        50
                )
                .unlockedBy("has_raw_tin", has(ModItems.RAW_TIN))
                .save(output, "raw_tin_blasting");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_SHOVEL)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', ModItems.BRONZE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_PICKAXE)
                .pattern("BBB")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', ModItems.BRONZE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_AXE)
                .pattern("BB ")
                .pattern("BS ")
                .pattern(" S ")
                .define('B', ModItems.BRONZE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_HOE)
                .pattern("BB ")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', ModItems.BRONZE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_SWORD)
                .pattern(" B ")
                .pattern(" B ")
                .pattern(" S ")
                .define('B', ModItems.BRONZE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_HELMET.get())
                .pattern("BBB")
                .pattern("B B")
                .pattern("   ")
                .define('B', ModItems.BRONZE_INGOT)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_CHESTPLATE.get())
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BRONZE_INGOT)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_LEGGINGS.get())
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', ModItems.BRONZE_INGOT)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_BOOTS.get())
                .pattern("   ")
                .pattern("B B")
                .pattern("B B")
                .define('B', ModItems.BRONZE_INGOT)
                .unlockedBy("bronze_ingot", has(ModItems.BRONZE_INGOT))
                .save(output);
    }
}
