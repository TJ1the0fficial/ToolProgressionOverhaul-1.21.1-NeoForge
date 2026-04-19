package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.AdvancementProvider;
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
        addBlock(ModBlocks.DEEPSLATE_TIN_ORE, "Deepslate Tin Ore");
        addBlock(ModBlocks.TIN_BLOCK, "Block Of Tin");
        addBlock(ModBlocks.RAW_TIN_BLOCK, "Block Of Raw Tin");
        addBlock(ModBlocks.BRONZE_BLOCK, "Block of Bronze");
        addBlock(ModBlocks.ALLOYING_SMELTER, "Alloying Smelter");

        // Advancements
        add(setId_T("road_to_growth"),"Road To Growth");
        add(setId_D("road_to_growth"),"Smelt Raw Tin!");

        add(setId_T("tin_ingot"),"Obtained Tin Ingot");
        add(setId_D("tin_ingot"),"Obtain Tin Ingot.");

        add(setId_T("bronze_ingot"),"Our first alloy!");
        add(setId_D("bronze_ingot"),"Combine Tin and Copper. 3 Copper and 1 Tin");
    }

    public static String setId_T(String title) {
        return "advancements."+ToolProgressionOverhaul.MODID+"."+title+".title";
    }
    public static String setId_D(String description) {
        return "advancements."+ToolProgressionOverhaul.MODID+"."+description+".description";
    }
}
