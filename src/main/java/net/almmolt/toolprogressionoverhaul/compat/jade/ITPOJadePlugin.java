package net.almmolt.toolprogressionoverhaul.compat.jade;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrusherBlockEntity;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrusherEntityBlock;
import net.almmolt.toolprogressionoverhaul.item.grades.Invar;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.addon.harvest.HarvestToolProvider;
import snownee.jade.addon.harvest.ToolHandler;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import java.util.List;

@WailaPlugin
public class ITPOJadePlugin implements IWailaPlugin {
    static ResourceLocation CRUSHER_PROGRESS = ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"crusher_progress");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(ICrusherToolTip.INSTANCE, CrusherBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(ICrusherToolTip.INSTANCE, CrusherEntityBlock.class);

        // Call the fix here, on the client side!
        registerPickaxes();
    }

    public static void registerPickaxes() {
        // 1. Find Jade's internal Pickaxe handler
        ToolHandler handler = HarvestToolProvider.TOOL_HANDLERS.get(ResourceLocation.parse("jade:pickaxe"));

        if (handler != null) {
            // 2. Get the list directly
            List<ItemStack> jadeTools = handler.getTools();

            // 3. ADD to the existing list. NEVER do "jadeTools = List.of(...)"
            for (AMtool.ToolSet toolSet : AMtool.registeredToolSets) {
                ItemStack stack = toolSet.pickaxeItem().get().getDefaultInstance();
                // Check to avoid duplicates
                if (jadeTools.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                    jadeTools.add(stack);
                }
            }
            System.out.println("TPO Jade: Custom pickaxes added to Jade's registry.");
        }
    }
}
