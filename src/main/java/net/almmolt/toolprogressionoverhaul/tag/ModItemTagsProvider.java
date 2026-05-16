package net.almmolt.toolprogressionoverhaul.tag;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMsimpleItem;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registeredArmorSets;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.registeredToolSets;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (DeferredHolder<Item, ? extends Item> item : ModItems.ITEMS.getEntries()) {
            tag(ModTags.TPO_ITEMS).add(item.get());
        }

        for (AMtool.ToolSet toolSet : registeredToolSets) {
            tag(Tags.Items.MINING_TOOL_TOOLS)
                    .add(toolSet.shovelItem().get())
                    .add(toolSet.pickaxeItem().get())
                    .add(toolSet.axeItem().get())
                    .add(toolSet.hoeItem().get())
                    .add(toolSet.hammerItem().get());
            tag(Tags.Items.MELEE_WEAPON_TOOLS)
                    .add(toolSet.swordItem().get())
                    .add(toolSet.axeItem().get());
            tag(ModTags.TPO_ITEMS)
                    .add(toolSet.swordItem().get())
                    .add(toolSet.shovelItem().get())
                    .add(toolSet.pickaxeItem().get())
                    .add(toolSet.axeItem().get())
                    .add(toolSet.hoeItem().get())
                    .add(toolSet.hammerItem().get());
            tag(ModTags.HAMMERS)
                    .add(toolSet.hammerItem().get());
            tag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                    .add(toolSet.swordItem().get())
                    .add(toolSet.axeItem().get());
            tag(ItemTags.DURABILITY_ENCHANTABLE)
                    .add(toolSet.pickaxeItem().get())
                    .add(toolSet.shovelItem().get())
                    .add(toolSet.axeItem().get())
                    .add(toolSet.hammerItem().get());
            tag(ItemTags.VANISHING_ENCHANTABLE)
                    .add(toolSet.swordItem().get())
                    .add(toolSet.shovelItem().get())
                    .add(toolSet.pickaxeItem().get())
                    .add(toolSet.axeItem().get())
                    .add(toolSet.hoeItem().get())
                    .add(toolSet.hammerItem().get());
            tag(ItemTags.MINING_ENCHANTABLE)
                    .add(toolSet.shovelItem().get())
                    .add(toolSet.pickaxeItem().get())
                    .add(toolSet.axeItem().get())
                    .add(toolSet.hoeItem().get())
                    .add(toolSet.hammerItem().get());
            tag(ItemTags.SWORD_ENCHANTABLE)
                    .add(toolSet.swordItem().get())
                    .add(toolSet.axeItem().get());
            tag(ItemTags.WEAPON_ENCHANTABLE)
                    .add(toolSet.swordItem().get())
                    .add(toolSet.axeItem().get());
        }
        for (AMarmor.ArmorSet armorSet : registeredArmorSets) {
            for (int i = 0; i < armorSet.asList().size(); ++i) {
                // all
                tag(Tags.Items.ARMORS).add(armorSet.asList().get(i).get());
                tag(ItemTags.DURABILITY_ENCHANTABLE).add(armorSet.asList().get(i).get());
                tag(ItemTags.VANISHING_ENCHANTABLE).add(armorSet.asList().get(i).get());
                tag(ItemTags.ARMOR_ENCHANTABLE).add(armorSet.asList().get(i).get());
                // helmet
                tag(ItemTags.HEAD_ARMOR).add(armorSet.helmetItem().get());
                tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(armorSet.helmetItem().get());
                // chestplate
                tag(ItemTags.CHEST_ARMOR).add(armorSet.chestplateItem().get());
                tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(armorSet.chestplateItem().get());
                // leggings
                tag(ItemTags.LEG_ARMOR).add(armorSet.leggingsItem().get());
                tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(armorSet.leggingsItem().get());
                // boots
                tag(ItemTags.FOOT_ARMOR).add(armorSet.bootsItem().get());
                tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(armorSet.bootsItem().get());
            }
        }

        for (Supplier<Item> wheel : AMsimpleItem.registeredWheels.keySet()) {
            tag(ModTags.CRUSHING_WHEELS).add(wheel.get());
        }

        tag(ItemTags.PICKAXES)
                .add(ModItems.STONE_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get());
        tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(ModItems.STONE_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get());
        tag(ModTags.TPO_ITEMS)
                .add(ModItems.STONE_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get());
        tag(ModTags.HAMMERS)
                .add(ModItems.STONE_HAMMER.get())
                .add(ModItems.IRON_HAMMER.get());

        tag(ModTags.INCORRECT_FOR_CRUSHER)
                .addTag(ModTags.CRUSHING_WHEELS);

        tag(ModTags.STONES)
                .add(Items.STONE)
                .add(Items.BLACKSTONE)
                .add(Items.SMOOTH_BASALT)
                .add(Items.RED_SANDSTONE)
                .add(Items.SANDSTONE)
                .add(Items.POLISHED_ANDESITE)
                .add(Items.POLISHED_DIORITE)
                .add(Items.POLISHED_GRANITE);
    }
}
