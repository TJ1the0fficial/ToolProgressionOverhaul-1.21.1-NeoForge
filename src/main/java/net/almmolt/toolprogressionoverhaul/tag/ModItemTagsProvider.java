package net.almmolt.toolprogressionoverhaul.tag;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (DeferredHolder<Item, ? extends Item> item : ModItems.ITEMS.getEntries()) {
            tag(ModTags.TPO_ITEMS).add(item.get());
        }

        tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(ModItems.BRONZE_SHOVEL.get())
                .add(ModItems.BRONZE_PICKAXE.get())
                .add(ModItems.BRONZE_AXE.get())
                .add(ModItems.BRONZE_HOE.get());
        tag(Tags.Items.MELEE_WEAPON_TOOLS)
                .add(ModItems.BRONZE_SWORD.get())
                .add(ModItems.BRONZE_AXE.get());
        tag(Tags.Items.ARMORS)
                .add(ModItems.BRONZE_HELMET.get())
                .add(ModItems.BRONZE_CHESTPLATE.get())
                .add(ModItems.BRONZE_LEGGINGS.get())
                .add(ModItems.BRONZE_BOOTS.get());
    }
}
