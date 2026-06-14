package net.almmolt.toolprogressionoverhaul.item.grades;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registerArmor;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registerArmorMaterial;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.registerTools;

public class Bronze {
    public static  Tier BRONZE_TIER;

    public static  AMtool.ToolSet BRONZE_TOOLS;

    public static  Holder<ArmorMaterial> BRONZE_MATERIAL;

    public static  AMarmor.ArmorSet BRONZE_ARMOR;

    public static void register() {
        // tier
        BRONZE_TIER = new SimpleTier(
                ModTags.INCORRECT_FOR_BRONZE_TOOL,
                350,
                5.0f,
                1.5f,
                8,
                () -> Ingredient.of(ModItems.BRONZE_INGOT)
        );

        // armor material
        BRONZE_MATERIAL = registerArmorMaterial(
                "bronze",ModItems.BRONZE_INGOT,8,
                2,
                5,
                4,
                3,
                4
        );

        // tools
        BRONZE_TOOLS = registerTools(
                "bronze","Bronze",BRONZE_TIER,
                5.5f,1.6f,
                4.0f,1.0f,
                3.5f,1.2f,
                9.0f,0.9f,
                1.0f,2.5f,
                4.0f,0.6f
        );

        // armor
        BRONZE_ARMOR = registerArmor(
                "bronze","Bronze",13,BRONZE_MATERIAL
        );
    }
}
