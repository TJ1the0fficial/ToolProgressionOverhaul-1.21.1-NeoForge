package net.almmolt.toolprogressionoverhaul.block.custom.crusher;

import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CrushingRecipe implements Recipe<CrushingRecipeInput> {
    public CrushingRecipe(int duration, Ingredient itemStack, Ingredient ingredient, ItemStack result) {
        this.duration = duration;
        this.crushingWheel = itemStack;
        this.ingredient = ingredient;
        this.result = result;
    }

    int duration;
    Ingredient crushingWheel;
    Ingredient ingredient;
    ItemStack result;

    public int getDuration() {
        return this.duration;
    }

    public Ingredient getCrushingWheel() {
        return this.crushingWheel;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResultItemStack() {
        return this.result;
    }

    @Override
    public boolean matches(CrushingRecipeInput input, Level level) {
        // Only test the item being crushed against the recipe ingredient
        return ingredient.test(input.inputStack()) && crushingWheel.test(input.crushingWheel());
    }

    @Override
    public ItemStack assemble(CrushingRecipeInput input, HolderLookup.Provider registries) {
        // If the internal result is somehow null, return the standard EMPTY stack instead
        if (this.result == null) {
            return ItemStack.EMPTY;
        }
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CRUSHING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CRUSHING.get();
    }
}
