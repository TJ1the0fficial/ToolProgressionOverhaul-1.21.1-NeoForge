package net.almmolt.toolprogressionoverhaul.events;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

@EventBusSubscriber(modid = ToolProgressionOverhaul.MODID)
public class ModComponentModifier {

    @SubscribeEvent
    public static void modifyComponents(ModifyDefaultComponentsEvent event) {
        // The new durability value you want
        int newGoldDurability = 216;

        // Modify specific gold tools
        event.modify(Items.GOLDEN_PICKAXE, builder -> builder.set(DataComponents.MAX_DAMAGE, newGoldDurability));
        event.modify(Items.GOLDEN_AXE, builder -> builder.set(DataComponents.MAX_DAMAGE, newGoldDurability));
        event.modify(Items.GOLDEN_SHOVEL, builder -> builder.set(DataComponents.MAX_DAMAGE, newGoldDurability));
        event.modify(Items.GOLDEN_HOE, builder -> builder.set(DataComponents.MAX_DAMAGE, newGoldDurability));
        event.modify(Items.GOLDEN_SWORD, builder -> builder.set(DataComponents.MAX_DAMAGE, newGoldDurability));
    }
}
