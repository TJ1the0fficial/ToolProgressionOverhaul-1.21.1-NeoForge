package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registeredArmorSets;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMsimpleItem.registeredItems;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.registeredToolSets;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ToolProgressionOverhaul.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Supplier<Item> item : registeredItems.keySet()) {
            basicItem(item.get());
        }

        for (AMtool.ToolSet toolSet : registeredToolSets) {
            for (DeferredItem<? extends Item> tool : toolSet.asList()) {
                handheldItem(tool.get());
            }
        }

        handheldItem(ModItems.IRON_HAMMER.get());

        for (AMarmor.ArmorSet armorSet : registeredArmorSets) {
            basicItem(armorSet.helmetItem().get());
            basicItem(armorSet.chestplateItem().get());
            basicItem(armorSet.leggingsItem().get());
            basicItem(armorSet.bootsItem().get());
        }
    }
}
