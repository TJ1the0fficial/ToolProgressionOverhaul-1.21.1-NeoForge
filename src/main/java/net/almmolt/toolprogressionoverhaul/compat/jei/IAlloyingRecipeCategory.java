package net.almmolt.toolprogressionoverhaul.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.List;

public class IAlloyingRecipeCategory implements IRecipeCategory<AlloyingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "alloying");
    public static final RecipeType<AlloyingRecipe> TYPE = new RecipeType<>(UID, AlloyingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public IAlloyingRecipeCategory(IGuiHelper helper) {
        // Points to your GUI texture
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "textures/gui/container/alloying_smelter_gui.png");

        // This "crops" your GUI texture for the JEI window.
        // Adjust these numbers (x, y, width, height) to fit your GUI's center area
        this.background = helper.createDrawable(texture, 0, 0, 176, 94);
//        this.icon = helper.createDrawableLeaf(new ItemStack(ModBlocks.ALLOYING_SMELTER.get()));
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.ALLOYING_SMELTER.get()));
    }

    @Override
    public RecipeType<AlloyingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.toolprogressionoverhaul.alloying_smelter_block");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyingRecipe recipe, IFocusGroup focuses) {
        List<SizedIngredient> ingredients = recipe.getSizedIngredients();

        // Safety checks to prevent the IndexOutOfBoundsException crash
        if (ingredients.size() >= 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 44, 23)
//                    .addSizedIngredient(ingredients.get(0)); // Note: addSizedIngredient
                    .addItemStack(ingredients.get(0).getItems()[0]);
        }

        if (ingredients.size() >= 2) {
            builder.addSlot(RecipeIngredientRole.INPUT, 80, 23)
//                    .addSizedIngredient(ingredients.get(1));
                    .addItemStack(ingredients.get(1).getItems()[0]);
        }

        if (ingredients.size() >= 3) {
            builder.addSlot(RecipeIngredientRole.INPUT, 116, 23)
//                    .addSizedIngredient(ingredients.get(2));
                    .addItemStack(ingredients.get(2).getItems()[0]);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 69)
                .addItemStack(recipe.getResultItem(null));
    }
}
