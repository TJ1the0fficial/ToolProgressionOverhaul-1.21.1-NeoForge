package net.almmolt.toolprogressionoverhaul.events;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrusherBlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ToolProgressionOverhaul.MODID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // This links your Crusher Block Entity Type to your custom Renderer class
        event.registerBlockEntityRenderer(
                ModBlockEntities.CRUSHER_BLOCK_ENTITY.get(),
                CrusherBlockEntityRenderer::new
        );
    }
}
