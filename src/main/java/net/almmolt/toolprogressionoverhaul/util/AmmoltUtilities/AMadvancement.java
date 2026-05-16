package net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class AMadvancement {
    public static AdvancementHolder createTask(
            Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, // neccesary
            String name,
            Item item,
            AdvancementHolder parent
    ) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        // Handle Parent Logic
        if (parent != null) {
            builder.parent(parent);
        } else {
            // Note: If 'story/root' is NOT a real file yet, use .addCriterion and display
            // settings on this advancement to make IT the root.
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"textures/block/bronze_block.png"), // Root needs a background
                    AdvancementType.TASK,
                    true, true, false
            );
        }

        // Display settings (only if not already set by root logic above)
        if (parent != null) {
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"textures/block/bronze_block.png"),
                    AdvancementType.TASK,
                    true, true, false
            );
        }

        builder.rewards(AdvancementRewards.Builder.experience(100));

        // Use a proper trigger - using Items.DIRT is fine for testing, but
        // usually you'd want 'has_item' for the item passed in the parameters
        builder.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item));

        // BUILD AND SAVE
        // builder.save returns the AdvancementHolder automatically!
        // No need for final arrays or double calling.
        return builder.save(saver, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, name), existingFileHelper);
    }

    public static AdvancementHolder createGoal(
            Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, // neccesary
            String name,
            Item item,
            AdvancementHolder parent
    ) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        // Handle Parent Logic
        if (parent != null) {
            builder.parent(parent);
        } else {
            // Note: If 'story/root' is NOT a real file yet, use .addCriterion and display
            // settings on this advancement to make IT the root.
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"textures/block/bronze_block"), // Root needs a background
                    AdvancementType.GOAL,
                    true, true, false
            );
        }

        // Display settings (only if not already set by root logic above)
        if (parent != null) {
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"textures/block/bronze_block"),
                    AdvancementType.GOAL,
                    true, true, false
            );
        }

        builder.rewards(AdvancementRewards.Builder.experience(100));

        // Use a proper trigger - using Items.DIRT is fine for testing, but
        // usually you'd want 'has_item' for the item passed in the parameters
        builder.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item));

        // BUILD AND SAVE
        // builder.save returns the AdvancementHolder automatically!
        // No need for final arrays or double calling.
        return builder.save(saver, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, name), existingFileHelper);
    }

    public static AdvancementHolder createChallenge(
            Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, // neccesary
            String name,
            Item item,
            AdvancementHolder parent
    ) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        // Handle Parent Logic
        if (parent != null) {
            builder.parent(parent);
        } else {
            // Note: If 'story/root' is NOT a real file yet, use .addCriterion and display
            // settings on this advancement to make IT the root.
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"textures/block/bronze_block"), // Root needs a background
                    AdvancementType.CHALLENGE,
                    true, true, false
            );
        }

        // Display settings (only if not already set by root logic above)
        if (parent != null) {
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"textures/block/bronze_block"),
                    AdvancementType.CHALLENGE,
                    true, true, false
            );
        }

        builder.rewards(AdvancementRewards.Builder.experience(100));

        // Use a proper trigger - using Items.DIRT is fine for testing, but
        // usually you'd want 'has_item' for the item passed in the parameters
        builder.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item));

        // BUILD AND SAVE
        // builder.save returns the AdvancementHolder automatically!
        // No need for final arrays or double calling.
        return builder.save(saver, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, name), existingFileHelper);
    }
}
