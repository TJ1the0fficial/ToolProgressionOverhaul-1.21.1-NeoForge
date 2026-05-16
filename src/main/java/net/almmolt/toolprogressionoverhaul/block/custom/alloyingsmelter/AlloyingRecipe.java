package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlloyingRecipe implements Recipe<AlloyingRecipeInput> {
    // An in-code representation of our recipe data. This can be basically anything you want.
    // Common things to have here is a processing time integer of some kind, or an experience reward.
    // Note that we now use an ingredient instead of an item stack for the input.
    private final List<SizedIngredient> ingredients;
    private final ItemStack result;
    private final int duration;
    private final int experience;

    // Add a constructor that sets all properties.
    public AlloyingRecipe(ItemStack result,int duration, int experience,List<SizedIngredient> ingredients) {
        this.ingredients = ingredients;
        this.result = result;
        this.duration = duration;
        this.experience = experience;
    }

    // A list of our ingredients. Does not need to be overridden if you have no ingredients
    // (the default implementation returns an empty list here). It makes sense to cache larger lists in a field.
//    @Override
//    public NonNullList<Ingredient> getIngredients() {
//        return getInputItems();
//    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALLOYING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALLOYING.get();
    }

//    default NonNullList<Ingredient> getIngredients() {
//        return NonNullList.create();
//    }


    public List<SizedIngredient> getSizedIngredients() {
        return this.ingredients;
    }

    // Grid-based recipes should return whether their recipe can fit in the given dimensions.
    // We don't have a grid, so we just return if any item can be placed in there.
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    // Check whether the given input matches this recipe. The first parameter matches the generic.
    // We check our blockstate and our item stack, and only return true if both match.
    @Override
    public boolean matches(AlloyingRecipeInput input, Level level) { // AI wrote this code, cause I couldn't check whether in any combination a match is found or not
        // 1. Collect all non-empty items from the input slots
        List<ItemStack> inputItems = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                inputItems.add(stack);
            }
        }

        // 2. If the number of items provided doesn't match the number of ingredients, it's not a match
        // (This prevents 1 item matching a 2-item recipe, or extra "junk" items being present)
        if (inputItems.size() != this.ingredients.size()) {
            return false;
        }

        // 3. For every ingredient in the recipe...
        for (SizedIngredient ingredient : this.ingredients) {
            boolean matched = false;

            // ...search the input items for a match
            for (int i = 0; i < inputItems.size(); i++) {
                if (ingredient.test(inputItems.get(i))) {
                    // Found a match! Remove this item so it can't be used for the NEXT ingredient
                    inputItems.remove(i);
                    matched = true;
                    break;
                }
            }

            // If an ingredient found no matching item, the recipe fails
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    // Return an UNMODIFIABLE version of your result here. The result of this method is mainly intended
    // for the recipe book, and commonly used by JEI and other recipe viewers as well.
    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getExperience() {
        return this.experience;
    }

    // Return the result of the recipe here, based on the given input. The first parameter matches the generic.
    // IMPORTANT: Always call .copy() if you use an existing result! If you don't, things can and will break,
    // as the result exists once per recipe, but the assembled stack is created each time the recipe is crafted.
//    @Override
    public ItemStack assemble(AlloyingRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    public List<SizedIngredient> getInputItems() {
        return this.ingredients;
    }

    public ItemStack getResultItemStack() {
        return this.result;
    }

    // This example outlines the most important methods. There is a number of other methods to override.
    // Check the class definition of Recipe to view them all.
}
