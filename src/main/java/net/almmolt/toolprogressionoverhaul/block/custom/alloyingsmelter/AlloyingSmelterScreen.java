package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlloyingSmelterScreen extends AbstractContainerScreen<AlloyingSmelterMenu> {
    public AlloyingSmelterScreen(AlloyingSmelterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    String label = "Alloying Smelter";
    int labelX = 0;
    int labelY = 0;

    // Points to 'assets/examplemod/textures/gui/container/example_container.png'
    private static final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "textures/gui/container/alloying_smelter_gui.png");
    private static final ResourceLocation ALLOYING_PROGRESS_MIDDLE_LOCATION = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "textures/gui/container/alloying_smelter_gui_alloying_progress_middle.png");
    private static final ResourceLocation ALLOYING_PROGRESS_SIDES_LOCATION = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "textures/gui/container/alloying_smelter_gui_alloying_progress_sides.png");
    private static final ResourceLocation BURN_PROGRESS = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "textures/gui/container/lit_progress.png");

    // mouseX and mouseY indicate the scaled coordinates of where the cursor is in on the screen
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        /*
         * This method is added by the container screen to render
         * the tooltip of the hovered slot.
         */

        // 2. Draw the Burning Flame (Animated from bottom up)
        // Let's say your "lit flame" is located at U: 176, V: 0 on your texture
        // and is 14x14 pixels.
        if (menu.getScaledFuel() > 0) {
            int fuelHeight = menu.getScaledFuel();

            graphics.blit(BURN_PROGRESS,
                    this.leftPos + 144, // X position on screen
                    this.topPos + 31 + (14-fuelHeight), // Y position (moves up as it fills)
                    0,(14-fuelHeight),
                    14,fuelHeight,
                    14,14
            );
        }

        // 3. Draw the Progress Arrow (Animated from left to right)
        int progressHeight = menu.getScaledProgress();
        graphics.blit(ALLOYING_PROGRESS_MIDDLE_LOCATION,
                this.leftPos + 80, // X position on screen
                this.topPos + 32, // Y position on screen
                80, // U: The X coordinate on the texture file
                32, // V: The Y coordinate on the texture file
                256, // Width (calculated scaled value)
                (int) (progressHeight / 2.2f) // Height
        );
        graphics.blit(ALLOYING_PROGRESS_SIDES_LOCATION,
                this.leftPos + 43, // X position on screen
                this.topPos + 32, // Y position on screen
                43, // U: The X coordinate on the texture file
                32, // V: The Y coordinate on the texture file
                256, // Width (calculated scaled value)
                progressHeight // Height
        );

        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        /*
         * Renders the background texture to the screen. 'leftPos' and
         * 'topPos' should already represent the top left corner of where
         * the texture should be rendered as it was precomputed from the
         * 'imageWidth' and 'imageHeight'. The two zeros represent the
         * integer u/v coordinates inside the 256 x 256 PNG file.
         */
        guiGraphics.blit(BACKGROUND_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        // Assume we have some Component 'label'
        // 'label' is drawn at 'labelX' and 'labelY'
        graphics.drawString(this.font, String.valueOf(menu.getProgress()), this.labelX+25, this.labelY+50, 0x404040);
        graphics.drawString(this.font, String.valueOf(menu.getFuelCapacity()), this.labelX+25, this.labelY+60, 0x404040);
    }

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

        // Tick things here
    }
}