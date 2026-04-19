package net.almmolt.toolprogressionoverhaul.recipe;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ToolProgressionOverhaul.MODID);

//<RecipeHolder<? extends Recipe<CraftingInput>>>
    public static final Supplier<RecipeType<AlloyingSmelterRecipe>> ALLOYING_SMELTER_RECIPE =
            RECIPE_TYPES.register(
                    "right_click_block",
                    // We need the qualifying generic here due to generics being generics.
                    () -> RecipeType.<AlloyingSmelterRecipe>simple(ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "alloying_recipe_type")
            )
    );
}
