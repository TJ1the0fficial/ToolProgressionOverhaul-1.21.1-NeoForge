package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockStateProvider {
    // Parameter values are provided by GatherDataEvent.
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ToolProgressionOverhaul.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.TIN_ORE.get(),cubeAll(ModBlocks.TIN_ORE.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_TIN_ORE.get(),cubeAll(ModBlocks.DEEPSLATE_TIN_ORE.get()));
        simpleBlockWithItem(ModBlocks.TIN_BLOCK.get(),cubeAll(ModBlocks.TIN_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.RAW_TIN_BLOCK.get(),cubeAll(ModBlocks.RAW_TIN_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.BRONZE_BLOCK.get(),cubeAll(ModBlocks.BRONZE_BLOCK.get()));
        horizontalBlock(
                ModBlocks.ALLOYING_SMELTER.get(),
                ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/alloying_smelter_sides"),
                ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/alloying_smelter_front"),
                ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/alloying_smelter_top")
        );
    }
}