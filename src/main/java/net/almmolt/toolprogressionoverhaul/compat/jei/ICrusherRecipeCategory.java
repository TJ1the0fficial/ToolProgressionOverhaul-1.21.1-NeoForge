package net.almmolt.toolprogressionoverhaul.compat.jei;

import com.mojang.math.Axis;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import org.jetbrains.annotations.Nullable;

public class ICrusherRecipeCategory implements IRecipeCategory<CrushingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "crushing");
    public static final RecipeType<CrushingRecipe> TYPE = new RecipeType<>(UID, CrushingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ICrusherRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(160, 90);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModBlocks.CRUSHER.get()));
    }

    @Override
    public void draw(CrushingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // 1. Draw the Base Crusher Block
        guiGraphics.pose().pushPose();

        // We keep the AMblock at a low Z (50) so it's safely in the background
        guiGraphics.pose().translate(64, 25, 50);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);

        guiGraphics.renderFakeItem(new ItemStack(ModBlocks.CRUSHER.get()), 0, 0);

        // 2. Prepare the Overlay Position
        // We use a modest Z (100). This is higher than the AMblock (50)
        // but much lower than the Tooltip (400).
        guiGraphics.pose().translate(8.0f, 3.5f, 100.0f);

        // 3. The Tilt and Rotation (XP 60, ZP 45)
        guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(60));
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(45));
        guiGraphics.pose().scale(0.6f, 0.6f, 0.6f);

        // 4. THE MAGIC: Disable Depth Testing
        // This forces the next thing drawn to be on TOP of what is already there,
        // even if it's "physically" behind it.
        com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();

        // 5. Draw the Wheel
        int frameIndex = (int) (System.currentTimeMillis() / 150) % 6;
        int vOffset = frameIndex * 16;
        ResourceLocation texture = getTextureForItem(recipe.getCrushingWheel().getItems()[0].getItem());

        guiGraphics.blit(texture, -8, -8, 0, 0, vOffset, 16, 16, 16, 96);

        // 6. RE-ENABLE Depth Testing
        // IMPORTANT: You must turn it back on, otherwise the rest of the
        // Minecraft UI (and tooltips) will break!
        com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();

        guiGraphics.pose().popPose();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CrushingRecipe recipe, IFocusGroup focuses) {
        // Input Slot: Place it "above" where you drew the 3D AMblock
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 10)
                .addIngredients(recipe.getIngredient());

        builder.addSlot(RecipeIngredientRole.INPUT, 64+32, 10)
                .addIngredients(recipe.getCrushingWheel());

        // Output Slot: Place it "below" or to the side
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64+8, 60)
                .addItemStack(recipe.getResultItemStack());
    }

    @Override
    public IDrawable getBackground() {
        return this.background; // JEI needs this to know the area size
    }

    @Override
    public RecipeType<CrushingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Crusher");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    private ResourceLocation getTextureForItem(Item item) {
        if (item == null || item == Items.AIR) {
            return ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "textures/block/iron_crusher_wheel_top.png");
        }

        String itemId = BuiltInRegistries.ITEM.getKey(item).getPath();

        // Notice: We added "textures/" and ".png"
        return ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "textures/block/" + itemId + "_top.png");
    }
}
