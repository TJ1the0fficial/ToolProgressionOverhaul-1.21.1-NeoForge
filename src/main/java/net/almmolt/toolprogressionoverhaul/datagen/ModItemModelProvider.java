package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ToolProgressionOverhaul.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.TIN_INGOT.get());
        basicItem(ModItems.RAW_TIN.get());
        basicItem(ModItems.BRONZE_INGOT.get());

        handheldItem(ModItems.BRONZE_SWORD.get());
        handheldItem(ModItems.BRONZE_AXE.get());
        handheldItem(ModItems.BRONZE_PICKAXE.get());
        handheldItem(ModItems.BRONZE_SHOVEL.get());
        handheldItem(ModItems.BRONZE_HOE.get());

        basicItem(ModItems.BRONZE_HELMET.get());
        basicItem(ModItems.BRONZE_CHESTPLATE.get());
        basicItem(ModItems.BRONZE_LEGGINGS.get());
        basicItem(ModItems.BRONZE_BOOTS.get());

        simpleBlockItem(ModBlocks.ALLOYING_SMELTER.get());
    }
}
