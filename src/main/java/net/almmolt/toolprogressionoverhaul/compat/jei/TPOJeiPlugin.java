package net.almmolt.toolprogressionoverhaul.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingRecipe;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe;
import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes; // Your recipe type registry
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class TPOJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new IAlloyingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ICrusherRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Minecraft.getInstance().level.getRecipeManager();

        // Find all your Alloying recipes
        List<AlloyingRecipe> recipesAlloying = rm.getAllRecipesFor(ModRecipes.ALLOYING.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();

        // Register the recipes to your custom category
        registration.addRecipes(IAlloyingRecipeCategory.TYPE, recipesAlloying);


        // Find all your Crusher recipes
        List<CrushingRecipe> recipesCrushing = rm.getAllRecipesFor(ModRecipes.CRUSHING.get())
                .stream()
                .map(RecipeHolder::value)
                .toList();

        // Register the recipes to your custom category
        registration.addRecipes(ICrusherRecipeCategory.TYPE, recipesCrushing);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // This allows players to click your Block in JEI to see the recipes it can make
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ALLOYING_SMELTER.get()), IAlloyingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CRUSHER.get()), ICrusherRecipeCategory.TYPE);
    }
}