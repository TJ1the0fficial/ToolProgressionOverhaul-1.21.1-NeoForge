package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlloyingSmelterScreen extends AbstractContainerScreen<AlloyingSmelterMenu> {
    // Change the first parameter from AbstractContainerMenu to AlloyingSmelterMenu
    public AlloyingSmelterScreen(AlloyingSmelterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    // Points to 'assets/examplemod/textures/gui/container/example_container.png'
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            ToolProgressionOverhaul.MODID, "textures/gui/container/alloying_smelter_gui.png"
    );

    // In some Screen subclass

    // mouseX and mouseY indicate the scaled coordinates of where the cursor is in on the screen
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    // In some AbstractContainerScreen subclass
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // super.renderLabels draws the default titles automatically
        super.renderLabels(graphics, mouseX, mouseY);

        // If you want to draw a custom string:
        graphics.drawString(this.font, this.title, 8, 6, 0x404040);
    }

    // In some AbstractContainerScreen subclass
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);

        /*
         * This method is added by the container screen to render
         * the tooltip of the hovered slot.
         */
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    // In some Screen subclass

    @Override
    public void onClose() {
        // Stop any handlers here

        // Call last in case it interferes with the override
        super.onClose();
    }

    @Override
    public void removed() {
        // Reset initial states here

        // Call last in case it interferes with the override
        super.removed();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }
}
