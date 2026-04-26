package net.almmolt.toolprogressionoverhaul.recipe;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ToolProgressionOverhaul.MODID);
}
