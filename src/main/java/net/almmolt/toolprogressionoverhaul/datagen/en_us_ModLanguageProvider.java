package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class en_us_ModLanguageProvider extends LanguageProvider {
    public en_us_ModLanguageProvider(PackOutput output) {
        super(
                // Provided by the GatherDataEvent.
                output,
                // Your mod id.
                ToolProgressionOverhaul.MODID,
                // The locale to use. You may use multiple language providers for different locales.
                "en_us"
        );
    }

    @Override
    protected void addTranslations() {
        // Items
        addItem(ModItems.TIN_INGOT,"Tin Ingot");
        addItem(ModItems.RAW_TIN,"Raw Tin");
        addItem(ModItems.BRONZE_INGOT, "Bronze Ingot");
        // - Tools
        addItem(ModItems.BRONZE_SHOVEL, "Bronze Shovel");
        addItem(ModItems.BRONZE_PICKAXE, "Bronze Pickaxe");
        addItem(ModItems.BRONZE_AXE, "Bronze Axe");
        addItem(ModItems.BRONZE_HOE, "Bronze Hoe");
        addItem(ModItems.BRONZE_SWORD, "Bronze Sword");
        // - Armors
        addItem(ModItems.BRONZE_HELMET, "Bronze Helmet");
        addItem(ModItems.BRONZE_CHESTPLATE, "Bronze Chestplate");
        addItem(ModItems.BRONZE_LEGGINGS, "Bronze Leggings");
        addItem(ModItems.BRONZE_BOOTS, "Bronze Boots");

        // Blocks
        addBlock(ModBlocks.TIN_ORE, "Tin Ore");
        addBlock(ModBlocks.TIN_DEEPSLATE_ORE, "Tin Deepslate Ore");

        // Advancements
        add("advancements."+ToolProgressionOverhaul.MODID+".road_to_growth.title","Road to Overhaul");
        add("advancements."+ToolProgressionOverhaul.MODID+".road_to_growth.description","Smelt Raw Tin");

        add("advancements."+ToolProgressionOverhaul.MODID+".tin_ingot.title","Obtained Tin Ingot");
        add("advancements."+ToolProgressionOverhaul.MODID+".tin_ingot.description","Obtain Tin Ingot");

        add("advancements."+ToolProgressionOverhaul.MODID+".bronze_ingot.title","Our first alloy!");
        add("advancements."+ToolProgressionOverhaul.MODID+".bronze_ingot.description","Combine Tin and Copper");

    }
}
