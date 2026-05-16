package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.List;

public class AlloyingRecipeBuilder extends SimpleRecipeBuilder {
    private final List<SizedIngredient> ingredients = new ArrayList<>();
    private int duration = 200;
    private int experience = 10;

    // Since we have exactly one of each input, we pass them to the constructor.
    // Builders for recipe serializers that have ingredient lists of some sort would usually
    // initialize an empty list and have #addIngredient or similar methods instead.
    public AlloyingRecipeBuilder(ItemStack result) {
        super(result);
    }

    public AlloyingRecipeBuilder addInput(ItemLike item, int count) {
        this.ingredients.add(SizedIngredient.of(item, count));
        return this; // Returns itself so you can chain it
    }

    public AlloyingRecipeBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public AlloyingRecipeBuilder setExperience(int experience) {
        this.experience = experience;
        return this;
    }

    // Saves a recipe using the given RecipeOutput and id. This method is defined in the RecipeBuilder interface.
    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        // Build the advancement.
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        // Our factory parameters are the result, the AMblock state, and the ingredient.
        AlloyingRecipe recipe = new AlloyingRecipe(result,this.duration,this.experience,this.ingredients);
        // Pass the id, the recipe, and the recipe advancement into the RecipeOutput.
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
