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

public class Invar {
    public static  Tier INVAR_TIER;
    public static  Holder<ArmorMaterial> INVAR_MATERIAL;
    public static  AMtool.ToolSet INVAR_TOOLS;
    public static  AMarmor.ArmorSet INVAR_ARMOR;

    public static void register() {
        // tier
        INVAR_TIER = new SimpleTier(
                ModTags.INCORRECT_FOR_INVAR_TOOL,
                600,
                4.0f,
                1.0f,
                8,
                () -> Ingredient.of(ModItems.INVAR_INGOT)
        );

        // armor material
        INVAR_MATERIAL = registerArmorMaterial(
                "invar",ModItems.INVAR_INGOT,8,
                3,
                5,
                4,
                2,
                6
        );

        // tools
        INVAR_TOOLS = registerTools(
                "invar","Invar",INVAR_TIER,
                5.0f,1.6f,
                5.0f,1.0f,
                4.0f,1.2f,
                9.0f,0.9f,
                2.0f,2.5f,
                3.0f,0.6f
        );

        // armor
        INVAR_ARMOR = registerArmor(
                "invar","Invar",22,INVAR_MATERIAL
        );
    }
}
