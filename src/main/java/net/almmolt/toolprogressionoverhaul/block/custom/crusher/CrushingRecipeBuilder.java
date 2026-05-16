package net.almmolt.toolprogressionoverhaul.block.custom.crusher;

import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.SimpleRecipeBuilder;
import net.almmolt.toolprogressionoverhaul.tag.ModItemTagsProvider;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class CrushingRecipeBuilder extends SimpleRecipeBuilder {
    public CrushingRecipeBuilder(ItemStack result) {
        super(result);
    }

    private int duration;
    private Ingredient crusherWheel;
    private Ingredient ingredient;

    public CrushingRecipeBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public CrushingRecipeBuilder addInput(Item item) {
        this.ingredient = Ingredient.of(new ItemStack(item));
        return this;
    }

    public CrushingRecipeBuilder setWheel(Item wheel) {
        this.crusherWheel = Ingredient.of(wheel);
        return this;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        // Build the advancement.
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        // Our factory parameters are the result, the AMblock state, and the ingredient.
        CrushingRecipe recipe = new CrushingRecipe(this.duration,this.crusherWheel,this.ingredient,result);
        // Pass the id, the recipe, and the recipe advancement into the RecipeOutput.
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
