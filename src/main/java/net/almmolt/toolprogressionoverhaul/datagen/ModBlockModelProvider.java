package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterEntityBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
//        horizontalBlock(
//                ModBlocks.ALLOYING_SMELTER.get(),
//                ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/alloying_smelter_sides"),
//                ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/alloying_smelter_front"),
//                ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/alloying_smelter_top")
//        );
        // 1. Create the "Off" model
        ModelFile offModel = models().orientable("alloying_smelter",
                modLoc("block/alloying_smelter_sides"),
                modLoc("block/alloying_smelter_front"),
                modLoc("block/alloying_smelter_top"));

        // 2. Create the "On" model (pointing to the _on texture)
        ModelFile onModel = models().orientable("alloying_smelter_on",
                modLoc("block/alloying_smelter_sides"),
                modLoc("block/alloying_smelter_front_alloying"),
                modLoc("block/alloying_smelter_top"));

        // 3. Build the BlockState with variants
        horizontalBlock(ModBlocks.ALLOYING_SMELTER.get(),
                state -> state.getValue(AlloyingSmelterEntityBlock.LIT) ? onModel : offModel
        );

        simpleBlockItem(ModBlocks.ALLOYING_SMELTER.get(),offModel);
    }
}