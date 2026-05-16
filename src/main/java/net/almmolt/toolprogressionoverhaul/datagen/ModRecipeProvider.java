package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingRecipeBuilder;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipeBuilder;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMrecipe;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMsimpleItem;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMrecipe.*;

public class ModRecipeProvider extends RecipeProvider {
    // Get the parameters from GatherDataEvent.
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Tin stuff
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_DUST.get(), 3)
                .requires(ModItems.COPPER_DUST.get(),3)
                .requires(ModItems.TIN_DUST.get())
                .unlockedBy("tin_ingot", has(ModItems.TIN_DUST.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "bronze_ingot_from_alloying"));

        // Raw Tin
        craftingRecipe_smeltAndBlast(ModItems.RAW_TIN.get(),ModItems.TIN_INGOT.get(),output);

        // Smelt/Blast from blocks
        craftingRecipe_smeltAndBlast(ModBlocks.TIN_ORE.blockItem().get(), ModItems.TIN_INGOT.get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.DEEPSLATE_TIN_ORE.blockItem().get(), ModItems.TIN_INGOT.get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.RAW_TIN_BLOCK.blockItem().get(), ModBlocks.TIN_BLOCK.blockItem().get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.RAW_NICKEL_BLOCK.blockItem().get(), ModBlocks.NICKEL_BLOCK.blockItem().get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.RAW_SILVER_BLOCK.blockItem().get(), ModBlocks.SILVER_BLOCK.blockItem().get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.SILVER_ORE.blockItem().get(), ModItems.SILVER_INGOT.get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.DEEPSLATE_SILVER_ORE.blockItem().get(), ModItems.SILVER_INGOT.get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.NICKEL_ORE.blockItem().get(), ModItems.NICKEL_INGOT.get(),output);
        craftingRecipe_smeltAndBlast(ModBlocks.DEEPSLATE_NICKEL_ORE.blockItem().get(), ModItems.NICKEL_INGOT.get(),output);

        // Bronze Tools / Armor
        crafingRecipe_Tools(ModItems.BRONZE_TOOLS,ModItems.BRONZE_INGOT.get(),output);
        craftingRecipe_Armor(ModItems.BRONZE_ARMOR,ModItems.BRONZE_INGOT.get(),output);
        //
        // Silver Tools / Armor
        crafingRecipe_Tools(ModItems.SILVER_TOOLS,ModItems.SILVER_INGOT.get(),output);
        craftingRecipe_Armor(ModItems.SILVER_ARMOR,ModItems.SILVER_INGOT.get(),output);
        //
        // Invar Tools / Armor
        crafingRecipe_Tools(ModItems.INVAR_TOOLS,ModItems.INVAR_INGOT.get(),output);
        craftingRecipe_Armor(ModItems.INVAR_ARMOR,ModItems.INVAR_INGOT.get(),output);
        //

        // material to blocks
        craftingRecipe_itemAndBlock(ModItems.TIN_INGOT.get(),ModBlocks.TIN_BLOCK.block().get(),output);
        craftingRecipe_itemAndBlock(ModItems.BRONZE_INGOT.get(),ModBlocks.BRONZE_BLOCK.block().get(),output);
        craftingRecipe_itemAndBlock(ModItems.NICKEL_INGOT.get(),ModBlocks.NICKEL_BLOCK.block().get(),output);
        craftingRecipe_itemAndBlock(ModItems.SILVER_INGOT.get(),ModBlocks.SILVER_BLOCK.block().get(),output);
        craftingRecipe_itemAndBlock(ModItems.INVAR_INGOT.get(),ModBlocks.INVAR_BLOCK.block().get(),output);

        craftingRecipe_itemAndBlock(ModItems.RAW_TIN.get(),ModBlocks.RAW_TIN_BLOCK.block().get(),output);
        craftingRecipe_itemAndBlock(ModItems.RAW_NICKEL.get(),ModBlocks.RAW_NICKEL_BLOCK.block().get(),output);
        craftingRecipe_itemAndBlock(ModItems.RAW_SILVER.get(),ModBlocks.RAW_SILVER_BLOCK.block().get(),output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALLOYING_SMELTER_ASITEM.get())
                .pattern("TBT")
                .pattern("BFB")
                .pattern("RbR")
                .define('T', ModItems.TIN_INGOT)
                .define('B', ModItems.BRONZE_INGOT)
                .define('b', Blocks.BRICKS.asItem())
                .define('R', Items.REDSTONE)
                .define('F', Blocks.BLAST_FURNACE.asItem())
                .unlockedBy("iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        // to dust
        craftingRecipe_toDust(ModItems.TIN_DUST, new HashMap<>(Map.of(
                ModItems.RAW_TIN.get(),2,
                ModItems.TIN_INGOT.get(),1
        )),output);

        craftingRecipe_toDust(ModItems.BRONZE_DUST, new HashMap<>(Map.of(
                ModItems.BRONZE_INGOT.get(),1
        )),output);

        craftingRecipe_toDust(ModItems.IRON_DUST, new HashMap<>(Map.of(
                Items.RAW_IRON,2,
                Items.IRON_INGOT,1
        )),output);

        craftingRecipe_toDust(ModItems.COPPER_DUST, new HashMap<>(Map.of(
                Items.RAW_COPPER,2,
                Items.COPPER_INGOT,1
        )),output);

        craftingRecipe_toDust(ModItems.NICKEL_DUST, new HashMap<>(Map.of(
                ModItems.RAW_NICKEL.get(),2,
                ModItems.NICKEL_INGOT.get(),1
        )),output);

        craftingRecipe_toDust(ModItems.SILVER_DUST, new HashMap<>(Map.of(
                ModItems.RAW_SILVER.get(),2,
                ModItems.SILVER_INGOT.get(),1
        )),output);

        craftingRecipe_toDust(ModItems.INVAR_DUST, new HashMap<>(Map.of(
                ModItems.INVAR_INGOT.get(),1
        )),output);
        //

        new AlloyingRecipeBuilder(new ItemStack(ModItems.BRONZE_INGOT.get(),4))
                .addInput(ModItems.TIN_DUST.get(),1)
                .addInput(ModItems.COPPER_DUST.get(),3)
                .setDuration(200)
                .unlockedBy("has_tin_dust",has(ModItems.TIN_DUST.get()))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"bronze_ingot_alloying_from_dust"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_HAMMER)
                .pattern("BBB")
                .pattern("BSB")
                .pattern(" S ")
                .define('B', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(output);

        // stone tools below
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_HAMMER)
                .pattern("BKB")
                .pattern("BSB")
                .pattern(" S ")
                .define('B', ModTags.STONES)
                .define('S', Items.STICK)
                .define('K', Items.SMOOTH_STONE)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_SHOVEL)
                .pattern(" B ")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', ModTags.STONES)
                .define('S', Items.STICK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_PICKAXE)
                .pattern("BBB")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', ModTags.STONES)
                .define('S', Items.STICK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_AXE)
                .pattern("BB ")
                .pattern("BS ")
                .pattern(" S ")
                .define('B', ModTags.STONES)
                .define('S', Items.STICK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_HOE)
                .pattern("BB ")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', ModTags.STONES)
                .define('S', Items.STICK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STONE_SWORD)
                .pattern(" B ")
                .pattern(" B ")
                .pattern(" S ")
                .define('B', ModTags.STONES)
                .define('S', Items.STICK)
                .unlockedBy("has_stone", has(Items.STONE))
                .save(output);
        // stone tools above

        new AlloyingRecipeBuilder(new ItemStack(ModItems.INVAR_INGOT.get(),3))
                .addInput(ModItems.IRON_DUST.get(),2)
                .addInput(ModItems.NICKEL_DUST.get(),1)
                .setDuration(200)
                .unlockedBy("has_iron_dust",has(ModItems.IRON_DUST.get()))
                .save(output,ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"invar_ingot_alloying_from_dust"));

        craftingRecipe_smeltAndBlast(ModItems.RAW_NICKEL.get(),ModItems.NICKEL_INGOT.get(),output);
        craftingRecipe_smeltAndBlast(ModItems.RAW_SILVER.get(),ModItems.SILVER_INGOT.get(),output);
        craftingRecipe_smeltAndBlast(Blocks.RAW_COPPER_BLOCK.asItem(),Blocks.COPPER_BLOCK.asItem(),output);
        craftingRecipe_smeltAndBlast(Blocks.RAW_IRON_BLOCK.asItem(),Blocks.IRON_BLOCK.asItem(),output);
        craftingRecipe_smeltAndBlast(Blocks.RAW_GOLD_BLOCK.asItem(),Blocks.GOLD_BLOCK.asItem(),output);

        // dust smelt+blast recipes
        craftingRecipe_smeltAndBlast(ModItems.IRON_DUST.get(),Items.IRON_INGOT,output);

        craftingRecipe_smeltAndBlast(ModItems.TIN_DUST.get(),ModItems.TIN_INGOT.get(),output);

        craftingRecipe_smeltAndBlast(ModItems.COPPER_DUST.get(),Items.COPPER_INGOT,output);

        craftingRecipe_smeltAndBlast(ModItems.BRONZE_DUST.get(),ModItems.BRONZE_INGOT.get(),output);

        craftingRecipe_smeltAndBlast(ModItems.SILVER_DUST.get(),ModItems.SILVER_INGOT.get(),output);

        craftingRecipe_smeltAndBlast(ModItems.NICKEL_DUST.get(),ModItems.NICKEL_INGOT.get(),output);

        craftingRecipe_smeltAndBlast(ModItems.INVAR_DUST.get(),ModItems.INVAR_INGOT.get(),output);

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(ModItems.COAL_DUST),
                        RecipeCategory.MISC,
                        ModItems.COKE,
                        0.5f,
                        150
                )
                .unlockedBy(itemId(ModItems.COAL_DUST.get()), has(ModItems.COAL_DUST))
                .save(output,lazyOutput(ModItems.COAL_DUST.get(),ModItems.COKE.get(),"blasting"));
        //

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.FLINT_AND_STEEL)
                .pattern(" S ")
                .pattern("F  ")
                .pattern("   ")
                .define('F',Items.FLINT)
                .define('S',Items.DIAMOND)
                .unlockedBy("has_steel",has(Items.DIAMOND))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_CRUSHING_WHEEL)
                .pattern(" IB")
                .pattern("INI")
                .pattern("BI ")
                .define('B',Items.IRON_BLOCK)
                .define('I',Items.IRON_INGOT)
                .define('N',ModItems.INVAR_INGOT)
                .unlockedBy("has_invar",has(ModItems.INVAR_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SILVER_CRUSHING_WHEEL)
                .pattern(" IB")
                .pattern("INI")
                .pattern("BI ")
                .define('B',ModBlocks.SILVER_BLOCK.blockItem())
                .define('I',ModItems.SILVER_INGOT)
                .define('N',ModItems.INVAR_INGOT)
                .unlockedBy("has_invar",has(ModItems.INVAR_INGOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModBlocks.CRUSHER_ASITEM)
                .pattern("C C")
                .pattern("C C")
                .pattern("NRN")
                .define('C',Tags.Items.STONES)
                .define('R',Items.REDSTONE_BLOCK)
                .define('N',ModBlocks.INVAR_BLOCK.blockItem())
                .unlockedBy("has_invar",has(ModItems.INVAR_INGOT))
                .save(output);

        new CrushingRecipeBuilder(new ItemStack(Items.GRAVEL))
                .setDuration(200)
                .setWheel(ModItems.IRON_CRUSHING_WHEEL.asItem())
                .addInput(Items.COBBLESTONE)
                .save(output,AMrecipe.lazyOutput(Items.COBBLESTONE,Items.GRAVEL,"crushing"));

        new CrushingRecipeBuilder(new ItemStack(Items.DIRT))
                .setDuration(200)
                .setWheel(ModItems.IRON_CRUSHING_WHEEL.asItem())
                .addInput(Items.GRAVEL)
                .save(output,AMrecipe.lazyOutput(Items.GRAVEL,Items.DIRT,"crushing"));

        new CrushingRecipeBuilder(new ItemStack(Items.SAND))
                .setDuration(200)
                .setWheel(ModItems.IRON_CRUSHING_WHEEL.asItem())
                .addInput(Items.DIRT)
                .save(output,AMrecipe.lazyOutput(Items.DIRT,Items.SAND,"crushing"));

        // dust by crusher
        AMrecipe.crushWithAll(new HashMap<>(Map.of(Items.RAW_IRON,2,Items.IRON_INGOT,1)),ModItems.IRON_DUST.get(),200,output);
        AMrecipe.crushWithAll(new HashMap<>(Map.of(Items.RAW_COPPER,2,Items.COPPER_INGOT,1)),ModItems.COPPER_DUST.get(),200,output);
        AMrecipe.crushWithAll(new HashMap<>(Map.of(Items.COAL,2,Items.CHARCOAL,1)),ModItems.COAL_DUST.get(),200,output);
        AMrecipe.crushWithAll(new HashMap<>(Map.of(Items.RAW_COPPER,2,Items.COPPER_INGOT,1)),ModItems.TIN_DUST.get(),200,output);
        AMrecipe.crushWithAll(ModItems.BRONZE_INGOT.get(),ModItems.BRONZE_DUST.get(),10,output);
        AMrecipe.crushWithAll(new HashMap<>(Map.of(ModItems.RAW_NICKEL.get(),2,ModItems.NICKEL_INGOT.get(),1)),ModItems.NICKEL_DUST.get(),200,output);
        AMrecipe.crushWithAll(new HashMap<>(Map.of(ModItems.RAW_SILVER.get(),2,ModItems.SILVER_INGOT.get(),1)),ModItems.SILVER_DUST.get(),200,output);
        AMrecipe.crushWithAll(ModItems.INVAR_INGOT.get(),ModItems.INVAR_DUST.get(),200,output);
        //
    }
}
