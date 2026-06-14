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

public class Steel {
    public static  Tier STEEL_TIER;

    public static  AMtool.ToolSet STEEL_TOOLS;

    public static  Holder<ArmorMaterial> STEEL_MATERIAL;

    public static  AMarmor.ArmorSet STEEL_ARMOR;

    public static void register() {
        // tier
        STEEL_TIER = new SimpleTier(
                ModTags.INCORRECT_FOR_STEEL_TOOL,
                1228,
                5.5f,
                2.5f,
                14,
                () -> Ingredient.of(ModItems.STEEL_INGOT)
        );

        // armor material
        STEEL_MATERIAL = registerArmorMaterial(
                "steel",ModItems.STEEL_INGOT,20,
                3,
                8,
                6,
                3,
                5,
                1,
                0.05f
        );

        // tools
        STEEL_TOOLS = registerTools(
                "steel","Steel",STEEL_TIER,
                6.5f,1.6f,
                5.0f,1.0f,
                4.5f,1.2f,
                9.0f,0.9f,
                1.0f,2.5f,
                6.0f,0.3f
        );

        // armor
        STEEL_ARMOR = registerArmor(
                "steel","Steel",22,STEEL_MATERIAL
        );
    }
}
