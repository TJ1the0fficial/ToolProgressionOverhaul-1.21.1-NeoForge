package net.almmolt.toolprogressionoverhaul.tag;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    // Get parameters from GatherDataEvent.
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ToolProgressionOverhaul.MODID, existingFileHelper);
    }

    // Add your tag entries here.
    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(ModTags.MINEABLE_BY_PICKAXE)
                .add(ModBlocks.TIN_ORE.block().get())
                .add(ModBlocks.TIN_BLOCK.block().get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.block().get())
                .add(ModBlocks.RAW_TIN_BLOCK.block().get())
                .add(ModBlocks.BRONZE_BLOCK.block().get())
                .add(ModBlocks.ALLOYING_SMELTER.get())
                .add(ModBlocks.NICKEL_ORE.block().get())
                .add(ModBlocks.DEEPSLATE_NICKEL_ORE.block().get())
                .add(ModBlocks.SILVER_ORE.block().get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.block().get())
                .add(ModBlocks.NICKEL_BLOCK.block().get())
                .add(ModBlocks.RAW_NICKEL_BLOCK.block().get())
                .add(ModBlocks.SILVER_BLOCK.block().get())
                .add(ModBlocks.RAW_SILVER_BLOCK.block().get())
                .add(ModBlocks.INVAR_BLOCK.block().get())
                .add(ModBlocks.CRUSHER.get());

        tag(ModTags.TIN_ORES)
                .add(ModBlocks.TIN_ORE.block().get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.block().get());

        // Invar
        tag(ModTags.INCORRECT_FOR_INVAR_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        // Silver
        tag(ModTags.INCORRECT_FOR_SILVER_TOOL)
                .addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.NEEDS_SILVER_TOOL)
                .addTag(ModTags.NEEDS_BRONZE_TOOL);

        // Bronze
        tag(ModTags.INCORRECT_FOR_BRONZE_TOOL)
                .addTag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        tag(ModTags.NEEDS_BRONZE_TOOL)
                .addTag(Tags.Blocks.ORES_IRON)
                .add(Blocks.RAW_IRON_BLOCK);

        // Iron
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.NICKEL_ORE.block().get())
                .add(ModBlocks.DEEPSLATE_NICKEL_ORE.block().get())
                .add(ModBlocks.SILVER_ORE.block().get())
                .add(ModBlocks.DEEPSLATE_SILVER_ORE.block().get())
                .add(ModBlocks.NICKEL_BLOCK.block().get())
                .add(ModBlocks.RAW_NICKEL_BLOCK.block().get())
                .add(ModBlocks.SILVER_BLOCK.block().get())
                .add(ModBlocks.RAW_SILVER_BLOCK.block().get());
        tag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .add(Blocks.DIAMOND_ORE)
                .add(Blocks.DEEPSLATE_DIAMOND_ORE)
                .add(Blocks.DIAMOND_BLOCK);

        // Gold
        tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
                .remove(BlockTags.NEEDS_IRON_TOOL);

        // Stone
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.TIN_ORE.block().get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.block().get())
                .add(ModBlocks.TIN_BLOCK.block().get())
                .add(ModBlocks.RAW_TIN_BLOCK.block().get())
                .add(ModBlocks.BRONZE_BLOCK.block().get());
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .addTag(ModTags.NEEDS_BRONZE_TOOL);

        // Wood
        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .addTag(ModTags.NEEDS_BRONZE_TOOL)
                .add(ModBlocks.TIN_ORE.block().get())
                .add(ModBlocks.DEEPSLATE_TIN_ORE.block().get());
    }
}
