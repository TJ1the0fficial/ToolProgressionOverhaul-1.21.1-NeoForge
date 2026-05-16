package net.almmolt.toolprogressionoverhaul.recipe;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingRecipe;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingRecipeSerializer;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipeSerializer;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    // recipes
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ToolProgressionOverhaul.MODID);

    public static final Supplier<RecipeType<AlloyingRecipe>> ALLOYING =
            RECIPE_TYPES.register(
                    "alloying_recipe",
                    // We need the qualifying generic here due to generics being generics.
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "alloying"))
            );
    public static final Supplier<RecipeType<CrushingRecipe>> CRUSHING =
            RECIPE_TYPES.register(
                    "crushing_recipe",
                    () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "crushing"))
            );

    // serializers
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ToolProgressionOverhaul.MODID);

    public static final Supplier<RecipeSerializer<AlloyingRecipe>> ALLOYING_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("alloying", AlloyingRecipeSerializer::new);

    public static final Supplier<RecipeSerializer<CrushingRecipe>> CRUSHING_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("crushing", CrushingRecipeSerializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
