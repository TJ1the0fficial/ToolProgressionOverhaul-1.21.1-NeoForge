package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterEntityBlock;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMblock;
import net.minecraft.data.PackOutput;
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
        for (AMblock.BlockSet blockSet : AMblock.registeredBlocks) {
            simpleBlockWithItem(blockSet.block().get(),cubeAll(blockSet.block().get()));
        }
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

        // CRUSHER
        ModelFile crusherModel = models().cubeBottomTop("crusher",
                modLoc("block/crusher_side"),
                modLoc("block/crusher_bottom"),
                modLoc("block/crusher_top")
        );
        horizontalBlock(ModBlocks.CRUSHER.get(), crusherModel);
        simpleBlockItem(ModBlocks.CRUSHER.get(), crusherModel);
    }
}