package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMblock;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registeredArmorSets;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMblock.registeredBlocks;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMsimpleItem.registeredItems;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.registeredToolSets;

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
        for (Supplier<Item> item : registeredItems.keySet()) {
            addItem(item, registeredItems.get(item));
        }

        // ModItemset
        for (AMtool.ToolSet toolSet : registeredToolSets) {
            addItem(toolSet.swordItem(), toolSet.swordDisplayName());
            addItem(toolSet.shovelItem(), toolSet.shovelDisplayName());
            addItem(toolSet.pickaxeItem(), toolSet.pickaxeDisplayName());
            addItem(toolSet.axeItem(), toolSet.axeDisplayName());
            addItem(toolSet.hoeItem(), toolSet.hoeDisplayName());
            addItem(toolSet.hammerItem(), toolSet.hammerDisplayName());
        }
        addItem(ModItems.IRON_HAMMER, "Iron Hammer");

        // - Armors
        for (AMarmor.ArmorSet armorSet : registeredArmorSets) {
            addItem(armorSet.helmetItem(),armorSet.helmetDisplayName());
            addItem(armorSet.chestplateItem(),armorSet.chestplateDisplayName());
            addItem(armorSet.leggingsItem(),armorSet.leggingsDisplayName());
            addItem(armorSet.bootsItem(),armorSet.leggingsDisplayName());
        }

        // Blocks
        for (AMblock.BlockSet blockSet : registeredBlocks) {
            addBlock(blockSet.block(),blockSet.blockDisplayName());
        }
        addBlock(ModBlocks.ALLOYING_SMELTER, "Alloying Smelter");
        addBlock(ModBlocks.CRUSHER, "Crusher");

        // Advancements
        add(setId_T("road_to_growth"),"Road To Growth");
        add(setId_D("road_to_growth"),"Smelt Raw Tin!");

        add(setId_T("tin_ingot"),"Obtained Tin Ingot");
        add(setId_D("tin_ingot"),"Obtain Tin Ingot.");

        add(setId_T("bronze_ingot"),"Our first alloy!");
        add(setId_D("bronze_ingot"),"Combine Tin and Copper. 3 Copper and 1 Tin");

        add(setId_T("bronze_hammer"),"Hammering Time!");
        add(setId_D("bronze_hammer"),"Craft dust forms of materials with the Hammer to double the gain! Hammers are also capable of mining in a 3x3 tile radius!");

        add(setId_T("alloying_smelter"),"Now let's get to Alloying!");
        add(setId_D("alloying_smelter"),"Craft an Alloying Smelter.");

        add(setId_T("iron_ingot"),"Just to show the way.");
        add(setId_D("iron_ingot"),"Smelt or Blast Raw Iron or use a Hammer to crush it down to dust, then smelt that.");

        add(setId_T("silver_ingot"),"Terror of the Undead.");
        add(setId_D("silver_ingot"),"Craft silver tools to terrorize undead mobs! Craft silver armor to face them even tougher!");

        add(setId_T("raw_nickel"),"Use of Hammers.");
        add(setId_D("raw_nickel"),"Use the hammers to crush it down. Or smelt it and make pretty block out of it!");

        add(setId_T("invar_ingot"),"Slower, but Stronger!");
        add(setId_D("invar_ingot"),"Invar is as slow as the Stone, but more than two times more durable than Iron!");

        add(setId_T("crusher"),"Crush, crush, CRUSH!");
        add(setId_D("crusher"),"Drop your materials above or channel it through a hopper and you can crush your materials with ease!");

        add(setId_T("crusher_wheels"),"Core component.");
        add(setId_D("crusher_wheels"),"You have to place it in a Crusher. You have to keep in mind that not everything can be crushed with every crusher wheel!");
    }

    public static String setId_T(String title) {
        return "advancements."+ToolProgressionOverhaul.MODID+"."+title+".title";
    }
    public static String setId_D(String description) {
        return "advancements."+ToolProgressionOverhaul.MODID+"."+description+".description";
    }

    @Override
    public void addItem(Supplier<? extends Item> key, String name) {
        super.addItem(key, name);
    }
}
