package net.almmolt.toolprogressionoverhaul.block.custom.crusher;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Matrix4f;

public class CrusherBlockEntityRenderer implements BlockEntityRenderer<CrusherBlockEntity> {

    public CrusherBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CrusherBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack wheel = be.inventory.getStackInSlot(0);
        if (wheel.isEmpty()) return;

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(getTextureForItem(wheel.getItem()));

        // --- CORRECTED ANIMATION LOGIC ---
        int totalFrames = 6;
        int frameIndex = 0;

        boolean isWorking = be.getBlockState().getValue(CrusherEntityBlock.WORKING);

        if (isWorking) {
            // Speed: 2 ticks per frame. Frames: 0 to 5.
            frameIndex = (int) (be.getLevel().getGameTime() / 2) % 6;
        }

        // 1. Get the actual height of the sprite in the atlas
        float totalVHeight = sprite.getV1() - sprite.getV0();
        float frameHeightUV = totalVHeight / totalFrames;

        // 2. Calculate UVs based on the current frame index
        float minV = sprite.getV0() + (frameIndex * frameHeightUV);
        float maxV = minV + frameHeightUV;
        // ---------------------------------

        poseStack.pushPose();
        poseStack.translate(0.5, 1.005, 0.5);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        Direction facing = be.getBlockState().getValue(CrusherEntityBlock.FACING);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-facing.toYRot()));

        VertexConsumer consumer = buffer.getBuffer(RenderType.cutout());
        Matrix4f matrix = poseStack.last().pose();
        float s = 0.45f;

        // Use the calculated minV and maxV
        renderVertex(consumer, matrix, -s, -s, 0, sprite.getU0(), minV, 15728880, combinedOverlay);
        renderVertex(consumer, matrix, -s,  s, 0, sprite.getU0(), maxV, 15728880, combinedOverlay);
        renderVertex(consumer, matrix,  s,  s, 0, sprite.getU1(), maxV, 15728880, combinedOverlay);
        renderVertex(consumer, matrix,  s, -s, 0, sprite.getU1(), minV, 15728880, combinedOverlay);

        poseStack.popPose();
    }

    private void renderVertex(VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, float u, float v, int light, int overlay) {
        consumer.addVertex(matrix, x, y, z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(overlay)
                .setLight(light)
                .setNormal(0, 0, 1); // Points "Up" relative to the flat quad
    }

    private ResourceLocation getTextureForItem(Item item) {
        if (item == null || item == Items.AIR) {
            return ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/iron_crusher_wheel_top");
        }

        String itemId = BuiltInRegistries.ITEM.getKey(item).getPath();

        // No extension needed for the Atlas, but ensure your blocks.json atlas includes ALL these IDs
        return ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "block/" + itemId + "_top");
    }
}
